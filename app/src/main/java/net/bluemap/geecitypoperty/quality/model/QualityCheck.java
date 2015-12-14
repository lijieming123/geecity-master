package net.bluemap.geecitypoperty.quality.model;

import java.util.List;

/**
 * 品质检查类
 * Created by LiuPeng on 2015/9/14.
 */
public class QualityCheck {

    private QualityServer server;
    //服务内容
    private String serviceContent;
    //检查时间
    private String checkDate;

    private List<QualityCheckItem> checkItems;

    public QualityServer getServer() {
        return server;
    }

    public void setServer(QualityServer server) {
        this.server = server;
    }

    public String getServiceContent() {
        return serviceContent;
    }

    public void setServiceContent(String serviceContent) {
        this.serviceContent = serviceContent;
    }

    public String getCheckDate() {
        return checkDate;
    }

    public void setCheckDate(String checkDate) {
        this.checkDate = checkDate;
    }

    public List<QualityCheckItem> getCheckItems() {
        return checkItems;
    }

    public void setCheckItems(List<QualityCheckItem> checkItems) {
        this.checkItems = checkItems;
    }
}
