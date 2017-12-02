package com.cloud.resources.flowtrack;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2017/11/24
 * @Description:数据项
 * @Modifier:
 * @ModifyContent:
 */
public class FlowTrackOneItem {
    /**
     * 时间
     */
    private String time = "";
    /**
     * 标题
     */
    private String title = "";
    /**
     * 描述
     */
    private String des = "";
    /**
     * 是否选中
     */
    private boolean isChk = false;

    public FlowTrackOneItem() {
    }

    public FlowTrackOneItem(String time, String title, String des, boolean isChk) {
        this.time = time;
        this.title = title;
        this.des = des;
        this.isChk = isChk;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public boolean isChk() {
        return isChk;
    }

    public void setChk(boolean chk) {
        isChk = chk;
    }
}
