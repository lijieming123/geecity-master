package net.bluemap.geecitypoperty.task.network;

import android.content.Context;

import net.bluemap.geecitypoperty.common.HttpPostAPI;
import net.bluemap.geecitypoperty.common.model.KeyValueBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 获取接待类型
 * Created by LiuPeng on 2015/10/24.
 */
public class GetTaskLevelHPI extends HttpPostAPI {

    List<KeyValueBean> list = new ArrayList<>();

    public GetTaskLevelHPI(Context context) {
        super(context);
    }

    @Override
    protected void getInputParam(HashMap<String, Object> params) {

    }

    @Override
    protected boolean analysisOutput(String result) throws JSONException {
        JSONArray data = new JSONArray(result);
        list.clear();
        for(int i = 0; i<data.length();i++){
            JSONObject o = data.getJSONObject(i);
            KeyValueBean kvb = new KeyValueBean();
            kvb.setKey(o.getString("id"));
            kvb.setValue(o.getString("name"));
            list.add(kvb);
        }
        return true;
    }

    public List<KeyValueBean> getList() {
        return list;
    }

    @Override
    protected String getMethodName() {
        return "task/level.php";
    }
}
