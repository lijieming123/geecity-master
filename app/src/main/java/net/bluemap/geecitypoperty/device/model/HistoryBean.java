package net.bluemap.geecitypoperty.device.model;

/**
 * 设备巡检历史bean
 * Created by LiuPeng on 2015/8/15.
 */
public class HistoryBean {

    private String type;
    private String lastTime;
    private String state;
    private String situation;


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLastTime() {
        return lastTime;
    }

    public void setLastTime(String lastTime) {
        this.lastTime = lastTime;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getSituation() {
        return situation;
    }

    public void setSituation(String situation) {
        this.situation = situation;
    }
}
