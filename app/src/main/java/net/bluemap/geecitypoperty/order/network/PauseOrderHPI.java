package net.bluemap.geecitypoperty.order.network;

import android.content.Context;

import net.bluemap.geecitypoperty.common.HttpPostAPI;

import org.json.JSONException;

import java.util.HashMap;

/**
 * 截停接口
 * Created by LiuPeng on 2015/8/9.
 */
public class PauseOrderHPI extends HttpPostAPI{

    private String userId;
    private String reason;
    private String orderId;

    public PauseOrderHPI(Context context) {
        super(context);
    }

    @Override
    protected void getInputParam(HashMap<String, Object> params) {
        params.put("userId", userId);
        params.put("reason", reason);
        params.put("orderId", orderId);
    }

    @Override
    protected boolean analysisOutput(String result) throws JSONException {
        return true;
    }

    @Override
    protected String getMethodName() {
        return "order/pause.php";
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
