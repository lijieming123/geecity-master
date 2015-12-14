package net.bluemap.geecitypoperty.device.network;

import android.content.Context;

import net.bluemap.geecitypoperty.common.HttpPostAPI;
import net.bluemap.geecitypoperty.common.Util;
import net.bluemap.geecitypoperty.device.model.CheckBean;
import net.bluemap.geecitypoperty.device.model.DeviceBean;
import net.bluemap.geecitypoperty.device.model.DeviceOfflineBean;
import net.bluemap.geecitypoperty.device.model.HistoryBean;
import net.tsz.afinal.FinalDb;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * 获取设备操作类别
 * Created by LiuPeng on 2015/8/14.
 */
public class GetDeviceDetailHPI extends HttpPostAPI {

    //入
    private String id;

    //出
    private DeviceBean device;

    private String jsonString="{}";

    public GetDeviceDetailHPI(Context context) {
        super(context);
    }

    @Override
    protected void getInputParam(HashMap<String, Object> params) {
        params.put("id", id);
    }

    @Override
    protected boolean analysisOutput(String result) throws JSONException {
        jsonString = result;
        JSONObject data = new JSONObject(result);
        device = new DeviceBean();
        device.setId(id);
        device.setName(data.getString("name"));
        device.setPosition(data.getString("position"));
        device.setCourtId(data.getString("courtId"));
        //巡检列表
        JSONArray checkArray = data.getJSONArray("checkList");
        device.getChecks().clear();
        for(int i = 0; i < checkArray.length(); i ++){
            JSONObject check = checkArray.getJSONObject(i);
            CheckBean cb = new CheckBean();
            cb.setType(check.getString("type"));
            cb.setLastTime(Util.getClientDate(check.getString("lastTime")));
            cb.setNextTime(Util.getClientDate(check.getString("nextTime")));
            device.getChecks().add(cb);
        }
        //历史记录
        JSONArray historyArray = data.getJSONArray("historyList");
        device.getHistory().clear();
        for(int i = 0; i < historyArray.length(); i ++){
            JSONObject hist = historyArray.getJSONObject(i);
            HistoryBean hb = new HistoryBean();
            hb.setType(hist.getString("type"));
            hb.setLastTime(Util.getClientDate(hist.getString("lastTime")));
            hb.setState(hist.getString("state"));
            hb.setSituation(hist.getString("situation"));
            device.getHistory().add(hb);
        }
        return true;
    }

    @Override
    protected String getMethodName() {
        return "device/detail.php";
    }

    public void setId(String id) {
        this.id = id;
    }

    public DeviceBean getDevice() {
        return device;
    }

    public String getJson() {
        return jsonString;
    }

    @Override
    protected boolean doOffLine() {
        FinalDb db = FinalDb.create(getContext());
        DeviceOfflineBean dob = db.findById(id, DeviceOfflineBean.class);
        try {
            analysisOutput(dob.getJson());
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
