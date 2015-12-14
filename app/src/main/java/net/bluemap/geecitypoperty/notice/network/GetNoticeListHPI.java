package net.bluemap.geecitypoperty.notice.network;

import android.content.Context;

import net.bluemap.geecitypoperty.common.HttpPostAPI;
import net.bluemap.geecitypoperty.notice.model.NoticeBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import hz.toollib.util.DateTimeUtil;

/**
 * 获取通知列表
 * Created by Liu Peng on 2015/7/26.
 */
public class GetNoticeListHPI extends HttpPostAPI {

    //入参
    private String username;
    private String departId;

    private int page;

    //出参
    List<NoticeBean> notices = new ArrayList<NoticeBean>();

    public GetNoticeListHPI(Context context) {
        super(context);
    }

    @Override
    protected void getInputParam(HashMap<String, Object> params) {
        params.put("username",username);
        params.put("departId",departId);
        params.put("page", page);
    }

    //[{"id":2,"title":"测试","pubTime":"2015-05-04 19:34:10","isRead":true}]
    @Override
    protected boolean analysisOutput(String result) throws JSONException {
        notices.clear();
        JSONArray data = new JSONArray(result);
        for(int i = 0; i< data.length(); i++){
            NoticeBean nb = new NoticeBean();
            JSONObject nj = data.getJSONObject(i);
            nb.setId(nj.getString("id"));
            nb.setTitle(nj.getString("title"));
            nb.setDetail(nj.getString("content").replace("&nbsp;",""));
            nb.setRead(nj.getBoolean("isRead"));
            nb.setDate(DateTimeUtil.formatDateTime("yyyy-MM-dd HH:mm:ss", nj.getString("pubTime"), "M月d日"));
            notices.add(nb);
        }
        return true;
    }

    @Override
    protected String getMethodName() {
        return "notice/list.php";
    }

    public List<NoticeBean> getNotices() {
        return notices;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setDepartId(String departId) {
        this.departId = departId;
    }

    public void setPage(int page) {
        this.page = page;
    }
}
