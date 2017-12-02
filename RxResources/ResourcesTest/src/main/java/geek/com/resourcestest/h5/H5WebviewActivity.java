package geek.com.resourcestest.h5;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import geek.com.resourcestest.R;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2017/11/19
 * @Description:
 * @Modifier:
 * @ModifyContent:
 */
public class H5WebviewActivity extends Activity {

    @BindView(R.id.h5_test)
    H5WebView h5Test;
    @BindView(R.id.btn)
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.h5_web_view);
        ButterKnife.bind(this);
        h5Test.setActivity(this);
        h5Test.loadUrl("http://news.cri.cn/20171117/960da35a-97e6-2201-d17a-b512f9b870e8.html");
    }

    @OnClick(R.id.btn)
    public void onBtnClick() {

    }
}
