package net.bluemap.geecitypoperty;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;

import net.bluemap.geecitypoperty.common.ShareUtil;
import net.bluemap.geecitypoperty.meter.model.RoomFilterAdapter;
import net.bluemap.geecitypoperty.meter.model.RoomFilterBean;
import net.bluemap.geecitypoperty.meter.network.GetCourtsHPI;
import net.bluemap.geecitypoperty.receive.AddReceiveActivity;
import net.bluemap.geecitypoperty.receive.ReceiveListFragment;
import net.bluemap.geecitypoperty.receive.network.GetReceiveStateHPI;
import net.tsz.afinal.annotation.view.ViewInject;

import java.util.HashMap;

import hz.toollib.interfaces.WebAPIListener;
import hz.toollib.util.DialogUtil;

public class RobBillActivity extends AppCompatActivity {
    private RoomFilterAdapter areaAdapter;
    private SimpleAdapter typeAdapter;
    @ViewInject(id = R.id.ar_spn_area2)
    Spinner spnArea;
    @ViewInject(id = R.id.ar_spn_type2)
    Spinner spnType;
    //记录当前选中的小区id和状态
    private String mSelectedCourtId;
    private String mSelectedRemark;


    //小区接口
    GetCourtsHPI getCourtsHPI;

    //接待类型接口
    GetReceiveStateHPI getReceiveTypesHPI;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rob_bill);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("抢单列表");
        toolbar. setNavigationIcon(R.mipmap.common_btn_back);
        setSupportActionBar(toolbar);

    }
    @Override
    protected void onResume() {
        super.onResume();
        loadReceiveList(mSelectedCourtId, mSelectedRemark);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_single_add, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_add) {
            //跳转到新增接待页面
            Intent intent = new Intent(this, AddReceiveActivity.class);
            startActivity(intent);
            return true;
        }else if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    /**
     * 加载数据
     */
    private void loadData(){
        getCourtsHPI = new GetCourtsHPI(this);
        getCourtsHPI.setUserName(ShareUtil.getInstance(this).getLoginInfo().getUserName());
        getCourtsHPI.setListener(new WebAPIListener() {
            @Override
            public void onLoadSuccess(int successNum) {
                fillArea();
            }

            @Override
            public void onLoadFail(int errNum, String errMessage) {
                //弹出提示
                DialogUtil.getInstance(RobBillActivity.this).showShortToast(errMessage);
            }
        });
        getCourtsHPI.doConnectInBackground();

        getReceiveTypesHPI = new GetReceiveStateHPI(this);
        getReceiveTypesHPI.setListener(new WebAPIListener() {
            @Override
            public void onLoadSuccess(int successNum) {
                fillType();
            }

            @Override
            public void onLoadFail(int errNum, String errMessage) {
                //弹出提示
                DialogUtil.getInstance(RobBillActivity.this).showShortToast(errMessage);
            }
        });
        getReceiveTypesHPI.doConnectInBackground();
    }

    /**
     * 填充小区数据
     */
    private void fillArea(){
        areaAdapter = new RoomFilterAdapter();
        areaAdapter.getList().addAll(getCourtsHPI.getCourts());
        spnArea.setAdapter(areaAdapter);
        spnArea.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                RoomFilterBean rfb = (RoomFilterBean) parent.getSelectedItem();
                mSelectedCourtId = rfb.getId();
                loadReceiveList(mSelectedCourtId, mSelectedRemark);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    /**
     * 填充类型数据
     */
    private void fillType(){
        typeAdapter = new SimpleAdapter(this,getReceiveTypesHPI.getStates(),R.layout.item_room_filter,
                new String[]{"text"},new int[]{R.id.irf_tv_text});
        spnType.setAdapter(typeAdapter);
        spnType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                HashMap<String, Object> map = (HashMap<String, Object>)parent.getSelectedItem();
                mSelectedRemark = map.get("remark").toString();
                loadReceiveList(mSelectedCourtId, mSelectedRemark);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    /**
     * 待下拉数据加载完毕后，可以加载接待列表
     */
    private void loadReceiveList(String courtId, String state){
        if(courtId == null || state == null){
            return;
        }
        Log.d("debug", "加载接待列表");
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        ReceiveListFragment fragment = ReceiveListFragment.newInstance(courtId, state);
        transaction.replace(R.id.ar_fl, fragment);
        transaction.commit();
    }

}
