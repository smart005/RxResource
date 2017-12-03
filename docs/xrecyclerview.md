XRefreshRecyclerView[带刷新和加载]
-----------
###### 1.布局
```xml
<com.cloud.resources.xrecyclerview.XRefreshRecyclerView
android:id="@+id/activity_center_list_srlv"
android:layout_width="match_parent"
android:layout_height="match_parent"
android:background="@color/color_ffffff"
android:scrollbars="vertical"
app:adapter="@{adapter}"
app:frv_divider="@color/transparent"
app:frv_dividerHeight="@dimen/spacing_0"
app:frv_isEmptyViewKeepShowHeadOrFooter="false"
app:frv_layoutManager="linear"
app:frv_layoutManagerOrientation="vertical" />
```
###### 2.代码
```text
binding.activityCenterListSrlv.setXListViewListener(new OnXListViewListener() {
    @Override
    public void onRefresh() {
        
    }

    @Override
    public void onLoadMore() {
        
    }
});
binding.activityCenterListSrlv.setPullRefreshEnable(false);
binding.activityCenterListSrlv.setPullLoadEnable(true);
binding.activityCenterListSrlv.refresh();
```