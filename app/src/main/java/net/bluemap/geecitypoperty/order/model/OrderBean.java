package net.bluemap.geecitypoperty.order.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 派单Bean类
 * Created by LiuPeng on 2015/7/30.
 */
public class OrderBean {

    //id
    private String id;
    //状态
    private String state;
    //类型
    private String type;
    //提交时间
    private String submitTime;
    //预约时间
    private String orderTime;
    //房间
    private String room;
    //联系人
    private String contact;
    //联系电话
    private String phone;
    //派单内容
    private String content;
    //图片列表
    private List<String> images = new ArrayList<>();

    /**state >= 1时，添加以下字段*/
    //报修时间
    private String repairTime;
    //要求完工时间
    private String expectTime;

    /**state >= 2时，添加以下字段*/
    //派工时间
    private String taskTime;

    /**state = 3时，以下字段有值*/
    //结单时间
    private String closeTime;
    //开工时间
    private String startTime;
    //完工时间
    private String endTime;
    //材料费
    private String materialCost;
    //工时费
    private String hourCharge;
    //材料使用情况
    private String materialUsage;
    //其他说明
    private String other;

    /**state = 4时，以下字段有值*/
    //截停时间
    private String pauseTime;
    //截停原因
    private String pauseReason;



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(String submitTime) {
        this.submitTime = submitTime;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getRepairTime() {
        return repairTime;
    }

    public void setRepairTime(String repairTime) {
        this.repairTime = repairTime;
    }

    public String getExpectTime() {
        return expectTime;
    }

    public void setExpectTime(String expectTime) {
        this.expectTime = expectTime;
    }

    public String getTaskTime() {
        return taskTime;
    }

    public void setTaskTime(String taskTime) {
        this.taskTime = taskTime;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getMaterialCost() {
        return materialCost;
    }

    public void setMaterialCost(String materialCost) {
        this.materialCost = materialCost;
    }

    public String getHourCharge() {
        return hourCharge;
    }

    public void setHourCharge(String hourCharge) {
        this.hourCharge = hourCharge;
    }

    public String getMaterialUsage() {
        return materialUsage;
    }

    public void setMaterialUsage(String materialUsage) {
        this.materialUsage = materialUsage;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public String getPauseTime() {
        return pauseTime;
    }

    public void setPauseTime(String pauseTime) {
        this.pauseTime = pauseTime;
    }

    public String getPauseReason() {
        return pauseReason;
    }

    public void setPauseReason(String pauseReason) {
        this.pauseReason = pauseReason;
    }

    public String getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(String closeTime) {
        this.closeTime = closeTime;
    }
}
