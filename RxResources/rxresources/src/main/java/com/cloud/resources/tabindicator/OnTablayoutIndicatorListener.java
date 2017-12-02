package com.cloud.resources.tabindicator;

import android.os.Bundle;
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
public interface OnTablayoutIndicatorListener {
    public Fragment onBuildFragment(int position, TabItem tabItem, Bundle bundle);
}
