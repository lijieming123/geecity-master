package net.bluemap.geecitypoperty.order.network;

import android.content.Context;

import net.bluemap.geecitypoperty.common.HttpPostAPI;

import org.json.JSONException;

import java.util.HashMap;

/**
 * 完工接口
 * Created by LiuPeng on 2015/8/9.
 */
public class FinishOrderHPI extends HttpPostAPI{

    private String startTime;
    private String endTime;
    private String materialCost;
    private String hourCharge;
    private String materialUsage;
    private String other;
    private String userId;
    private String orderId;

    public FinishOrderHPI(Context context) {
        super(context);
    }

    @Override
    protected void getInputParam(HashMap<String, Object> params) {
        params.put("startTime",startTime);
        params.put("endTime",endTime);
        params.put("materialCost",materialCost);
        params.put("hourCharge",hourCharge);
        params.put("materialUsage",materialUsage);
        params.put("other",other);
        params.put("userId",userId);
        params.put("orderId",orderId);
    }

    @Override
    protected boolean analysisOutput(String result) throws JSONException {
        return true;
    }

    @Override
    protected String getMethodName() {
        return "order/finish.php";
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public void setMaterialCost(String materialCost) {
        this.materialCost = materialCost;
    }

    public void setHourCharge(String hourCharge) {
        this.hourCharge = hourCharge;
    }

    public void setMaterialUsage(String materialUsage) {
        this.materialUsage = materialUsage;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
