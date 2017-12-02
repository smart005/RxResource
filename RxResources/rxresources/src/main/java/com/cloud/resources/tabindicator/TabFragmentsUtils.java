package com.cloud.resources.tabindicator;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.cloud.core.ObjectJudge;
import com.cloud.core.logger.Logger;
import com.cloud.resources.beans.TabItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2017/10/18
 * @Description:
 * @Modifier:
 * @ModifyContent:
 */
class TabFragmentsUtils {

    private int currTabIndex = 0;
    private boolean isFirst = true;
    private int contentFlId = 0;
    private HashMap<String, Fragment> fragmentlist = new HashMap<String, Fragment>();
    private List<Fragment> fragments = new ArrayList<Fragment>();
    private OnTablayoutIndicatorListener onTablayoutIndicatorListener = null;
    private List<TabItem> tabItems = null;

    public void setOnTablayoutIndicatorListener(OnTablayoutIndicatorListener listener) {
        this.onTablayoutIndicatorListener = listener;
    }

    public void setTabItems(List<TabItem> tabItems) {
        this.tabItems = tabItems;
    }

    public void switchContent(int tabIndex, FragmentManager fm) {
        try {
            if (tabIndex == currTabIndex && !isFirst) {
                return;
            }
            isFirst = false;
            FragmentTransaction ft = fm.beginTransaction();
            Fragment f = getFragment(fm, ft, tabIndex);
            Fragment cf = fm.findFragmentByTag(getTabTag(currTabIndex));
            if (cf != null) {
                ft.hide(cf);
            }
            ft.show(f);
            ft.commitAllowingStateLoss();
            currTabIndex = tabIndex;
        } catch (Exception e) {
            Logger.L.error(e);
        }
    }

    public List<Fragment> getFragments() {
        return fragments;
    }

    private String getTabTag(int tabIndex) {
        return String.format("60699961_%s", tabIndex);
    }

    public void setContentFlId(int id) {
        this.contentFlId = id;
    }

    private Fragment getFragment(FragmentManager fm, FragmentTransaction ft, int tabIndex) {
        String tag = getTabTag(tabIndex);
        Fragment f = fm.findFragmentByTag(tag);
        boolean isAdd = false;
        if (f == null) {
            Bundle bundle = new Bundle();
            bundle.putInt("POSITION", tabIndex);
            if (onTablayoutIndicatorListener != null && !ObjectJudge.isNullOrEmpty(tabItems)) {
                TabItem tabItem = tabItems.get(tabIndex);
                f = onTablayoutIndicatorListener.onBuildFragment(tabIndex, tabItem, bundle);
                f.setArguments(bundle);
                fragments.add(f);
            }
            isAdd = true;
        } else {
            isAdd = false;
        }
        if (fragmentlist.containsKey(tag)) {
            f = fragmentlist.get(tag);
        }
        if (isAdd) {
            ft.add(contentFlId, f, tag);
        }
        return f;
    }
}
