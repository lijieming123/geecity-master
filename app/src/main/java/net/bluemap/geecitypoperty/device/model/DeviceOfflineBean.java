package net.bluemap.geecitypoperty.device.model;

/**
 * 离线的设备类，json中是GetDeviceDetailHPI返回的数据
 * Created by LiuPeng on 2015/9/5.
 */
public class DeviceOfflineBean {
    private String id;
    private String name;
    private String position;
    private String json;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }
}
