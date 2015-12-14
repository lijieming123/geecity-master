package net.bluemap.geecitypoperty.task.network;

import android.content.Context;

import net.bluemap.geecitypoperty.common.HttpPostAPI;

import org.json.JSONException;

import java.util.HashMap;

/**
 * 修改后续受理人
 * Created by LiuPeng on 15/10/29.
 */
public class UpdateAccepterHPI extends HttpPostAPI {

    private String taskId;
    private String userName;

    public UpdateAccepterHPI(Context context) {
        super(context);
    }

    @Override
    protected void getInputParam(HashMap<String, Object> params) {
        params.put("userName", userName);
        params.put("taskId", taskId);
    }

    @Override
    protected boolean analysisOutput(String result) throws JSONException {
        return true;
    }

    @Override
    protected String getMethodName() {
        return "task/updateFollowContact.php";
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
