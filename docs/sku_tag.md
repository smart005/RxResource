sku标签使用
----
![images](/images/sku_img.png)

###### 1.创建sku视图
```xml
<com.cloud.resources.flows.FlowSkuView
    android:id="@+id/fsv_view"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:orientation="vertical">
    <!--如果是非动态那么也可以在这里直接添加-->
    <!--<include layout="@layout/sku_item_view" />-->
</com.cloud.resources.flows.FlowSkuView>

注:
1.规格视图和FlowItemsView视图如果设置android:tag="sku";
  如果在布局视图直接添加的，那么每一组的android:tag不能相同;
2.
```
[FlowItemsView使用请点这里](/docs/tag_list_122.md)

###### 2.sku数据准备
```java
1.将API返回的规格及sku属性转成以下列表格式;
LinkedHashMap<SkuSepecItem, List<SkuItem>> skuList = new LinkedHashMap<SkuSepecItem, List<SkuItem>>();
//规格
SkuSepecItem sepec = new SkuSepecItem();
sepec.setSepecTag("sku");
sepec.setSepecName("尺寸");
//规格对应的sku属性
List<SkuItem> sepecSkuList = new ArrayList<SkuItem>();
//这里SkuItem(sku,alias),sku值和alias别名;内部判断以sku为准，显示以alias为准;
sepecSkuList.add(new SkuItem("9.7寸"));
//添加至集合
skuList.put(sepec, sepecSkuList);

//设置规格-属性
fsvView.setSkuList(skuList);

2.设置API返回的有效(可用)sku(每一组形式:9.7寸,金色,32G,12);
fsvView.setEffectiveSkuList(List<String>, "每一组的数据的分隔符");
```

###### 3.设置每一组sku布局
```java
//通过设置布局id来构建sku规格视图
fsvView.setSkuItemLayoutId(R.layout.sku_item_view)
注：如果设置该属性那么第一步在视图中添加的sku_item_view将无效;
```

###### 4.sku初始及选择后事件监听
```java
fsvView.setOnSkuItemChangeListener(skuItemChangeListener)
private OnSkuItemChangeListener skuItemChangeListener = new OnSkuItemChangeListener() {
    @Override
    public void onSkuItemChange(String checkedSkus) {

    }
};
```

###### 5.绑定并初始化默认选中项
```java
fsvView.bind("初始选择sku属性", "第一个参数分隔符")

示例:fsvView.bind("9.7寸,金色,32G,12", ",")
```
