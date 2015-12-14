package net.bluemap.geecitypoperty.device.network;

import android.content.Context;

import net.bluemap.geecitypoperty.common.HttpPostAPI;
import net.bluemap.geecitypoperty.common.model.DeviceHandleType;
import net.tsz.afinal.FinalDb;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 获取设备操作类别
 * Created by LiuPeng on 2015/8/14.
 */
public class GetDeviceHandleTypeHPI extends HttpPostAPI {

    List<String> types = new ArrayList<>();

    public GetDeviceHandleTypeHPI(Context context) {
        super(context);
    }

    @Override
    protected void getInputParam(HashMap<String, Object> params) {

    }

    @Override
    protected boolean analysisOutput(String result) throws JSONException {
        JSONArray data =  new JSONArray(result);
        types.clear();
        for(int i = 0; i< data.length(); i++){
            types.add(data.getString(i));
        }
        return true;
    }

    @Override
    protected boolean doOffLine() {
        types.clear();
        FinalDb db = FinalDb.create(getContext());
        List<DeviceHandleType> list = db.findAll(DeviceHandleType.class);
        for(DeviceHandleType dht : list){
            types.add(dht.getName());
        }
        return true;
    }

    @Override
    protected String getMethodName() {
        return "device/handletype.php";
    }

    public List<String> getTypes() {
        return types;
    }
}
