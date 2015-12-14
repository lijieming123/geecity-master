package net.bluemap.geecitypoperty.order.network;

import android.content.Context;

import net.bluemap.geecitypoperty.common.HttpPostAPI;
import net.bluemap.geecitypoperty.order.model.DepartBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 获取处理部门接口
 * Created by LiuPeng on 2015/8/12.
 */
public class GetDepartmentHPI extends HttpPostAPI {

    private List<DepartBean> departments = new ArrayList<>();

    public GetDepartmentHPI(Context context) {
        super(context);
    }

    @Override
    protected void getInputParam(HashMap<String, Object> params) {

    }

    @Override
    protected boolean analysisOutput(String result) throws JSONException {
        departments.clear();
        JSONArray data = new JSONArray(result);
        for(int i = 0; i < data.length(); i++){
            DepartBean db = new DepartBean();
            JSONObject object = data.getJSONObject(i);
            db.setId(object.getString("id"));
            db.setName(object.getString("name"));
            departments.add(db);
        }
        return true;
    }

    @Override
    protected String getMethodName() {
        return "order/department.php";
    }

    public List<DepartBean> getDepartments() {
        return departments;
    }
}
