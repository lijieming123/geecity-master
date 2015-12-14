package net.bluemap.geecitypoperty.common.model;

/**
 * 登录信息类
 * Created by Liu Peng on 2015/7/24.
 */
public class LoginInfo {

    //用户id
    private String id;
    //用户名
    private String userName;
    //用户真名
    private String realName;
    //密码
    private String password;
    //小区id
    private String areaId = "1";
    //用户权限
    private int auth = 0;
    //部门id
    private String departId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public int getAuth() {
        return auth;
    }

    public void setAuth(int auth) {
        this.auth = auth;
    }

    public String getDepartId() {
        return departId;
    }

    public void setDepartId(String departId) {
        this.departId = departId;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }
}
