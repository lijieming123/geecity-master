package net.bluemap.geecitypoperty.task;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;

import net.bluemap.geecitypoperty.R;
import net.bluemap.geecitypoperty.common.ShareUtil;
import net.bluemap.geecitypoperty.meter.model.RoomFilterAdapter;
import net.bluemap.geecitypoperty.meter.model.RoomFilterBean;
import net.bluemap.geecitypoperty.meter.network.GetCourtsHPI;
import net.bluemap.geecitypoperty.task.network.GetTaskStateHPI;
import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.annotation.view.ViewInject;

import java.util.HashMap;

import hz.toollib.interfaces.WebAPIListener;
import hz.toollib.util.DialogUtil;

/**
 * 我的任务Activity
 */
public class TaskListActivity extends AppCompatActivity {

    @ViewInject(id = R.id.atl_spn_type)
    Spinner spnType;
    @ViewInject(id = R.id.atl_spn_area)
    Spinner spnArea;

    private RoomFilterAdapter areaAdapter;
    private SimpleAdapter typeAdapter;

    //记录当前选中的小区id和状态
    private String mSelectedCourtId;
    private String mSelectedState;

    //小区接口
    GetCourtsHPI getCourtsHPI;

    //任务类型接口
    GetTaskStateHPI getTaskTypesHPI;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);
        FinalActivity.initInjectedView(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("我的任务");
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.mipmap.common_btn_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        loadData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadReceiveList(mSelectedCourtId, mSelectedState);
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
                DialogUtil.getInstance(TaskListActivity.this).showShortToast(errMessage);
            }
        });
        getCourtsHPI.doConnectInBackground();

        getTaskTypesHPI = new GetTaskStateHPI(this);
        getTaskTypesHPI.setListener(new WebAPIListener() {
            @Override
            public void onLoadSuccess(int successNum) {
                fillType();
            }

            @Override
            public void onLoadFail(int errNum, String errMessage) {
                //弹出提示
                DialogUtil.getInstance(TaskListActivity.this).showShortToast(errMessage);
            }
        });
        getTaskTypesHPI.doConnectInBackground();
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
                RoomFilterBean rfb = (RoomFilterBean)parent.getSelectedItem();
                mSelectedCourtId = rfb.getId();
                loadReceiveList(mSelectedCourtId,mSelectedState);
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
        typeAdapter = new SimpleAdapter(this,getTaskTypesHPI.getStates(),R.layout.item_room_filter,
                new String[]{"text"},new int[]{R.id.irf_tv_text});
        spnType.setAdapter(typeAdapter);
        spnType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                HashMap<String, Object> map = (HashMap<String, Object>)parent.getSelectedItem();
                mSelectedState = map.get("remark").toString();
                loadReceiveList(mSelectedCourtId,mSelectedState);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    /**
     * 待下拉数据加载完毕后，可以加载任务列表
     */
    private void loadReceiveList(String courtId, String state){
        if(courtId == null || state == null){
            return;
        }
        Log.d("debug", "加载任务列表");
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        TaskListFragment fragment = TaskListFragment.newInstance(courtId, state);
        transaction.replace(R.id.atl_fl, fragment);
        transaction.commit();
    }

}
