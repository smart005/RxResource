package com.cloud.resources.refresh;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.HeaderViewListAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.cloud.core.beans.BaseBean;
import com.cloud.core.logger.Logger;
import com.cloud.resources.R;
import com.cloud.resources.refresh.api.RefreshLayout;
import com.cloud.resources.refresh.footer.ClassicsFooter;
import com.cloud.resources.refresh.header.ClassicsHeader;
import com.cloud.resources.refresh.listener.OnRefreshLoadmoreListener;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2017/11/23
 * @Description:listview刷新视图
 * @Modifier:
 * @ModifyContent:
 */
public class XRefreshRecyclerView extends RelativeLayout {

    private SmartRefreshLayout xRefreshSrfl = null;
    private RecyclerView xRefreshListLv = null;
    private OnXListViewListener mListViewListener;
    private OnXListViewItemClickListener onXListViewItemClickListener = null;

    public XRefreshRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        try {
            View view = View.inflate(context, R.layout.x_refresh_recycler_view, null);
            xRefreshSrfl = (SmartRefreshLayout) view.findViewById(R.id.x_refresh_srfl);
            xRefreshSrfl.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
                @Override
                public void onLoadmore(RefreshLayout refreshlayout) {
                    if (mListViewListener != null) {
                        mListViewListener.onLoadMore();
                    }
                }

                @Override
                public void onRefresh(RefreshLayout refreshlayout) {
                    if (mListViewListener != null) {
                        mListViewListener.onRefresh();
                    }
                }
            });
            xRefreshListLv = (RecyclerView) view.findViewById(R.id.x_refresh_recycler_rlv);
            LayoutParams rlparam = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
            this.addView(view, rlparam);
        } catch (Exception e) {
            Logger.L.error(e);
        }
    }

    public void setXListViewListener(OnXListViewListener listener) {
        this.mListViewListener = listener;
    }

    public ClassicsHeader getRefreshHeader() {
        ClassicsHeader refreshHeader = (ClassicsHeader) xRefreshSrfl.getRefreshHeader();
        return refreshHeader;
    }

    public ClassicsFooter getClassicsFooter() {
        ClassicsFooter refreshFooter = (ClassicsFooter) xRefreshSrfl.getRefreshFooter();
        return refreshFooter;
    }

    public SmartRefreshLayout getSmartRefreshLayout() {
        return xRefreshSrfl;
    }

    public void setAdapter(RecyclerView.Adapter adapter) {
        if (xRefreshListLv == null || adapter == null) {
            return;
        }
        xRefreshListLv.setAdapter(adapter);
    }

    public void setPullRefreshEnable(boolean enable) {
        xRefreshSrfl.setEnableRefresh(enable);
    }

    public void setPullLoadEnable(boolean enable) {
        xRefreshSrfl.setEnableLoadmore(enable);
    }

    public void refresh() {
        if (mListViewListener != null) {
            mListViewListener.onRefresh();
        }
    }

    public void initRL() {
        xRefreshSrfl.finishRefresh();
        xRefreshSrfl.finishLoadmore();
    }

    /**
     * 检测实图控件加载状态(业务数据完成后调用此方法)
     *
     * @param baseBean
     * @param isEnableLoad 是否启用加载
     */
    public void checkViewLoadStatus(BaseBean baseBean, boolean isEnableLoad) {
        try {
            if (baseBean == null) {
                this.setPullLoadEnable(false);
                return;
            }
            if (baseBean.isLastPage() && !baseBean.isHasNextPage()) {
                this.setPullLoadEnable(false);
            } else {
                if (baseBean.getPageNum() > baseBean.getPages()) {
                    this.setPullLoadEnable(false);
                } else {
                    if (baseBean.getPageNum() == baseBean.getLastPage()) {
                        this.setPullLoadEnable(false);
                    } else {
                        if (!isEnableLoad) {
                            this.setPullLoadEnable(false);
                        }
                    }
                }
            }
        } catch (Exception e) {
            Logger.L.error(e);
        }
    }

    /**
     * 检测实图控件加载状态(业务数据完成后调用此方法)
     *
     * @param baseBean
     */
    public void checkViewLoadStatus(BaseBean baseBean) {
        checkViewLoadStatus(baseBean, true);
    }

    public void setOnXListViewItemClickListener(OnXListViewItemClickListener listener) {
        this.onXListViewItemClickListener = listener;
    }

    public RecyclerView getRecyclerView() {
        return this.xRefreshListLv;
    }
}
