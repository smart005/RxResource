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
public class FlowTrackNodeItem {
    private int cx = 0;
    private int cy = 0;
    private int radius = 0;
    private Paint paint = null;

    public int getCx() {
        return cx;
    }

    public void setCx(int cx) {
        this.cx = cx;
    }

    public int getCy() {
        return cy;
    }

    public void setCy(int cy) {
        this.cy = cy;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public Paint getPaint() {
        return paint;
    }

    public void setPaint(Paint paint) {
        this.paint = paint;
    }
}
