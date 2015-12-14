package net.bluemap.geecitypoperty.order.network;

import android.content.Context;

import net.bluemap.geecitypoperty.common.HttpPostAPI;

import org.json.JSONException;

import java.util.HashMap;

/**
 * 接单接口，无出参，回调success即为成功
 * Created by LiuPeng on 2015/8/9.
 */
public class CatchOrderHPI extends HttpPostAPI{
    //入参
    private String orderId;
    private String userId;

    public CatchOrderHPI(Context context) {
        super(context);
    }

    @Override
    protected void getInputParam(HashMap<String, Object> params) {
        params.put("orderId",orderId);
        params.put("userId",userId);
    }

    @Override
    protected boolean analysisOutput(String result) throws JSONException {
        return true;
    }

    @Override
    protected String getMethodName() {
        return "order/catch.php";
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
