package net.bluemap.geecitypoperty.message.network;

import android.content.Context;

import net.bluemap.geecitypoperty.common.HttpPostAPI;

import org.json.JSONException;

import java.util.HashMap;

/**
 * 获取信息详情
 * Created by LiuPeng on 2015/9/20.
 */
public class GetMessageDetailHPI extends HttpPostAPI {

    private int messageId;

    public GetMessageDetailHPI(Context context) {
        super(context);
    }

    @Override
    protected void getInputParam(HashMap<String, Object> params) {
        params.put("messageId", messageId);
    }

    @Override
    protected boolean analysisOutput(String result) throws JSONException {

        return true;
    }

    @Override
    protected String getMethodName() {
        return "message/detail.php";
    }
}
