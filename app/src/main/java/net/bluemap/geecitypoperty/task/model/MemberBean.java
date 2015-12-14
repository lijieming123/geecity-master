package net.bluemap.geecitypoperty.task.model;

/**
 * 成员Bean
 * Created by LiuPeng on 2015/10/30.
 */
public class MemberBean {
    //id，姓名，部门，职位
    private String id;
    private String name;
    private String depart;
    private String position;

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

    public String getDepart() {
        return depart;
    }

    public void setDepart(String depart) {
        this.depart = depart;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}
