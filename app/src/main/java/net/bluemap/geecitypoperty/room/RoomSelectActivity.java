package net.bluemap.geecitypoperty.room;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import net.bluemap.geecitypoperty.R;
import net.bluemap.geecitypoperty.common.model.KeyValueBean;
import net.bluemap.geecitypoperty.meter.RoomInfoActivity;
import net.bluemap.geecitypoperty.meter.network.GetRoomDetailHPI;
import net.bluemap.geecitypoperty.room.network.GetRoomHPIFactory;
import net.bluemap.geecitypoperty.room.network.GetRoomLevel;
import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.annotation.view.ViewInject;

import hz.toollib.interfaces.WebAPIListener;
import hz.toollib.util.DialogUtil;
import hz.toollib.widget.DividerItemDecoration;

/**
 * 房间选择Activity
 * 参数level分为小区选择1、楼座选择2、单元选择3和房间选择4
 */
public class RoomSelectActivity extends AppCompatActivity {

    @ViewInject(id = R.id.ars_rv_list)
    RecyclerView recyclerView;

    RoomAdapter adapter;

    int level = 1;
    String courtId;
    String buildingId;
    String unitId;
    boolean isFilter;

    //4级接口
    GetRoomLevel getRoomHPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_select);
        FinalActivity.initInjectedView(this);

        level = getIntent().getIntExtra("level", 1);
        courtId = getIntent().getStringExtra("courtId");
        buildingId = getIntent().getStringExtra("buildingId");
        unitId = getIntent().getStringExtra("unitId");
        isFilter = getIntent().getBooleanExtra("isFilter", false);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(GetRoomHPIFactory.GetLevelName(level));
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        toolbar.setNavigationIcon(R.mipmap.common_btn_back);

        loadData();
    }

    /**
     * 加载数据
     */
    private void loadData(){
        DialogUtil.getInstance(this).showProgressDialog("","正在加载中...",false);
        getRoomHPI = GetRoomHPIFactory.getRoomHPIInstance(this,level,courtId,buildingId,unitId);
        getRoomHPI.setListener(new WebAPIListener() {
            @Override
            public void onLoadSuccess(int successNum) {
                DialogUtil.getInstance(RoomSelectActivity.this).dismissProgressDialog();
                initList();
            }

            @Override
            public void onLoadFail(int errNum, String errMessage) {
                DialogUtil.getInstance(RoomSelectActivity.this).dismissProgressDialog();
                DialogUtil.getInstance(RoomSelectActivity.this).showShortToast(errMessage);
            }
        });
        getRoomHPI.doConnectInBackground();
    }

    /**
     * 加载列表
     */
    private void initList(){
        //初始化RecyclerView
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL_LIST));
        adapter = new RoomAdapter(getRoomHPI.getList(), new RoomAdapter.OnItemClickListener() {
            @Override
            public void onClick(KeyValueBean kv) {
                switch (level) {
                    case 1:
                    default:
                        Intent intent1 = new Intent(RoomSelectActivity.this, RoomSelectActivity.class);
                        intent1.putExtra("courtId", kv.getKey());
                        intent1.putExtra("courtName", kv.getValue());
                        intent1.putExtra("level", level + 1);
                        intent1.putExtra("isFilter",isFilter);
                        startActivityForResult(intent1,10);
                        break;
                    case 2:
                        Intent intent2 = new Intent(RoomSelectActivity.this, RoomSelectActivity.class);
                        intent2.putExtras(getIntent().getExtras());
                        intent2.putExtra("buildingId", kv.getKey());
                        intent2.putExtra("buildingName", kv.getValue());
                        intent2.putExtra("level", level + 1);
                        intent2.putExtra("isFilter",isFilter);
                        startActivityForResult(intent2, 10);
                        break;
                    case 3:
                        Intent intent3 = new Intent(RoomSelectActivity.this, RoomSelectActivity.class);
                        intent3.putExtras(getIntent().getExtras());
                        intent3.putExtra("unitId", kv.getKey());
                        intent3.putExtra("unitName", kv.getValue());
                        intent3.putExtra("level", level + 1);
                        intent3.putExtra("isFilter",isFilter);
                        startActivityForResult(intent3, 10);
                        break;
                    case 4:
                        loadRoomDetailAndJump(kv.getKey(), kv.getValue());
                        break;
                }
            }
        });
        recyclerView.setAdapter(adapter);
    }

    /**
     * 第4级执行跳转或回调
     * @param roomId
     * @param roomName
     */
    private void loadRoomDetailAndJump(String roomId,String roomName){
        final Intent intent = new Intent();
        intent.putExtra("court", courtId);
        intent.putExtra("building", buildingId);
        intent.putExtra("unit", unitId);
        intent.putExtra("room", roomId);
        intent.putExtra("roomFullName",
                getIntent().getStringExtra("courtName") + "-"
                        + getIntent().getStringExtra("buildingName") + "-"
                        + getIntent().getStringExtra("unitName") + "-"
                        + roomName);
        //获取房间详情
        DialogUtil.getInstance(this).showProgressDialog("", "正在获取房间详情", false);
        final GetRoomDetailHPI getRoomDetailHPI = new GetRoomDetailHPI(this);
        getRoomDetailHPI.setCourtId(courtId);
        getRoomDetailHPI.setBuildingId(buildingId);
        getRoomDetailHPI.setUnitId(unitId);
        getRoomDetailHPI.setRoomId(roomId);
        getRoomDetailHPI.setListener(new WebAPIListener() {
            @Override
            public void onLoadSuccess(int successNum) {
                DialogUtil.getInstance(RoomSelectActivity.this).dismissProgressDialog();
                intent.putExtra("contact", getRoomDetailHPI.getRoom().getResident());
                intent.putExtra("tel", getRoomDetailHPI.getRoom().getPhone());
                if (getIntent().getBooleanExtra("isFilter", false)) {
                    //返回结果
                    setResult(RESULT_OK, intent);
                    finish();
                } else {
                    //跳转到房间详情页面
                    intent.setClass(RoomSelectActivity.this, RoomInfoActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onLoadFail(int errNum, String errMessage) {
                DialogUtil.getInstance(RoomSelectActivity.this).dismissProgressDialog();
                DialogUtil.getInstance(RoomSelectActivity.this).showShortToast(errMessage);
            }
        });
        getRoomDetailHPI.doConnectInBackground();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //如果是选择器模式，则一路回调
        if(isFilter){
            setResult(resultCode, data);
            finish();
        }
    }
}
