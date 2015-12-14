package net.bluemap.geecitypoperty.order.network;

import android.content.Context;

import net.bluemap.geecitypoperty.common.HttpPostAPI;
import net.bluemap.geecitypoperty.common.Util;
import net.bluemap.geecitypoperty.order.model.OrderBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import hz.toollib.util.StringUtil;

/**
 * 获取派单列表接口
 * Created by Administrator on 2015/7/30.
 */
public class GetOrderListHPI extends HttpPostAPI {

    //输入
    private int page;
    private String areaId = "";
    private String state;
    private String userId = "";
    //输出
    List<OrderBean> orders = new ArrayList<>();

    public GetOrderListHPI(Context context) {
        super(context);
    }

    @Override
    protected void getInputParam(HashMap<String, Object> params) {
        params.put("state", state);
        params.put("areaId", areaId);
        params.put("userId", userId);
        params.put("page", page);
    }

    @Override
    protected boolean analysisOutput(String result) throws JSONException {
        JSONArray data = new JSONArray(result);
        orders.clear();
        for(int i = 0; i < data.length(); i++){
            OrderBean ob = new OrderBean();
            JSONObject oj = data.getJSONObject(i);
            ob.setState(state);
            ob.setContact(oj.getString("contact"));
            ob.setId(oj.getString("id"));
            ob.setType(oj.getString("type"));
            ob.setPhone(oj.getString("tel"));
            ob.setRoom(oj.getString("room"));
            ob.setSubmitTime(Util.getClientDatetime(oj.getString("submitTime")));
            if(StringUtil.isEmpty(oj.getString("orderTime"))){
                ob.setOrderTime("无");
            }else{
                ob.setOrderTime(Util.getClientDatetime(oj.getString("orderTime")));
            }
            orders.add(ob);
        }
        return true;
    }

    @Override
    protected String getMethodName() {
        return "order/list.php";
    }

    public List<OrderBean> getOrders() {
        return orders;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }
}
