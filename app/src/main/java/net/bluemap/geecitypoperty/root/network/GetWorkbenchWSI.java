package net.bluemap.geecitypoperty.root.network;

import android.content.Context;

import net.bluemap.geecitypoperty.common.HttpPostAPI;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * 获取我的工作台，首页上显示的信息接口类
 * Created by Liu Peng on 2015/7/25.
 */
public class GetWorkbenchWSI extends HttpPostAPI {

    //入参
    private String departId;
    private String userId;
    private String userName;

    //出参
    private int noticeCount = 0;
    private int receiveCount = 0;
    private int taskCount = 0;
    private int deviceCount = 0;

    public GetWorkbenchWSI(Context context) {
        super(context);
    }

    @Override
    protected void getInputParam(HashMap<String, Object> params) {
        params.put("departId",departId);
        params.put("userId",userId);
        params.put("userName",userName);
    }

    @Override
    protected boolean analysisOutput(String result) throws JSONException {
        JSONObject data = new JSONObject(result);
        //通知，最新通知、条目数量
        noticeCount = data.getInt("noticeNum");
        //接待
        receiveCount = data.getInt("receiveNum");
        //任务
        taskCount = data.getInt("taskNum");
        //设备
        deviceCount = data.getInt("deviceNum");
        return true;
    }

    @Override
    protected String getMethodName() {
        return "common/main.php";
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setDepartId(String departId) {
        this.departId = departId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getNoticeCount() {
        return noticeCount;
    }

    public int getReceiveCount() {
        return receiveCount;
    }

    public int getTaskCount() {
        return taskCount;
    }

    public int getDeviceCount() {
        return deviceCount;
    }
}
