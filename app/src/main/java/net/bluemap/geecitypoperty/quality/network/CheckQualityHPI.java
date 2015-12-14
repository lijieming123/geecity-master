package net.bluemap.geecitypoperty.quality.network;

import android.content.Context;

import net.bluemap.geecitypoperty.common.HttpPostAPI;

import org.json.JSONException;

import java.util.HashMap;

/**
 * 提交品质检查
 *
 * Created by LiuPeng on 2015/9/14.
 */
public class CheckQualityHPI extends HttpPostAPI {

    public CheckQualityHPI(Context context) {
        super(context);
    }

    @Override
    protected void getInputParam(HashMap<String, Object> params) {
        //TODO 输入参数
    }

    @Override
    protected boolean analysisOutput(String result) throws JSONException {
        return true;
    }

    @Override
    protected String getMethodName() {
        return "quality/check.php";
    }

}
