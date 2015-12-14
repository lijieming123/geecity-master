package net.bluemap.geecitypoperty.meter.model;

/**
 * 房间bean
 * Created by LiuPeng on 2015/8/22.
 */
public class RoomBean {

    //房间id、名称
    private String id;
    private String name;

    //住户、联系电话
    private String resident;
    private String phone;

    //物业费缴至日期
    private String payToDate;

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

    public String getResident() {
        return resident;
    }

    public void setResident(String resident) {
        this.resident = resident;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPayToDate() {
        return payToDate;
    }

    public void setPayToDate(String payToDate) {
        this.payToDate = payToDate;
    }
}
