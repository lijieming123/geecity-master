package net.bluemap.geecitypoperty;

import android.app.Application;
import android.util.Log;
import cn.jpush.android.api.JPushInterface;

/**
 * For developer startup JPush SDK
 * 
 * �?般建议在自定�? Application 类里初始化�?�也可以在主 Activity 里�??
 */
public class ExampleApplication extends Application {
    private static final String TAG = "JPush";

    @Override
    public void onCreate() {    	     
    	 Log.d(TAG, "[ExampleApplication] onCreate");
         super.onCreate();
         
         JPushInterface.setDebugMode(true); 	// 设置�?启日�?,发布时请关闭日志
         JPushInterface.init(this);     		// 初始�? JPush
    }
}
