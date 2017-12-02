package com.cloud.resources.x5;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.cloud.core.ObjectJudge;
import com.cloud.core.enums.DialogButtonsEnum;
import com.cloud.core.enums.RuleParams;
import com.cloud.core.logger.Logger;
import com.cloud.core.utils.ConvertUtils;
import com.cloud.core.utils.PixelUtils;
import com.cloud.core.utils.ValidUtils;
import com.cloud.resources.R;
import com.cloud.resources.dialog.BaseMessageBox;
import com.cloud.resources.enums.MsgBoxClickButtonEnum;
import com.tencent.smtt.export.external.interfaces.GeolocationPermissionsCallback;
import com.tencent.smtt.export.external.interfaces.JsPromptResult;
import com.tencent.smtt.export.external.interfaces.JsResult;
import com.tencent.smtt.export.external.interfaces.SslError;
import com.tencent.smtt.export.external.interfaces.SslErrorHandler;
import com.tencent.smtt.sdk.CookieSyncManager;
import com.tencent.smtt.sdk.DownloadListener;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import java.io.File;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2017/11/17
 * @Description:
 * @Modifier:
 * @ModifyContent:
 */
public class BaseWebLoad extends RelativeLayout {

    private ProgressBar progressBar = null;
    private WebView webView = null;
    private boolean isParseError = false;

    public BaseWebLoad(Context context, AttributeSet attrs) {
        super(context, attrs);
        View view = View.inflate(context, R.layout.x5_web_view, null);
        RelativeLayout.LayoutParams vparam = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        this.addView(view, vparam);
        onPreCreated(context);
        init();
        initListener();
    }

    public void initializa(Activity activity) {
        if (activity == null) {
            return;
        }
        Window window = activity.getWindow();
        if (window != null) {
            window.setFormat(PixelFormat.TRANSLUCENT);
            if (Build.VERSION.SDK_INT >= 11) {
                window.setFlags(
                        android.view.WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
                        android.view.WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED
                );
            }
        }
    }

    private void init() {
        try {
            progressBar = (ProgressBar) findViewById(R.id.rx_web_pbar);
            progressBar.setMax(100);
            progressBar.setProgress(0);
            Drawable mdrawable = progressBar.getResources().getDrawable(R.drawable.progressbar_style);
            progressBar.setProgressDrawable(mdrawable);
            webView = (WebView) findViewById(R.id.rx_web_x5wv);
            webView.requestFocus();
            webView.setVerticalScrollbarOverlay(false);
            webView.setHorizontalScrollbarOverlay(false);
            webView.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);
            JavascriptInterfaceItem[] javascriptInterfaces = getJavascriptInterfaces();
            if (!ObjectJudge.isNullOrEmpty(javascriptInterfaces)) {
                for (JavascriptInterfaceItem anInterface : javascriptInterfaces) {
                    webView.addJavascriptInterface(anInterface.getJavascriptInterface(), anInterface.getJsInterfaceName());
                }
            }
            webView.removeJavascriptInterface("searchBoxJavaBridge_");
            webView.removeJavascriptInterface("accessibilityTra");
            webView.removeJavascriptInterface("accessibility");
            initSetting();
        } catch (Exception e) {
            Logger.L.error(e);
        }
    }

    private void initSetting() {
        try {
            WebSettings settings = webView.getSettings();
            if (settings != null) {
                settings.setJavaScriptEnabled(true);
                settings.setJavaScriptCanOpenWindowsAutomatically(true);
                settings.setAllowFileAccess(true);
                settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
                settings.setSupportZoom(true);
                settings.setBuiltInZoomControls(true);
                settings.setUseWideViewPort(true);
                settings.setSupportMultipleWindows(true);
                settings.setLoadWithOverviewMode(true);
                settings.setAppCacheEnabled(true);
                settings.setDatabaseEnabled(true);
                settings.setDomStorageEnabled(true);
                settings.setGeolocationEnabled(true);
                settings.setAppCacheMaxSize(Long.MAX_VALUE);
                settings.setPluginState(WebSettings.PluginState.ON_DEMAND);
                settings.setDefaultZoom(WebSettings.ZoomDensity.MEDIUM);
                settings.setDefaultTextEncodingName("utf-8");
                File database = this.getContext().getDir("database", Context.MODE_PRIVATE);
                if (database != null) {
                    settings.setDatabasePath(database.getPath());
                }
                File geolocation = this.getContext().getDir("geolocation", Context.MODE_APPEND);
                if (geolocation != null) {
                    settings.setGeolocationDatabasePath(geolocation.getPath());
                }
                File appcache = this.getContext().getDir("appcache", Context.MODE_PRIVATE);
                if (appcache != null) {
                    settings.setAppCachePath(appcache.getPath());
                }
                settings.setBlockNetworkImage(false);
                settings.setLoadsImagesAutomatically(true);
                settings.setSavePassword(true);
            }
            CookieSyncManager.createInstance(getContext());
            CookieSyncManager.getInstance().sync();
            webView.setClickable(true);
        } catch (Exception e) {
            Logger.L.error(e);
        }
    }

    private void initListener() {
        if (webView == null) {
            return;
        }
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView webView, String url) {
                return onOverrideUrlLoading(webView, url);
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                isParseError = true;
                onLoadFinished(view, false, errorCode, description, failingUrl);
            }

            @Override
            public void onReceivedSslError(WebView webView, SslErrorHandler sslErrorHandler, SslError sslError) {
                sslErrorHandler.proceed();
            }

            @Override
            public void onPageStarted(WebView webView, String url, Bitmap favicon) {
                progressBar.setVisibility(VISIBLE);
            }

            @Override
            public void onPageFinished(WebView webView, String url) {
                if (!isParseError) {
                    onLoadFinished(webView, true, 0, "", url);
                }
                progressBar.setProgress(0);
                progressBar.setVisibility(GONE);
            }
        });
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onJsConfirm(WebView view, String url, String message, JsResult result) {
                mbox.setContentGravity(Gravity.LEFT);
                mbox.setContent(message);
                mbox.setShowTitle(false);
                mbox.setTarget("ON_JS_CONFIRM_TARGET", result);
                mbox.show(view.getContext(), DialogButtonsEnum.ConfirmCancel);
                return true;
            }

            private View createPromptEditView(Context context, String defaultText) {
                LinearLayout ll = new LinearLayout(context);
                LinearLayout.LayoutParams llparam = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        PixelUtils.dip2px(context, 32));
                EditText editText = new EditText(context);
                editText.setLayoutParams(llparam);
                editText.setPadding(2, 1, 2, 1);
                editText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
                editText.setTextColor(Color.parseColor("#323232"));
                editText.setHintTextColor(Color.parseColor("#999999"));
                editText.setText(defaultText);
                ll.addView(editText);
                return ll;
            }

            @Override
            public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
                mbox.setContentGravity(Gravity.LEFT);
                mbox.setShowTitle(true);
                mbox.setTitle(message);
                mbox.setContentView(createPromptEditView(view.getContext(), defaultValue));
                Object[] extras = {result, defaultValue};
                mbox.setTarget("ON_JS_PROMPT_TARGET", extras);
                mbox.show(view.getContext(), DialogButtonsEnum.ConfirmCancel);
                return true;
            }

            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                mbox.setContentGravity(Gravity.CENTER);
                mbox.setContent(message);
                mbox.setShowTitle(false);
                mbox.show(view.getContext(), DialogButtonsEnum.Confirm);
                result.confirm();
                return true;
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                progressBar.setProgress(newProgress);
                progressBar.postInvalidate();
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                BaseWebLoad.this.onReceivedTitle(title);
            }

            @Override
            public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissionsCallback callback) {
                callback.invoke(origin, true, false);
            }
        });
        webView.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimeType, long size) {

            }
        });
    }

    private BaseMessageBox mbox = new BaseMessageBox() {
        @Override
        public void onItemClickListener(View v, MsgBoxClickButtonEnum mcbenum, String target, Object extraData) {
            if (TextUtils.equals(target, "ON_JS_CONFIRM_TARGET")) {
                JsResult result = (JsResult) extraData;
                if (result == null) {
                    if (mcbenum == MsgBoxClickButtonEnum.Confirm) {
                        result.confirm();
                    } else {
                        result.cancel();
                    }
                }
            } else if (TextUtils.equals(target, "ON_JS_PROMPT_TARGET")) {
                if (extraData == null) {
                    dismiss();
                } else {
                    if (extraData instanceof Object[]) {
                        Object[] extras = (Object[]) extraData;
                        if (extras == null || extras.length != 2) {
                            dismiss();
                        } else {
                            JsPromptResult promptResult = (JsPromptResult) extras[0];
                            String defaultText = String.valueOf(extras[1]);
                            if (promptResult != null && !TextUtils.isEmpty(defaultText)) {
                                if (mcbenum == MsgBoxClickButtonEnum.Confirm) {
                                    promptResult.confirm(defaultText);
                                } else {
                                    promptResult.cancel();
                                }
                            } else {
                                dismiss();
                            }
                        }
                    } else {
                        dismiss();
                    }
                }
            } else {
                dismiss();
            }
        }
    };

    public void onDestroy() {
        mbox.dismiss();
        if (webView != null) {
            webView.destroy();
        }
    }

    protected void onPreCreated(Context context) {

    }

    protected JavascriptInterfaceItem[] getJavascriptInterfaces() {
        return null;
    }

    protected boolean onOverrideUrlLoading(WebView webView, String url) {
        return false;
    }

    protected void onLoadFinished(WebView webView, boolean success, int errorCode, String description, String failingUrl) {

    }

    protected void onReceivedTitle(String title) {

    }

    /**
     * 获取额外头数据
     *
     * @return
     */
    protected Map<String, String> getExtraHeaders(
            Map<String, String> extraHeaders) {
        return extraHeaders;
    }

    public void postUrl(String url, HashMap<String, String> postData) {
        try {
            isParseError = false;
            if (webView == null) {
                return;
            }
            String data = "";
            if (!ObjectJudge.isNullOrEmpty(postData)) {
                Iterator<Map.Entry<String, String>> iter = postData
                        .entrySet().iterator();
                StringBuffer sb = new StringBuffer();
                while (iter.hasNext()) {
                    Map.Entry<String, String> entry = (Map.Entry<String, String>) iter
                            .next();
                    sb.append(String.format("%s=%s&", entry.getKey(),
                            URLEncoder.encode(entry.getValue(), "utf-8")));
                }
                if (sb.length() > 0) {
                    data = sb.substring(0, sb.length() - 1);
                }
            }
            if (!TextUtils.isEmpty(data)) {
                webView.postUrl(url, data.getBytes());
            }
        } catch (Exception e) {
            Logger.L.error(e);
        }
    }

    public void postUrl(String url) {
        postUrl(url, null);
    }

    public void loadUrl(String url, Map<String, String> extraHeaders) {
        try {
            isParseError = false;
            if (extraHeaders == null) {
                extraHeaders = new HashMap<String, String>();
            }
            Map<String, String> headersdata = getExtraHeaders(extraHeaders);
            if (headersdata == null) {
                webView.loadUrl(url);
            } else {
                webView.loadUrl(url, headersdata);
            }
        } catch (Exception e) {
            Logger.L.error(e);
        }
    }

    public void loadUrl(String url) {
        loadUrl(url, null);
    }

    public void loadData(String htmlContent) {
        try {
            isParseError = false;
            StringBuffer sb = new StringBuffer();
            sb.append("<!DOCTYPE html>");
            sb.append("<html>");
            sb.append("<head>");
            sb.append("<meta charset=\"utf-8\"/>");
            sb.append("<meta name=\"viewport\" content=\"width=device-width,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no\"/>");
            sb.append("<style type=\"text/css\">body,div,ul,li {padding: 0;margin: 0;display: block;}</style>");
            sb.append("</head>");
            sb.append("<body>");
            sb.append(htmlContent);
            sb.append("</body>");
            sb.append("</html>");
            webView.loadDataWithBaseURL("", sb.toString(), "text/html", "utf-8", "");
        } catch (Exception e) {
            Logger.L.error(e);
        }
    }

    private void finishOrGoBack(Activity activity, boolean flag, int enterAnim, int exitAnim, OnFinishOrGoBackListener finishOrGoBackListener) {
        try {
            if (flag) {
                activity.finish();
            } else {
                if (webView.canGoBack()) {
                    webView.goBack();
                    if (finishOrGoBackListener != null) {
                        finishOrGoBackListener.onFinishOrGoBack(webView.canGoBack());
                    }
                } else {
                    if (finishOrGoBackListener != null) {
                        finishOrGoBackListener.onFinishOrGoBack(false);
                    } else {
                        activity.finish();
                    }
                    activity.overridePendingTransition(enterAnim != 0 ? enterAnim : 0, exitAnim != 0 ? exitAnim : 0);
                }
            }
        } catch (Exception e) {
            Logger.L.error(e);
        }
    }

    /**
     * 回退web或结束当前窗口
     *
     * @param activity
     * @param flag     true直接结束当前窗口,false先回退再结束
     */
    public void finishOrGoBack(Activity activity, boolean flag, OnFinishOrGoBackListener finishOrGoBackListener) {
        finishOrGoBack(activity, flag, 0, 0, null);
    }

    /**
     * 回退web或结束当前窗口
     *
     * @param activity
     * @param flag     true直接结束当前窗口,false先回退再结束
     */
    public void finishOrGoBack(Activity activity) {
        finishOrGoBack(activity, false, null);
    }

    public WebView getWebView() {
        return webView;
    }

    protected String convertRGBToHex(int r, int g, int b) {
        String rFString, rSString, gFString, gSString,
                bFString, bSString, result;
        int red, green, blue;
        int rred, rgreen, rblue;
        red = r / 16;
        rred = r % 16;
        if (red == 10) rFString = "A";
        else if (red == 11) rFString = "B";
        else if (red == 12) rFString = "C";
        else if (red == 13) rFString = "D";
        else if (red == 14) rFString = "E";
        else if (red == 15) rFString = "F";
        else rFString = String.valueOf(red);

        if (rred == 10) rSString = "A";
        else if (rred == 11) rSString = "B";
        else if (rred == 12) rSString = "C";
        else if (rred == 13) rSString = "D";
        else if (rred == 14) rSString = "E";
        else if (rred == 15) rSString = "F";
        else rSString = String.valueOf(rred);

        rFString = rFString + rSString;

        green = g / 16;
        rgreen = g % 16;

        if (green == 10) gFString = "A";
        else if (green == 11) gFString = "B";
        else if (green == 12) gFString = "C";
        else if (green == 13) gFString = "D";
        else if (green == 14) gFString = "E";
        else if (green == 15) gFString = "F";
        else gFString = String.valueOf(green);

        if (rgreen == 10) gSString = "A";
        else if (rgreen == 11) gSString = "B";
        else if (rgreen == 12) gSString = "C";
        else if (rgreen == 13) gSString = "D";
        else if (rgreen == 14) gSString = "E";
        else if (rgreen == 15) gSString = "F";
        else gSString = String.valueOf(rgreen);

        gFString = gFString + gSString;

        blue = b / 16;
        rblue = b % 16;

        if (blue == 10) bFString = "A";
        else if (blue == 11) bFString = "B";
        else if (blue == 12) bFString = "C";
        else if (blue == 13) bFString = "D";
        else if (blue == 14) bFString = "E";
        else if (blue == 15) bFString = "F";
        else bFString = String.valueOf(blue);

        if (rblue == 10) bSString = "A";
        else if (rblue == 11) bSString = "B";
        else if (rblue == 12) bSString = "C";
        else if (rblue == 13) bSString = "D";
        else if (rblue == 14) bSString = "E";
        else if (rblue == 15) bSString = "F";
        else bSString = String.valueOf(rblue);
        bFString = bFString + bSString;
        result = "#" + rFString + gFString + bFString;
        return result;
    }

    protected boolean isMatchThisColor(String bgcolor, String thisColor) {
        try {
            if (TextUtils.isEmpty(bgcolor)) {
                return false;
            }
            String pattern = String.format(RuleParams.MatchTagBetweenContent.getValue(), "\\(", "\\)");
            String text = ValidUtils.matche(pattern, bgcolor);
            String mcolor = "";
            if (TextUtils.isEmpty(text)) {
                mcolor = bgcolor;
            } else {
                int r = 0, g = 0, b = 0;
                String[] rgbs = text.split(",");
                if (rgbs.length == 3) {
                    r = ConvertUtils.toInt(rgbs[0]);
                    g = ConvertUtils.toInt(rgbs[1]);
                    b = ConvertUtils.toInt(rgbs[2]);
                    mcolor = convertRGBToHex(r, g, b);
                } else if (rgbs.length == 4) {
                    if (TextUtils.equals(rgbs[0], "0")) {
                        mcolor = thisColor;
                    }
                }
            }
            boolean isWhite = false;
            if (!TextUtils.isEmpty(mcolor)) {
                mcolor = mcolor.toLowerCase();
                if (TextUtils.equals(mcolor, thisColor)) {
                    isWhite = true;
                }
            }
            return isWhite;
        } catch (Exception e) {
            return false;
        }
    }

    protected void getWebBackgroundColor() {
        WebView webView = getWebView();
        if (webView == null) {
            return;
        }
        webView.loadUrl("javascript:window.mibao.getBackgroundColor(window.getComputedStyle(document.body,null).getPropertyValue('background-color'))");
    }

    protected void getSelectText() {
        WebView webView = getWebView();
        if (webView == null) {
            return;
        }
        webView.loadUrl("javascript:window.mibao.getSelectText(window.getSelection().toString());");
    }
}
