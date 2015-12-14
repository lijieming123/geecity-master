package net.bluemap.geecitypoperty.task.network;

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
 * 获取任务列表
 * Created by LiuPeng on 15/10/27.
 */
public class GetTaskListHPI extends HttpPostAPI {

    private String courtId;
    private String remark;
    private int page;
    private String userName;

    private List<TaskBean> tasks = new ArrayList<>();


    public GetTaskListHPI(Context context) {
        super(context);
    }

    @Override
    protected void getInputParam(HashMap<String, Object> params) {
        params.put("courtId", courtId);
        params.put("remark", remark);
        params.put("page", page);
        params.put("userName", userName);
    }

    @Override
    protected boolean analysisOutput(String result) throws JSONException {
        tasks.clear();
        JSONArray data = new JSONArray(result);
        for(int i = 0; i<data.length(); i++){
            JSONObject o = data.getJSONObject(i);
            TaskBean tb = new TaskBean();
            tb.setId(o.getString("id"));
            tb.setState(JsonUtil.getString(o,"state","未知"));
            tb.setRoom(JsonUtil.getString(o,"room",""));
            tb.setContact(JsonUtil.getString(o,"contact",""));
            tb.setTitle(JsonUtil.getString(o,"title","无标题"));
            tb.setTime(JsonUtil.getString(o,"time",""));
            tasks.add(tb);
        }
        return true;
    }

    @Override
    protected String getMethodName() {
        return "task/list.php";
    }

    public List<TaskBean> getTasks() {
        return tasks;
    }

    public void setCourtId(String courtId) {
        this.courtId = courtId;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
