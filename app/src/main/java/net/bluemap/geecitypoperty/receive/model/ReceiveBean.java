package net.bluemap.geecitypoperty.receive.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 接待实体
 * Created by LiuPeng on 2015/10/23.
 */
public class ReceiveBean {
    //id
    private String id = "";
    //内容
    private String content = "";
    //类型
    private String type = "未知";
    //来源
    private String source = "未知";
    //地址
    private String address = "";
    //联系人
    private String contact = "";
    //时间
    private String time = "";
    //接收人
    private String receiver = "";
    //受理人
    private String accepter = "";
    //状态
    private String state = "";

    //详情字段：
    //预约时间
    private String orderTime = "";
    //图片
    private List<String> images = new ArrayList<>();
    //电话
    private String tel = "";
    //客户反应
    private String reaction = "一般";
    //是否两次以上投诉
    private String twos = "";
    //受理公司
    private String accepterCom = "";

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getAccepter() {
        return accepter;
    }

    public void setAccepter(String accepter) {
        this.accepter = accepter;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getReaction() {
        return reaction;
    }

    public void setReaction(String reaction) {
        this.reaction = reaction;
    }

    public String getTwos() {
        return twos;
    }

    public void setTwos(String twos) {
        this.twos = twos;
    }

    public String getAccepterCom() {
        return accepterCom;
    }

    public void setAccepterCom(String accepterCom) {
        this.accepterCom = accepterCom;
    }
}
