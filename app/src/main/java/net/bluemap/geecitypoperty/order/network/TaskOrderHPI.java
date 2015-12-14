package net.bluemap.geecitypoperty.order.network;

import android.content.Context;

import net.bluemap.geecitypoperty.common.HttpPostAPI;

import org.json.JSONException;

import java.util.HashMap;

/**
 * 派单接口
 * Created by LiuPeng on 2015/8/9.
 */
public class TaskOrderHPI extends HttpPostAPI{
    public TaskOrderHPI(Context context) {
        super(context);
    }

    //输入
    private String userId;
    private String employee;
    private String expectTime;
    private String orderId;

    @Override
    protected void getInputParam(HashMap<String, Object> params) {
        params.put("userId",userId);
        params.put("employee",employee);
        params.put("expectTime",expectTime);
        params.put("orderId",orderId);
    }

    @Override
    protected boolean analysisOutput(String result) throws JSONException {
        return true;
    }

    @Override
    protected String getMethodName() {
        return "order/task.php";
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEmployee() {
        return employee;
    }

    public void setEmployee(String employee) {
        this.employee = employee;
    }

    public String getExpectTime() {
        return expectTime;
    }

    public void setExpectTime(String expectTime) {
        this.expectTime = expectTime;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
