package net.bluemap.geecitypoperty.meter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;

import net.bluemap.geecitypoperty.R;
import net.bluemap.geecitypoperty.common.ShareUtil;
import net.bluemap.geecitypoperty.meter.model.RoomFilterAdapter;
import net.bluemap.geecitypoperty.meter.model.RoomFilterBean;
import net.bluemap.geecitypoperty.meter.network.GetBuildingHPI;
import net.bluemap.geecitypoperty.meter.network.GetCourtsHPI;
import net.bluemap.geecitypoperty.meter.network.GetRoomDetailHPI;
import net.bluemap.geecitypoperty.meter.network.GetRoomHPI;
import net.bluemap.geecitypoperty.meter.network.GetUnitHPI;
import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.annotation.view.ViewInject;

import hz.toollib.interfaces.WebAPIListener;
import hz.toollib.util.DialogUtil;

/**
 * 四级联动查询界面
 * 传参数isFilter为true可使用选择器模式，
 * 使用startActivityForResult调用
 * onresult中返回此id和房间信息
 */
public class RoomFilterActivity extends AppCompatActivity {

    DialogUtil dialogUtil;

    @ViewInject(id = R.id.toolbar) Toolbar mToolbar;
    @ViewInject(id = R.id.arf_spn_court) Spinner spnCourt;
    @ViewInject(id = R.id.arf_spn_building) Spinner spnBuilding;
    @ViewInject(id = R.id.arf_spn_unit)Spinner spnUnit;
    @ViewInject(id = R.id.arf_spn_room)Spinner spnRoom;
    @ViewInject(id = R.id.arf_button_search, click = "onSearchClick") Button btnSearch;

    //四级联动当前选择
    String mCourtId;
    String mBuildingId;
    String mUnitId;
    String mRoomId;


    //四级联动adapter
    private RoomFilterAdapter mCourtAdapter;
    private RoomFilterAdapter mBuildingAdapter;
    private RoomFilterAdapter mUnitAdapter;
    private RoomFilterAdapter mRoomAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_filter);
        FinalActivity.initInjectedView(this);

        dialogUtil = DialogUtil.getInstance(this);

        mToolbar.setTitle("房间查询");
        mToolbar.setNavigationIcon(R.mipmap.common_btn_back);
        setSupportActionBar(mToolbar);

        if(getIntent().getBooleanExtra("isFilter",false)){
            btnSearch.setText("确定");
        }

        initListener();

        initAdapters();

        loadCourts();

    }

    /**
     * 加载小区列表
     */
    private void loadCourts(){
        //dialogUtil.showProgressDialog("","正在加载小区列表...",false);
        final GetCourtsHPI getCourtsHPI = new GetCourtsHPI(this);
        getCourtsHPI.setUserName(ShareUtil.getInstance(this).getLoginInfo().getUserName());
        getCourtsHPI.setListener(new WebAPIListener() {
            @Override
            public void onLoadSuccess(int successNum) {
                dialogUtil.dismissProgressDialog();
                mCourtAdapter.getList().clear();
                mCourtAdapter.getList().addAll(getCourtsHPI.getCourts());
                mCourtAdapter.notifyDataSetChanged();
                spnCourt.setSelection(0);
                spnCourt.getOnItemSelectedListener().onItemSelected(spnCourt,spnCourt,0,0);
            }

            @Override
            public void onLoadFail(int errNum, String errMessage) {
                dialogUtil.dismissProgressDialog();
            }
        });
        getCourtsHPI.doConnectInBackground();
    }

    /**
     * 加载楼座列表
     */
    private void loadBuildings(){
        //dialogUtil.showProgressDialog("","正在加载楼座列表...",false);
        final GetBuildingHPI getBuildingHPI = new GetBuildingHPI(this);
        getBuildingHPI.setCourtId(mCourtId);
        getBuildingHPI.setListener(new WebAPIListener() {
            @Override
            public void onLoadSuccess(int successNum) {
                dialogUtil.dismissProgressDialog();
                mBuildingAdapter.getList().clear();
                mBuildingAdapter.getList().addAll(getBuildingHPI.getBuildings());
                mBuildingAdapter.notifyDataSetChanged();
                spnBuilding.setSelection(0);
                spnBuilding.getOnItemSelectedListener().onItemSelected(spnBuilding, spnCourt, 0, 0);
            }

            @Override
            public void onLoadFail(int errNum, String errMessage) {
                dialogUtil.dismissProgressDialog();
            }
        });
        getBuildingHPI.doConnectInBackground();
    }

    /**
     * 加载单元列表
     */
    private void loadUnits(){
        //dialogUtil.showProgressDialog("","正在加载单元列表...",false);
        final GetUnitHPI getUnitHPI = new GetUnitHPI(this);
        getUnitHPI.setCourtId(mCourtId);
        getUnitHPI.setBuildingId(mBuildingId);
        getUnitHPI.setListener(new WebAPIListener() {
            @Override
            public void onLoadSuccess(int successNum) {
                dialogUtil.dismissProgressDialog();
                mUnitAdapter.getList().clear();
                mUnitAdapter.getList().addAll(getUnitHPI.getUnits());
                mUnitAdapter.notifyDataSetChanged();
                spnUnit.setSelection(0);
                spnUnit.getOnItemSelectedListener().onItemSelected(spnUnit, spnCourt, 0, 0);
            }

            @Override
            public void onLoadFail(int errNum, String errMessage) {
                dialogUtil.dismissProgressDialog();
            }
        });
        getUnitHPI.doConnectInBackground();
    }

    /**
     * 加载房间列表
     */
    private void loadRooms(){
        //dialogUtil.showProgressDialog("","正在加载房间列表...",false);
        final GetRoomHPI getRoomHPI = new GetRoomHPI(this);
        getRoomHPI.setCourtId(mCourtId);
        getRoomHPI.setBuildingId(mBuildingId);
        getRoomHPI.setUnitId(mUnitId);
        getRoomHPI.setListener(new WebAPIListener() {
            @Override
            public void onLoadSuccess(int successNum) {
                dialogUtil.dismissProgressDialog();
                mRoomAdapter.getList().clear();
                mRoomAdapter.getList().addAll(getRoomHPI.getRooms());
                mRoomAdapter.notifyDataSetChanged();
                spnRoom.setSelection(0);
                spnRoom.getOnItemSelectedListener().onItemSelected(spnRoom, spnCourt, 0, 0);
            }

            @Override
            public void onLoadFail(int errNum, String errMessage) {
                dialogUtil.dismissProgressDialog();
            }
        });
        getRoomHPI.doConnectInBackground();
    }


    /**
     * 搜索点击
     * @param view
     */
    public void onSearchClick(View view){
        final Intent intent = new Intent();

        //获取菜单选择
        final RoomFilterBean court = (RoomFilterBean)spnCourt.getSelectedItem();
        final RoomFilterBean building = (RoomFilterBean)spnBuilding.getSelectedItem();
        final RoomFilterBean unit = (RoomFilterBean) spnUnit.getSelectedItem();
        final RoomFilterBean room = (RoomFilterBean) spnRoom.getSelectedItem();

        intent.putExtra("court", court.getId());
        intent.putExtra("building", building.getId());
        intent.putExtra("unit", unit.getId());
        intent.putExtra("room", room.getId());
        intent.putExtra("roomFullName", court.getText() + "-" + building.getText() + "-" + unit.getText() + "-" + room.getText());
        //获取房间详情
        DialogUtil.getInstance(this).showProgressDialog("","正在获取房间详情",false);
        final GetRoomDetailHPI getRoomDetailHPI = new GetRoomDetailHPI(this);
        getRoomDetailHPI.setCourtId(court.getId());
        getRoomDetailHPI.setBuildingId(building.getId());
        getRoomDetailHPI.setUnitId(unit.getId());
        getRoomDetailHPI.setRoomId(room.getId());
        getRoomDetailHPI.setListener(new WebAPIListener() {
            @Override
            public void onLoadSuccess(int successNum) {
                DialogUtil.getInstance(RoomFilterActivity.this).dismissProgressDialog();
                intent.putExtra("contact", getRoomDetailHPI.getRoom().getResident());
                intent.putExtra("tel", getRoomDetailHPI.getRoom().getPhone());
                if (getIntent().getBooleanExtra("isFilter", false)) {
                    //返回结果
                    setResult(RESULT_OK, intent);
                    finish();
                } else {
                    //跳转到房间详情页面
                    intent.setClass(RoomFilterActivity.this, RoomInfoActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onLoadFail(int errNum, String errMessage) {
                DialogUtil.getInstance(RoomFilterActivity.this).dismissProgressDialog();
                DialogUtil.getInstance(RoomFilterActivity.this).showShortToast(errMessage);
            }
        });
        getRoomDetailHPI.doConnectInBackground();
    }

    /**
     * 初始化监听
     */
    private void initListener(){
        spnCourt.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                RoomFilterBean item = (RoomFilterBean) parent.getSelectedItem();
                mCourtId = item.getId();
                loadBuildings();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        spnBuilding.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                RoomFilterBean item = (RoomFilterBean) parent.getSelectedItem();
                mBuildingId = item.getId();
                loadUnits();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        spnUnit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                RoomFilterBean item = (RoomFilterBean) parent.getSelectedItem();
                mUnitId = item.getId();
                loadRooms();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        spnRoom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                RoomFilterBean item = (RoomFilterBean) parent.getSelectedItem();
                mRoomId = item.getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    /**
     * 初始化adapter
     */
    private void initAdapters(){
        mCourtAdapter = new RoomFilterAdapter();
        spnCourt.setAdapter(mCourtAdapter);

        mBuildingAdapter = new RoomFilterAdapter();
        spnBuilding.setAdapter(mBuildingAdapter);

        mUnitAdapter = new RoomFilterAdapter();
        spnUnit.setAdapter(mUnitAdapter);

        mRoomAdapter = new RoomFilterAdapter();
        spnRoom.setAdapter(mRoomAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
