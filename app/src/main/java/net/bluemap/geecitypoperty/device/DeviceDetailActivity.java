package net.bluemap.geecitypoperty.device;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.ScrollView;
import android.widget.TextView;

import net.bluemap.geecitypoperty.R;
import net.bluemap.geecitypoperty.common.FragmentAdapter;
import net.bluemap.geecitypoperty.common.ShareUtil;
import net.bluemap.geecitypoperty.common.model.LoginInfo;
import net.bluemap.geecitypoperty.common.model.PhotoAdapter;
import net.bluemap.geecitypoperty.device.model.CheckTypeAdapter;
import net.bluemap.geecitypoperty.device.model.DeviceBean;
import net.bluemap.geecitypoperty.device.network.GetDeviceDetailHPI;
import net.bluemap.geecitypoperty.device.network.GetDeviceHandleTypeHPI;
import net.bluemap.geecitypoperty.device.network.HandleDeviceHPI;
import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.annotation.view.ViewInject;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import hz.toollib.interfaces.WebAPIListener;
import hz.toollib.util.DialogUtil;
import hz.toollib.util.FileUtil;

public class DeviceDetailActivity extends AppCompatActivity {

    DialogUtil dialogUtil;
    LoginInfo li;

    //二维码扫描的结果
    String result;

    //选择的检查类型
    String selectType;

    @ViewInject(id = R.id.toolbar) Toolbar toolbar;
    @ViewInject(id = R.id.add_tv_name) TextView tvName;
    @ViewInject(id = R.id.add_tv_position) TextView tvPosition;

    //详情布局
    @ViewInject(id = R.id.add_ll_detail) LinearLayout llDetail;

    @ViewInject(id = R.id.add_btn_handle,click = "handle") Button btnHandle;

    @ViewInject(id = R.id.add_tab) TabLayout tab;
    @ViewInject(id = R.id.add_vp) ViewPager vp;

    //类型选择布局
    @ViewInject(id = R.id.add_rv_check) RecyclerView rvCheck;

    //提交布局
    @ViewInject(id = R.id.add_sv_submit) ScrollView svSubmit;
    @ViewInject(id = R.id.add_tv_type) TextView tvType;
    @ViewInject(id = R.id.add_rb_normal) RadioButton rbNormal;
    @ViewInject(id = R.id.add_rb_abnormal) RadioButton rbAbnormal;
    @ViewInject(id = R.id.add_et_content) EditText etContent;
    @ViewInject(id = R.id.add_rv_photo) RecyclerView rvPhoto;
    @ViewInject(id = R.id.add_btn_submit,click = "submit") Button btnSubmit;

    //接口
    GetDeviceDetailHPI getDeviceDetailHPI;
    GetDeviceHandleTypeHPI getDeviceHandleTypeHPI;
    HandleDeviceHPI handleDeviceHPI;

    //类型选择
    RecyclerView.Adapter adapter;

    //图片adapter
    PhotoAdapter photoAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_detail);
        FinalActivity.initInjectedView(this);

        toolbar.setTitle("设备巡检详情");
        toolbar.setNavigationIcon(R.mipmap.common_btn_back);
        setSupportActionBar(toolbar);

        result = getIntent().getStringExtra("result");

        dialogUtil = DialogUtil.getInstance(this);
        li = ShareUtil.getInstance(this).getLoginInfo();

        rvPhoto.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvPhoto.setLayoutManager(llm);
        photoAdapter = new PhotoAdapter();
        photoAdapter.setEditable(true);
        rvPhoto.setAdapter(photoAdapter);
        loadData();
    }

    /**
     * 加载数据
     */
    private void loadData(){
        dialogUtil.showProgressDialog("", "正在加载...", false);
        getDeviceDetailHPI = new GetDeviceDetailHPI(this);
        //二维码的结果就是设备的id
        getDeviceDetailHPI.setId(result);
        getDeviceDetailHPI.setListener(new WebAPIListener() {
            @Override
            public void onLoadSuccess(int successNum) {
                dialogUtil.dismissProgressDialog();
                fillData();
            }

            @Override
            public void onLoadFail(int errNum, String errMessage) {
                dialogUtil.dismissProgressDialog();
                dialogUtil.showCommitDialog("错误", errMessage);
            }
        });
        getDeviceDetailHPI.doConnectInBackground();
    }

    /**
     * 填充数据
     */
    private void fillData(){
        DeviceBean db = getDeviceDetailHPI.getDevice();
        tvName.setText("设备名称：" + db.getName());
        tvPosition.setText("所在位置：" + db.getPosition());
        //初始化ViewPager
        tab.setTabMode(TabLayout.MODE_FIXED);
        List<String> titles = new ArrayList<>();
        List<Fragment> fragments = new ArrayList<>();

        titles.add("巡检信息");
        titles.add("历史记录");
        tab.addTab(tab.newTab().setText("巡检信息"));
        tab.addTab(tab.newTab().setText("历史记录"));
        DeviceCheckFragment checkFragment = DeviceCheckFragment.newInstance();
        checkFragment.setList(getDeviceDetailHPI.getDevice().getChecks());
        fragments.add(checkFragment);
        DeviceHistoryFragment historyFragment = DeviceHistoryFragment.newInstance();
        historyFragment.setList(getDeviceDetailHPI.getDevice().getHistory());
        fragments.add(historyFragment);
        FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager(),fragments,titles);
        vp.setAdapter(adapter);
        tab.setupWithViewPager(vp);
        tab.setTabsFromPagerAdapter(adapter);
    }

    /**
     * 处理按钮点击
     * @param view
     */
    public void handle(View view){
        llDetail.setVisibility(View.GONE);
        btnHandle.setVisibility(View.GONE);
        rvCheck.setHasFixedSize(true);
        rvCheck.setLayoutManager(new LinearLayoutManager(this));
        getDeviceHandleTypeHPI = new GetDeviceHandleTypeHPI(this);
        adapter = new CheckTypeAdapter(this, getDeviceHandleTypeHPI.getTypes(), new CheckTypeAdapter.onTypeSelectListener() {
            @Override
            public void onTypeSelect(String type) {
                //隐藏列表，保存类型，显示提交界面
                selectType = type;
                rvCheck.setVisibility(View.GONE);
                svSubmit.setVisibility(View.VISIBLE);
                tvType.setText("类型：" + selectType);
            }
        });
        rvCheck.setAdapter(adapter);
        getDeviceHandleTypeHPI.setListener(new WebAPIListener() {
            @Override
            public void onLoadSuccess(int successNum) {
                //显示列表
                dialogUtil.dismissProgressDialog();
                adapter.notifyDataSetChanged();
                rvCheck.setVisibility(View.VISIBLE);
            }

            @Override
            public void onLoadFail(int errNum, String errMessage) {
                dialogUtil.dismissProgressDialog();
            }
        });
        dialogUtil.showProgressDialog("", "请稍等...", false);
        getDeviceHandleTypeHPI.doConnectInBackground();
    }

    /**
     * 提交按钮点击
     * @param view
     */
    public void submit(View view){
        dialogUtil.showProgressDialog("","正在提交...",false);
        handleDeviceHPI = new HandleDeviceHPI(this);
        handleDeviceHPI.setUserId(li.getId());
        handleDeviceHPI.setType(selectType);
        handleDeviceHPI.setState(rbNormal.isChecked() ? "正常" : "异常");
        handleDeviceHPI.setId(result);
        handleDeviceHPI.setContent(etContent.getText().toString());
        handleDeviceHPI.setImages(photoAdapter.getPhotos());
        handleDeviceHPI.setListener(new WebAPIListener() {
            @Override
            public void onLoadSuccess(int successNum) {
                dialogUtil.dismissProgressDialog();
                dialogUtil.showCommitDialogWithFinish("", "提交成功");
            }

            @Override
            public void onLoadFail(int errNum, String errMessage) {
                dialogUtil.dismissProgressDialog();
                dialogUtil.showCommitDialog("错误", errMessage);
            }
        });
        handleDeviceHPI.doConnectInBackground();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    String photoName = "";
    /**
     * 添加图片
     * @param view
     */
    public void addPhoto(View view){
        //拍照并保存
        photoName = FileUtil.getSDPath() + "/geecity/images/" + new Date().getTime()+ ".jpg";
        File file = new File(photoName);
        File path = file.getParentFile();
        path.mkdirs();

        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            // 将图片显示
            photoAdapter.getPhotos().add(photoName);
            photoAdapter.notifyDataSetChanged();
        }
    }
}
