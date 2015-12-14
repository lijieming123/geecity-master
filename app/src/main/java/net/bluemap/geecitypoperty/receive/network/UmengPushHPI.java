package net.bluemap.geecitypoperty.receive.network;

import android.content.Context;

import net.bluemap.geecitypoperty.common.JsonUtil;
import net.bluemap.geecitypoperty.common.model.UmengHttpPostAPI;
import net.bluemap.geecitypoperty.task.model.TaskBean;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2015/12/9.
 * 用于友盟推送的接口
 */
public class UmengPushHPI extends UmengHttpPostAPI{
    private String appkey;
    private String timestamp;
    private String type;
    private String alias;
    private String payload;
    private String display_type;
    private String body;
    private String ticker;
    private String title;
    private String text;
    private String after_open;
    public void setmReceiver(String mReceiver) {
        this.mReceiver = mReceiver;
    }

    private String mReceiver;
    private List<TaskBean> mListReceive=new ArrayList<>();
    public UmengPushHPI(Context context) {
        super(context);
    }

    @Override
    protected void getInputParam(HashMap<String, Object> params) {
        params.put("appkey","56668599e0f55a1bac00062f");
        params.put("timestamp",1111111111);//时间戳10 或13位
        params.put("type","unicast");  // 必填 消息发送类型
        params.put("alias",mReceiver);   //当type=customizedcast时, 开发者填写自己的alias。要求不超过50个alias,多个alias以英文逗号间隔。
        params.put("payload","");// 必填 消息内容(Android最大为1840B), 包含参数说明如下(JSON格式):
        params.put("display_type","notification");
        params.put("body","");//消息类型
        params.put("ticker","");//通知栏显示的文字
        params.put("title","");//通知标题
        params.put("text","");//通知文字描述
        params.put("after_open","go_app");// 点击"通知"的后续行为，默认为打开app
    }
    public List<TaskBean> getmListReceive() {
        return mListReceive;
    }

    @Override
    protected boolean analysisOutput(String result) throws JSONException {
        mListReceive.clear();
        JSONObject data=new JSONObject(result);
        for(int i = 0; i<data.length(); i++){
            TaskBean task = new TaskBean();
            JSONObject o = data.getJSONObject(""+i);
            task.setReceiver(JsonUtil.getString(o,"receiver","无"));
            mListReceive.add(task);
        }
        return true;
    }

    @Override
    protected String getMethodName() {
        return "https://msg.umeng.com/api/send?sign=mysign";
    }
}
