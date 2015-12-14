package net.bluemap.geecitypoperty.order.network;

import android.content.Context;

import net.bluemap.geecitypoperty.common.HttpPostAPI;

import org.json.JSONException;

import java.util.HashMap;

/**
 * 抢单接口
 * Created by LiuPeng on 2015/8/9.
 */
public class ScrambleOrderHPI extends HttpPostAPI {
    public ScrambleOrderHPI(Context context) {
        super(context);
    }

    //输入
    private String userId;
    private String expectTime;
    private String orderId;

    @Override
    protected void getInputParam(HashMap<String, Object> params) {
        params.put("userId",userId);
        params.put("expectTime",expectTime);
        params.put("orderId",orderId);
    }

    @Override
    protected boolean analysisOutput(String result) throws JSONException {
        return true;
    }

    @Override
    protected String getMethodName() {
        return "order/scramble.php";
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setExpectTime(String expectTime) {
        this.expectTime = expectTime;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
