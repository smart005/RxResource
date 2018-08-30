线性类视图(继承LinearLayout),横向纵向拖拽工具类
----

###### 示例

![images](/images/linear_view_drager.png)

###### 代码
```java
//实例拖拽对象,其中泛型只要继承LinearLayout即可
LinearDrager<LinearLayout> drager = new LinearDrager<LinearLayout>();

//构建拖拽对象;context:当前上下文;container:要拖拽视图的容器,即LinearLayout或继承LinearLayout的容器;
drager.builder(Context context, V container);

//设置拖拽视图
drager.setDragViews(View... views);

//设置拖拽视图监听
drager.setOnLinearDragerListener(new OnLinearDragerListener() {
    @Override
    public void onLinearDrager(View view, int position) {
    	//视图拖拽后回调监听
    	//view:当前拖拽的视图
    	//position:当前视图拖拽后对应集合的索引
    }
});
```