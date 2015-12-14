package net.bluemap.geecitypoperty.device;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.Window;

import net.bluemap.geecitypoperty.R;
import net.bluemap.geecitypoperty.device.model.CheckTypeAdapter;
import net.bluemap.geecitypoperty.device.network.GetDeviceHandleTypeHPI;

import hz.toollib.interfaces.WebAPIListener;

/**
 * 设备处理类型选择对话框
 * Created by LiuPeng on 2015/8/15.
 */
public class DeviceCheckDialog extends Dialog {

    Context context;
    private String id;

    GetDeviceHandleTypeHPI getDeviceHandleTypeHPI;

    //ui
    RecyclerView rlList;
    RecyclerView.Adapter adapter;



    public DeviceCheckDialog(Context context) {
        super(context);
        init(context);
    }

    public DeviceCheckDialog(Context context, int theme) {
        super(context, theme);
        init(context);
    }

    protected DeviceCheckDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        init(context);
    }

    private void init(Context context){
        this.context = context;
        //透明主题
        this.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        this.getWindow().setGravity(Gravity.BOTTOM);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setContentView(R.layout.dialog_device_handle);

        rlList = (RecyclerView) findViewById(R.id.ddh_rv_list);
        rlList.setHasFixedSize(true);
        rlList.setLayoutManager(new LinearLayoutManager(context));
    }

    public void setId(String id){
        this.id = id;
    }

    @Override
    public void show() {
        super.show();
        //调用接口
        getDeviceHandleTypeHPI = new GetDeviceHandleTypeHPI(context);
        adapter = new CheckTypeAdapter(context, getDeviceHandleTypeHPI.getTypes(), new CheckTypeAdapter.onTypeSelectListener() {
            @Override
            public void onTypeSelect(String type) {
                //跳转到DeviceSubmitActivity，传id和type
            }
        });
        getDeviceHandleTypeHPI.setListener(new WebAPIListener() {
            @Override
            public void onLoadSuccess(int successNum) {

            }

            @Override
            public void onLoadFail(int errNum, String errMessage) {

            }
        });
        getDeviceHandleTypeHPI.doConnectInBackground();
    }
}
