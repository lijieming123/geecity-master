package net.bluemap.geecitypoperty.device;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import net.bluemap.geecitypoperty.R;
import net.bluemap.geecitypoperty.device.model.DeviceHandleAdapter;
import net.bluemap.geecitypoperty.device.model.DeviceHandleBean;
import net.bluemap.geecitypoperty.device.model.DeviceOfflineBean;
import net.bluemap.geecitypoperty.device.network.GetDeviceDetailHPI;
import net.bluemap.geecitypoperty.device.network.HandleDeviceHPI;
import net.tsz.afinal.FinalDb;

import java.util.ArrayList;
import java.util.List;

import hz.toollib.util.DialogUtil;

/**
 * 提交设备计划对话框
 * Created by Liu Peng on 2015/7/26.
 */
public class SubmitDialog extends Dialog{

    RecyclerView recyclerView;
    Button button;
    Button btnCancel;
    TextView textView;

    DeviceHandleAdapter adapter;

    //接口
    HandleDeviceHPI handleDeviceHPI;

    public SubmitDialog(Context context) {
        super(context);
        initDialog(context);
    }

    public SubmitDialog(Context context, int theme) {
        super(context, theme);
        initDialog(context);
    }

    protected SubmitDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        initDialog(context);
    }

    private void initDialog(Context context){
        this.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setContentView(R.layout.dialog_device_submit);
        WindowManager windowManager = ((Activity) context).getWindowManager();
        final Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = this.getWindow().getAttributes();
        lp.width = (int)(display.getWidth() * 0.8); //设置宽度
        //lp.height = (int)(display.getHeight() * 0.6); //设置高度
        this.getWindow().setAttributes(lp);
        setCancelable(false);
        setCanceledOnTouchOutside(false);

        recyclerView = (RecyclerView) findViewById(R.id.dds_rv);
        textView = (TextView) findViewById(R.id.dds_tv);
        button = (Button) findViewById(R.id.dds_btn);
        btnCancel = (Button) findViewById(R.id.dds_btn_cancel);

        findList();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (button.getText().toString().equals("完成")) {
                    dismiss();
                    return;
                }
                submit();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    private void findList(){
        FinalDb db = FinalDb.create(getContext());
        List<DeviceOfflineBean> deviceList = db.findAll(DeviceOfflineBean.class);

        adapter = new DeviceHandleAdapter();

        adapter.getList().addAll(db.findAll(DeviceHandleBean.class));

        for(DeviceHandleBean handle : adapter.getList()){
            for(DeviceOfflineBean device : deviceList){
                if(handle.getDeviceId().equals(device.getId())){
                    handle.setDeviceName(device.getName());
                }
            }
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
    }

    private void submit(){
        textView.setVisibility(View.VISIBLE);
        btnCancel.setVisibility(View.GONE);
        button.setText("正在提交");
        button.setEnabled(false);
        handleDeviceHPI = new HandleDeviceHPI(getContext());
        //必须是在线模式
        handleDeviceHPI.setIsOnline(true);
        new Thread(){
            @Override
            public void run() {
                for(int i = 0; i < adapter.getList().size(); i++){
                    final int fi = i;
                    final DeviceHandleBean dhb = adapter.getList().get(i);
                    //刷新UI
                    handler.obtainMessage(100, fi + 1,0,dhb).sendToTarget();
                    handleDeviceHPI.setId(dhb.getDeviceId());
                    handleDeviceHPI.setType(dhb.getType());
                    handleDeviceHPI.setUserId(dhb.getUserId());
                    handleDeviceHPI.setContent(dhb.getContent());
                    handleDeviceHPI.setState(dhb.getState());
                    List<String> images = new ArrayList<String>();
                    String[] imageSplit = dhb.getImages().split(",");
                    for(int j = 0 ; j < imageSplit.length; j++){
                        images.add(imageSplit[j]);
                    }
                    handleDeviceHPI.setImages(images);
                    if(handleDeviceHPI.doConnect() == GetDeviceDetailHPI.SUCCESS){
                        //从数据库删除此数据
                        FinalDb db = FinalDb.create(getContext());
                        db.delete(dhb);
                    } else{
                        DialogUtil.getInstance(getContext()).showCommitDialog("", "操作出现异常，请重试");
                        dismiss();
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
                    textView.setText("正在提交："+ msg.arg1 + "/" + adapter.getList().size());
                    adapter.getList().remove(msg.obj);
                    adapter.notifyDataSetChanged();
                    break;
                case 1000:
                    textView.setVisibility(View.INVISIBLE);
                    button.setText("完成");
                    button.setEnabled(true);
                    break;
            }
        }
    };

}
