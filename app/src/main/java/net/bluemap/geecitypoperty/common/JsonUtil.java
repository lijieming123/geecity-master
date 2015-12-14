package net.bluemap.geecitypoperty.common;

import org.json.JSONException;
import org.json.JSONObject;

import hz.toollib.util.StringUtil;

/**
 * JSON工具类
 * Created by LiuPeng on 2015/11/9.
 */
public class JsonUtil {

    /**
     * 获取json字符串，空字符串或异常返回默认值
     * @param jsonObject json对象
     * @param key json key
     * @param defaultValue 默认值
     * @return
     */
    public static String getString(JSONObject jsonObject,String key, String defaultValue){
        try {
            String result = jsonObject.getString(key);
            if(StringUtil.isEmpty(result)){
                return defaultValue;
            }else{
                return result;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return defaultValue;
    }
}
