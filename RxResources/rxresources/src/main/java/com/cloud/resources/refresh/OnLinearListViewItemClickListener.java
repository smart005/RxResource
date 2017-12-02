/**
 *
 */
package com.cloud.resources.refresh;

import android.view.View;
import android.widget.AdapterView;

/**
 *
 * @Author LIJINGHUAN
 * @Email:ljh0576123@163.com
 * @CreateTime:2016年3月7日 下午1:09:06
 * @Description:
 * @Modifier:
 * @ModifyContent:
 *
 */
public interface OnLinearListViewItemClickListener {
    public void onItemClick(BaseLinearListView parent, View view, int position, long id);
}