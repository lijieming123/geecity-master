package net.bluemap.geecitypoperty.device.network;

import android.content.Context;

import net.bluemap.geecitypoperty.common.HttpPostAPI;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by Administrator on 2015/12/7.
 */
public class HPI extends HttpPostAPI {
    public HPI(Context context) {
        super(context);
    }

    @Override
    protected void getInputParam(HashMap<String, Object> params) {

    }

    @Override
    protected boolean analysisOutput(String result) throws JSONException {
        JSONObject object=new JSONObject(result);
        return false;
    }

    @Override
    protected String getMethodName() {
        return "";
    }
}
