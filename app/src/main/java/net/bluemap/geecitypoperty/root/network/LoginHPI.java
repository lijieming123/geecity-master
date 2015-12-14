package net.bluemap.geecitypoperty.root.network;

import android.content.Context;

import net.bluemap.geecitypoperty.common.HttpPostAPI;
import net.bluemap.geecitypoperty.common.ShareUtil;
import net.bluemap.geecitypoperty.common.model.LoginInfo;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import hz.toollib.util.StringUtil;

/**
 * 登录接口类
 * Created by Liu Peng on 2015/7/25.
 */
public class LoginHPI extends HttpPostAPI {

    //入参
    private String username;
    private String password;
    //出参
    private LoginInfo loginInfo;

    public LoginHPI(Context context) {
        super(context);
    }

    @Override
    protected void getInputParam(HashMap<String, Object> params) {
        params.put("username",username);
        //将密码进行md516位加密
        String md5 = StringUtil.getMD5String(password, 16, false);
        params.put("password",md5);
    }

    @Override
    protected boolean analysisOutput(String result) throws JSONException {
        JSONObject data = new JSONObject(result);
        loginInfo = new LoginInfo();
        loginInfo.setId(data.getString("id"));
        loginInfo.setPassword(password);
        loginInfo.setUserName(username);
        loginInfo.setAreaId(data.getString("areaId"));
        loginInfo.setAuth(data.getInt("auth"));
        loginInfo.setDepartId(data.getString("departId"));
        loginInfo.setRealName(data.getString("realName"));
        return true;
    }

    @Override
    protected boolean doOffLine() {
        //离线模式，判断上次是否登录成功，离线登录
        LoginInfo li = ShareUtil.getInstance(getContext()).getLoginInfo();
        return username.equals(li.getUserName()) && password.equals(li.getPassword());
    }

    @Override
    protected String getMethodName() {
        return "common/login.php";
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LoginInfo getLoginInfo() {
        return loginInfo;
    }
}
