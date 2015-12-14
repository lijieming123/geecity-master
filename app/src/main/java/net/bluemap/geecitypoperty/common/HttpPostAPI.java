package net.bluemap.geecitypoperty.common;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import hz.toollib.interfaces.WebAPIListener;
import hz.toollib.template.WebServiceTemplate;
import hz.toollib.util.NetUtils;

/**
 * HTTP POST访问网络模板，用法同WebServiceTemplate
 * @see WebServiceTemplate
 * Created by Liupeng on 2015/8/8.
 */
public abstract class HttpPostAPI {

    //private static final String HOSTNAME = "http://4.89892528.cn:8001/";

    public static final int SUCCESS = 1;
    public static final int CANCEL = 0;
    public static final int ERROR_NETWORK = -1;
    public static final int ERROR_IO = -2;
    public static final int ERROR_JSON = -3;
    public static final int ERROR_XML = -4;
    public static final int ERROR_CUSTOM = -5;
    public static final int ERROR_DATA = -6;

    //接口监听
    private WebAPIListener listener;
    public void setListener(WebAPIListener listener) {
        this.listener = listener;
    }

    public Context getContext() {
        return context;
    }

    Context context;

    //接口控制
    //是否取消了操作
    private boolean isCanceled = false;
    //是否debug模式
    private boolean isDebug = false;
    //是否强制在线模式
    private boolean isOnline = false;

    public HttpPostAPI(Context context){
        this.context = context;
    }

    /**
     * 设置是否是调试模式，调试模式下，不进行网络访问，只执行解析方法analysisOutput，返回成功回调
     * @param isDebug 是否是调试模式
     */
    public void setIsDebug(boolean isDebug) {
        this.isDebug = isDebug;
    }

    /**
     * 设置是否取消任务
     * @param isCanceled 是否取消任务
     */
    public void setCanceled(boolean isCanceled){
        this.isCanceled = isCanceled;
    }

    /**
     * 设置是否强制离线操作。如果此值为false，则会在手机没有网络是尝试读取离线数据，
     * 在未重写doOffline的情况下会报出网络异常。
     * @param isOnline 是否离线操作
     */
    public void setIsOnline(boolean isOnline) {
        this.isOnline = isOnline;
    }

    /**
     * 获取URL
     * @return URL
     */
    protected String getURL(){
        return ShareUtil.getInstance(context).getHostUrl();
    }

    /**
     * 设置输入
     */
    abstract protected void getInputParam(HashMap<String,Object> params);

    /**
     *
     * @param result webservice返回的字符串
     * @return 解析是否成功
     * @throws JSONException
     */
    abstract protected boolean analysisOutput(String result) throws JSONException;

    /**
     * 获取接口方法名
     * @return 方法名，此处是后续的完整URL
     */
    abstract protected String getMethodName();

    /**
     * 离线操作，需要离线操作的接口重写此方法。在此方法中实现analysisOutput中应做的处理
     * @return
     */
    protected boolean doOffLine(){
        sendMessage(ERROR_NETWORK,"网络异常，请检查网络连接");
        return false;
    }

    /**
     * 进行连接（异步）
     */
    public final void doConnectInBackground(){
        if(!NetUtils.isConnected(context) && !isOnline){
            if(doOffLine()){
                sendMessage(SUCCESS,"离线操作成功");
            }else{
                sendMessage(ERROR_DATA, "离线操作失败");
            }
            return;
        }
        new Thread(){
            @Override
            public void run(){
                if(isDebug){
                    try {
                        analysisOutput(null);
                        sendMessage(SUCCESS,null);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    return;
                }
                doConnect();
            }
        }.start();
    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(listener == null){
                return;
            }
            if(msg.what > 0){
                listener.onLoadSuccess(msg.what);
            }else if (msg.what < 0){
                Log.e("http", msg.obj.toString());
                listener.onLoadFail(msg.what, msg.obj.toString());
            }
        }
    };

    /**
     * 非异步的连接，需要线程调用
     */
    public final int doConnect(){
        //TODO POST请求
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try{
            URL realUrl = new URL(getURL()+getMethodName());
            URLConnection conn = realUrl.openConnection();
            //设置请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0(compatible; MSIE 6.0; Windows NT 5.1; SV1)");
            //发送POST请求的设置
            conn.setConnectTimeout(5000);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            out = new PrintWriter(conn.getOutputStream());
            //取出参数
            HashMap<String,Object> map = new HashMap<>();
            getInputParam(map);
            String params = "";
            Iterator iter = map.entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry entry = (Map.Entry) iter.next();
                Object key = entry.getKey();
                Object val = entry.getValue();
                params += key + "=" + val + (iter.hasNext()?"&":"");
            }
            Log.d("http", realUrl.getHost()+realUrl.getPath() + "?" + params);
            out.print(params);
            out.flush();
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while((line = in.readLine())!= null){
                result += line;
            }
            if(result.equals("")){
                return sendMessage(ERROR_NETWORK,"网络异常，请检查网络连接");
            }
            Log.d("http", result);
            //预处理
            JSONObject json = new JSONObject(result);
            if(!json.getBoolean("success")){
                return sendMessage(ERROR_DATA,json.getString("message"));
            }
            if(analysisOutput(json.getString("data"))){
                return sendMessage(SUCCESS,null);
            }
            return ERROR_DATA;
        }catch(Exception e){
            e.printStackTrace();
            return sendMessage(ERROR_NETWORK,"网络异常，请检查网络连接");
        }finally{
            try{
                if(out != null){
                    out.close();
                }
                if(in != null){
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    protected int sendMessage(int what,Object obj){
        //如果取消任务，返回CANCEL
        if(isCanceled){
            return CANCEL;
        }
        Message msg = new Message();
        msg.obj = obj;
        msg.what = what;
        handler.sendMessage(msg);
        return what;
    }
}
