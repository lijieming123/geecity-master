package net.bluemap.geecitypoperty.device.network;

import android.content.Context;
import android.graphics.Bitmap;

import net.bluemap.geecitypoperty.common.HttpPostAPI;
import net.bluemap.geecitypoperty.device.model.DeviceHandleBean;
import net.tsz.afinal.FinalDb;

import org.json.JSONException;

import java.util.HashMap;
import java.util.List;

import hz.toollib.util.FileUtil;
import hz.toollib.util.ImageUtil;

/**
 * 设备处理接口
 * Created by LiuPeng on 2015/8/16.
 */
public class HandleDeviceHPI extends HttpPostAPI{
    //输入参数
    private String id;
    private String userId;
    private String type;
    private String state;
    private String content;
    private List<String> images;

    public HandleDeviceHPI(Context context) {
        super(context);
    }


    @Override
    protected void getInputParam(HashMap<String, Object> params) {
        params.put("id",id);
        params.put("userId",userId);
        params.put("type",type);
        params.put("state",state);
        params.put("content", content);
        //将图片转换成base64
        if(images != null){
            String base64s = "";
            for(String image : images){
                //按照720比例压缩图片
                Bitmap bitmap = ImageUtil.getBitmapFromFile(image);
                int height = 720;
                int width = (int)(height * (bitmap.getWidth() / (double)bitmap.getHeight()));
                Bitmap scaleBitmap = ImageUtil.getScaleBitmap(bitmap, width, height);
                //转base64
                ImageUtil.saveBitmapToFile(scaleBitmap,image);
                base64s = base64s + "," + FileUtil.file2Base64(image);
                //FileUtil.file2Base64File(image,FileUtil.getSDPath()+"/base64test.txt");
            }
            if(base64s.startsWith(",")){
                base64s = base64s.substring(1);
            }
            params.put("images",base64s);
        }
    }

    @Override
    protected boolean analysisOutput(String result) throws JSONException {
        return true;
    }

    @Override
    protected String getMethodName() {
        return "device/handle.php";
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    @Override
    protected boolean doOffLine() {
        FinalDb db = FinalDb.create(getContext());
        DeviceHandleBean dhb = new DeviceHandleBean();
        dhb.setDeviceId(id);
        dhb.setUserId(userId);
        dhb.setContent(content);
        if(images!=null){
            String imageFiles = "";
            for(String image : images){
                imageFiles = imageFiles + "," + image;
            }
            if(imageFiles.startsWith(",")){
                imageFiles = imageFiles.substring(1);
            }
            dhb.setImages(imageFiles);
        }
        dhb.setState(state);
        dhb.setType(type);
        db.save(dhb);
        return true;
    }
}
