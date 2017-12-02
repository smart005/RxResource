package geek.com.resourcestest.h5;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;

import com.cloud.resources.x5.BaseWebLoad;
import com.cloud.resources.x5.JavascriptInterface;
import com.cloud.resources.x5.JavascriptInterfaceItem;
import com.tencent.smtt.sdk.WebView;

import java.util.Map;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2016/12/13
 * @Description:
 * @Modifier:
 * @ModifyContent:
 */
public class H5WebView extends BaseWebLoad {

    private Activity activity = null;
    private boolean isLoadedForFinished = false;

    public H5WebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    @Override
    protected boolean onOverrideUrlLoading(WebView view, String url) {
        return false;
    }

    @Override
    protected void onLoadFinished(WebView view, boolean success, int errorCode, String description, String url) {
        getWebBackgroundColor();
    }

    @Override
    protected JavascriptInterfaceItem[] getJavascriptInterfaces() {
        JavascriptInterfaceItem[] items = {new JavascriptInterfaceItem(new H5ForJs(), "mibao")};
        return items;
    }

    @Override
    protected void onReceivedTitle(String title) {

    }

    public interface OnH5WebViewListener {
        public void onReceivedTitle(String title);

        public void onExtraHeadersListener(Map<String, String> extraHeaders);

        public boolean onUrlListener(String url);

        public void onShowLoginModal(boolean isCallback);

        public void clickedEvent(String extras);

        public void onLoadFinished(WebView view, boolean success, int errorCode, String description, String url);
    }

    public interface OnSelectTextListener {
        public void onSelectText(String selectText);
    }

    class H5ForJs {

        @JavascriptInterface
        public String getToken() {
            return "";
        }

        @JavascriptInterface
        public void showLoginModal(boolean isCallback) {

        }

        @JavascriptInterface
        public void downloadApk(String url, String platformName) {

        }

        @JavascriptInterface
        public void statisticsClickCountAction(String platformid) {

        }

        @JavascriptInterface
        public void getAPIMethod(String extras) {

        }

        @JavascriptInterface
        public void eventStatistical(String statisticalJson) {

        }

        @JavascriptInterface
        public void share(String shareContent) {

        }

        @JavascriptInterface
        public void clickedEvent(String extras) {

        }

        @JavascriptInterface
        public void getSelectText(String selectText) {

        }

        @JavascriptInterface
        public void getBackgroundColor(String color) {
            boolean matchThisColor = isMatchThisColor(color, "#ffffff");
        }
    }
}
