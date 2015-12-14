package net.bluemap.geecitypoperty.order;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import net.bluemap.geecitypoperty.R;
import net.bluemap.geecitypoperty.common.ShareUtil;
import net.bluemap.geecitypoperty.common.model.LoginInfo;
import net.bluemap.geecitypoperty.common.model.PhotoAdapter;
import net.bluemap.geecitypoperty.order.model.OrderBean;
import net.bluemap.geecitypoperty.order.model.StateManager;
import net.bluemap.geecitypoperty.order.network.CatchOrderHPI;
import net.bluemap.geecitypoperty.order.network.GetOrderDetialHPI;
import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.annotation.view.ViewInject;

import hz.toollib.interfaces.WebAPIListener;
import hz.toollib.util.DialogUtil;

/**
 * 派单详细页（受理、接单页）
 * 传id和state参数，根据参数值来确定显示的数据和操作按钮
 * state == 0 跳至受理页
 * state == 1 接单按钮
 * state == 2 跳至闭单
 * state == 3 显示完工信息
 * state == 4 显示截停信息
 * state == 5 已评价，暂显示基本信息
 * state == 6 已回访，暂显示基本信息
 *
 */
public class OrderDetailActivity extends AppCompatActivity {

    @ViewInject(id = R.id.toolbar) Toolbar toolbar;
    @ViewInject(id = R.id.aod_pb_loading) ProgressBar pbLoading;

    /**内容布局*/
    /**基本信息，一直显示*/
    @ViewInject(id = R.id.aod_ll_wait) LinearLayout llWait;
    //派单类型+状态
    @ViewInject(id = R.id.aod_tv_title) TextView tvTitle;
    //提交时间
    @ViewInject(id = R.id.aod_tv_submit) TextView tvSubmit;
    //预约时间
    @ViewInject(id = R.id.aod_tv_order) TextView tvOrder;
    //派单房间
    @ViewInject(id = R.id.aod_tv_room) TextView tvRoom;
    //联系人
    @ViewInject(id = R.id.aod_tv_contact) TextView tvContact;
    //联系电话
    @ViewInject(id = R.id.aod_tv_tel) TextView tvTel;
    //派单内容
    @ViewInject(id = R.id.aod_tv_content) TextView tvContent;
    //图片
    @ViewInject(id = R.id.aod_tv_photo) TextView tvPhoto;
    @ViewInject(id = R.id.aod_rv_photos) RecyclerView rvPhotos;

    /**受理布局*/
    @ViewInject(id = R.id.aod_cv_accept) CardView cvAccept;
    /**抢单状态时显示*/
    @ViewInject(id = R.id.aod_ll_scramble) LinearLayout llScramble;
    //报修时间
    @ViewInject(id = R.id.aod_tv_repair_time) TextView tvRepairTime;
    //要求完成时间
    @ViewInject(id = R.id.aod_tv_expect_time) TextView tvExpectTime;

    /**已派工模式时显示*/
    @ViewInject(id = R.id.aod_ll_tasking) LinearLayout llTasking;
    //派工时间
    @ViewInject(id = R.id.aod_tv_task_time) TextView tvTaskTime;

    /**完工或截停时显示*/
    @ViewInject(id = R.id.aod_cv_close) CardView cvClose;

    //完工信息
    @ViewInject(id = R.id.aod_ll_finish) LinearLayout llFinish;
    //结单时间
    @ViewInject(id = R.id.aod_tv_close_time) TextView tvCloseTime;
    //开工时间
    @ViewInject(id = R.id.aod_tv_start_time) TextView tvStartTime;
    //完工时间
    @ViewInject(id = R.id.aod_tv_end_time) TextView tvEndTime;
    //材料费
    @ViewInject(id = R.id.aod_tv_material_cost) TextView tvMaterialCost;
    //工时费
    @ViewInject(id = R.id.aod_tv_hour_charge) TextView tvHourCharge;
    //材料使用情况
    @ViewInject(id = R.id.aod_tv_material_usage) TextView tvMaterialUsage;
    //其他说明
    @ViewInject(id = R.id.aod_tv_other) TextView tvOther;

    //截停信息
    @ViewInject(id = R.id.aod_ll_pause) LinearLayout llPause;
    //截停时间
    @ViewInject(id = R.id.aod_tv_pause_time) TextView tvPauseTime;
    //截停原因
    @ViewInject(id = R.id.aod_tv_pause_reason) TextView tvPauseReason;
    //操作按钮
    @ViewInject(id = R.id.aod_btn_commit, click = "onCommit") Button btnCommit;

    private String id;
    private int state;
    private LoginInfo li;

    //接口
    private GetOrderDetialHPI getOrderDetialHPI;

    //图片adapter
    private PhotoAdapter mPhotoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        FinalActivity.initInjectedView(this);
        toolbar.setTitle("派单详细");
        toolbar.setNavigationIcon(R.mipmap.common_btn_back);
        setSupportActionBar(toolbar);
        id = getIntent().getStringExtra("id");
        state = Integer.valueOf(getIntent().getStringExtra("state"));
        li = ShareUtil.getInstance(this).getLoginInfo();

        //初始化视图显示
        initView();
        //加载数据
        loadData();
    }

    /**
     * 初始化视图
     */
    private void initView(){
        //开始时全部隐藏
        cvAccept.setVisibility(View.GONE);
        cvClose.setVisibility(View.GONE);
        llWait.setVisibility(View.GONE);
        llScramble.setVisibility(View.GONE);
        llTasking.setVisibility(View.GONE);
        llFinish.setVisibility(View.GONE);
        llPause.setVisibility(View.GONE);
        btnCommit.setVisibility(View.GONE);
        tvTitle.setVisibility(View.GONE);
        tvTitle.setBackgroundResource(StateManager.getStateColorId(state));
        rvPhotos.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvPhotos.setLayoutManager(llm);
    }

    /**
     * 加载数据
     */
    private void loadData(){
        getOrderDetialHPI = new GetOrderDetialHPI(this);
        getOrderDetialHPI.setId(id);
        getOrderDetialHPI.setListener(new WebAPIListener() {
            @Override
            public void onLoadSuccess(int successNum) {
                fillData();
                pbLoading.setVisibility(View.GONE);
            }

            @Override
            public void onLoadFail(int errNum, String errMessage) {
                pbLoading.setVisibility(View.GONE);
                DialogUtil.getInstance(OrderDetailActivity.this).showCommitDialog("错误",errMessage);
            }
        });
        getOrderDetialHPI.doConnectInBackground();
    }

    /**
     * 填充数据
     */
    private void fillData(){
        OrderBean order = getOrderDetialHPI.getOrder();
        tvTitle.setText(order.getType() + "（" + StateManager.getStateText(state) + "）");
        tvTitle.setVisibility(View.VISIBLE);
        tvSubmit.setText("提交时间：" + order.getSubmitTime());
        tvOrder.setText("预约时间：" + order.getOrderTime());
        tvRoom.setText(order.getRoom());
        tvContact.setText(order.getContact());
        tvTel.setText(order.getPhone());
        tvContent.setText("派单内容：" + order.getContent());
        // 图片
        if(order.getImages().size() == 0){
            tvPhoto.setText("相关图片：无");
            rvPhotos.setVisibility(View.GONE);
        }else{
            mPhotoAdapter = new PhotoAdapter();
            mPhotoAdapter.getPhotos().addAll(order.getImages());
            rvPhotos.setAdapter(mPhotoAdapter);
        }

        //内容的显示
        llWait.setVisibility(View.VISIBLE);
        if(state >= 1){     //抢单之后的状态
            cvAccept.setVisibility(View.VISIBLE);
            llScramble.setVisibility(View.VISIBLE);
            tvRepairTime.setText("报修时间：" + order.getRepairTime());
            tvExpectTime.setText("要求完工时间：" + order.getExpectTime());
        }
        if(state >= 2){     //派工之后的状态
            llTasking.setVisibility(View.VISIBLE);
            tvTaskTime.setText("派工时间：" + order.getTaskTime());
        }
        if(state > 2){  //结单的状态（完工或截停）
            cvClose.setVisibility(View.VISIBLE);
            if(state == 3){     //完工状态
                llFinish.setVisibility(View.VISIBLE);
                tvCloseTime.setText("结单时间：" + order.getCloseTime());
                tvStartTime.setText("开工时间：" + order.getStartTime());
                tvEndTime.setText("完工时间：" + order.getEndTime());
                tvMaterialCost.setText("材料费：" + order.getMaterialCost());
                tvHourCharge.setText("工时费：" + order.getHourCharge());
                tvMaterialUsage.setText("材料使用情况：" + order.getMaterialUsage());
                tvOther.setText("其他说明：" + order.getOther());
            }
            if(state == 4){     //截停状态
                llPause.setVisibility(View.VISIBLE);
                tvPauseTime.setText("截停时间：" + order.getPauseTime());
                tvPauseReason.setText("截停原因：" + order.getPauseReason());
            }
        }

        //按钮的显示
        if(state <=2){      //只有未受理、抢单中和已派工三个状态有操作键
            btnCommit.setVisibility(View.VISIBLE);
            btnCommit.setText(StateManager.getStateButtonText(state));
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 提交按钮点击
     * @param v 按钮view
     */
    public void onCommit(View v){
        switch (state){
            case 0:     //跳转到受理页
                Intent accIntent = new Intent(this,OrderAcceptActivity.class);
                accIntent.putExtra("id",id);
                startActivity(accIntent);
                finish();
                break;
            case 1:     //确认接单
                catchOrder();
                break;
            case 2:     //跳转到闭单页
                Intent closeIntent = new Intent(this,OrderCloseActivity.class);
                closeIntent.putExtra("id",id);
                startActivity(closeIntent);
                finish();
                break;
            default:
                break;
        }
    }

    /**
     * 接单
     */
    private void catchOrder(){
        CatchOrderHPI catchOrderHPI = new CatchOrderHPI(this);
        catchOrderHPI.setUserId(li.getId());
        catchOrderHPI.setOrderId(id);
        catchOrderHPI.setListener(new WebAPIListener() {
            @Override
            public void onLoadSuccess(int successNum) {
                DialogUtil.getInstance(OrderDetailActivity.this).showCommitDialogWithFinish("", "接单成功");
            }

            @Override
                public void onLoadFail(int errNum, String errMessage) {
                DialogUtil.getInstance(OrderDetailActivity.this).showCommitDialog("接单失败", "错误信息："+errMessage);
            }
        });
        catchOrderHPI.doConnectInBackground();
    }
}
