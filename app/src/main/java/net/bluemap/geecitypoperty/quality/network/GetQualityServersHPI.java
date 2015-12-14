package net.bluemap.geecitypoperty.quality.network;

import android.content.Context;

import net.bluemap.geecitypoperty.common.HttpPostAPI;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 获取品质检查服务提供者列表
 * Created by LiuPeng on 2015/9/14.
 */
public class GetQualityServersHPI extends HttpPostAPI{

    //out
    private List<HashMap<String,Object>> list = new ArrayList<>();

    public GetQualityServersHPI(Context context) {
        super(context);
    }

    @Override
    protected void getInputParam(HashMap<String, Object> params) {

    }

    @Override
    protected boolean analysisOutput(String result) throws JSONException {
        list.clear();
        JSONArray data = new JSONArray(result);
        for(int i = 0; i < data.length(); i++){
            HashMap<String,Object> map = new HashMap<>();
            JSONObject object = data.getJSONObject(i);
            map.put("id",object.getInt("id"));
            map.put("name", object.getString("name"));
            list.add(map);
        }
        return true;
    }

    @Override
    protected String getMethodName() {
        return "quality/servers.php";
    }

    public List<HashMap<String, Object>> getList() {
        return list;
    }
}
