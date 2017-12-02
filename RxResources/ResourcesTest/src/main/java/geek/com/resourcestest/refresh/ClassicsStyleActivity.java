package geek.com.resourcestest.refresh;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

import com.cloud.resources.refresh.SmartRefreshLayout;
import com.cloud.resources.refresh.api.RefreshLayout;
import com.cloud.resources.refresh.listener.OnRefreshListener;
import com.cloud.resources.refresh.listener.OnRefreshLoadmoreListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import geek.com.resourcestest.R;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2017/11/23
 * @Description:
 * @Modifier:
 * @ModifyContent:
 */
public class ClassicsStyleActivity extends Activity {
    @BindView(R.id.refresh_srfl)
    SmartRefreshLayout refreshSrfl;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.classics_style_view);
        ButterKnife.bind(this);
        refreshSrfl.setEnableRefresh(true);
        refreshSrfl.setEnableLoadmore(false);
        refreshSrfl.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {

            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {

            }
        });
    }
}
