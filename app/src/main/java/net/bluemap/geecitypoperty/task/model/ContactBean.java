package net.bluemap.geecitypoperty.task.model;

/**
 * 客户联系记录bean
 * Created by LiuPeng on 15/10/29.
 */
public class ContactBean {

    private String number;
    private String contact;
    private String content;
    private String createTime;
    private String createName;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName;
    }
}
