package net.bluemap.geecitypoperty.receive.network;

import android.content.Context;

import net.bluemap.geecitypoperty.common.HttpPostAPI;
import net.bluemap.geecitypoperty.receive.model.ReceiveBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import hz.toollib.util.StringUtil;

/**
 * 获取接待列表
 * Created by LiuPeng on 15/10/22.
 */
public class GetReceiveListHPI extends HttpPostAPI{

    //入参
    private int page;
    private String courtId;
    private String userName;
    private String remark;
    private List<ReceiveBean> receiveList = new ArrayList<>();

    public GetReceiveListHPI(Context context) {
        super(context);
    }

    @Override
    protected void getInputParam(HashMap<String, Object> params) {
        params.put("page",page);
        params.put("courtId", courtId);
        params.put("userName", userName);
        params.put("remark", remark);
    }

    @Override
    protected boolean analysisOutput(String result) throws JSONException {
        receiveList.clear();
        JSONArray data = new JSONArray(result);
        for(int i = 0; i<data.length();i++){
            JSONObject o = data.getJSONObject(i);
            ReceiveBean rb  = new ReceiveBean();
            rb.setId(o.getString("id"));
            if(!StringUtil.isEmpty(o.getString("source")))
                rb.setSource(o.getString("source"));
            if(!StringUtil.isEmpty(o.getString("type")))
                rb.setType(o.getString("type"));
            rb.setAddress(o.getString("address"));
            rb.setContact(o.getString("contact"));
            rb.setContent(o.getString("content"));
            rb.setAccepter(o.getString("accepter"));
            rb.setReceiver(o.getString("receiver"));
            if(!StringUtil.isEmpty(o.getString("time")))
                rb.setTime(o.getString("time"));
            rb.setState(o.getString("state"));
            receiveList.add(rb);
        }
        return true;
    }

    @Override
    protected String getMethodName() {
        return "receive/list.php";
    }

    public List<ReceiveBean> getReceiveList() {
        return receiveList;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public void setCourtId(String courtId) {
        this.courtId = courtId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
