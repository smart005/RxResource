package com.cloud.resources.tabindicator;

import android.support.v4.app.Fragment;

import com.cloud.resources.beans.TabItem;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2017/10/26
 * @Description:
 * @Modifier:
 * @ModifyContent:
 */
public interface OnTablayoutIndicatorScorllListener {
    public void onPageSelected(int position, TabItem tabItem, Fragment fragment);
}
