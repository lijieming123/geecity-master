package net.bluemap.geecitypoperty.room.network;

import android.content.Context;

import net.bluemap.geecitypoperty.common.model.KeyValueBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 获取小区列表
 * Created by LiuPeng on 2015/8/22.
 */
public class GetUnitHPI extends GetRoomLevel {

    private String courtId;
    private String buildingId;

    private List<KeyValueBean> units = new ArrayList<>();

    public GetUnitHPI(Context context) {
        super(context);
    }

    @Override
    protected void getInputParam(HashMap<String, Object> params) {
        params.put("courtId", courtId);
        params.put("buildingId", buildingId);
    }

    @Override
    protected boolean analysisOutput(String result) throws JSONException {
        JSONArray data = new JSONArray(result);
        units.clear();
        for(int i = 0; i < data.length(); i++){
            JSONObject o = data.getJSONObject(i);
            KeyValueBean rf = new KeyValueBean(o.getString("id"),o.getString("name"));
            units.add(rf);
        }
        return true;
    }

    @Override
    protected String getMethodName() {
        return "room/unit.php";
    }

    @Override
    public void setIds(String userName, String courtId, String buildingId, String unitId) {
        this.courtId = courtId;
        this.buildingId = buildingId;
    }

    @Override
    public List<KeyValueBean> getList() {
        return units;
    }
}
