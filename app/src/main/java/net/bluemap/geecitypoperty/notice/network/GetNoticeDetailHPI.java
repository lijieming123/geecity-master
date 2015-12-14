package net.bluemap.geecitypoperty.notice.network;

import android.content.Context;

import net.bluemap.geecitypoperty.common.HttpPostAPI;
import net.bluemap.geecitypoperty.common.Util;
import net.bluemap.geecitypoperty.notice.model.NoticeBean;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;

import hz.toollib.util.DateTimeUtil;
import hz.toollib.util.StringUtil;

/**
 * 获取通知详细
 * Created by Liu Peng on 2015/7/26.
 */
public class GetNoticeDetailHPI extends HttpPostAPI {

    //入参
    private String username;
    private String realName;
    private String id;

    //出参
    NoticeBean notice;

    public GetNoticeDetailHPI(Context context) {
        super(context);
    }

    @Override
    protected void getInputParam(HashMap<String, Object> params) {
        params.put("username",username);
        params.put("realName",realName);
        params.put("id", id);
    }

    //"data":{"title":"新消息","content":"请问阿斯顿请问请问","pubTime":"2015-08-16 15:32:42","checkTime":"2015-08-16 16:11:02"}
    @Override
    protected boolean analysisOutput(String result) throws JSONException {
        notice = new NoticeBean();
        JSONObject data = new JSONObject(result);
        notice.setId(id);
        notice.setTitle(data.getString("title"));
        notice.setDetail(data.getString("content"));
        notice.setDate(DateTimeUtil.formatDateTime("yyyy-MM-dd HH:mm:ss", data.getString("pubTime"), "yyyy年M月d日"));
        if(StringUtil.isEmpty(data.getString("checkTime"))){
            //如果为null，获取当前时间
            notice.setCheckDate(DateTimeUtil.dateToString(Calendar.getInstance().getTime(),"yyyy年M月d日 HH : mm"));
        }else{
            notice.setCheckDate(Util.getClientDatetime(data.getString("checkTime")));
        }

        return true;
    }

    @Override
    protected String getMethodName() {
        return "notice/detail.php";
    }

    public NoticeBean getNotice() {
        return notice;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setId(String id) {
        this.id = id;
    }
}
