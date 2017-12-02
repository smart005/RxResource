package com.cloud.resources.flowtrack;

import android.graphics.Paint;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2017/11/25
 * @Description:
 * @Modifier:
 * @ModifyContent:
 */
public class FlowTrackLineItem {
    private int startX = 0;
    private int startY = 0;
    private int stopX = 0;
    private int stopY = 0;
    private Paint paint = null;

    public int getStartX() {
        return startX;
    }

    public void setStartX(int startX) {
        this.startX = startX;
    }

    public int getStartY() {
        return startY;
    }

    public void setStartY(int startY) {
        this.startY = startY;
    }

    public int getStopX() {
        return stopX;
    }

    public void setStopX(int stopX) {
        this.stopX = stopX;
    }

    public int getStopY() {
        return stopY;
    }

    public void setStopY(int stopY) {
        this.stopY = stopY;
    }

    public Paint getPaint() {
        return paint;
    }

    public void setPaint(Paint paint) {
        this.paint = paint;
    }
}
