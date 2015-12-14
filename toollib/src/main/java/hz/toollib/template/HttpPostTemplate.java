package hz.toollib.template;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import org.json.JSONException;

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

/**
 * HTTP POST访问网络模板，用法同WebServiceTemplate
 * @see WebServiceTemplate
 * Created by Liupeng on 2015/8/8.
 */
public abstract class HttpPostTemplate {

    private static final String HOSTNAME = "http://4.89892528.cn:8001/";

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

    Context context;

    //接口控制
    private boolean isCanceled = false;
    private boolean isDebug = false;

    public HttpPostTemplate(Context context){
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
     * 获取URL
     * @return URL
     */
    protected String getURL(){
        return HOSTNAME;
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
     * 进行连接（异步）
     */
    public final void doConnectInBackground(){
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
                Log.e("http", "网络连接异常");
                return sendMessage(ERROR_NETWORK,"网络异常，请检查网络连接");
            }
            Log.d("http", result);
            if(analysisOutput(result)){
                return sendMessage(SUCCESS,null);
            }
            Log.e("webservice", "数据解析异常");
            return ERROR_DATA;
        }catch(Exception e){
            e.printStackTrace();
            Log.e("http", "网络连接异常");
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
