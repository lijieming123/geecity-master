package net.bluemap.geecitypoperty.device.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 设备bean
 * Created by LiuPeng on 2015/8/15.
 */
public class DeviceBean {
    private String id;
    private String name;
    private String courtId;
    private String position;
    private List<CheckBean> checks = new ArrayList<>();
    private List<HistoryBean> history = new ArrayList<>();

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

    public List<CheckBean> getChecks() {
        return checks;
    }

    public List<HistoryBean> getHistory() {
        return history;
    }

    public String getCourtId() {
        return courtId;
    }

    public void setCourtId(String courtId) {
        this.courtId = courtId;
    }
}
