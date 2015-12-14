package net.bluemap.geecitypoperty.task.network;

import android.content.Context;

import net.bluemap.geecitypoperty.common.HttpPostAPI;
import net.bluemap.geecitypoperty.task.model.ContactBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 获取联系记录列表
 * Created by LiuPeng on 15/10/29.
 */
public class GetContactListHPI extends HttpPostAPI {

    private String taskId;
    private int page;

    List<ContactBean> contacts = new ArrayList<>();

    public GetContactListHPI(Context context) {
        super(context);
    }

    @Override
    protected void getInputParam(HashMap<String, Object> params) {
        params.put("taskId", taskId);
        params.put("page", page);
    }

    @Override
    protected boolean analysisOutput(String result) throws JSONException {
        JSONArray data = new JSONArray(result);
        contacts.clear();
        for(int i = 0; i< data.length(); i++){
            ContactBean cb = new ContactBean();
            JSONObject o = data.getJSONObject(i);
            cb.setNumber(o.getString("number"));
            cb.setContact(o.getString("contact"));
            cb.setContent(o.getString("content"));
            cb.setCreateName(o.getString("createName"));
            cb.setCreateTime(o.getString("createTime"));
            contacts.add(cb);
        }
        return true;
    }

    @Override
    protected String getMethodName() {
        return "task/contactList.php";
    }

    public List<ContactBean> getContacts() {
        return contacts;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public void setPage(int page) {
        this.page = page;
    }
}
