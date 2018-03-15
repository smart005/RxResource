带索引导航分组列表视图
-----
![image](/images/xrefresh_indexbar_list_img.jpg)

###### 1.在布局文件中添加以下控件
```xml
<com.cloud.resources.refresh.XRefreshIndexBarListView
	android:id="@+id/test_xiblv"
	android:layout_width="match_parent"
	android:layout_height="match_parent"
	android:background="@color/color_ffffff" />
```
###### 2.控件初始化
```java
//禁用加载更多
testXiblv.setPullLoadEnable(false);
//列表刷新和加载监听回调
testXiblv.setXListViewListener(new OnXListViewListener() {
    @Override
    public void onRefresh() {
    	//这里模拟刷新动作
    }

    @Override
    public void onLoadMore() {
    	//这里模拟加载更多动作
    }
});
//获取数据源或接口列表(其中数据item自定义即可,前提需要继承BaseIndexPinyinBean)
List<CourierItem> dataList = testXiblv.getDataList();
//设置适配器
testXiblv.setAdapter(new CourierListAdapter(this, dataList));
//刷新(初始化时调用表示首次需要加载数据)
testXiblv.refresh();
```
###### 3.数据item结构
```java
public class CourierItem extends BaseIndexPinyinBean {
	...

	@Override
    public String getTarget() {
        return 这里返回item要显示的信息;
    }
}
```
###### 4.数据加载完成后需要调用以下方法
```java
//设置数据列表
testXiblv.setDatalist(courierItems);
//如果有分页显示的可addDatalist来添加数据
//testXiblv.addDatalist(courierItems);
//通知并绑定数据列表
testXiblv.notifyBind();
```
###### 5.最后初始化列表
```java
//实际项目中这里一般在网络请求结束后调用
testXiblv.initRL();
```

