package net.bluemap.geecitypoperty.meter.network;

import android.content.Context;

import net.bluemap.geecitypoperty.common.HttpPostAPI;
import net.bluemap.geecitypoperty.meter.model.RoomBean;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * 获取房间详细接口
 * Created by LiuPeng on 2015/8/22.
 */
public class GetRoomDetailHPI extends HttpPostAPI{

    private String courtId;
    private String buildingId;
    private String unitId;
    private String roomId;

    private RoomBean room;

    public GetRoomDetailHPI(Context context) {
        super(context);
    }

    @Override
    protected void getInputParam(HashMap<String, Object> params) {
        params.put("courtId", courtId);
        params.put("buildingId", buildingId);
        params.put("unitId", unitId);
        params.put("roomId", roomId);
    }

    @Override
    protected boolean analysisOutput(String result) throws JSONException {
        JSONObject data = new JSONObject(result);
        room = new RoomBean();
        //room.setId(data.getString("id"));
        //room.setName(data.getString("name"));
        room.setResident(data.getString("name"));
        //room.setPayToDate(Util.getClientDate(data.getString("payToDate")));
        room.setPhone(data.getString("tel"));
        return true;
    }

    @Override
    protected String getMethodName() {
        return "room/detail.php";
    }
    public RoomBean getRoom() {
        return room;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public void setCourtId(String courtId) {
        this.courtId = courtId;
    }

    public void setBuildingId(String buildingId) {
        this.buildingId = buildingId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }
}
