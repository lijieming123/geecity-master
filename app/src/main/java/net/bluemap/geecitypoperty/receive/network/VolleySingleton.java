package net.bluemap.geecitypoperty.receive.network;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

/**
 * Created by Li on 2015/11/25
 * Volley网络链接单例
 */
public class VolleySingleton {

    private static VolleySingleton mInstance;
    private RequestQueue mRequestQueue;//消息队列
    private ImageLoader mImageLoader;//ImageLoader对象
    private static Context mCtx;

    private VolleySingleton(Context context) {
        mCtx = context;
        mRequestQueue = getRequestQueue();
        mImageLoader = getImageLoader();
    }

    public static synchronized VolleySingleton getInstance(Context context){
        if(mInstance == null){
            mInstance = new VolleySingleton(context);
        }
        return mInstance;
    }
    public RequestQueue getRequestQueue(){
        if(mRequestQueue == null){
            mRequestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
        }
        return mRequestQueue;
    }

    public ImageLoader getImageLoader(){
        if(mImageLoader==null){
            mImageLoader = new ImageLoader(getRequestQueue(), new ImageLoader.ImageCache(){

                private final LruCache<String,Bitmap> cache = new LruCache<String,Bitmap>(20);//设置图片缓存
                @Override
                public Bitmap getBitmap(String url) {
                    return null;
                }

                @Override
                public void putBitmap(String url, Bitmap bitmap) {
                }
            });
        }
        return mImageLoader;
    }
    //将请求添加到队列中
    public void addToRequestQueue(Request req){
        getRequestQueue().add(req);
    }


}
