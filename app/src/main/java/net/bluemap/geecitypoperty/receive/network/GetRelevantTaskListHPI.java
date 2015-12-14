package net.bluemap.geecitypoperty.receive.network;

import android.content.Context;

import net.bluemap.geecitypoperty.common.HttpPostAPI;
import net.bluemap.geecitypoperty.common.JsonUtil;
import net.bluemap.geecitypoperty.task.model.TaskBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 获取相关任务列表
 * Created by LiuPeng on 2015/10/30.
 */
public class GetRelevantTaskListHPI extends HttpPostAPI {

    private int page;
    private String receiveId;

    List<TaskBean> tasks = new ArrayList<>();

    public GetRelevantTaskListHPI(Context context) {
        super(context);
    }

    @Override
    protected void getInputParam(HashMap<String, Object> params) {
        params.put("page",page);
        params.put("receiveId", receiveId);
    }

    @Override
    protected boolean analysisOutput(String result) throws JSONException {
        JSONArray data = new JSONArray(result);
        tasks.clear();
        for(int i = 0; i<data.length(); i++){
            TaskBean task = new TaskBean();
            JSONObject o = data.getJSONObject(i);
            task.setId(JsonUtil.getString(o,"id",""));
            task.setState(JsonUtil.getString(o,"state","未知"));
            task.setType(JsonUtil.getString(o,"type","未知"));
            task.setLevel(JsonUtil.getString(o,"level",""));
            task.setReceiver(JsonUtil.getString(o,"receiver","无"));
            task.setFollowReceiver(JsonUtil.getString(o,"followReceiver","无"));
            task.setContent(JsonUtil.getString(o,"content",""));
            task.setAnswerTime(JsonUtil.getString(o,"answerTime",""));
            task.setPromiseTime(JsonUtil.getString(o,"promiseTime",""));
            task.setHandleTime(JsonUtil.getString(o,"handleTime",""));
            tasks.add(task);
        }
        return true;
    }

    @Override
    protected String getMethodName() {
        return "receive/tasks.php";
    }

    public void setPage(int page) {
        this.page = page;
    }

    public void setReceiveId(String receiveId) {
        this.receiveId = receiveId;
    }

    public List<TaskBean> getTasks() {
        return tasks;
    }
}
