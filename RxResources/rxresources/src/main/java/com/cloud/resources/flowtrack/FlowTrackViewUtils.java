package com.cloud.resources.flowtrack;

import android.graphics.Canvas;
import android.text.StaticLayout;

import com.cloud.core.ObjectJudge;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2017/11/25
 * @Description:
 * @Modifier:
 * @ModifyContent:
 */
public class FlowTrackViewUtils {

    private List<FlowTrackOneItem> flowTrackOneItems = new ArrayList<FlowTrackOneItem>();
    private Hashtable<Integer, Object> canvasList = new Hashtable<Integer, Object>();
    private List<Integer> keys = new ArrayList<Integer>();
    private int contentHeight = 0;

    public Hashtable<Integer, Object> getCanvasList() {
        return canvasList;
    }

    public void setCanvasList(Hashtable<Integer, Object> canvasList) {
        this.canvasList = canvasList;
    }

    public List<Integer> getKeys() {
        return this.keys;
    }

    public int getContentHeight() {
        return contentHeight;
    }

    public void setContentHeight(int contentHeight) {
        this.contentHeight = contentHeight;
    }

    public void setFlowTrackOneItems(List<FlowTrackOneItem> flowTrackOneItems) {
        this.flowTrackOneItems.clear();
        if (!ObjectJudge.isNullOrEmpty(flowTrackOneItems)) {
            this.flowTrackOneItems.addAll(flowTrackOneItems);
        }
        this.canvasList.clear();
    }

    public List<FlowTrackOneItem> getFlowTrackOneItems() {
        return this.flowTrackOneItems;
    }

    protected void onPreview() {

    }

    public void onDraw(Canvas canvas) {
        if (ObjectJudge.isNullOrEmpty(canvasList)) {
            onPreview();
        }
        for (Integer key : keys) {
            Object o = canvasList.get(key);
            if (o instanceof CanvasParams) {
                CanvasParams value = (CanvasParams) o;
                drawStaticLayout(canvas, value.getStaticLayout(), value.getDx(), value.getDy());
            } else if (o instanceof FlowTrackNodeItem) {
                FlowTrackNodeItem value = (FlowTrackNodeItem) o;
                canvas.drawCircle(value.getCx(), value.getCy(), value.getRadius(), value.getPaint());
            } else if (o instanceof FlowTrackLineItem) {
                FlowTrackLineItem lineItem = (FlowTrackLineItem) o;
                canvas.drawLine(lineItem.getStartX(), lineItem.getStartY(), lineItem.getStopX(), lineItem.getStopY(), lineItem.getPaint());
            }
        }
    }

    private void drawStaticLayout(Canvas canvas, StaticLayout layout, int dx, int dy) {
        canvas.save();
        canvas.translate(dx, dy);
        layout.draw(canvas);
        canvas.restore();
    }

    protected void onDrawView() {

    }

    public int onMeasure() {
        if (!ObjectJudge.isNullOrEmpty(canvasList)) {
            return contentHeight;
        }
        canvasList.clear();
        onDrawView();
        return contentHeight;
    }
}
