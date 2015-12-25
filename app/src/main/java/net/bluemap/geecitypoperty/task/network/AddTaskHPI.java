package net.bluemap.geecitypoperty.task.network;

import android.content.Context;

import net.bluemap.geecitypoperty.common.HttpPostAPI;

import org.json.JSONException;

import java.util.HashMap;

/**
 * 添加任务
 * Created by LiuPeng on 2015/10/31.
 */
public class AddTaskHPI extends HttpPostAPI {

    private String receiveId;
    private String state = "待处理";
    private String title;
    private String type;
    private String level;
    private String receiver;
    private String followReceiver;
    private String accountability;
    private String thirdPart;
    private String answerTime;
    private String promiseTime;
    private String handleTime;
    private String content;
    private String createName;
    //派工时间
    private String taskTime;
private  String jdlx;

    public void setJdlx(String jdlx) {
        this.jdlx = jdlx;
    }

    public AddTaskHPI(Context context) {
        super(context);
    }

    @Override
    protected void getInputParam(HashMap<String, Object> params) {
        params.put("receiveId", receiveId);
        params.put("state", state);
        params.put("title", title);
        params.put("type", type);
        params.put("level", level);
        params.put("receiver", receiver);
        params.put("followReceiver", followReceiver);
        params.put("accountability", accountability);
        params.put("thirdPart", thirdPart);
        params.put("answerTime", answerTime);
        params.put("promiseTime", promiseTime);
        params.put("handleTime", handleTime);
        params.put("content", content);
        params.put("createName", createName);
        params.put("taskTime", taskTime);
        params.put("jdlx",jdlx);
    }

    @Override
    protected boolean analysisOutput(String result) throws JSONException {
        return true;
    }

    @Override
    protected String getMethodName() {
        return "task/add.php";
    }

    public void setReceiveId(String receiveId) {
        this.receiveId = receiveId;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public void setFollowReceiver(String followReceiver) {
        this.followReceiver = followReceiver;
    }

    public void setAccountability(String accountability) {
        this.accountability = accountability;
    }

    public void setThirdPart(String thirdPart) {
        this.thirdPart = thirdPart;
    }

    public void setAnswerTime(String answerTime) {
        this.answerTime = answerTime;
    }

    public void setPromiseTime(String promiseTime) {
        this.promiseTime = promiseTime;
    }

    public void setHandleTime(String handleTime) {
        this.handleTime = handleTime;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setCreateName(String createName) {
        this.createName = createName;
    }

    public void setTaskTime(String taskTime) {
        this.taskTime = taskTime;
    }
}
