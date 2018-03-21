纵向自定义流程视图
-------
![images](/images/v_flow_track.jpg)

###### 1.添加控件至布局中
```xml
<ScrollView
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:fadingEdge="none"
    android:overScrollMode="never"
    android:paddingLeft="@dimen/spacing_16"
    android:paddingRight="@dimen/spacing_16"
    android:scrollbars="none">

    <com.cloud.resources.flowtrack.VerticalFlowTrackOneView
        android:id="@+id/flow_vtft"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        app:vftov_leftTextNodeSpacing="@dimen/spacing_16"
        app:vftov_leftViewWidth="@dimen/spacing_60"
        app:vftov_nodeCableLineColor="@color/color_e7effe"
        app:vftov_nodeColor="@color/color_e7effe"
        app:vftov_paddingLeft="@dimen/spacing_0"
        app:vftov_paddingRight="@dimen/spacing_16"
        app:vftov_rightTextNodeSpacing="@dimen/spacing_10"
        app:vftov_selectNodeColor="@color/color_4c5b71" />
</ScrollView>

注：
1.为超出后有滚动效果因此需要放在ScrollView中;
2.其中android:fadingEdge="none"、android:overScrollMode="never"和android:scrollbars="none"
  是为滚动时没有滚动条和拉动阴影效果；
```

###### 2.添加数据绑定
```java
1.将数据转化成以下形式：
  List<FlowTrackOneItem> flowTrackOneItems = new ArrayList<FlowTrackOneItem>();

2.绑定数据flowVtft.bind(flowTrackOneItems);

其中FlowTrackOneItem结构如下：
public class FlowTrackOneItem {
    /**
     * 时间(在每个节点的左边展示,若要换行请用\\n表示)
     */
    private String time = "";
    /**
     * 标题
     */
    private String title = "";
    /**
     * 描述
     */
    private String des = "";
    /**
     * 是否选中
     */
    private boolean isChk = false;

    ...
}
```

###### 3.方法属性
| 方法                                                | 描述         |
|-----------------------------------------------------|--------------|
| void clear()                                        | 清空流程节点 |
| void bind(List<FlowTrackOneItem> flowTrackOneItems) | 绑定数据     |

###### 4.控件属性
| 自定义属性                     | 描述                     |
|--------------------------------|--------------------------|
| app:vftov_leftTextNodeSpacing  | 左边文本与节点之间的距离 |
| app:vftov_rightTextNodeSpacing | 右边文本与节点之间的距离 |
| app:vftov_leftViewWidth        | 节点左边视图的宽度       |
| app:vftov_nodeCableLineColor   | 节点之间连接线的颜色     |
| app:vftov_nodeColor            | 节点颜色                 |
| app:vftov_selectNodeColor      | 选中节点颜色             |
| app:vftov_paddingLeft          | 控件左内边距             |
| app:vftov_paddingRight         | 控件右内边距             |
| app:vftov_paddingBottom        | 控件下内边距             |
| app:vftov_paddingTop           | 控件上内边距             |
| app:vftov_buttomOffset         | 控件底部偏移量           |
| app:vftov_desTextColor         | 节点右边文本颜色         |
| app:vftov_desTextSize          | 节点右边文本大小         |
| app:vftov_nodeSize             | 节点大小                 |
| app:vftov_timeSelectTextColor  | 选中文本项对应的时间颜色 |
| app:vftov_timeTextColor        | 默认时间文本颜色         |
| app:vftov_timeTextSize         | 默认时间文本大小         |
| app:vftov_titleTextBlod        | 标题是否加粗             |
| app:vftov_titleTextColor       | 标题文本颜色             |
| app:vftov_titleTextSize        | 标题文本大小             |





