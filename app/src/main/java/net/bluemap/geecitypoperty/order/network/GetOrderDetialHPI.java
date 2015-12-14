package net.bluemap.geecitypoperty.order.network;

import android.content.Context;

import net.bluemap.geecitypoperty.common.HttpPostAPI;
import net.bluemap.geecitypoperty.common.Util;
import net.bluemap.geecitypoperty.order.model.OrderBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import hz.toollib.util.StringUtil;

/**
 * 获取派单详情
 * Created by LiuPeng on 2015/8/8.
 */
public class GetOrderDetialHPI extends HttpPostAPI{

    //输入参数
    private String id;

    //输出
    private OrderBean order = new OrderBean();

    public GetOrderDetialHPI(Context context) {
        super(context);
    }

    @Override
    protected void getInputParam(HashMap<String, Object> params) {
        params.put("id", id);
    }

    @Override
    protected boolean analysisOutput(String result) throws JSONException {
        JSONObject data = new JSONObject(result);
        //基础信息
        order.setId(id);
        order.setType(data.getString("type"));
        order.setContact(data.getString("contact"));
        order.setPhone(data.getString("tel"));
        order.setSubmitTime(Util.getClientDatetime(data.getString("submitTime")));
        if(StringUtil.isEmpty(data.getString("orderTime"))){
            order.setOrderTime("无");
        }else {
            order.setOrderTime(Util.getClientDatetime(data.getString("orderTime")));
        }
        order.setRoom(data.getString("room"));
        order.setContent(data.getString("content"));
        // 图片
        order.getImages().clear();
        JSONArray images = data.getJSONArray("images");
        for(int i = 0; i < images.length(); i++){
            order.getImages().add(images.getString(i));
        }

        //报修时间，要求完工时间
        order.setRepairTime(Util.getClientDatetime(data.getString("repairTime")));
        order.setExpectTime(Util.getClientDatetime(data.getString("expectTime")));
        //派工时间
        order.setTaskTime(Util.getClientDatetime(data.getString("taskTime")));
        //结单时间、开工时间、完工时间、材料费、工时费、材料使用情况、其他说明
        order.setCloseTime(Util.getClientDatetime(data.getString("closeTime")));
        order.setStartTime(Util.getClientDatetime(data.getString("startTime")));
        order.setEndTime(Util.getClientDatetime(data.getString("endTime")));
        //费用保留两位小数
        double materialCost = Double.valueOf(data.getString("materialCost"));
        double hourCharge = Double.valueOf(data.getString("hourCharge"));
        order.setMaterialCost(String.format("%.2f",materialCost));
        order.setHourCharge(String.format("%.2f",hourCharge));
        order.setMaterialUsage(data.getString("materialUsage"));
        order.setOther(data.getString("other"));
        //截停时间、截停原因
        order.setPauseTime(Util.getClientDatetime(data.getString("pauseTime")));
        order.setPauseReason(data.getString("pauseReason"));

        return true;
    }

    @Override
    protected String getMethodName() {
        return "order/detail.php";
    }

    public void setId(String id) {
        this.id = id;
    }

    public OrderBean getOrder() {
        return order;
    }
}
