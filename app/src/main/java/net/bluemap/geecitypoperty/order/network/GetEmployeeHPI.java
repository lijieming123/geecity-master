package net.bluemap.geecitypoperty.order.network;

import android.content.Context;

import net.bluemap.geecitypoperty.common.HttpPostAPI;
import net.bluemap.geecitypoperty.order.model.EmployeeBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 获取处理人员接口
 * Created by LiuPeng on 2015/8/12.
 */
public class GetEmployeeHPI extends HttpPostAPI {


    private List<EmployeeBean> employee = new ArrayList<>();
    private String departId;

    public GetEmployeeHPI(Context context) {
        super(context);
    }

    @Override
    protected void getInputParam(HashMap<String, Object> params) {
        params.put("departId",departId);
    }

    @Override
    protected boolean analysisOutput(String result) throws JSONException {
        employee.clear();
        JSONArray data = new JSONArray(result);
        for(int i = 0; i < data.length(); i++){
            EmployeeBean eb = new EmployeeBean();
            JSONObject object = data.getJSONObject(i);
            eb.setId(object.getString("id"));
            eb.setName(object.getString("name"));
            eb.setSelect(false);
            employee.add(eb);
        }
        return true;
    }

    @Override
    protected String getMethodName() {
        return "order/employee.php";
    }


    public void setDepartId(String departId) {
        this.departId = departId;
    }

    public List<EmployeeBean> getEmployee() {
        return employee;
    }
}
