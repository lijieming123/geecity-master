package net.bluemap.geecitypoperty.order;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import net.bluemap.geecitypoperty.R;
import net.bluemap.geecitypoperty.common.DateTimePicker;
import net.bluemap.geecitypoperty.common.HttpPostAPI;
import net.bluemap.geecitypoperty.common.ShareUtil;
import net.bluemap.geecitypoperty.common.model.LoginInfo;
import net.bluemap.geecitypoperty.order.network.FinishOrderHPI;
import net.bluemap.geecitypoperty.order.network.PauseOrderHPI;
import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.annotation.view.ViewInject;

import hz.toollib.interfaces.WebAPIListener;
import hz.toollib.util.DialogUtil;

/**
 * 闭单页面
 * Created by LiuPeng on 2015/8/10.
 */
public class OrderCloseActivity extends AppCompatActivity{

    @ViewInject(id = R.id.toolbar)
    Toolbar mToolbar;

    @ViewInject(id = R.id.aoc_rb_finish) RadioButton rbFinish;
    @ViewInject(id = R.id.aoc_ll_pause) LinearLayout llPause;
    @ViewInject(id = R.id.aoc_et_reason) EditText etReason;
    @ViewInject(id = R.id.aoc_ll_finish) LinearLayout llFinish;
    @ViewInject(id = R.id.aoc_dtp_start) DateTimePicker dtpStart;
    @ViewInject(id = R.id.aoc_dtp_end) DateTimePicker dtpEnd;
    @ViewInject(id = R.id.aoc_et_material_cost) EditText etMaterialCost;
    @ViewInject(id = R.id.aoc_et_hour_charge) EditText etHourCharge;
    @ViewInject(id = R.id.aoc_et_material_usage) EditText etMaterialUsage;
    @ViewInject(id = R.id.aoc_et_other) EditText etOther;
    @ViewInject(id = R.id.aoc_btn_commit,click = "commit") Button btnCommit;

    //通用变量
    private LoginInfo li;
    private DialogUtil dialogUtil;
    //参数
    private String orderId;

    //截停接口
    PauseOrderHPI pauseOrderHPI;
    //完工接口
    FinishOrderHPI finishOrderHPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_close);

        FinalActivity.initInjectedView(this);

        mToolbar.setTitle("闭单");
        mToolbar.setNavigationIcon(R.mipmap.common_btn_back);
        setSupportActionBar(mToolbar);

        orderId = getIntent().getStringExtra("id");
        li = ShareUtil.getInstance(this).getLoginInfo();
        dialogUtil = DialogUtil.getInstance(this);

        //单选按钮
        rbFinish.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){  //完工
                    llFinish.setVisibility(View.VISIBLE);
                    llPause.setVisibility(View.GONE);
                }else{
                    llPause.setVisibility(View.VISIBLE);
                    llFinish.setVisibility(View.GONE);
                }
            }
        });
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
     * 提交事件
     * @param view View
     */
    public void commit(View view){
        HttpPostAPI httpPostAPI;
        //有效值检测
        if(rbFinish.isChecked()){   //完工
            if(etMaterialCost.getText().toString().equals("")){
                dialogUtil.showCommitDialog("信息不完整", "请填写材料费");
                return;
            }
            if(etHourCharge.getText().toString().equals("")){
                dialogUtil.showCommitDialog("信息不完整", "请填写工时费");
                return;
            }
            if(etMaterialUsage.getText().equals("")){
                dialogUtil.showCommitDialog("信息不完整", "请填写材料使用情况");
                return;
            }
            finishOrderHPI = new FinishOrderHPI(this);
            finishOrderHPI.setUserId(li.getId());
            finishOrderHPI.setOrderId(orderId);
            finishOrderHPI.setStartTime(dtpStart.getDateText("yyyy-MM-dd HH:mm:ss"));
            finishOrderHPI.setEndTime(dtpEnd.getDateText("yyyy-MM-dd HH:mm:ss"));
            finishOrderHPI.setMaterialCost(etMaterialCost.getText().toString());
            finishOrderHPI.setHourCharge(etHourCharge.getText().toString());
            finishOrderHPI.setMaterialUsage(etMaterialUsage.getText().toString());
            finishOrderHPI.setOther(etOther.getText().toString());
            httpPostAPI = finishOrderHPI;
        }else{  //截停
            if(etReason.getText().toString().equals("")){
                dialogUtil.showCommitDialog("信息不完整", "请填写截停原因");
                return;
            }
            pauseOrderHPI = new PauseOrderHPI(this);
            pauseOrderHPI.setUserId(li.getId());
            pauseOrderHPI.setOrderId(orderId);
            pauseOrderHPI.setReason(etReason.getText().toString());
            httpPostAPI = pauseOrderHPI;
        }

        httpPostAPI.setListener(new WebAPIListener() {
            @Override
            public void onLoadSuccess(int successNum) {
                dialogUtil.dismissProgressDialog();
                dialogUtil.showCommitDialogWithFinish("","操作成功");
            }

            @Override
            public void onLoadFail(int errNum, String errMessage) {
                dialogUtil.dismissProgressDialog();
                dialogUtil.showCommitDialog("错误",errMessage);
            }
        });
        dialogUtil.showProgressDialog("","正在提交...",false);
        httpPostAPI.doConnectInBackground();
    }
}
