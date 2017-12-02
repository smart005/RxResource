package geek.com.resourcestest;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;

import com.cloud.resources.flowtrack.FlowTrackOneItem;
import com.cloud.resources.flowtrack.VerticalFlowTrackOneView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2017/11/24
 * @Description:
 * @Modifier:
 * @ModifyContent:
 */
public class FlowtrackActivity extends Activity {

    @BindView(R.id.flow_vtft)
    VerticalFlowTrackOneView flowVtft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.flowtrack_view);
        ButterKnife.bind(this);
        final List<FlowTrackOneItem> flowTrackOneItems = new ArrayList<>();
        flowTrackOneItems.add(new FlowTrackOneItem("", "已签收", "感谢使用顺丰,期待再次为您服务", true));
        flowTrackOneItems.add(new FlowTrackOneItem("", "正在派送", "正在派送途中,请您准备签收【派件人:杜军,电话:133xxxxxxxx】", false));
        flowTrackOneItems.add(new FlowTrackOneItem("", "在路上", "快件在【杭州下沙中转场】已装车，准备发往【杭州江干八号大街营业点】", false));
        flowTrackOneItems.add(new FlowTrackOneItem("", "在路上", "顺丰速运 已收取快件", true));
        flowTrackOneItems.add(new FlowTrackOneItem("", "已出库", "您的订单出库完成，顺丰揽件中", false));
        flowTrackOneItems.add(new FlowTrackOneItem("", "已签收", "感谢使用顺丰,期待再次为您服务", true));
        flowTrackOneItems.add(new FlowTrackOneItem("", "正在派送", "正在派送途中,请您准备签收【派件人:杜军,电话:133xxxxxxxx】", false));
        flowTrackOneItems.add(new FlowTrackOneItem("", "在路上", "快件在【杭州下沙中转场】已装车，准备发往【杭州江干八号大街营业点】", false));
        flowTrackOneItems.add(new FlowTrackOneItem("", "在路上", "顺丰速运 已收取快件", true));
        flowTrackOneItems.add(new FlowTrackOneItem("", "已出库", "您的订单出库完成，顺丰揽件中", false));
        flowTrackOneItems.add(new FlowTrackOneItem("", "已签收", "感谢使用顺丰,期待再次为您服务", true));
        flowTrackOneItems.add(new FlowTrackOneItem("", "正在派送", "正在派送途中,请您准备签收【派件人:杜军,电话:133xxxxxxxx】", false));
        flowTrackOneItems.add(new FlowTrackOneItem("", "在路上", "快件在【杭州下沙中转场】已装车，准备发往【杭州江干八号大街营业点】", false));
        flowTrackOneItems.add(new FlowTrackOneItem("", "在路上", "顺丰速运 已收取快件", true));
        flowTrackOneItems.add(new FlowTrackOneItem("", "已出库", "您的订单出库完成，顺丰揽件中", false));
        flowTrackOneItems.add(new FlowTrackOneItem("", "已签收", "感谢使用顺丰,期待再次为您服务", true));
        flowTrackOneItems.add(new FlowTrackOneItem("", "正在派送", "正在派送途中,请您准备签收【派件人:杜军,电话:133xxxxxxxx】", false));
        flowTrackOneItems.add(new FlowTrackOneItem("", "在路上", "快件在【杭州下沙中转场】已装车，准备发往【杭州江干八号大街营业点】", false));
        flowTrackOneItems.add(new FlowTrackOneItem("", "在路上", "顺丰速运 已收取快件", true));
        flowTrackOneItems.add(new FlowTrackOneItem("", "已出库", "您的订单出库完成，顺丰揽件中", false));
        flowTrackOneItems.add(new FlowTrackOneItem("", "已签收", "感谢使用顺丰,期待再次为您服务", true));
        flowTrackOneItems.add(new FlowTrackOneItem("", "正在派送", "正在派送途中,请您准备签收【派件人:杜军,电话:133xxxxxxxx】", false));
        flowTrackOneItems.add(new FlowTrackOneItem("", "在路上", "快件在【杭州下沙中转场】已装车，准备发往【杭州江干八号大街营业点】", false));
        flowTrackOneItems.add(new FlowTrackOneItem("", "在路上", "顺丰速运 已收取快件", true));
        flowTrackOneItems.add(new FlowTrackOneItem("", "已出库", "您的订单出库完成，顺丰揽件中", false));
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                flowVtft.bind(flowTrackOneItems);
            }
        }, 3000);
    }
}
