package com.cloud.resources.flows;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cloud.core.ObjectJudge;
import com.cloud.core.beans.MapEntry;
import com.cloud.core.logger.Logger;
import com.cloud.core.utils.ConvertUtils;
import com.cloud.core.utils.PixelUtils;
import com.cloud.resources.R;
import com.cloud.resources.beans.FlowEditBean;
import com.cloud.resources.beans.FlowLayoutInstance;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2015-10-19 上午11:07:41
 * @Description: 流式可编辑项组件(类似多选联系人编辑控件)
 * @Modifier:
 * @ModifyContent:
 */
public class FlowEditItemsLayout extends LinearLayout {

    private List<MapEntry<String, FlowEditBean>> data = new ArrayList<MapEntry<String, FlowEditBean>>();
    private List<FlowEditBean> tags = new ArrayList<FlowEditBean>();
    private FlowLayout mflv = null;
    private FlowLayoutInstance mfli = new FlowLayoutInstance();
    private int ENABLE_CHECK_TAG_KEY = 915283048;
    private int ITEM_FLOW_LAYOUT_INSTANCE = 2079536775;
    private int ITEM_FLOW_LAYOUT_EDITABLE = 307368464;

    private OnFlowLayoutListener mflowLayoutListener = null;

    /**
     * @param listener
     */
    public void setOnFlowLayoutListener(OnFlowLayoutListener listener) {
        if (mflowLayoutListener != null) {
            return;
        }
        this.mflowLayoutListener = listener;
        changeInstanceSetting();
    }

    public FlowEditItemsLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(false, attrs);
    }

    public FlowEditItemsLayout(Context context) {
        super(context);
        init(true, null);
    }

    private void init(boolean flagparams, AttributeSet attrs) {
        if (flagparams) {
            LayoutParams params = new LayoutParams(
                    LayoutParams.MATCH_PARENT,
                    LayoutParams.WRAP_CONTENT);
            this.setLayoutParams(params);
        }
        int mpadding = PixelUtils.dip2px(getContext(), 3);
        this.setPadding(mpadding, mpadding, mpadding, mpadding);
    }

    private void changeInstanceSetting() {
        if (mflowLayoutListener != null) {
            mfli = mflowLayoutListener.getFlowLayoutInstance(mfli);
            mflv = (FlowLayout) View.inflate(getContext(), R.layout.flow_item_layout_view, null);
            LayoutParams flvparams = new LayoutParams(
                    LayoutParams.MATCH_PARENT,
                    LayoutParams.WRAP_CONTENT);
            this.addView(mflv, flvparams);
            mflv.setBackgroundResource(mfli.getFlowBackground());
        }
    }

    public void setEnableCheck(boolean isEnableCheck) {
        if (mfli != null) {
            mfli.setEnableCheck(isEnableCheck);
        }
    }

    public void setEnableDelete(boolean isEnableDelete) {
        if (mfli != null) {
            mfli.setEnableDelete(isEnableDelete);
        }
    }

    public void setSingleChecked(boolean isSingleChecked) {
        if (mfli != null) {
            mfli.setSingleChecked(isSingleChecked);
        }
    }

    public void setFlowItemPaddingLeft(int flowItemPaddingLeft) {
        if (mfli != null) {
            mfli.setFlowItemPaddingLeft(flowItemPaddingLeft);
        }
    }

    public void setFlowItemPaddingRight(int flowItemPaddingRight) {
        if (mfli != null) {
            mfli.setFlowItemPaddingRight(flowItemPaddingRight);
        }
    }

    public void setFlowItemPaddingBottom(int flowItemPaddingBottom) {
        if (mfli != null) {
            mfli.setFlowItemPaddingBottom(flowItemPaddingBottom);
        }
    }

    public void setFlowItemPaddingTop(int flowItemPaddingTop) {
        if (mfli != null) {
            mfli.setFlowItemPaddingTop(flowItemPaddingTop);
        }
    }

    public void setFlowBackground(int flowBackground) {
        if (mfli != null) {
            mfli.setFlowBackground(flowBackground);
        }
    }

    public void setFlowItemBackground(int flowItemBackground) {
        if (mfli != null) {
            mfli.setFlowItemBackground(flowItemBackground);
        }
    }

    public void setSelectedItemBackground(int selectedItemBackground) {
        if (mfli != null) {
            mfli.setSelectedItemBackground(selectedItemBackground);
        }
    }

    public void setTitleTextColor(int titleTextColor) {
        if (mfli != null) {
            mfli.setTitleTextColor(titleTextColor);
        }
    }

    public void setSelectedTitleTextColor(int selectedTitleTextColor) {
        if (mfli != null) {
            mfli.setSelectedTitleTextColor(selectedTitleTextColor);
        }
    }

    public void setOrSoSpacing(int orSoSpacing) {
        if (mfli != null) {
            mfli.setOrSoSpacing(orSoSpacing);
        }
    }

    public void setUpAndDownSpacing(int upAndDownSpacing) {
        if (mfli != null) {
            mfli.setUpAndDownSpacing(upAndDownSpacing);
        }
    }

    /**
     * 重置已构建的项
     */
    public void resetItem(int position, FlowLayoutInstance flowLayoutInstance, Object tagobj) {
        try {
            View itemView = findItem(position);
            if (itemView == null) {
                return;
            }
            itemView.setPadding(0,
                    flowLayoutInstance.getUpAndDownSpacing() > 0 ? flowLayoutInstance.getUpAndDownSpacing() : 0,
                    flowLayoutInstance.getOrSoSpacing() > 0 ? flowLayoutInstance.getOrSoSpacing() : 0, 0);
            View contentview = itemView.findViewById(R.id.flow_edit_content_rl);
            int orsosp = PixelUtils.dip2px(getContext(), 5);
            int udsp = PixelUtils.dip2px(getContext(), 3);
            int left = flowLayoutInstance.getFlowItemPaddingLeft() > 0 ? PixelUtils.dip2px(
                    getContext(), flowLayoutInstance.getFlowItemPaddingLeft()) : orsosp;
            int top = flowLayoutInstance.getFlowItemPaddingTop() > 0 ? PixelUtils.dip2px(getContext(),
                    flowLayoutInstance.getFlowItemPaddingTop()) : udsp;
            int right = flowLayoutInstance.getFlowItemPaddingRight() > 0 ? PixelUtils.dip2px(
                    getContext(), flowLayoutInstance.getFlowItemPaddingRight()) : orsosp;
            int bottom = flowLayoutInstance.getFlowItemPaddingBottom() > 0 ? PixelUtils.dip2px(
                    getContext(), flowLayoutInstance.getFlowItemPaddingBottom()) : udsp;
            contentview.setPadding(left, top, right, bottom);
            contentview.setBackgroundResource(flowLayoutInstance.getFlowItemBackground());
            TextView titleView = (TextView) contentview.findViewById(R.id.flow_title);
            titleView.setTextColor(flowLayoutInstance.getTitleTextColor());
            LinearLayout deleteView = (LinearLayout) contentview.findViewById(R.id.flow_delete);
            if (flowLayoutInstance.isEnableDelete()) {
                deleteView.setVisibility(View.VISIBLE);
            } else {
                deleteView.setVisibility(View.GONE);
            }
            if (tagobj != null) {
                contentview.setTag(tagobj);
            }
            contentview.setTag(ITEM_FLOW_LAYOUT_INSTANCE, flowLayoutInstance);
            if (flowLayoutInstance.isEnableCheck()) {
                contentview.setTag(ENABLE_CHECK_TAG_KEY, 0);
                contentview.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        checkItemDealWith(v);
                    }
                });
            } else {
                contentview.setOnClickListener(null);
            }
        } catch (Exception e) {
            Logger.L.error(e);
        }
    }

    public void resetItem(int position, FlowLayoutInstance flowLayoutInstance) {
        resetItem(position, flowLayoutInstance, null);
    }

    /**
     * 重置已构建的项
     */
    public void resetItems() {
        try {
            if (mflv == null || mflv.getChildCount() == 0) {
                return;
            }
            int childCount = mflv.getChildCount();
            for (int i = 0; i < childCount; i++) {
                resetItem(i, mfli);
            }
            if (mflv.getChildCount() > 1) {
                mflv.setBackgroundResource(mfli.getFlowBackground());
            }
        } catch (Exception e) {
            Logger.L.error(e);
        }
    }

    public FlowLayoutInstance getFlowConfig() {
        FlowLayoutInstance flowLayoutInstance = new FlowLayoutInstance();
        flowLayoutInstance.setEnableCheck(mfli.isEnableCheck());
        flowLayoutInstance.setEnableDelete(mfli.isEnableDelete());
        flowLayoutInstance.setSingleChecked(mfli.isSingleChecked());
        flowLayoutInstance.setFlowItemPaddingLeft(mfli.getFlowItemPaddingLeft());
        flowLayoutInstance.setFlowItemPaddingRight(mfli.getFlowItemPaddingRight());
        flowLayoutInstance.setFlowItemPaddingBottom(mfli.getFlowItemPaddingBottom());
        flowLayoutInstance.setFlowItemPaddingTop(mfli.getFlowItemPaddingTop());
        flowLayoutInstance.setFlowBackground(mfli.getFlowBackground());
        flowLayoutInstance.setFlowItemBackground(mfli.getFlowItemBackground());
        flowLayoutInstance.setSelectedItemBackground(mfli.getSelectedItemBackground());
        flowLayoutInstance.setTitleTextColor(mfli.getTitleTextColor());
        flowLayoutInstance.setSelectedTitleTextColor(mfli.getSelectedTitleTextColor());
        flowLayoutInstance.setOrSoSpacing(mfli.getOrSoSpacing());
        flowLayoutInstance.setUpAndDownSpacing(mfli.getUpAndDownSpacing());
        return flowLayoutInstance;
    }

    public void addItem(String title, Object tagobj) {
        addItem(title, tagobj, false);
    }

    public void addItem(String title, Object tagobj, boolean check) {
        addItem(title, tagobj, mfli, check);
    }

    public void addItem(String title, Object tagobj, FlowLayoutInstance flowLayoutInstance, boolean check) {
        try {
            if (mflowLayoutListener != null) {
                if (isContainsItem(title)) {
                    return;
                }
                final FlowEditable ea = new FlowEditable(getContext(), flowLayoutInstance);
                View itemview = ea.getItemView();
                View contentview = itemview.findViewById(R.id.flow_edit_content_rl);
                ea.getTitleView().setText(title);
                contentview.setTag(tagobj);
                ea.getDeleteView().setTag(itemview);
                ea.getDeleteView().setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            View itemv = (View) v.getTag();
                            if (itemv != null) {
                                int position = mflv.indexOfChild(itemv);
                                data.remove(position);
                                tags.remove(position);
                                mflv.removeView(itemv);
                            }
                            if (mflv.getChildCount() <= 1) {
                                mflv.setBackgroundResource(0);
                            }
                            if (mflowLayoutListener != null) {
                                mflowLayoutListener.deleteItem(itemv.getTag());
                            }
                        } catch (Exception e) {
                            Logger.L.error(e);
                        }
                    }
                });
                if (flowLayoutInstance.isEnableDelete()) {
                    ea.getDeleteView().setVisibility(View.VISIBLE);
                } else {
                    ea.getDeleteView().setVisibility(View.GONE);
                }
                if (flowLayoutInstance.isEnableCheck()) {
                    contentview.setTag(ENABLE_CHECK_TAG_KEY, 0);
                }
                MapEntry<String, FlowEditBean> mentry = new MapEntry<String, FlowEditBean>();
                mentry.setKey(title);
                mentry.setValue(new FlowEditBean(title, tagobj, itemview));
                if (check) {
                    TextView chktextview = (TextView) contentview.findViewById(R.id.flow_title);
                    contentview.setTag(ENABLE_CHECK_TAG_KEY, 1);
                    if (flowLayoutInstance.getSelectedItemBackground() != 0) {
                        contentview.setBackgroundResource(flowLayoutInstance.getSelectedItemBackground());
                        chktextview.setTextColor(flowLayoutInstance.getSelectedTitleTextColor());
                    }
                }
                data.add(mentry);
                tags.add(new FlowEditBean(title, tagobj, contentview, ea.getTitleView()));
                contentview.setTag(ITEM_FLOW_LAYOUT_INSTANCE, flowLayoutInstance);
                contentview.setTag(ITEM_FLOW_LAYOUT_EDITABLE, ea);
                if (flowLayoutInstance.isEnableCheck()) {
                    contentview.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            checkItemDealWith(v);
                        }
                    });
                }
                mflv.addView(itemview);
                if (mflv.getChildCount() > 1) {
                    mflv.setBackgroundResource(flowLayoutInstance.getFlowBackground());
                }
                if (mfli.getLayoutDirection() == LayoutDirection.rtl) {
                    if (mflv.getChildCount() == 1) {
                        final FlowEditable fempty = new FlowEditable(getContext(), flowLayoutInstance);
                        View feitemview = fempty.getItemView();
                        fempty.getTitleView().setText("1");
                        feitemview.setId(116912274);
                        feitemview.setVisibility(INVISIBLE);
                        mflv.addView(feitemview, 0);
                    } else {
                        View empty = mflv.findViewById(116912274);
                        if (empty != null) {
                            mflv.removeView(empty);
                        }
                    }
                }
                if (data.size() == 1) {
                    mflv.measure(0, 0);
                    this.getLayoutParams().height = mflv.getMeasuredHeight() +
                            this.getPaddingTop() + this.getPaddingBottom() +
                            PixelUtils.dip2px(getContext(), 3);
                } else {
                    this.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
                }
            }
        } catch (Exception e) {
            Logger.L.error(e);
        }
    }

    private void checkItemDealWith(View v) {
        try {
            if (v.getTag() == null) {
                return;
            }
            FlowLayoutInstance itemInstance = (FlowLayoutInstance) v.getTag(ITEM_FLOW_LAYOUT_INSTANCE);
            if (itemInstance.isEnableCheck()) {
                int checktag = ConvertUtils.toInt(v.getTag(ENABLE_CHECK_TAG_KEY));
                if (itemInstance.isSingleChecked()) {
                    List<FlowEditBean> selitems = getFlowEditBeanSelectedItems();
                    if (!ObjectJudge.isNullOrEmpty(selitems)) {
                        clearSelectedItems(selitems, itemInstance);
                    }
                }
                FlowEditable flowEditable = (FlowEditable) v.getTag(ITEM_FLOW_LAYOUT_EDITABLE);
                setItemCheckStatus(v,
                        itemInstance.isLeastSelected() ? 0 : checktag,
                        flowEditable.getTitleView(),
                        itemInstance);
            }
            if (mflowLayoutListener != null) {
                mflowLayoutListener.onItemClickListener(v.getTag());
            }
        } catch (Exception e) {
            Logger.L.error(e);
        }
    }

    private void setItemCheckStatus(View v, int checktag, TextView tv, FlowLayoutInstance flowLayoutInstance) {
        if (checktag == 1) {
            v.setTag(ENABLE_CHECK_TAG_KEY, 0);
            v.setBackgroundResource(flowLayoutInstance.getFlowItemBackground());
            tv.setTextColor(flowLayoutInstance.getTitleTextColor());
        } else {
            v.setTag(ENABLE_CHECK_TAG_KEY, 1);
            if (flowLayoutInstance.getSelectedItemBackground() != 0) {
                v.setBackgroundResource(flowLayoutInstance.getSelectedItemBackground());
                tv.setTextColor(flowLayoutInstance.getSelectedTitleTextColor());
            }
        }
    }

    private boolean isContainsItem(String key) {
        boolean flag = false;
        for (MapEntry<String, FlowEditBean> mentry : data) {
            if (TextUtils.equals(mentry.getKey().trim(), key.trim())) {
                flag = true;
                break;
            }
        }
        return flag;
    }

    public View findItem(int position) {
        try {
            if (position < 0) {
                position = 0;
            }
            if (ObjectJudge.isNullOrEmpty(data) || data.size() < (position + 1)) {
                return null;
            }
            MapEntry<String, FlowEditBean> entry = data.get(position);
            FlowEditBean value = entry.getValue();
            return value.getView();
        } catch (Exception e) {
            Logger.L.error(e);
        }
        return null;
    }

    public void deleteItem(int position) {
        try {
            if (position < 0) {
                position = 0;
            }
            if (ObjectJudge.isNullOrEmpty(data) || data.size() < (position + 1)) {
                return;
            }
            FlowEditBean removeItem = null;
            for (int i = 0; i < data.size(); i++) {
                if (i == position) {
                    MapEntry<String, FlowEditBean> entry = data.get(i);
                    removeItem = entry.getValue();
                    break;
                }
            }
            if (removeItem != null) {
                data.remove(position);
                tags.remove(position);
                mflv.removeView(removeItem.getView());
            }
            if (mflv.getChildCount() <= 1) {
                mflv.setBackgroundResource(0);
            }
        } catch (Exception e) {
            Logger.L.error(e);
        }
    }

    public List<FlowEditBean> getAllItems() {
        return tags;
    }

    public void deleteItems() {
        if (mflv != null) {
            mflv.removeAllViews();
            data.clear();
            tags.clear();
        }
    }

    public List<FlowEditBean> getFlowEditBeanSelectedItems() {
        List<FlowEditBean> lst = new ArrayList<FlowEditBean>();
        try {
            for (MapEntry<String, FlowEditBean> mentry : data) {
                View v = mentry.getValue().getView().findViewById(R.id.flow_edit_content_rl);
                int checktag = ConvertUtils.toInt(v.getTag(ENABLE_CHECK_TAG_KEY));
                if (checktag == 1) {
                    lst.add(mentry.getValue());
                }
            }
        } catch (Exception e) {
            Logger.L.error(e);
        }
        return lst;
    }

    public void setSelectedItems(List<String> selectedTitles) {
        try {
            List<FlowEditBean> selitems = getFlowEditBeanSelectedItems();
            for (FlowEditBean flowEditBean : selitems) {
                View v = flowEditBean.getView().findViewById(R.id.flow_edit_content_rl);
                FlowLayoutInstance flowLayoutInstance = (FlowLayoutInstance) v.getTag(ITEM_FLOW_LAYOUT_INSTANCE);
                TextView textview = (TextView) v.findViewById(R.id.flow_title);
                v.setTag(ENABLE_CHECK_TAG_KEY, 0);
                v.setBackgroundResource(flowLayoutInstance.getFlowItemBackground());
                textview.setTextColor(flowLayoutInstance.getTitleTextColor());
            }
            for (String item : selectedTitles) {
                for (MapEntry<String, FlowEditBean> mentry : data) {
                    if (TextUtils.equals(item, mentry.getKey().trim())) {
                        View v = mentry.getValue().getView().findViewById(R.id.flow_edit_content_rl);
                        FlowLayoutInstance flowLayoutInstance = (FlowLayoutInstance) v.getTag(ITEM_FLOW_LAYOUT_INSTANCE);
                        TextView textview = (TextView) v.findViewById(R.id.flow_title);
                        v.setTag(ENABLE_CHECK_TAG_KEY, 1);
                        if (mfli.getSelectedItemBackground() != 0) {
                            v.setBackgroundResource(flowLayoutInstance.getSelectedItemBackground());
                            textview.setTextColor(flowLayoutInstance.getSelectedTitleTextColor());
                        }
                    }
                }
            }
        } catch (Exception e) {
            Logger.L.error(e);
        }
    }

    public void setSelectedItem(String selectedTitle) {
        List<String> lst = new ArrayList<String>();
        lst.add(selectedTitle);
        setSelectedItems(lst);
    }

    private void clearSelectedItems(List<FlowEditBean> lst, FlowLayoutInstance flowLayoutInstance) {
        try {
            if (ObjectJudge.isNullOrEmpty(lst)) {
                return;
            }
            for (FlowEditBean flowEditBean : lst) {
                View v = flowEditBean.getView().findViewById(R.id.flow_edit_content_rl);
                TextView textview = (TextView) v.findViewById(R.id.flow_title);
                v.setTag(ENABLE_CHECK_TAG_KEY, 0);
                v.setBackgroundResource(flowLayoutInstance.getFlowItemBackground());
                textview.setTextColor(flowLayoutInstance.getTitleTextColor());
            }
        } catch (Exception e) {
            Logger.L.error(e);
        }
    }

    public void clearSelectItems() {
        try {
            List<FlowEditBean> selitems = getFlowEditBeanSelectedItems();
            if (ObjectJudge.isNullOrEmpty(selitems)) {
                return;
            }
            for (FlowEditBean flowEditBean : selitems) {
                View v = flowEditBean.getView().findViewById(R.id.flow_edit_content_rl);
                FlowLayoutInstance itemInstance = (FlowLayoutInstance) v.getTag(ITEM_FLOW_LAYOUT_INSTANCE);
                TextView textview = (TextView) v.findViewById(R.id.flow_title);
                v.setTag(ENABLE_CHECK_TAG_KEY, 0);
                v.setBackgroundResource(itemInstance.getFlowItemBackground());
                textview.setTextColor(itemInstance.getTitleTextColor());
            }
        } catch (Exception e) {
            Logger.L.error("clear all selected items error:", e);
        }
    }

    public <T> List<T> getSelectedItems() {
        List<T> lst = new ArrayList<T>();
        try {
            List<FlowEditBean> selitems = getFlowEditBeanSelectedItems();
            for (FlowEditBean flowEditBean : selitems) {
                Object obj = flowEditBean.getView().findViewById(R.id.flow_edit_content_rl).getTag();
                if (obj != null) {
                    lst.add((T) obj);
                }
            }
        } catch (Exception e) {
            Logger.L.error(e);
        }
        return lst;
    }
}
