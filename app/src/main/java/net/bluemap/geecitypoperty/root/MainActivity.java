package net.bluemap.geecitypoperty.root;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.umeng.message.ALIAS_TYPE;
import com.umeng.message.PushAgent;
import com.umeng.message.UmengRegistrar;

import net.bluemap.geecitypoperty.R;
import net.bluemap.geecitypoperty.common.ShareUtil;
import net.bluemap.geecitypoperty.common.model.LoginInfo;
import net.bluemap.geecitypoperty.device.DeviceActivity;
import net.bluemap.geecitypoperty.notice.NoticeListActivity;
import net.bluemap.geecitypoperty.order.OrderAcceptActivity;
import net.bluemap.geecitypoperty.order.OrderCloseActivity;
import net.bluemap.geecitypoperty.order.OrderDetailActivity;
import net.bluemap.geecitypoperty.order.OrderListActivity;
import net.bluemap.geecitypoperty.receive.ReceiveListActivity;
import net.bluemap.geecitypoperty.room.RoomSelectActivity;
import net.bluemap.geecitypoperty.root.network.GetWorkbenchWSI;
import net.bluemap.geecitypoperty.task.TaskListActivity;
import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.annotation.view.ViewInject;

import hz.toollib.interfaces.WebAPIListener;


public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    @ViewInject(id = R.id.btn_receive)
    Button mBtnReceive;
    @ViewInject(id = R.id.btn_rob_close)
    Button mBtnClose;
    @ViewInject(id = R.id.btn_detail)
    Button mBtnDetail;
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
    @ViewInject(id=R.id.btn_rob_bill)
     Button mBtnRobBill;
    LoginInfo li;
    PushAgent mPushAgent;
    //接口
    GetWorkbenchWSI getWorkbenchWSI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FinalActivity.initInjectedView(this);
        initView();
        mPushAgent = PushAgent.getInstance(getApplicationContext());
        PushAgent.getInstance(getApplicationContext()).onAppStart();
        String device_token = UmengRegistrar.getRegistrationId(getApplicationContext());
        Log.d("设备token",device_token);
        mBtnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentClose = new Intent(MainActivity.this, OrderCloseActivity.class);
                startActivity(intentClose);
            }
        });
        mBtnDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentDetail=new Intent(MainActivity.this, OrderDetailActivity.class);
                startActivity(intentDetail);
            }
        });
        mBtnReceive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentReceive=new Intent(MainActivity.this, OrderAcceptActivity.class);
                startActivity(intentReceive);
            }
        });
        mBtnRobBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //打开抢单列表界面
                Intent intent=new Intent(MainActivity.this, OrderListActivity.class);
                startActivity(intent);
            }
        });
         //应用程序启动统计
        //参考集成文档的1.5.1.2
        //http://dev.umeng.com/push/android/integration#1_5_1
        mPushAgent.onAppStart();
        PushAgent.sendSoTimeout(this, 600);	//设置护保进程间隔时间
        mPushAgent.enable();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
        Log.d("用户id",li.getId());
        try {
            mPushAgent.addAlias(li.getUserName(), ALIAS_TYPE.BAIDU);
            mPushAgent.addExclusiveAlias(li.getUserName(), ALIAS_TYPE.SINA_WEIBO);
        } catch (Exception e) {
            e.printStackTrace();
        }

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
        }).setNegativeButton("否",null).create().show();
    }
}
