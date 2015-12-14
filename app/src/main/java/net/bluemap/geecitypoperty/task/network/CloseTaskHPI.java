package net.bluemap.geecitypoperty.task.network;

import android.content.Context;

import net.bluemap.geecitypoperty.common.HttpPostAPI;

import org.json.JSONException;

import java.util.HashMap;

/**
 * 关闭任务
 * Created by LiuPeng on 2015/10/30.
 */
public class CloseTaskHPI extends HttpPostAPI {

    private String taskId;
    private String promiseTime;
    private String closeTime;
    private String finalLevel;
    private String handleType;
    private String repair;
    private String repairCount;

    //质量，态度，时效性，0=满意，1=不满意
    private String quality;
    private String attitude;
    private String timeliness;
    private String closeType;
    private String closeContent;
    private String solution;

    public CloseTaskHPI(Context context) {
        super(context);
    }

    @Override
    protected void getInputParam(HashMap<String, Object> params) {
        params.put("taskId", taskId);
        params.put("promiseTime", promiseTime);
        params.put("closeTime", closeTime);
        params.put("finalLevel", Integer.valueOf(finalLevel));
        params.put("handleType", Integer.valueOf(handleType));
        params.put("repair", repair);
        params.put("repairCount", repairCount);
        params.put("quality", quality);
        params.put("attitude", attitude);
        params.put("timeliness", timeliness);
        params.put("closeType", closeType);
        params.put("closeContent", closeContent);
        params.put("solution", solution);
    }

    @Override
    protected boolean analysisOutput(String result) throws JSONException {
        return true;
    }

    @Override
    protected String getMethodName() {
        return "task/close.php";
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public void setPromiseTime(String promiseTime) {
        this.promiseTime = promiseTime;
    }

    public void setCloseTime(String closeTime) {
        this.closeTime = closeTime;
    }

    public void setFinalLevel(String finalLevel) {
        this.finalLevel = finalLevel;
    }

    public void setHandleType(String handleType) {
        this.handleType = handleType;
    }

    public void setRepair(String repair) {
        this.repair = repair;
    }

    public void setRepairCount(String repairCount) {
        this.repairCount = repairCount;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

    public void setAttitude(String attitude) {
        this.attitude = attitude;
    }

    public void setTimeliness(String timeliness) {
        this.timeliness = timeliness;
    }

    public void setCloseType(String closeType) {
        this.closeType = closeType;
    }

    public void setCloseContent(String closeContent) {
        this.closeContent = closeContent;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }
}
