package geek.com.resourcestest;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.multidex.MultiDexApplication;
import android.support.v7.app.AppCompatDelegate;

import com.cloud.resources.refresh.SmartRefreshLayout;
import com.cloud.resources.refresh.api.DefaultRefreshHeaderCreater;
import com.cloud.resources.refresh.api.RefreshHeader;
import com.cloud.resources.refresh.api.RefreshLayout;
import com.cloud.resources.refresh.header.ClassicsHeader;

public class ResourcesApplication extends MultiDexApplication {

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        SmartRefreshLayout.setDefaultRefreshHeaderCreater(new DefaultRefreshHeaderCreater() {
            @NonNull
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                return new ClassicsHeader(context);
            }
        });
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }
}
