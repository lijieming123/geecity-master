package net.bluemap.geecitypoperty.device.model;

/**
 * 设备巡检bean
 * Created by LiuPeng on 2015/8/15.
 */
public class CheckBean {
    private String type;
    private String lastTime;
    private String nextTime;

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

    public String getNextTime() {
        return nextTime;
    }

    public void setNextTime(String nextTime) {
        this.nextTime = nextTime;
    }
}
