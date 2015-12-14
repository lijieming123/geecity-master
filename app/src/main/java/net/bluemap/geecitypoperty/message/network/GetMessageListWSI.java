package net.bluemap.geecitypoperty.message.network;

import android.content.Context;

import net.bluemap.geecitypoperty.common.HttpPostAPI;
import net.bluemap.geecitypoperty.message.model.MessageBean;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2015/7/26.
 */
public class GetMessageListWSI extends HttpPostAPI {

    //入参

    //出参
    List<MessageBean> messages = new ArrayList<>();

    public GetMessageListWSI(Context context) {
        super(context);
    }

    @Override
    protected void getInputParam(HashMap<String, Object> params) {

    }

    @Override
    protected boolean analysisOutput(String result) throws JSONException {

        return true;
    }

    @Override
    protected String getMethodName() {
        return "message/list.php";
    }

    public List<MessageBean> getMessages() {
        return messages;
    }
}
