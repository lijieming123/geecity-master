package net.bluemap.geecitypoperty.common;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import net.bluemap.geecitypoperty.common.model.LoginInfo;

/**
 * SharedPreferences工具类
 * 为此项目提供统一Shared访问接口，尽量不要自行写shared访问代码造成混乱
 * Created by Liu Peng on 2015/7/24.
 */
public class ShareUtil {

    private static ShareUtil shareUtil;
    private Context mContext;

    static public ShareUtil getInstance(Context context){
        if(shareUtil == null){
            shareUtil = new ShareUtil(context);
        }
        return  shareUtil;
    }

    private ShareUtil(Context context){
        this.mContext = context;
    }

    /**
     * 保存登录信息
     */
    public void saveLoginInfo(LoginInfo loginInfo){
        SharedPreferences shared = mContext.getSharedPreferences("userInfo", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = shared.edit();
        editor.putString("id",loginInfo.getId());
        editor.putString("userName",loginInfo.getUserName());
        editor.putString("password",loginInfo.getPassword());
        editor.putString("areaId",loginInfo.getAreaId());
        editor.putInt("auth",loginInfo.getAuth());
        editor.putString("departId",loginInfo.getDepartId());
        editor.putString("realName",loginInfo.getRealName());
        editor.commit();
    }

    /**
     * 获取登录信息
     * @return
     */
    public LoginInfo getLoginInfo(){
        SharedPreferences shared = mContext.getSharedPreferences("userInfo", Activity.MODE_PRIVATE);
        LoginInfo li = new LoginInfo();
        li.setId(shared.getString("id", ""));
        li.setUserName(shared.getString("userName", ""));
        li.setPassword(shared.getString("password", ""));
        li.setAreaId(shared.getString("areaId", ""));
        li.setAuth(shared.getInt("auth", 0));
        li.setDepartId(shared.getString("departId", ""));
        li.setRealName(shared.getString("realName", ""));
        return li;
    }

    /**
     * 获取保存的url
     * @return
     */
    public String getHostUrl(){
        SharedPreferences shared = mContext.getSharedPreferences("setting", Activity.MODE_PRIVATE);
        return shared.getString("hostUrl","http://4.89892528.cn:8002/");
    }

    /**
     * 保存url
     * @param url
     */
    public void saveHostUrl(String url){
        SharedPreferences shared = mContext.getSharedPreferences("setting", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = shared.edit();
        if(!url.startsWith("http://")){
            url = "http://" + url;
        }
        if(!url.endsWith("/")){
            url = url + "/";
        }
        editor.putString("hostUrl", url);
        editor.commit();
    }

}
