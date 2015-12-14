package net.bluemap.geecitypoperty.order.model;

/**
 * 派工人员bean
 * Created by LiuPeng on 2015/8/9.
 */
public class EmployeeBean {
    private String id;
    private String name;
    private boolean select;

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

    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }
}
