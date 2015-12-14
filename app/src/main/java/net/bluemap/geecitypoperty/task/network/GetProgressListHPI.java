package net.bluemap.geecitypoperty.task.network;

import android.content.Context;

import net.bluemap.geecitypoperty.common.HttpPostAPI;
import net.bluemap.geecitypoperty.task.model.ProgressBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 获取进展列表
 * Created by LiuPeng on 15/10/29.
 */
public class GetProgressListHPI extends HttpPostAPI {

    private String taskId;
    private int page;

    private List<ProgressBean> progressList = new ArrayList<>();

    public GetProgressListHPI(Context context) {
        super(context);
    }

    @Override
    protected void getInputParam(HashMap<String, Object> params) {
        params.put("taskId", taskId);
        params.put("page", page);
    }

    @Override
    protected boolean analysisOutput(String result) throws JSONException {
        progressList.clear();
        JSONArray data = new JSONArray(result);
        for(int i = 0; i<data.length(); i++){
            ProgressBean pb = new ProgressBean();
            JSONObject o = data.getJSONObject(i);
            pb.setNumber(o.getString("number"));
            pb.setCreateName(o.getString("createName"));
            pb.setCreateTime(o.getString("createTime"));
            pb.setState(o.getString("state"));
            pb.setContent(o.getString("content"));
            pb.setRemark1(o.getString("remark1"));
            pb.setRemark2(o.getString("remark2"));
            pb.setRemark3(o.getString("remark3"));
            String[] images = o.getString("images").split(",");
            for(String image : images){
                if(!image.equals("")){
                    pb.getImages().add(getURL()+image.substring(1));
                }
            }
            progressList.add(pb);
        }
        return true;
    }

    @Override
    protected String getMethodName() {
        return "task/progressList.php";
    }

    public List<ProgressBean> getProgressList() {
        return progressList;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public void setPage(int page) {
        this.page = page;
    }
}
