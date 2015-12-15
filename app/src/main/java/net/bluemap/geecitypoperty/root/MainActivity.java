package net.bluemap.geecitypoperty.root;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.bluemap.geecitypoperty.ExampleUtil;
import net.bluemap.geecitypoperty.R;
import net.bluemap.geecitypoperty.common.ShareUtil;
import net.bluemap.geecitypoperty.common.model.LoginInfo;
import net.bluemap.geecitypoperty.device.DeviceActivity;
import net.bluemap.geecitypoperty.notice.NoticeListActivity;
import net.bluemap.geecitypoperty.receive.ReceiveListActivity;
import net.bluemap.geecitypoperty.room.RoomSelectActivity;
import net.bluemap.geecitypoperty.root.network.GetWorkbenchWSI;
import net.bluemap.geecitypoperty.task.TaskListActivity;
import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.annotation.view.ViewInject;

import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import hz.toollib.interfaces.WebAPIListener;


public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    @ViewInject(id = R.id.am_srl_refresh) SwipeRefreshLayout srlRefresh;

    @ViewInject(id = R.id.am_ll_notice, click = "onClick")
    LinearLayout llNotice;
    @ViewInject(id = R.id.am_ll_receive, click = "onClick")
    LinearLayout llReceive;
    @ViewInject(id = R.id.am_ll_room, click = "onClick")
    LinearLayout llRoom;
    @ViewInject(id = R.id.am_ll_task, click = "onClick")
    LinearLayout llTask;
    @ViewInject(id = R.id.am_ll_device, click = "onClick")
    LinearLayout llDevice;
    @ViewInject(id = R.id.am_ll_setting, click = "onClick")
    LinearLayout llSetting;

    @ViewInject(id = R.id.am_tv_device)
    TextView tvDevice;
    @ViewInject(id = R.id.am_tv_notice)
    TextView tvNotice;
    @ViewInject(id = R.id.am_tv_receive)
    TextView tvReceive;
    @ViewInject(id = R.id.am_tv_task)
    TextView tvTask;
    @ViewInject(id = R.id.am_tv_username)
    TextView tvUsername;

    LoginInfo li;

    //接口
    GetWorkbenchWSI getWorkbenchWSI;
    public static boolean isForeground = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FinalActivity.initInjectedView(this);

        initView();
        //推送初始化
        init();
        //绑定广播接收
        registerMessageReceiver();
        //设置设备别名
//        String deviceId=JPushInterface.getRegistrationID(getApplication());







    }
    private void init() {
        JPushInterface.init(getApplicationContext());
    }
    @Override
    protected void onResume() {
        isForeground = true;
        super.onResume();
        loadData();
        JPushInterface.setAlias(getApplicationContext(), li.getUserName(), new TagAliasCallback() {
            @Override
            public void gotResult(int i, String s, Set<String> set) {
                Log.d("Tag", "" + i + s);
            }
        });


    }

    private void initView(){
        srlRefresh.post(new Runnable() {
            @Override
            public void run() {
                srlRefresh.setRefreshing(true);
            }
        });
        srlRefresh.setOnRefreshListener(this);
        tvUsername.setText(ShareUtil.getInstance(this).getLoginInfo().getUserName());
    }

    private void loadData(){
        getWorkbenchWSI = new GetWorkbenchWSI(this);
         li = ShareUtil.getInstance(this).getLoginInfo();
        getWorkbenchWSI.setUserId(li.getId());
        getWorkbenchWSI.setDepartId(li.getDepartId());
        getWorkbenchWSI.setUserName(li.getUserName());

        srlRefresh.post(new Runnable() {
            @Override
            public void run() {
                srlRefresh.setRefreshing(true);
            }
        });
        getWorkbenchWSI.setListener(new WebAPIListener() {
            @Override
            public void onLoadSuccess(int successNum) {
                //填充数据
                //tvDevice.setText(getWorkbenchWSI.getDeviceCount()+"条设备计划");
                tvNotice.setText(getWorkbenchWSI.getNoticeCount()+"条未读");
                tvReceive.setText(getWorkbenchWSI.getReceiveCount()+"条未处理");
                tvTask.setText(getWorkbenchWSI.getTaskCount()+"条未处理");
                srlRefresh.setRefreshing(false);

            }

            @Override
            public void onLoadFail(int errNum, String errMessage) {

            }
        });
        getWorkbenchWSI.doConnectInBackground();
    }

    @Override
    public void onRefresh() {
        loadData();
    }

    /**
     * 模块按钮点击
     * @param view
     */
    public void onClick(View view){
        Intent intent = new Intent();
        Class activity = null;
        switch (view.getId()){
            case R.id.am_ll_notice:
                activity = NoticeListActivity.class;
                break;
            case R.id.am_ll_receive:
                activity = ReceiveListActivity.class;
                break;
            case R.id.am_ll_room:
                activity = RoomSelectActivity.class;
                break;
            case R.id.am_ll_task:
                activity = TaskListActivity.class;
                break;
            case R.id.am_ll_device:
                activity = DeviceActivity.class;
                break;
            case R.id.am_ll_setting:
                activity = SettingActivity.class;
                break;
        }
        if(activity != null){
            intent.setClass(this,activity);
            startActivity(intent);
        }
    }

    /**
     * 注销登录
     * @param view
     */
    public void onLogout(View view){
        new AlertDialog.Builder(this).setTitle("注销登录").setMessage("是否要注销登录？注销后会清空登录名和密码").setPositiveButton("是", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //忘记用户名和密码，返回登录界面
                SharedPreferences shared = MainActivity.this.getSharedPreferences("userInfo", Activity.MODE_PRIVATE);
                SharedPreferences.Editor editor = shared.edit();
                editor.clear();
                editor.commit();
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }).setNegativeButton("否", null).create().show();
    }



    @Override
    protected void onPause() {
        isForeground = false;
        super.onPause();
    }


    @Override
    protected void onDestroy() {
        unregisterReceiver(mMessageReceiver);
        super.onDestroy();
    }


    //for receive customer msg from jpush server
    private MessageReceiver mMessageReceiver;
    public static final String MESSAGE_RECEIVED_ACTION = "com.example.jpushdemo.MESSAGE_RECEIVED_ACTION";
    public static final String KEY_TITLE = "title";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_EXTRAS = "extras";

    public void registerMessageReceiver() {
        mMessageReceiver = new MessageReceiver();
        IntentFilter filter = new IntentFilter();
        filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        filter.addAction(MESSAGE_RECEIVED_ACTION);
        registerReceiver(mMessageReceiver, filter);
    }

    public class MessageReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (MESSAGE_RECEIVED_ACTION.equals(intent.getAction())) {
                String messge = intent.getStringExtra(KEY_MESSAGE);
                String extras = intent.getStringExtra(KEY_EXTRAS);
                StringBuilder showMsg = new StringBuilder();
                showMsg.append(KEY_MESSAGE + " : " + messge + "\n");
                if (!ExampleUtil.isEmpty(extras)) {
                    showMsg.append(KEY_EXTRAS + " : " + extras + "\n");
                }

            }
        }
    }
}
