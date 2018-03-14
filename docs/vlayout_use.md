VLayout布局/数据格式/使用
----
```text
在APP开发时首页很多复杂的效果基本上都可以用vlayout来完成;
但我们真正在项目开发过程中发现vlayout没有直接提供的方法,只能间接的方式一步步实现;(如某一块区域是横向滚动的视图)
另外vlayout也存在一些小坑以及直接使用起来也并不是那么方便;
因此才有了以下封装;
```
###### VLayoutType
```enum
public enum VLayoutType {
    /**
     * 线性横向
     */
    LinearHorizontal,
    /**
     * 线性纵向
     */
    LinearVertical,
    /**
     * 线性纵向瀑布形式
     */
    LinearVerticalStaggered,
    /**
     * 1拖N(最多5个)
     */
    OnePlusN,
    /**
     * 单个对象
     */
    SingleObject,
    /**
     * 当页面滑动到该图片区域才显示
     */
    ScrollFix
}
```
###### 1.初始化设置
```java
private VLayoutUtils vLayoutUtils = new VLayoutUtils();
//设置item缓存池大小
vLayoutUtils.setViewPool(testRv, 10);
```
###### 2_0.数据类型及监听说明
```java
SubAdapter<BaseViewHolder<每个item view holder>, 每个item view holder, 每条数据的类型> subAdapter = new SubAdapter<>();

private OnSubViewListener<BaseViewHolder<ItemViewHolder>, ItemViewHolder, AppBean> subViewListener = new OnSubViewListener<BaseViewHolder<ItemViewHolder>, ItemViewHolder, AppBean>() {
    @Override
    public BaseViewHolder<ItemViewHolder> onCreateHolder(String subKey) {
    	//创建每个item的holder对象,这里可根据subKey来区分不同绑定类型
        ItemViewHolder itemViewHolder = new ItemViewHolder();
        BaseViewHolder<ItemViewHolder> holder = new BaseViewHolder<ItemViewHolder>(itemViewHolder.getContentView());
        holder.setVH(itemViewHolder);
        return holder;
    }

    @Override
    public void onBindHolder(String subKey, ItemViewHolder holder, AppBean entity) {
    	//数据绑定，可根据subKey来区分
        holder.ivGridItemIcon.setImageDrawable(entity.getAppIcon());
        holder.tvGridItemName.setText(entity.getAppName());
    }

    @Override
    public void onItemClick(String subKey, View itemView, AppBean entity) {
    	//每个item点击响应
    }

    @Override
    public void onHSViewSpace(String subKey,SpaceItem spaceItem) {
    	//横向滚动视图每个item间距及属性设置
        Rect outRect = spaceItem.getOutRect();
        outRect.left = PixelUtils.dip2px(VLayoutActivity.this, 8);
        outRect.right = PixelUtils.dip2px(VLayoutActivity.this, 8);
        outRect.top = PixelUtils.dip2px(VLayoutActivity.this, 20);
        outRect.bottom = PixelUtils.dip2px(VLayoutActivity.this, 20);
    }
};
```
###### 2.横向滚动视图
```java
//创建适配器
SubAdapter<BaseViewHolder<ItemViewHolder>, ItemViewHolder, AppBean> subAdapter = new SubAdapter<>();
//添加数据集
subAdapter.setDataList(mList);
//设置本次数据绑定的唯一标识
subAdapter.setSubKey("hlist");
//设置监听对象
subAdapter.setOnSubViewListener(subViewListener);
//设置视图布局类型
subAdapter.setVLayoutType(VLayoutType.LinearHorizontal);
//横向滚动视图需要设置其总高度
subAdapter.sethScrollViewHeight(PixelUtils.dip2px(this, 460));
//数据列数
subAdapter.setSpanCount(2);
//添加适配器至集合
vLayoutUtils.addSubAdapter(this, subAdapter);
```
###### 3.纵向线性/瀑布流形式
```java
//创建适配器
SubAdapter<BaseViewHolder<ItemViewHolder>, ItemViewHolder, AppBean> subAdapter2 = new SubAdapter<>();
//添加数据集
subAdapter2.setDataList(mList);
//设置本次数据绑定的唯一标识
subAdapter2.setSubKey("hlist2");
//设置监听对象
subAdapter2.setOnSubViewListener(subViewListener);
//设置视图布局类型
subAdapter2.setVLayoutType(VLayoutType.LinearVertical);
//数据列数
subAdapter2.setSpanCount(2);
//每个item之间上下间距
subAdapter2.setVGap(PixelUtils.dip2px(this, 10));
//每个item之间左右间距
subAdapter2.setHGap(PixelUtils.dip2px(this, 10));
//添加适配器至集合
vLayoutUtils.addSubAdapter(this, subAdapter2);
```
###### 4.1拖N形式
```java
//创建适配器
SubAdapter<BaseViewHolder<ItemViewHolder>, ItemViewHolder, AppBean> subAdapter3 = new SubAdapter<>();
//添加数据集
subAdapter3.setDataList(mList);
//设置本次数据绑定的唯一标识
subAdapter3.setSubKey("hlist3");
//设置监听对象
subAdapter3.setOnSubViewListener(subViewListener);
//设置显示个数，目前最大支持5个
subAdapter3.setOnePlusNCount(5);
//设置视图布局类型
subAdapter3.setVLayoutType(VLayoutType.OnePlusN);
//添加适配器至集合
vLayoutUtils.addSubAdapter(this, subAdapter3);
```
###### 5.单个对象(如布局中间插入标题)形式
```java
//创建适配器
SubAdapter<BaseViewHolder<ItemViewHolder>, ItemViewHolder, AppBean> subAdapter4 = new SubAdapter<>();
//添加数据集
subAdapter4.setDataItem(mList.get(0));
//设置本次数据绑定的唯一标识
subAdapter4.setSubKey("hlist4");
//设置监听对象
subAdapter4.setOnSubViewListener(subViewListener);
//设置视图布局类型
subAdapter4.setVLayoutType(VLayoutType.SingleObject);
//添加适配器至集合
vLayoutUtils.addSubAdapter(this, subAdapter4);
```
###### 6.相对于布局的某一位置固定（可用于返回顶部视图）
```java
//创建适配器
SubAdapter<BaseViewHolder<ItemViewHolder>, ItemViewHolder, AppBean> subAdapter5 = new SubAdapter<>();
//添加数据集
subAdapter5.setDataItem(mList.get(0));
//设置本次数据绑定的唯一标识
subAdapter5.setSubKey("hlist5");
//设置监听对象
subAdapter5.setOnSubViewListener(subViewListener);
//设置视图布局类型
subAdapter5.setVLayoutType(VLayoutType.ScrollFix);
//相对于最外层布局的对齐方式(在vlayout的FixLayoutHelper下有相应的属性)
subAdapter5.setScrollFixAlianType(FixLayoutHelper.BOTTOM_LEFT);
//x方向外边距
subAdapter5.setScrollFixXSpace(PixelUtils.dip2px(this, 100));
//y方向外边距
subAdapter5.setScrollFixYSpace(PixelUtils.dip2px(this, 10));
//添加适配器至集合
vLayoutUtils.addSubAdapter(this, subAdapter5);
```

###### 最后进行绑定
```java
vLayoutUtils.builder(testRv);
```
###### 适配器通知进行刷新数据
```java
//列表数据通知
//第三个参数isRefresh=true表示清空原来的数据再设值，false为数据叠加;
vLayoutUtils.notifyDataSetChanged(mList, "hlist", true);
//对单条数据进行通知
vLayoutUtils.notifyDataSetChanged(mList.get(0), "hlist5");
```
###### ItemViewHolder格式
```java
public class ItemViewHolder extends BaseItemViewHolder {
    public ItemViewHolder() {
        //这里必须要设置当前视图
        setContentView(view);
    }
}
```