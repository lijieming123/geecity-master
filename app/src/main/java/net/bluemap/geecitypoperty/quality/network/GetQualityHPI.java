package net.bluemap.geecitypoperty.quality.network;

import android.content.Context;

import net.bluemap.geecitypoperty.common.HttpPostAPI;
import net.bluemap.geecitypoperty.quality.model.QualityCheckItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 获取品质检查项目列表
 * Created by LiuPeng on 2015/9/14.
 */
public class GetQualityHPI extends HttpPostAPI {

    List<QualityCheckItem> list = new ArrayList<>();

    public GetQualityHPI(Context context) {
        super(context);
    }

    @Override
    protected void getInputParam(HashMap<String, Object> params) {

    }

    @Override
    protected boolean analysisOutput(String result) throws JSONException {
        JSONArray data = new JSONArray(result);
        list.clear();
        for(int i =0;i<data.length();i++){
            JSONObject object = data.getJSONObject(i);
            QualityCheckItem item = new QualityCheckItem();
            item.setId(object.getInt("id"));
            item.setProject(object.getString("project"));
            item.setScoreDetail(object.getString("scoreDetail"));
            item.setScoreTotal(object.getInt("scoreTotal"));
            list.add(item);
        }
        return true;
    }

    @Override
    protected String getMethodName() {
        return "quality/quality.php";
    }

    public List<QualityCheckItem> getList() {
        return list;
    }
}
