package net.bluemap.geecitypoperty.notice.model;

/**
 * Created by Administrator on 2015/7/26.
 */
public class NoticeBean {
    //id
    private String id;
    //发布日期
    private String date;
    //标题
    private String title;
    //内容
    private String detail;
    //查看日期
    private String checkDate;
    //是否被查看
    private boolean read;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getCheckDate() {
        return checkDate;
    }

    public void setCheckDate(String checkDate) {
        this.checkDate = checkDate;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }
}
