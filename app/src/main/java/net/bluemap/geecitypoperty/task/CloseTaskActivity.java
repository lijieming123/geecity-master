package net.bluemap.geecitypoperty.task;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;

import net.bluemap.geecitypoperty.R;
import net.bluemap.geecitypoperty.common.DateTimePicker;
import net.bluemap.geecitypoperty.common.model.KeyValueAdapter;
import net.bluemap.geecitypoperty.common.model.KeyValueBean;
import net.bluemap.geecitypoperty.task.network.CloseTaskHPI;
import net.bluemap.geecitypoperty.task.network.GetTaskLevelHPI;
import net.bluemap.geecitypoperty.task.network.GetTaskTypesHPI;
import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.annotation.view.ViewInject;

import hz.toollib.interfaces.WebAPIListener;
import hz.toollib.util.DialogUtil;

/**
 * 关闭任务页面
 */
public class CloseTaskActivity extends AppCompatActivity {

    //UIs
    @ViewInject(id = R.id.act_dtp_promise)
    DateTimePicker dtpPromise;
    @ViewInject(id = R.id.act_dtp_close)
    DateTimePicker dtpClose;
    @ViewInject(id = R.id.act_spn_level)
    Spinner spnLevel;
    @ViewInject(id = R.id.act_spn_type)
    Spinner spnType;
    @ViewInject(id = R.id.act_cb_repair)
    CheckBox cbRepair;
    @ViewInject(id = R.id.act_et_repairCount)
    EditText etRepairCount;

    @ViewInject(id = R.id.act_rg_quality)
    RadioGroup rgQuality;
    @ViewInject(id = R.id.act_rg_attitude)
    RadioGroup rgAttitude;
    @ViewInject(id = R.id.act_rg_timeliness)
    RadioGroup rgTimeliness;

    @ViewInject(id = R.id.act_rg_closeType)
    RadioGroup rgCloseType;
    @ViewInject(id = R.id.act_et_closeContent)
    EditText etCloseContent;
    @ViewInject(id = R.id.act_et_solution)
    EditText etSolution;

    private String taskId;

    //接口
    GetTaskLevelHPI getTaskLevelHPI;
    GetTaskTypesHPI getTaskTypesHPI;

    KeyValueAdapter levelAdapter;
    KeyValueAdapter typeAdapter;

    DialogUtil dialogUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_close_task);
        FinalActivity.initInjectedView(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("关闭任务");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.mipmap.common_btn_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        taskId = getIntent().getStringExtra("id");
        dialogUtil = DialogUtil.getInstance(this);
        initView();
    }

    private void initView(){
        //是否返修点击后显示返修次数
        cbRepair.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                etRepairCount.setVisibility(isChecked?View.VISIBLE:View.GONE);
            }
        });

        //加载处理方式和级别的数据
        getTaskLevelHPI = new GetTaskLevelHPI(this);
        getTaskLevelHPI.setListener(new WebAPIListener() {
            @Override
            public void onLoadSuccess(int successNum) {
                levelAdapter = new KeyValueAdapter();
                levelAdapter.getList().addAll(getTaskLevelHPI.getList());
                spnLevel.setAdapter(levelAdapter);
            }

            @Override
            public void onLoadFail(int errNum, String errMessage) {
                dialogUtil.showShortToast(errMessage);
            }
        });
        getTaskLevelHPI.doConnectInBackground();

        getTaskTypesHPI = new GetTaskTypesHPI(this);
        getTaskTypesHPI.setListener(new WebAPIListener() {
            @Override
            public void onLoadSuccess(int successNum) {
                typeAdapter = new KeyValueAdapter();
                typeAdapter.getList().addAll(getTaskTypesHPI.getList());
                spnType.setAdapter(typeAdapter);
            }

            @Override
            public void onLoadFail(int errNum, String errMessage) {
                dialogUtil.showShortToast(errMessage);
            }
        });
        getTaskTypesHPI.doConnectInBackground();
    }


    /**
     * 提交操作
     * @param view
     */
    public void onSubmit(View view){
        dialogUtil.showProgressDialog("","正在关闭任务",false);
        CloseTaskHPI closeTaskHPI = new CloseTaskHPI(this);
        closeTaskHPI.setTaskId(taskId);
        closeTaskHPI.setPromiseTime(dtpPromise.getDateText("yyyy-MM-dd HH:mm:ss"));
        closeTaskHPI.setCloseTime(dtpClose.getDateText("yyyy-MM-dd HH:mm:ss"));
        KeyValueBean level = (KeyValueBean)spnLevel.getSelectedItem();
        KeyValueBean type = (KeyValueBean) spnType.getSelectedItem();
        closeTaskHPI.setFinalLevel(level.getKey());
        closeTaskHPI.setHandleType(type.getKey());
        closeTaskHPI.setRepair(cbRepair.isChecked() ? "1" : "0");
        if(cbRepair.isChecked()){
            if(etRepairCount.getText().toString().equals("")){
                dialogUtil.showShortToast("请填写返修次数");
                return;
            }
            closeTaskHPI.setRepairCount(etRepairCount.getText().toString());
        }else{
            closeTaskHPI.setRepairCount("0");
        }
        closeTaskHPI.setQuality(rgQuality.getCheckedRadioButtonId() == R.id.act_rb_attitude0 ? "0" : "1");
        closeTaskHPI.setAttitude(rgAttitude.getCheckedRadioButtonId() == R.id.act_rb_attitude0 ? "0" : "1");
        closeTaskHPI.setTimeliness(rgTimeliness.getCheckedRadioButtonId() == R.id.act_rb_timeliness0 ? "0" : "1");
        if(rgCloseType.getCheckedRadioButtonId() == R.id.act_rb_1){
            closeTaskHPI.setCloseType("0");
        }else{
            closeTaskHPI.setCloseType("1");
        }
        closeTaskHPI.setCloseContent(etCloseContent.getText().toString());
        closeTaskHPI.setSolution(etSolution.getText().toString());
        closeTaskHPI.setListener(new WebAPIListener() {
            @Override
            public void onLoadSuccess(int successNum) {
                dialogUtil.dismissProgressDialog();
                dialogUtil.showShortToast("任务已关闭");
                finish();
            }

            @Override
            public void onLoadFail(int errNum, String errMessage) {
                dialogUtil.dismissProgressDialog();
                dialogUtil.showShortToast(errMessage);
            }
        });
        closeTaskHPI.doConnectInBackground();
    }
}
