package com.cloud.resources.flowtrack;

import android.text.StaticLayout;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2017/11/25
 * @Description:
 * @Modifier:
 * @ModifyContent:
 */
public class CanvasParams {
    private int dx = 0;
    private int dy = 0;
    private StaticLayout staticLayout = null;

    public int getDx() {
        return dx;
    }

    public void setDx(int dx) {
        this.dx = dx;
    }

    public int getDy() {
        return dy;
    }

    public void setDy(int dy) {
        this.dy = dy;
    }

    public StaticLayout getStaticLayout() {
        return staticLayout;
    }

    public void setStaticLayout(StaticLayout staticLayout) {
        this.staticLayout = staticLayout;
    }
}
