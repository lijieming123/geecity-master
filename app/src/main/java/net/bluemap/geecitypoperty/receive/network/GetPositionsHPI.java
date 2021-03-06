package net.bluemap.geecitypoperty.receive.network;

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
 * Created by LiuPeng on 2015/11/20.
 */
public class GetPositionsHPI  extends HttpPostAPI {

    List<KeyValueBean> list = new ArrayList<>();

    public GetPositionsHPI(Context context) {
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
        return "task/position.php";
    }
}
