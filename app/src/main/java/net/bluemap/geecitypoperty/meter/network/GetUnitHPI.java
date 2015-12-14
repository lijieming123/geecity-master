package net.bluemap.geecitypoperty.meter.network;

import android.content.Context;

import net.bluemap.geecitypoperty.common.HttpPostAPI;
import net.bluemap.geecitypoperty.meter.model.RoomFilterBean;

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
public class GetUnitHPI extends HttpPostAPI{

    private String courtId;
    private String buildingId;

    private List<RoomFilterBean> units = new ArrayList<>();

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
            RoomFilterBean rf = new RoomFilterBean();
            JSONObject o = data.getJSONObject(i);
            rf.setId(o.getString("id"));
            rf.setText(o.getString("name"));
            units.add(rf);
        }
        return true;
    }

    @Override
    protected String getMethodName() {
        return "room/unit.php";
    }

    public void setCourtId(String courtId) {
        this.courtId = courtId;
    }

    public void setBuildingId(String buildingId) {
        this.buildingId = buildingId;
    }

    public List<RoomFilterBean> getUnits() {
        return units;
    }
}
