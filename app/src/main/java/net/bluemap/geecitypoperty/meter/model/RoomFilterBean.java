package net.bluemap.geecitypoperty.meter.model;

/**
 * 房间四级联查筛选类，小区-楼座-单元-房间
 * Created by Liu Peng on 2015/7/26.
 */
public class RoomFilterBean {
    private String id;
    private String text;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
