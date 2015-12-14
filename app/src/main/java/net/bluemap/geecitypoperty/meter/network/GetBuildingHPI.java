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
public class GetBuildingHPI extends HttpPostAPI{

    private String courtId;

    private List<RoomFilterBean> buildings = new ArrayList<>();

    public GetBuildingHPI(Context context) {
        super(context);
    }

    @Override
    protected void getInputParam(HashMap<String, Object> params) {
        params.put("courtId",courtId);
    }

    @Override
    protected boolean analysisOutput(String result) throws JSONException {
        JSONArray data = new JSONArray(result);
        buildings.clear();
        for(int i = 0; i < data.length(); i++){
            RoomFilterBean rf = new RoomFilterBean();
            JSONObject o = data.getJSONObject(i);
            rf.setId(o.getString("id"));
            rf.setText(o.getString("name"));
            buildings.add(rf);
        }
        return true;
    }

    @Override
    protected String getMethodName() {
        return "room/building.php";
    }

    public void setCourtId(String courtId) {
        this.courtId = courtId;
    }

    public List<RoomFilterBean> getBuildings() {
        return buildings;
    }
}
