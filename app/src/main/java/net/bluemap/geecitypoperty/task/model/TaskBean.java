package net.bluemap.geecitypoperty.task.model;

/**
 * 任务bean
 * Created by LiuPeng on 15/10/27.
 */
public class TaskBean {
//任务类型
    private String jdlx;

    public String getJdlx() {
        return jdlx;
    }

    public void setJdlx(String jdlx) {
        this.jdlx = jdlx;
    }

    //所在接待的id
    private String receiveId = "";
    //列表项
    //id
    private String id = "";
    //序号
    private String number = "";
    //状态
    private String state = "未知";
    //房间
    private String room = "";
    //联系人
    private String contact = "无";
    //标题
    private String title = "无标题";
    //受理时间
    private String time = "";

    //相关任务字段
    //类型
    private String type = "未知";
    //级别
    private String level = "未知";
    //受理人
    private String receiver = "无";
    //后续受理人
    private String followReceiver = "无";
    //描述
    private String content = "";
    //答复时间
    private String answerTime = "";
    //承诺时间
    private String promiseTime = "";
    //处理时间
    private String handleTime = "";

    //详情字段
    //所在接待的描述

    private String rContent = "";
    //电话
    private String tel = "";
    //派工时间
    private String taskTime = "";
    //创建时间(受理时间)
    private String createTime = "";
    //责任单位
    private String accountability = "";
    //第三方处理单位
    private String thirdPart = "";

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getFollowReceiver() {
        return followReceiver;
    }

    public void setFollowReceiver(String followReceiver) {
        this.followReceiver = followReceiver;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAnswerTime() {
        return answerTime;
    }

    public void setAnswerTime(String answerTime) {
        this.answerTime = answerTime;
    }

    public String getPromiseTime() {
        return promiseTime;
    }

    public void setPromiseTime(String promiseTime) {
        this.promiseTime = promiseTime;
    }

    public String getHandleTime() {
        return handleTime;
    }

    public void setHandleTime(String handleTime) {
        this.handleTime = handleTime;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getTaskTime() {
        return taskTime;
    }

    public void setTaskTime(String taskTime) {
        this.taskTime = taskTime;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getAccountability() {
        return accountability;
    }

    public void setAccountability(String accountability) {
        this.accountability = accountability;
    }

    public String getThirdPart() {
        return thirdPart;
    }

    public void setThirdPart(String thirdPart) {
        this.thirdPart = thirdPart;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getrContent() {
        return rContent;
    }

    public void setrContent(String rContent) {
        this.rContent = rContent;
    }

    public String getReceiveId() {
        return receiveId;
    }

    public void setReceiveId(String receiveId) {
        this.receiveId = receiveId;
    }
}
