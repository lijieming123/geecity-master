package net.bluemap.geecitypoperty.task.network;

import android.content.Context;

import net.bluemap.geecitypoperty.common.HttpPostAPI;

import org.json.JSONException;

import java.util.HashMap;

/**
 * 新增联系记录
 * Created by LiuPeng on 15/10/29.
 */
public class AddContactHPI extends HttpPostAPI{

    //id，创建人，内容，联系人
    private String taskId;
    private String createName;
    private String content;
    private String contact;

    public AddContactHPI(Context context) {
        super(context);
    }

    @Override
    protected void getInputParam(HashMap<String, Object> params) {
        params.put("taskId", taskId);
        params.put("createName", createName);
        params.put("content", content);
        params.put("contact", contact);
    }

    @Override
    protected boolean analysisOutput(String result) throws JSONException {
        return true;
    }

    @Override
    protected String getMethodName() {
        return "task/addContact.php";
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public void setCreateName(String createName) {
        this.createName = createName;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
}
