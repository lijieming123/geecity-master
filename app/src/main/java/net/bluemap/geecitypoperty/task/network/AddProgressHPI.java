package net.bluemap.geecitypoperty.task.network;

import android.content.Context;
import android.graphics.Bitmap;

import net.bluemap.geecitypoperty.common.HttpPostAPI;

import org.json.JSONException;

import java.util.HashMap;
import java.util.List;

import hz.toollib.util.FileUtil;
import hz.toollib.util.ImageUtil;

/**
 * 新增进展
 * Created by LiuPeng on 15/10/29.
 */
public class AddProgressHPI extends HttpPostAPI {

    private String taskId;
    private String receiveId;
    private String createName;
    private String state;
    private String content;
    private String remark1;
    private String remark2;
    private String remark3;
    //图片
    private List<String> images;

    public AddProgressHPI(Context context) {
        super(context);
    }

    @Override
    protected void getInputParam(HashMap<String, Object> params) {
        params.put("taskId", taskId);
        params.put("receiveId", receiveId);
        params.put("createName", createName);
        params.put("state", state);
        params.put("content", content);
        params.put("remark1", remark1);
        params.put("remark2", remark2);
        params.put("remark3", remark3);
        //将图片转换成base64
        if(images != null){
            StringBuilder builder = new StringBuilder();
            for(int i = 0 ; i < images.size(); i++){
                String base64 = "";
                //图片大于720p则按照720比例压缩图片
                Bitmap bitmap = ImageUtil.getBitmapFromFile(images.get(i));
                if(bitmap.getHeight()>720||bitmap.getWidth()>720){
                    int height = 720;
                    int width = (int)(height * (bitmap.getWidth() / (double)bitmap.getHeight()));
                    Bitmap scaleBitmap = ImageUtil.getScaleBitmap(bitmap, width, height);
                    ImageUtil.saveBitmapToFile(scaleBitmap,images.get(i));
                }
                //转base64
                base64 = FileUtil.file2Base64(images.get(i));
                builder.append(",").append(base64);
            }
            params.put("images",builder.toString());
        }else{
            params.put("images", "");
        }
    }

    @Override
    protected boolean analysisOutput(String result) throws JSONException {
        return true;
    }

    @Override
    protected String getMethodName() {
        return "task/addProgress.php";
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public void setReceiveId(String receiveId) {
        this.receiveId = receiveId;
    }

    public void setCreateName(String createName) {
        this.createName = createName;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setRemark1(String remark1) {
        this.remark1 = remark1;
    }

    public void setRemark2(String remark2) {
        this.remark2 = remark2;
    }

    public void setRemark3(String remark3) {
        this.remark3 = remark3;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }
}
