package net.bluemap.geecitypoperty.receive.network;

import android.content.Context;
import android.graphics.Bitmap;

import net.bluemap.geecitypoperty.common.HttpPostAPI;

import org.json.JSONException;

import java.util.HashMap;
import java.util.List;

import hz.toollib.util.FileUtil;
import hz.toollib.util.ImageUtil;

/**
 * 新增接待
 * Created by LiuPeng on 2015/10/24.
 */
public class AddReceiveHPI extends HttpPostAPI {

    //入参
    //用户信息
//    private String userId;
    private String userName;

    //房间
    private String courtId;
    private String buildingId;
    private String unitId;
    private String roomId;
    private String roomName;
    //联系人
    private String contact;
    private String tel;
    //投诉房间
    private String complintCourtId;
    private String complintBuildingId;
    private String complintUnitId;
    private String complintRoomId;
    private String complintRoomName;
    //客户反应
    private String reaction;
    private String twos;
    private String time;
    private String content;
    private String type;
    private List<String> images;
    public AddReceiveHPI(Context context) {
        super(context);
    }

    @Override
    protected void getInputParam(HashMap<String, Object> params) {
        params.put("userName",userName);
        params.put("courtId",courtId);
        params.put("buildingId",buildingId);
        params.put("unitId",unitId);
        params.put("roomId",roomId);
        params.put("roomName",roomName);
        params.put("contact",contact);
        params.put("tel",tel);
        if(complintRoomId != null){
            params.put("complintCourtId",complintCourtId);
            params.put("complintBuildingId",complintBuildingId);
            params.put("complintUnitId",complintUnitId);
            params.put("complintRoomId",complintRoomId);
        }
        params.put("complintRoomName",complintRoomName);
        params.put("reaction",Integer.valueOf(reaction));
        params.put("twos",Integer.valueOf(twos));
        params.put("time",time);
        params.put("content",content);
        params.put("type",Integer.valueOf(type));
        //params.put("images",images);
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
        return "receive/add.php";
    }

    public void setCourtId(String courtId) {
        this.courtId = courtId;
    }

    public void setBuildingId(String buildingId) {
        this.buildingId = buildingId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public void setComplintCourtId(String complintCourtId) {
        this.complintCourtId = complintCourtId;
    }

    public void setComplintBuildingId(String complintBuildingId) {
        this.complintBuildingId = complintBuildingId;
    }

    public void setComplintUnitId(String complintUnitId) {
        this.complintUnitId = complintUnitId;
    }

    public void setComplintRoomId(String complintRoomId) {
        this.complintRoomId = complintRoomId;
    }

    public void setComplintRoomName(String complintRoomName) {
        this.complintRoomName = complintRoomName;
    }

    public void setReaction(String reaction) {
        this.reaction = reaction;
    }

    public void setTwos(String twos) {
        this.twos = twos;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
