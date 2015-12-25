package net.bluemap.geecitypoperty.task.network;

import android.content.Context;

import net.bluemap.geecitypoperty.common.HttpPostAPI;
import net.bluemap.geecitypoperty.common.JsonUtil;
import net.bluemap.geecitypoperty.task.model.TaskBean;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * 获取任务详细
 * Created by LiuPeng on 2015/10/31.
 */
public class GetTaskDetailHPI extends HttpPostAPI {

    private String taskId;

    private TaskBean task;

    public GetTaskDetailHPI(Context context) {
        super(context);
    }

    @Override
    protected void getInputParam(HashMap<String, Object> params) {
        params.put("taskId", taskId);
    }

    @Override
    protected boolean analysisOutput(String result) throws JSONException {
        JSONObject data = new JSONObject(result);
        task = new TaskBean();
        task.setId(JsonUtil.getString(data,"id", ""));
        task.setReceiveId(JsonUtil.getString(data, "rid", ""));
        task.setState(JsonUtil.getString(data, "state", "未知"));
        task.setRoom(JsonUtil.getString(data, "room", ""));
        task.setContact(JsonUtil.getString(data, "contact", "无"));
        task.setTel(JsonUtil.getString(data, "tel", ""));
        task.setrContent(JsonUtil.getString(data, "rcontent", ""));
        task.setType(JsonUtil.getString(data, "type", "未知"));
        task.setLevel(JsonUtil.getString(data, "level", ""));
        task.setReceiver(JsonUtil.getString(data, "receiver", "无"));
        task.setFollowReceiver(JsonUtil.getString(data, "followReceiver", "无"));
        task.setContent(JsonUtil.getString(data, "content", ""));
        task.setAnswerTime(JsonUtil.getString(data, "answerTime", ""));
        task.setPromiseTime(JsonUtil.getString(data, "promiseTime", ""));
        task.setHandleTime(JsonUtil.getString(data, "handleTime", ""));
        task.setTitle(JsonUtil.getString(data, "title", "无标题"));
        task.setTaskTime(JsonUtil.getString(data, "taskTime", ""));
        task.setCreateTime(JsonUtil.getString(data, "createTime", ""));
        task.setAccountability(JsonUtil.getString(data, "accountability", ""));
        task.setThirdPart(JsonUtil.getString(data, "thirdPart", ""));
        task.setJdlx(JsonUtil.getString(data,"jdlx",""));
        return true;
    }

    @Override
    protected String getMethodName() {
        return "task/detail.php";
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public TaskBean getTask() {
        return task;
    }
}
