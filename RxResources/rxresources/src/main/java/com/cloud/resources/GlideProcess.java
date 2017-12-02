package com.cloud.resources;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.BitmapRequestBuilder;
import com.bumptech.glide.BitmapTypeRequest;
import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.DrawableTypeRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.request.target.Target;
import com.cloud.core.RxCoreUtils;
import com.cloud.core.config.ResConfig;
import com.cloud.core.config.RxConfig;
import com.cloud.core.enums.ImgRuleType;
import com.cloud.core.glides.FormatDataModel;
import com.cloud.core.logger.Logger;

import java.io.File;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2016/6/13
 * @Description:
 * @Modifier:
 * @ModifyContent:
 */
public class GlideProcess {

    static class GlideTransProperties {
        public Context context = null;
        public String url = "";
        public Uri uri = null;
        public File file = null;
        public int defImg = 0;
        public int crossFade = 0;
        public ImageView imageView = null;
        public Target target = null;
        public int width = 0;
        public int height = 0;
        public int corners = 0;
        public int preLoadWidth = 0;
        public int preLoadHeight = 0;
        public ImgRuleType ruleType = ImgRuleType.None;
    }

    private static ResConfig config = null;
    private static RxConfig rxConfig = null;

    private static RxConfig getRxConfig(Context context) {
        if (rxConfig == null) {
            rxConfig = RxCoreUtils.getInstance().getConfig(context);
        }
        return rxConfig;
    }

    private static ResConfig getConfig(Context context) {
        RxConfig rxConfig = getRxConfig(context);
        if (config == null) {
            config = rxConfig.getResConfig();
        }
        return config;
    }

    private static DrawableTypeRequest getRequestManager(Context context,
                                                         ImgRuleType ruleType,
                                                         String url,
                                                         int imgWidth,
                                                         int imgHeight,
                                                         int imgCorners) {
        return Glide.with(context)
                .load(FormatDataModel.getUrl(context,
                        url,
                        ruleType,
                        imgWidth,
                        imgHeight,
                        imgCorners));
    }

    private static DrawableRequestBuilder loadConfig(DrawableTypeRequest request,
                                                     int preLoadWidth,
                                                     int preLoadHeight,
                                                     int defImg,
                                                     int crossFade) {
        //内存缓存
        DrawableRequestBuilder drawableRequestBuilder = request.skipMemoryCache(false)
                .dontAnimate();
        if (defImg != 0) {
            //默认背景(下载之前显示图片)
            drawableRequestBuilder.placeholder(defImg);
            //图片加载失败也显示默认图片
            drawableRequestBuilder.error(defImg);
        }
        if (crossFade > 0) {
            //淡入淡出动画效果,300为默认持续时间
            drawableRequestBuilder.crossFade(crossFade);
        }
        //图片显示模式
        drawableRequestBuilder.fitCenter()
                //没有动画效果
                //.dontAnimate()
                .thumbnail(0.1f);
        if (preLoadWidth > 0 && preLoadHeight > 0) {
            drawableRequestBuilder.preload(preLoadWidth, preLoadHeight);
        }
        drawableRequestBuilder.diskCacheStrategy(DiskCacheStrategy.SOURCE);
        drawableRequestBuilder.fitCenter();
        return drawableRequestBuilder;
    }

    private static BitmapRequestBuilder loadBitmapConfig(BitmapTypeRequest request, int preLoadWidth, int preLoadHeight, int defImg) {
        //内存缓存
        BitmapRequestBuilder bitmapRequestBuilder = request.skipMemoryCache(false);
        if (defImg != 0) {
            //默认背景(下载之前显示图片)
            bitmapRequestBuilder.placeholder(defImg);
            //图片加载失败也显示默认图片
            bitmapRequestBuilder.error(defImg);
        }
        //图片显示模式
        bitmapRequestBuilder.fitCenter()
                //没有动画效果
                //.dontAnimate()
                .thumbnail(0.1f);
        if (preLoadWidth > 0 && preLoadHeight > 0) {
            bitmapRequestBuilder.preload(preLoadWidth, preLoadHeight);
        }
        bitmapRequestBuilder.diskCacheStrategy(DiskCacheStrategy.SOURCE);
        bitmapRequestBuilder.fitCenter();
        return bitmapRequestBuilder;
    }

    private static DrawableRequestBuilder loadConfig(DrawableTypeRequest request, int defImg) {
        return loadConfig(request, 42, 42, defImg, 300);
    }

    private static BitmapRequestBuilder loadBitmapConfig(BitmapTypeRequest request, int defImg) {
        return loadBitmapConfig(request, 42, 42, defImg);
    }

    private static DrawableRequestBuilder loadConfig(Context context,
                                                     DrawableTypeRequest request) {
        ResConfig config = getConfig(context);
        return loadConfig(request, config.getDefBg());
    }

    private static BitmapRequestBuilder loadBitmapConfig(Context context,
                                                         BitmapTypeRequest request) {
        ResConfig config = getConfig(context);
        return loadBitmapConfig(request, config.getDefBg());
    }

    public static void load(Context context, ImgRuleType ruleType, String url, int defImg, int imgWidth, int imgHeight, int imgCorners, ImageView imageView) {
        GlideTransProperties transProperties = new GlideTransProperties();
        transProperties.context = context;
        transProperties.url = url;
        transProperties.defImg = defImg;
        transProperties.imageView = imageView;
        transProperties.ruleType = ruleType;
        transProperties.width = imgWidth;
        transProperties.height = imgHeight;
        transProperties.corners = imgCorners;
        loadImageUrlProcess(transProperties);
    }

    public static void load(Context context,
                            ImgRuleType ruleType,
                            String url,
                            int imgWidth,
                            int imgHeight,
                            int imgCorners,
                            ImageView imageView) {
        ResConfig config = getConfig(context);
        load(context, ruleType, url, config.getDefBg(), imgWidth, imgHeight, imgCorners, imageView);
    }

    public static void load(Context context, File file, int defImg, ImageView imageView) {
        GlideTransProperties transProperties = new GlideTransProperties();
        transProperties.context = context;
        transProperties.file = file;
        transProperties.defImg = defImg;
        transProperties.imageView = imageView;
        loadImageFileTargetProcess(transProperties);
    }

    public static void load(Context context,
                            File file,
                            ImageView imageView) {
        ResConfig config = getConfig(context);
        load(context, file, config.getDefBg(), imageView);
    }

    public static void load(Context context, Uri uri, Target target) {
        ResConfig config = getConfig(context);
        GlideTransProperties transProperties = new GlideTransProperties();
        transProperties.context = context;
        transProperties.uri = uri;
        transProperties.defImg = config.getDefBg();
        transProperties.crossFade = 300;
        transProperties.target = target;
        loadImageUriTargetProcess(context, transProperties);
    }

    public static void loadRadius(Context context,
                                  ImgRuleType ruleType,
                                  String url,
                                  int radius,
                                  ImageView imageView) {
        try {
            ResConfig config = getConfig(context);
            DrawableRequestBuilder drawableRequestBuilder = loadConfig(Glide.with(context).load(url),
                    2 * radius,
                    2 * radius,
                    config.getDefBg(),
                    200);
            drawableRequestBuilder.transform(new GlideCircleTransform(context));
            drawableRequestBuilder.into(imageView);
        } catch (Exception e) {
            Logger.L.error(e);
        }
    }

    private static void loadImageUrlProcess(GlideTransProperties transProperties) {
        try {
            loadConfig(getRequestManager(transProperties.context,
                    transProperties.ruleType,
                    transProperties.url,
                    transProperties.width,
                    transProperties.height,
                    transProperties.corners),
                    transProperties.defImg).
                    into(transProperties.imageView);
        } catch (Exception e) {
            Logger.L.error("load image process error:", e);
        }
    }

    private static void loadImageFileTargetProcess(GlideTransProperties transProperties) {
        try {
            loadConfig(Glide.with(transProperties.context).load(transProperties.file), 42, 42, transProperties.defImg, 300).into(transProperties.imageView);
        } catch (Exception e) {
            Logger.L.error("load image process error:", e);
        }
    }

    private static void loadImageUriTargetProcess(Context context,
                                                  GlideTransProperties transProperties) {
        try {
            ResConfig config = getConfig(context);
            loadConfig(Glide.with(transProperties.context).load(transProperties.uri),
                    transProperties.preLoadWidth,
                    transProperties.preLoadHeight,
                    config.getDefBg(),
                    transProperties.crossFade).into(transProperties.target);
        } catch (Exception e) {
            Logger.L.error("load image process error:", e);
        }
    }

    private static class GlideCircleTransform extends BitmapTransformation {
        public GlideCircleTransform(Context context) {
            super(context);
        }

        @Override
        protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
            return circleCrop(pool, toTransform);
        }

        private Bitmap circleCrop(BitmapPool pool, Bitmap source) {
            if (source == null) return null;

            int size = Math.min(source.getWidth(), source.getHeight());
            int x = (source.getWidth() - size) / 2;
            int y = (source.getHeight() - size) / 2;

            Bitmap squared = Bitmap.createBitmap(source, x, y, size, size);

            Bitmap result = pool.get(size, size, Bitmap.Config.ARGB_8888);
            if (result == null) {
                result = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
            }

            Canvas canvas = new Canvas(result);
            Paint paint = new Paint();
            paint.setShader(new BitmapShader(squared, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
            paint.setAntiAlias(true);
            float r = size / 2f;
            canvas.drawCircle(r, r, r, paint);
            return result;
        }

        @Override
        public String getId() {
            return getClass().getName();
        }
    }
}
