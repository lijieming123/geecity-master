package net.bluemap.geecitypoperty.receive.network;

import android.content.Context;

import net.bluemap.geecitypoperty.common.HttpPostAPI;

import org.json.JSONException;

import java.util.HashMap;

/**
 * Created by LiuPeng on 15/10/22.
 */
public class GetAreasHPI extends HttpPostAPI {
    public GetAreasHPI(Context context) {
        super(context);
    }

    @Override
    protected void getInputParam(HashMap<String, Object> params) {

    }

    @Override
    protected boolean analysisOutput(String result) throws JSONException {
        return true;
    }

    @Override
    protected String getMethodName() {
        return null;
    }
}
