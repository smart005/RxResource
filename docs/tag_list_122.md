1.2.2及之后版本标签使用
-----
标签效果图如下：

![image](/images/tag_list.jpg)

###### 创建视图
```xml
<com.cloud.resources.flows.FlowItemsView
android:id="@+id/fiv_view"
android:layout_width="match_parent"
android:layout_height="wrap_content"
android:padding="10dp"
android:visibility="visible"
app:dvg_gravity="left"
app:dvg_horizontalSpacing="10dp"
app:dvg_orientation="horizontal"
app:dvg_verticalSpacing="10dp" />
```

###### 标签支持功能
```java
1.支持标签对齐;
2.支持标签行数、列表限制;

其它功能以下属性做详情说明
```

###### 控件自定义属性
| 控件属性                       | 描述                                                                         |
|--------------------------------|------------------------------------------------------------------------------|
| app:dvg_gravity                | 子视图对齐方式;left-左,right-右,center-中,top-上,bottom-下,both-两端对齐     |
| app:dvg_orientation            | 子视图布局方向,horizontal横向排列  vertical纵向排列                          |
| app:dvg_horizontalSpacing      | 每个标签项左右之间间距                                                       |
| app:dvg_verticalSpacing        | 每个标签项上下之间间距                                                       |
| app:dvg_maxColumnNum           | 设置最大列数（用于 横向排列 模式）                                           |
| app:dvg_maxLineNum             | 设置最大行数（用于 竖向排列模式 模式）                                       |
| app:fiv_defaultItemBackground  | 标签项默认背景;若指定标签设有默认背景，则以标签的默认背景属性为准;           |
| app:fiv_selectedItemBackground | 标签项选中背景;若指定标签设有选中背景，则以标签的选中背景属性为准;           |
| app:fiv_disableItemBackground  | 标签项被禁用的背景;若指定标签设有禁用的背景，则以标签的禁用状态背景属性为准; |
| app:fiv_horizontalPadding      | 标签横向内边距（指每个标签左右两边的内边距）                                 |
| app:fiv_verticalPadding        | 标签纵向内边距（指每个标签上下两边的内边距）                                 |
| app:fiv_defaultItemTextColor   | 标签项默认文本颜色                                                           |
| app:fiv_selectedItemTextColor  | 标签选中项文本颜色                                                           |
| app:fiv_disableItemTextColor   | 标签被禁用项文本颜色                                                         |
| app:fiv_singleCheck            | true:在可用的选项范围内为单选行为;false:为多选行为;默认为true;               |

###### 控件方法
| 方法名                               | 描述                                                                   |
|--------------------------------------|------------------------------------------------------------------------|
| setEnableCheck(boolean enableCheck)  | 是否启用选择;true所有标签不可选择操作,反之可操作;默认为true;           |
| getDefaultTagProperties()            | 获取标签默认属性;相关资源及特性将继承自控件的属性;                     |
| addItem(TagProperties tagProperties) | 添加标签项,其中参数可先通过getDefaultTagProperties()获取,然后再修改值; |
| setSingleCheck(boolean singleCheck)  | true:在可用的选项范围内为单选行为;false:为多选行为;默认为true;         |
| getCheckItems()                      | 获取选中项属性及数据对象                                               |
| getTagProperties(String tagName)     | 根据标签名称获取属性                                                   |
| resetTag(TagProperties properties)   | 重置标签;参数推荐通过getTagProperties(tagName)获取                     |
| deleteTag(String tagName)            | 删除标签                                                               |
| clearAllTags()                       | 清空所有标签                                                           |

###### 标签项属性(调用addItem(...)需要传入的参数)
| 标签属性(TagProperties)                                    | 描述                     |
|------------------------------------------------------------|--------------------------|
| getText()                                                  | 获取标签文本             |
| setText(CharSequence text)                                 | 设置标签文本             |
| getData()                                                  | 获取该标签附带的数据对象 |
| setData(T data)                                            | 设置该标签附带的数据对象 |
| isCheck()                                                  | 该标签是否被选中         |
| setCheck(boolean check)                                    | 设置该标签是否被选中     |
| getDefaultItemBackground()                                 | 获取标签项默认背景       |
| setDefaultItemBackground(int defaultItemBackground)        | 设置标签项默认背景       |
| getSelectedItemBackground()                                | 获取标签项选中背景       |
| void setSelectedItemBackground(int selectedItemBackground) | 设置标签项选中背景       |
| getDisableItemBackground()                                 | 获取标签项被禁用的背景   |
| setDisableItemBackground(int disableItemBackground)        | 设置标签项被禁用的背景   |
| getDefaultItemTextColor()                                  | 获取标签项默认文本颜色   |
| setDefaultItemTextColor                                    | 设置标签默认文本颜色     |
| getSelectedItemTextColor()                                 | 获取标签选中文本颜色     |
| setSelectedItemTextColor()                                 | 设置标签选中文本颜色     |
| getDisableItemTextColor()                                  | 获取被标签文本颜色       |
| setDisableItemTextColor                                    | 设置被禁用标签项文本颜色 |

###### 标签项改变监听
```java
fivView.setOnItemChangeListener(tagItemChangeListener);

private OnItemChangeListener<String> tagItemChangeListener = new OnItemChangeListener<String>() {
    @Override
    public void onItemChange(View v, boolean isCheck, String entity) {

    }
};
```


