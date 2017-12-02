package com.cloud.resources.flows;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2017/10/30
 * @Description:布局方向
 * @Modifier:
 * @ModifyContent:
 */
public enum LayoutDirection {
    /**
     * 从左到右
     */
    ltr(0),
    /**
     * 从右到左
     */
    rtl(1);

    private int value = 0;

    private LayoutDirection(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    public static final LayoutDirection getLayoutDirection(int value) {
        LayoutDirection currEnum = null;
        for (LayoutDirection e : LayoutDirection.values()) {
            if (e.getValue() == value) {
                currEnum = e;
                break;
            }
        }
        return currEnum;
    }
}
