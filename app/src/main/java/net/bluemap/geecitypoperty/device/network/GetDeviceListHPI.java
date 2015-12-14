package net.bluemap.geecitypoperty.device.network;

import android.content.Context;

import net.bluemap.geecitypoperty.common.HttpPostAPI;
import net.bluemap.geecitypoperty.device.model.DeviceOfflineBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 获取设备列表
 * Created by LiuPeng on 2015/9/3.
 */
public class GetDeviceListHPI extends HttpPostAPI{

    private List<DeviceOfflineBean> list = new ArrayList<>();

    public GetDeviceListHPI(Context context) {
        super(context);
    }

    @Override
    protected void getInputParam(HashMap<String, Object> params) {

    }

    @Override
    protected boolean analysisOutput(String result) throws JSONException {
        JSONArray array = new JSONArray(result);
        list.clear();
        for(int i = 0; i < array.length(); i++){
            JSONObject object = array.getJSONObject(i);
            DeviceOfflineBean dob = new DeviceOfflineBean();
            dob.setId(object.getString("id"));
            dob.setName(object.getString("name"));
            dob.setPosition(object.getString("position"));
            dob.setJson("{}");
            list.add(dob);
        }
        return true;
    }

    @Override
    protected String getMethodName() {
        return "device/list.php";
    }

    public List<DeviceOfflineBean> getList() {
        return list;
    }
}
