package net.bluemap.geecitypoperty.quality.network;

import android.content.Context;

import net.bluemap.geecitypoperty.common.HttpPostAPI;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 获取品质检查的服务内容列表（用于下拉菜单）
 * Created by LiuPeng on 2015/9/14.
 */
public class GetQualityServicesHPI extends HttpPostAPI {

    private List<HashMap<String,Object>> list = new ArrayList<>();

    public GetQualityServicesHPI(Context context) {
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
            map.put("id",data.getString(i));
            map.put("name",data.getString(i));
            list.add(map);
        }
        return true;
    }

    @Override
    protected String getMethodName() {
        return "quality/services.php";
    }

    public List<HashMap<String, Object>> getList() {
        return list;
    }
}
