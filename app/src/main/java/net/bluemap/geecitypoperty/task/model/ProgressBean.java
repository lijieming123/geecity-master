package net.bluemap.geecitypoperty.task.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 进展bean
 * Created by LiuPeng on 15/10/29.
 */
public class ProgressBean {

    private String number;
    private String createName;
    private String createTime;
    private String state;
    private String content;
    private String remark1;
    private String remark2;
    private String remark3;
    private List<String> images = new ArrayList<>();

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getRemark1() {
        return remark1;
    }

    public void setRemark1(String remark1) {
        this.remark1 = remark1;
    }

    public String getRemark2() {
        return remark2;
    }

    public void setRemark2(String remark2) {
        this.remark2 = remark2;
    }

    public String getRemark3() {
        return remark3;
    }

    public void setRemark3(String remark3) {
        this.remark3 = remark3;
    }

    public List<String> getImages() {
        return images;
    }
}
