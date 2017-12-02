package com.cloud.resources.tabindicator;

import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.cloud.core.utils.GlobalUtils;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2016/7/8
 * @Description:
 * @Modifier:
 * @ModifyContent:
 */
class TopTabs {

    private int screenWidth = 0;
    private int currentItem = 0;
    private int tabnum = 0;
    private int indicatorWidth = 0;

    protected void onTabChanged(int position) {

    }

    public interface OnTabItemListener {
        public boolean onTabItemClick();
    }

    private OnTabItemListener onTabItemListener = null;

    public void setOnTabItemListener(OnTabItemListener listener) {
        onTabItemListener = listener;
    }

    public void setTabnum(int tabnum) {
        this.tabnum = tabnum;
    }

    public int getTabnum() {
        return this.tabnum;
    }

    /**
     * 设置滑动条的宽度为屏幕的1/n(根据Tab的个数而定)
     */
    public void initTabLineWidth(ImageView cursor, int tabnum, int indicatorWidth) {
        this.tabnum = tabnum;
        this.indicatorWidth = indicatorWidth;
        screenWidth = GlobalUtils.getScreenWidth(cursor.getContext());
        if (cursor != null) {
            LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) cursor
                    .getLayoutParams();
            int itemWidth = screenWidth / tabnum;
            if (indicatorWidth > itemWidth) {
                indicatorWidth = itemWidth;
            }
            lp.width = indicatorWidth > 0 ? indicatorWidth : itemWidth;
            cursor.setLayoutParams(lp);
        }
    }

    public class OnTabsPageChangeListener implements ViewPager.OnPageChangeListener {

        private ImageView mcursor = null;

        public OnTabsPageChangeListener(ImageView cursor, int tabnum) {
            this.mcursor = cursor;
            TopTabs.this.tabnum = tabnum;
        }

        /**
         * state滑动中的状态 有三种状态（0，1，2） 1：正在滑动 2：滑动完毕 0：什么都没做。
         */
        @Override
        public void onPageScrollStateChanged(int state) {

        }

        /**
         * position :当前页面，及你点击滑动的页面 offset:当前页面偏移的百分比 offsetPixels:当前页面偏移的像素位置
         */
        @Override
        public void onPageScrolled(int position, float offset, int offsetPixels) {
            switchSelector(mcursor, position, offset);
        }

        @Override
        public void onPageSelected(int position) {
            currentItem = position;
            onTabChanged(position);
        }
    }

    public void switchSelector(ImageView cursor, int position, float offset) {
        if (cursor != null) {
            LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) cursor
                    .getLayoutParams();
            double itemwidth = screenWidth * 1.0 / tabnum;
            double moffset = (itemwidth - indicatorWidth) / 2;
            if (currentItem == position) {
                lp.leftMargin = (int) (offset * itemwidth + currentItem * itemwidth + moffset);
            } else {
                lp.leftMargin = (int) (-(1 - offset) * itemwidth + currentItem * itemwidth + moffset);
            }
            cursor.setLayoutParams(lp);
        }
    }

    public class FragmentTabClickListener implements View.OnClickListener {
        private int index = 0;
        private ViewPager mVPager = null;
        private int tabContentType = 0;
        private TabFragmentsUtils fragmentsUtils = null;
        private FragmentManager fragmentManager = null;

        public FragmentTabClickListener(ViewPager mpager,
                                        int tabContentType,
                                        int index,
                                        TabFragmentsUtils fragmentsUtils,
                                        FragmentManager fragmentManager) {
            this.index = index;
            this.mVPager = mpager;
            this.tabContentType = tabContentType;
            this.fragmentsUtils = fragmentsUtils;
            this.fragmentManager = fragmentManager;
        }

        @Override
        public void onClick(View v) {
            if (onTabItemListener != null) {
                if (onTabItemListener.onTabItemClick()) {
                    if (tabContentType == 1) {
                        if (mVPager != null) {
                            mVPager.setCurrentItem(index);
                        }
                    } else {
                        fragmentsUtils.switchContent(index, fragmentManager);
                        onTabChanged(index);
                    }
                }
            } else {
                if (tabContentType == 1) {
                    if (mVPager != null) {
                        mVPager.setCurrentItem(index);
                    }
                } else {
                    fragmentsUtils.switchContent(index, fragmentManager);
                    onTabChanged(index);
                }
            }
        }
    }
}
