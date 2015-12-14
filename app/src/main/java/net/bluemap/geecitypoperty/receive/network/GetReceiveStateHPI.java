package net.bluemap.geecitypoperty.receive.network;

import android.content.Context;

import net.bluemap.geecitypoperty.common.HttpPostAPI;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 获取接待状态
 * Created by LiuPeng on 15/10/22.
 */
public class GetReceiveStateHPI extends HttpPostAPI {

    private List<HashMap<String,Object>> states = new ArrayList<>();

    public GetReceiveStateHPI(Context context) {
        super(context);
    }

    @Override
    protected void getInputParam(HashMap<String, Object> params) {

    }

    @Override
    protected boolean analysisOutput(String result) throws JSONException {
        states.clear();
        JSONArray data = new JSONArray(result);
        for(int i = 0 ;i <data.length();i++){
            JSONObject o = data.getJSONObject(i);
            HashMap<String,Object> map = new HashMap<>();
            map.put("remark",o.getString("remark"));
            map.put("value",o.getString("value"));
            map.put("count",o.getString("count"));
            //map.put("text",o.getString("value")+"("+o.getString("count")+")");
            map.put("text",o.getString("value"));
            states.add(map);
        }
        return true;
    }

    @Override
    protected String getMethodName() {
        return "receive/state.php";
    }

    public List<HashMap<String, Object>> getStates() {
        return states;
    }
}
