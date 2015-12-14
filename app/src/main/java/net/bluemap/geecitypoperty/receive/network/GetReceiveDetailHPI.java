package net.bluemap.geecitypoperty.receive.network;

import android.content.Context;

import net.bluemap.geecitypoperty.common.HttpPostAPI;
import net.bluemap.geecitypoperty.receive.model.ReceiveBean;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import hz.toollib.util.StringUtil;

/**
 * 接待详情接口
 * Created by LiuPeng on 2015/10/25.
 */
public class GetReceiveDetailHPI extends HttpPostAPI {

    //in
    private String receiveId;

    //out
    private ReceiveBean receive;

    public GetReceiveDetailHPI(Context context) {
        super(context);
    }

    @Override
    protected void getInputParam(HashMap<String, Object> params) {
        params.put("receiveId", receiveId);
    }

    @Override
    protected boolean analysisOutput(String result) throws JSONException {
        JSONObject data = new JSONObject(result);
        receive = new ReceiveBean();
        receive.setId(data.getString("id"));
        receive.setAddress(data.getString("address"));
        receive.setContact(data.getString("contact"));
        receive.setContent(data.getString("content"));
        if(!StringUtil.isEmpty(data.getString("orderTime")))
            receive.setOrderTime(data.getString("orderTime"));
        receive.setReceiver(data.getString("receiver"));
        receive.setAccepter(data.getString("accepter"));
        receive.setState(data.getString("state"));
        if (!StringUtil.isEmpty(data.getString("source")))
            receive.setSource(data.getString("source"));
        if (!StringUtil.isEmpty(data.getString("type")))
            receive.setType(data.getString("type"));
        receive.setTel(data.getString("tel"));
        if(!StringUtil.isEmpty(data.getString("reaction")))
            receive.setReaction(data.getString("reaction"));
        if (!StringUtil.isEmpty(data.getString("twos")))
            receive.setTwos(data.getString("twos"));
        if (!StringUtil.isEmpty(data.getString("time")))
            receive.setTime(data.getString("time"));
        if(!StringUtil.isEmpty(data.getString("accepterCom")))
            receive.setAccepterCom(data.getString("accepterCom"));
        String[] images = data.getString("images").split(",");
        for(String image : images){
            if(!image.equals("")){
                receive.getImages().add(getURL()+image.substring(1));
            }
        }
        return true;
    }

    @Override
    protected String getMethodName() {
        return "receive/detail.php";
    }

    public ReceiveBean getReceive() {
        return receive;
    }

    public void setReceiveId(String receiveId) {
        this.receiveId = receiveId;
    }
}
