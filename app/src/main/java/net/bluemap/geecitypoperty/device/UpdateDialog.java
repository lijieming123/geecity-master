package net.bluemap.geecitypoperty.device;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Message;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import net.bluemap.geecitypoperty.R;
import net.bluemap.geecitypoperty.common.model.DeviceHandleType;
import net.bluemap.geecitypoperty.device.model.DeviceOfflineBean;
import net.bluemap.geecitypoperty.device.network.GetDeviceDetailHPI;
import net.bluemap.geecitypoperty.device.network.GetDeviceHandleTypeHPI;
import net.bluemap.geecitypoperty.device.network.GetDeviceListHPI;
import net.tsz.afinal.FinalDb;

import hz.toollib.interfaces.WebAPIListener;
import hz.toollib.util.DialogUtil;

/**
 * 更新设备对话框
 * Created by Liu Peng on 2015/7/26.
 */
public class UpdateDialog extends Dialog{

    private TextView tvText;
    private Button btnStart;
    private Button btnCancel;

    GetDeviceListHPI getDeviceListHPI;
    GetDeviceDetailHPI getDeviceDetailHPI;
    GetDeviceHandleTypeHPI getDeviceHandleTypeHPI;

    public UpdateDialog(Context context) {
        super(context);
        initDialog(context);
    }

    public UpdateDialog(Context context, int theme) {
        super(context, theme);
        initDialog(context);
    }

    protected UpdateDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        initDialog(context);
    }

    private void initDialog(final Context context){
        this.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setContentView(R.layout.dialog_device_update);
        WindowManager windowManager = ((Activity) context).getWindowManager();
        final Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = this.getWindow().getAttributes();
        lp.width = (int)(display.getWidth() * 0.8); //设置宽度
        //lp.height = (int)(display.getHeight() * 0.6); //设置高度
        this.getWindow().setAttributes(lp);
        setCancelable(false);
        setCanceledOnTouchOutside(false);

        tvText = (TextView) findViewById(R.id.ddu_tv_text);
        btnStart = (Button) findViewById(R.id.ddu_btn_start);
        btnCancel = (Button) findViewById(R.id.ddu_btn_cancel);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btnStart.getText().toString().equals("关闭")){
                    dismiss();
                }else{
                    //更新设备类型表
                    btnStart.setText("正在下载");
                    btnStart.setEnabled(false);
                    btnCancel.setVisibility(View.GONE);
                    updateHandleType();
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        //先更新列表
        getDeviceListHPI = new GetDeviceListHPI(context);
        getDeviceListHPI.setListener(new WebAPIListener() {
            @Override
            public void onLoadSuccess(int successNum) {
                btnStart.setEnabled(true);
                tvText.setText("共有" + getDeviceListHPI.getList().size() + "条设备信息，是否更新？");
            }

            @Override
            public void onLoadFail(int errNum, String errMessage) {
                DialogUtil.getInstance(getContext()).showCommitDialog("",errMessage);
            }
        });
        getDeviceListHPI.doConnectInBackground();
    }

    /**
     * 更新设备处理类型表
     */
    private void updateHandleType(){
        handler.sendEmptyMessage(100);
        getDeviceHandleTypeHPI = new GetDeviceHandleTypeHPI(getContext());
        getDeviceHandleTypeHPI.setListener(new WebAPIListener() {
            @Override
            public void onLoadSuccess(int successNum) {
                //将设备处理类型存入数据库
                FinalDb db = FinalDb.create(getContext());
                db.deleteAll(DeviceHandleType.class);
                for(int i = 0; i < getDeviceHandleTypeHPI.getTypes().size(); i++){
                    DeviceHandleType deviceHandleType = new DeviceHandleType();
                    deviceHandleType.setName(getDeviceHandleTypeHPI.getTypes().get(i));
                    db.save(deviceHandleType);
                }
                //更新设备表
                updateDevice();
            }

            @Override
            public void onLoadFail(int errNum, String errMessage) {
                DialogUtil.getInstance(getContext()).showCommitDialog("",errMessage);
            }
        });
        getDeviceHandleTypeHPI.doConnectInBackground();
    }

    /**
     * 更新设备表
     */
    private void updateDevice(){
        final FinalDb db = FinalDb.create(getContext());
        db.deleteAll(DeviceOfflineBean.class);
        getDeviceDetailHPI = new GetDeviceDetailHPI(getContext());
        //更新,循环
        new Thread(){
            @Override
            public void run() {
                for(DeviceOfflineBean dob : getDeviceListHPI.getList()){
                    //更新ui
                    handler.obtainMessage(101, dob.getName()).sendToTarget();

                    getDeviceDetailHPI.setId(dob.getId());
                    if(getDeviceDetailHPI.doConnect() == GetDeviceDetailHPI.SUCCESS){
                        //获取到详情的json原始数据后，直接保存到数据库
                        dob.setJson(getDeviceDetailHPI.getJson());
                        db.save(dob);
                    }
                }
                handler.sendEmptyMessage(1000);
            }
        }.start();
    }



    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 100:
                    tvText.setText("正在更新设备处理类型列表");
                    break;
                case 101:
                    tvText.setText("正在更新设备："+msg.obj.toString());
                    break;
                case 1000:
                    tvText.setText("更新完成");
                    btnStart.setEnabled(true);
                    btnStart.setText("关闭");
                    break;
            }
        }
    };




}
