package com.cloud.resources.tabindicator;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cloud.core.ObjectJudge;
import com.cloud.core.logger.Logger;
import com.cloud.core.utils.GlobalUtils;
import com.cloud.core.utils.PixelUtils;
import com.cloud.resources.R;
import com.cloud.resources.beans.TabItem;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2017/10/26
 * @Description:自定义tablayout指示器
 * @Modifier:
 * @ModifyContent:
 */
public class TabLayoutIndicator extends RelativeLayout {

    private int tabIndicatorColor = 0;
    private int tabIndicatorSelectedColor = 0;
    private int tabIndicatorType = 1;
    private int tabSplitLineColor = 0;
    private int tabIndicatorNumber = 0;
    private int tabIndicatorWidth = 0;
    private int tabIndicatorHeight = 0;
    private int currentItem = 0;
    private int tabIndicatorSpacing = 0;
    private int tabItemTextColor = 0;
    private float tabItemTextSize = 0;
    private int tabItemTextSelectedColor = 0;
    private int tabSplitLineHeight = 0;
    private int tabLayoutBackgroundColor = 0;
    private boolean tabItemTextBlod = false;
    private int tabIndicatorContentType = 0;
    private int TINY_INDICATOR_CONTAINER_ID = 669740;
    private int TAB_INDICATOR_ID = 1525976109;
    private int TAB_LAYOUT_CONTAINER_ID = 1626604282;
    private int contentFrameLayoutId = 0;
    private int TAB_CONTAINER_SPLIT_TOP_ID = 137354059;
    private int TAB_CONTAINER_SPLIT_BOTTOM_ID = 1917471708;
    private int tabLayoutTopSplitLineColor = 0;
    private int tabLayoutBottomSplitLineColor = 0;
    private List<TabItem> tabItems = new ArrayList<TabItem>();
    private List<Fragment> fragments = new ArrayList<Fragment>();
    private ViewPager viewPager = null;
    private FragmentManager fragmentManager = null;
    private TabFragmentsUtils tabFragmentsUtils = new TabFragmentsUtils();
    private OnTablayoutIndicatorListener onTablayoutIndicatorListener = null;
    private OnTablayoutIndicatorScorllListener onTablayoutIndicatorScorllListener = null;
    private boolean isViewFromObject = false;
    private boolean isPerformClick = false;

    public TabLayoutIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        try {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.TabLayoutIndicator, 0, 0);
            tabIndicatorColor = a.getResourceId(R.styleable.TabLayoutIndicator_tli_tabIndicatorColor, 0);
            tabIndicatorSelectedColor = a.getResourceId(R.styleable.TabLayoutIndicator_tli_tabIndicatorSelectedColor, 0);
            tabIndicatorType = a.getInt(R.styleable.TabLayoutIndicator_tli_tabIndicatorType, 1);
            tabSplitLineColor = a.getResourceId(R.styleable.TabLayoutIndicator_tli_tabSplitLineColor, 0);
            tabSplitLineHeight = (int) a.getDimension(R.styleable.TabLayoutIndicator_tli_tabSplitLineHeight, 0);
            tabIndicatorWidth = (int) a.getDimension(R.styleable.TabLayoutIndicator_tli_tabIndicatorWidth, 1);
            tabIndicatorHeight = (int) a.getDimension(R.styleable.TabLayoutIndicator_tli_tabIndicatorHeight, 1);
            tabIndicatorSpacing = (int) a.getDimension(R.styleable.TabLayoutIndicator_tli_tabIndicatorSpacing, 4);
            tabItemTextColor = a.getColor(R.styleable.TabLayoutIndicator_tli_tabItemTextColor, 0);
            tabItemTextSize = a.getDimension(R.styleable.TabLayoutIndicator_tli_tabItemTextSize, 0);
            tabItemTextSelectedColor = a.getColor(R.styleable.TabLayoutIndicator_tli_tabItemTextSelectedColor, 0);
            tabLayoutBackgroundColor = a.getResourceId(R.styleable.TabLayoutIndicator_tli_tabLayoutBackgroundColor, 0);
            tabItemTextBlod = a.getBoolean(R.styleable.TabLayoutIndicator_tli_tabItemTextBlod, false);
            tabIndicatorContentType = a.getInt(R.styleable.TabLayoutIndicator_tli_tabIndicatorContentType, 1);
            tabLayoutTopSplitLineColor = a.getResourceId(R.styleable.TabLayoutIndicator_tli_tabLayoutTopSplitLineColor, 0);
            tabLayoutBottomSplitLineColor = a.getResourceId(R.styleable.TabLayoutIndicator_tli_tabLayoutBottomSplitLineColor, 0);
            isViewFromObject = a.getBoolean(R.styleable.TabLayoutIndicator_tli_tabIndicatorIsViewFromObject, false);
            a.recycle();
            this.setGravity(Gravity.CENTER_VERTICAL);
            List<TabItem> mlst = new ArrayList<TabItem>();
            mlst.add(new TabItem("1", "Tab1"));
            mlst.add(new TabItem("2", "Tab2"));
            mlst.add(new TabItem("3", "Tab3"));
            build(mlst);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setTabIndicatorNumber(int tabIndicatorNumber) {
        this.tabIndicatorNumber = tabIndicatorNumber;
    }

    public void setContentFrameLayoutId(int contentFrameLayoutId) {
        this.contentFrameLayoutId = contentFrameLayoutId;
    }

    public void setCurrentItem(int position) {
        try {
            if (position < 0) {
                position = 0;
            }
            this.currentItem = position;
            if (tabIndicatorContentType == 1) {
                LinearLayout row = (LinearLayout) this.findViewById(TINY_INDICATOR_CONTAINER_ID);
                if (row == null) {
                    return;
                }
                int childCount = row.getChildCount();
                if (childCount == 0) {
                    return;
                }
                if (position >= childCount) {
                    currentItem = childCount - 1;
                }
                for (int i = 0; i < childCount; i++) {
                    View childAt = row.getChildAt(i);
                    if (i == currentItem) {
                        childAt.setBackgroundResource(tabIndicatorSelectedColor);
                    } else {
                        childAt.setBackgroundResource(tabIndicatorColor);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setOnTablayoutIndicatorScorllListener(OnTablayoutIndicatorScorllListener listener) {
        this.onTablayoutIndicatorScorllListener = listener;
    }

    private View buildTinyIndicatorView(int position) {
        LinearLayout.LayoutParams vparam = new LinearLayout.LayoutParams(tabIndicatorWidth, tabIndicatorHeight);
        if (position > 0) {
            vparam.setMargins(tabIndicatorSpacing, 0, 0, 0);
        }
        View view = new View(getContext());
        view.setLayoutParams(vparam);
        view.setBackgroundResource(tabIndicatorColor);
        return view;
    }

    private void buildTinyIndicators() {
        try {
            LayoutParams vparam = new LayoutParams(
                    LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
            LinearLayout row = new LinearLayout(getContext());
            row.setLayoutParams(vparam);
            row.setOrientation(LinearLayout.HORIZONTAL);
            row.setGravity(Gravity.CENTER);
            row.setId(TINY_INDICATOR_CONTAINER_ID);
            for (int i = 0; i < tabIndicatorNumber; i++) {
                View view = buildTinyIndicatorView(i);
                if (currentItem == i) {
                    view.setBackgroundResource(tabIndicatorSelectedColor);
                } else {
                    view.setBackgroundResource(tabIndicatorColor);
                }
                row.addView(view);
            }
            this.addView(row);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private LinearLayout createTabItems(ViewPager viewPager,
                                        List<TabItem> tabItems) {
        LayoutParams vparam = new LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        vparam.addRule(RelativeLayout.ABOVE, TINY_INDICATOR_CONTAINER_ID);
        vparam.addRule(RelativeLayout.BELOW, TAB_CONTAINER_SPLIT_TOP_ID);
        LinearLayout row = new LinearLayout(getContext());
        row.setLayoutParams(vparam);
        row.setOrientation(LinearLayout.HORIZONTAL);
        row.setGravity(Gravity.CENTER);
        row.setId(TAB_LAYOUT_CONTAINER_ID);
        row.setBackgroundResource(tabLayoutBackgroundColor);
        tabFragmentsUtils.setContentFlId(contentFrameLayoutId);
        tabFragmentsUtils.setOnTablayoutIndicatorListener(onTablayoutIndicatorListener);
        tabFragmentsUtils.setTabItems(tabItems);
        for (int i = 0; i < tabItems.size(); i++) {
            TabItem tabItem = tabItems.get(i);
            TextView item = createTabItem();
            item.setText(tabItem.getName());
            if (viewPager != null || contentFrameLayoutId != 0) {
                TopTabs.FragmentTabClickListener tabClickListener = topTabs.new FragmentTabClickListener(viewPager,
                        tabIndicatorContentType,
                        i,
                        tabFragmentsUtils,
                        fragmentManager);
                item.setOnClickListener(tabClickListener);
            }
            if (i == currentItem) {
                item.setTextColor(tabItemTextSelectedColor);
            } else {
                item.setTextColor(tabItemTextColor);
            }
            if (i > 0) {
                row.addView(createTabSplit());
            } else {
                tabIndicatorWidth = (int) (tabItem.getName().length() * tabItemTextSize + 24);
                int itemWidth = GlobalUtils.getScreenWidth(getContext()) / tabItems.size();
                if (tabIndicatorWidth > itemWidth) {
                    tabIndicatorWidth = itemWidth;
                }
                ImageView imageView = (ImageView) findViewById(TAB_INDICATOR_ID);
                LinearLayout.LayoutParams imageViewLayoutParams = (LinearLayout.LayoutParams) imageView.getLayoutParams();
                imageViewLayoutParams.width = tabIndicatorWidth;
                imageViewLayoutParams.setMargins((itemWidth - tabIndicatorWidth) / 2, 0, 0, 0);
            }
            row.addView(item);
        }
        return row;
    }

    private TextView createTabItem() {
        LinearLayout.LayoutParams vparam = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT);
        vparam.weight = 1;
        TextView textView = new TextView(getContext());
        textView.setLayoutParams(vparam);
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(tabItemTextColor);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, PixelUtils.px2sp(getContext(), tabItemTextSize));
        textView.setSingleLine(true);
        if (tabItemTextBlod) {
            TextPaint paint = textView.getPaint();
            paint.setFakeBoldText(true);
        }
        return textView;
    }

    public List<TabItem> getTabItems() {
        return this.tabItems;
    }

    public void setViewPager(ViewPager viewPager) {
        this.viewPager = viewPager;
    }

    public void setOnTablayoutIndicatorListener(OnTablayoutIndicatorListener listener) {
        this.onTablayoutIndicatorListener = listener;
    }

    public void build() {
        this.removeAllViews();
        build(this.tabItems);
    }

    private void build(List<TabItem> tabItems) {
        try {
            if (tabIndicatorType == 2) {
                setTabIndicatorNumber(this.tabIndicatorNumber);
                buildTinyIndicators();
                setCurrentItem(this.currentItem);
            } else {
                topTabs.setTabnum(tabItems.size());
                this.addView(createTabContainerTopSplit());
                this.addView(createTabContainerBottomSplit());
                this.addView(createTabItemIndicator());
                this.addView(createTabItems(viewPager, tabItems));
                ImageView cursorIv = (ImageView) findViewById(TAB_INDICATOR_ID);
                topTabs.initTabLineWidth(cursorIv, topTabs.getTabnum(), tabIndicatorWidth);
                if (tabIndicatorContentType == 1) {
                    if (viewPager != null && fragmentManager != null) {
                        viewPager.setAdapter(new TabLayoutFragmentPagerAdapter(tabItems));
                        viewPager.setCurrentItem(0);
                        viewPager.setOnPageChangeListener(topTabs.new OnTabsPageChangeListener(cursorIv, topTabs.getTabnum()));
                        viewPager.setOffscreenPageLimit(topTabs.getTabnum());
                    }
                }
                performClickCurrItem(currentItem);
            }
        } catch (Exception e) {
            Logger.L.error(e);
        }
    }

    public void performClickCurrItem(int position) {
        try {
            LinearLayout container = (LinearLayout) findViewById(TAB_LAYOUT_CONTAINER_ID);
            if (container == null) {
                return;
            }
            int itemPosition = 0;
            int childCount = container.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View childAt = container.getChildAt(i);
                if (childAt instanceof TextView) {
                    TextView textView = (TextView) childAt;
                    if (position == itemPosition) {
                        //textView.performClick();带有声音效果
                        textView.callOnClick();
                        break;
                    }
                    itemPosition++;
                }
            }
        } catch (Exception e) {
            Logger.L.error(e);
        }
    }

    private View createTabContainerTopSplit() {
        LayoutParams vparam = new LayoutParams(LayoutParams.MATCH_PARENT, 1);
        vparam.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        View view = new View(getContext());
        view.setLayoutParams(vparam);
        view.setId(TAB_CONTAINER_SPLIT_TOP_ID);
        view.setBackgroundResource(tabLayoutTopSplitLineColor);
        return view;
    }

    private View createTabContainerBottomSplit() {
        LayoutParams vparam = new LayoutParams(LayoutParams.MATCH_PARENT, 1);
        vparam.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        View view = new View(getContext());
        view.setLayoutParams(vparam);
        view.setId(TAB_CONTAINER_SPLIT_BOTTOM_ID);
        view.setBackgroundResource(tabLayoutBottomSplitLineColor);
        return view;
    }

    private View createTabSplit() {
        LayoutParams vparam = new LayoutParams(PixelUtils.dip2px(getContext(), 1), tabSplitLineHeight);
        View view = new View(getContext());
        view.setLayoutParams(vparam);
        view.setBackgroundResource(tabSplitLineColor);
        return view;
    }

    private LinearLayout createTabItemIndicator() {
        LayoutParams vparam = new LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        vparam.addRule(RelativeLayout.ABOVE, TAB_CONTAINER_SPLIT_BOTTOM_ID);
        LinearLayout row = new LinearLayout(getContext());
        row.setLayoutParams(vparam);
        row.setId(TINY_INDICATOR_CONTAINER_ID);
        row.setOrientation(LinearLayout.HORIZONTAL);
        row.setBackgroundResource(tabLayoutBackgroundColor);

        LinearLayout.LayoutParams ivparam = new LinearLayout.LayoutParams(
                tabIndicatorWidth,
                tabIndicatorHeight
        );
        ImageView imageView = new ImageView(getContext());
        imageView.setLayoutParams(ivparam);
        imageView.setBackgroundResource(tabIndicatorColor);
        imageView.setId(TAB_INDICATOR_ID);
        row.addView(imageView);
        return row;
    }

    public void setFragmentManager(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    private TopTabs topTabs = new TopTabs() {
        @Override
        protected void onTabChanged(int position) {
            try {
                LinearLayout container = (LinearLayout) findViewById(TAB_LAYOUT_CONTAINER_ID);
                if (container == null) {
                    return;
                }
                int itemPosition = 0;
                int childCount = container.getChildCount();
                for (int i = 0; i < childCount; i++) {
                    View childAt = container.getChildAt(i);
                    if (childAt instanceof TextView) {
                        TextView textView = (TextView) childAt;
                        if (position == itemPosition) {
                            textView.setTextColor(tabItemTextSelectedColor);
                        } else {
                            textView.setTextColor(tabItemTextColor);
                        }
                        itemPosition++;
                    }
                }
                if (onTablayoutIndicatorScorllListener != null) {
                    TabItem tabItem = tabItems.get(position);
                    Fragment fragment = null;
                    List<Fragment> loadedFragments = getLoadedFragments();
                    if (!ObjectJudge.isNullOrEmpty(loadedFragments) && position < loadedFragments.size()) {
                        fragment = loadedFragments.get(position);
                        onTablayoutIndicatorScorllListener.onPageSelected(position, tabItem, fragment);
                    }
                }
            } catch (Exception e) {
                Logger.L.error(e);
            }
        }
    };

    class TabLayoutFragmentPagerAdapter extends PagerAdapter {

        private List<TabItem> tabItems = null;

        public TabLayoutFragmentPagerAdapter(List<TabItem> tabItems) {
            this.tabItems = tabItems;
        }

        @Override
        public int getCount() {
            return tabItems.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return isViewFromObject ? true : (view == object);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            try {
                if (fragments.size() > position) {
                    Fragment fragment = fragments.get(position);
                    container.removeView(fragment.getView());
                }
            } catch (Exception e) {
                Logger.L.error(e);
            }
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            Fragment fragment = null;
            try {
                if (fragments.size() > position) {
                    fragment = fragments.get(position);
                } else {
                    if (onTablayoutIndicatorListener != null) {
                        TabItem tabItem = tabItems.get(position);
                        Bundle bundle = new Bundle();
                        bundle.putInt("POSITION", position);
                        fragment = onTablayoutIndicatorListener.onBuildFragment(position, tabItem, bundle);
                        fragment.setArguments(bundle);
                        fragments.add(fragment);
                        if (onTablayoutIndicatorScorllListener != null) {
                            onTablayoutIndicatorScorllListener.onPageSelected(position, tabItem, fragment);
                        }
                    }
                }
                //如果fragment还没有added
                if (!fragment.isAdded() && fragmentManager != null) {
                    FragmentTransaction ft = fragmentManager.beginTransaction();
                    ft.add(fragment, String.format("tab_layout_indicator_%s", position));
                    ft.commit();
                    /**
                     * 在用FragmentTransaction.commit()方法提交FragmentTransaction对象后
                     * 会在进程的主线程中，用异步的方式来执行。
                     * 如果想要立即执行这个等待中的操作，就要调用这个方法（只能在主线程中调用）。
                     * 要注意的是，所有的回调和相关的行为都会在这个调用中被执行完成，因此要仔细确认这个方法的调用位置。
                     */
                    fragmentManager.executePendingTransactions();
                }
                if (fragment.getView().getParent() == null) {
                    container.addView(fragment.getView()); // 为viewpager增加布局
                }
            } catch (Exception e) {
                Logger.L.error(e);
            }
            return fragment.getView();
        }
    }

    public List<Fragment> getLoadedFragments() {
        if (tabIndicatorContentType == 1) {
            return this.fragments;
        } else {
            return this.tabFragmentsUtils.getFragments();
        }
    }
}
