package net.bluemap.geecitypoperty.task;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import net.bluemap.geecitypoperty.R;
import net.bluemap.geecitypoperty.common.DateTimePicker;
import net.bluemap.geecitypoperty.common.ShareUtil;
import net.bluemap.geecitypoperty.common.model.KeyValueAdapter;
import net.bluemap.geecitypoperty.common.model.KeyValueBean;
import net.bluemap.geecitypoperty.receive.network.UmengPushHPI;
import net.bluemap.geecitypoperty.task.network.AddTaskHPI;
import net.bluemap.geecitypoperty.task.network.GetTaskLevelHPI;
import net.bluemap.geecitypoperty.task.network.GetTaskTypesHPI;
import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.annotation.view.ViewInject;

import hz.toollib.interfaces.WebAPIListener;
import hz.toollib.util.DialogUtil;

/**
 * 新增任务
 */
public class AddTaskActivity extends AppCompatActivity {

    //UIs
    @ViewInject(id= R.id.linear_arrange_bill)
    LinearLayout mLinearLayoutDisBill;
    @ViewInject(id= R.id.tv_bill_type)
    TextView mTvBillType;
    RadioGroup mRadioGroup;
    @ViewInject(id= R.id.radio_distribute_bill)
    RadioButton mRadioBtnDistribute;
    @ViewInject(id= R.id.radio_rob_bill)
    RadioButton mRadioBtnRobBill;
    @ViewInject(id = R.id.aat_et_title)
    EditText etTitle;
    @ViewInject(id = R.id.aat_spn_type)
    Spinner spnType;
    @ViewInject(id = R.id.aat_spn_level)
    Spinner spnLevel;
    @ViewInject(id = R.id.aat_tv_accepter)
    TextView tvAccepter;
    @ViewInject(id = R.id.aat_tv_follow)
    TextView tvFollow;
    @ViewInject(id = R.id.aat_et_account)
    EditText etAccount;
    @ViewInject(id = R.id.aat_et_3rd)
    EditText et3rPart;
    @ViewInject(id = R.id.aat_et_content)
    EditText etContent;
    @ViewInject(id = R.id.aat_dtp_atime)
    DateTimePicker dtpAnswereTime;
    @ViewInject(id = R.id.aat_dtp_ptime)
    DateTimePicker dtpPromiseTime;
    @ViewInject(id = R.id.aat_dtp_htime)
    DateTimePicker dtpHandleTime;
    @ViewInject(id = R.id.aat_dtp_ttime)
    DateTimePicker dtpTaskTime;

    DialogUtil dialogUtil;

    //接口
    GetTaskLevelHPI getTaskLevelHPI;
    GetTaskTypesHPI getTaskTypesHPI;
    UmengPushHPI mUmengPushHPI=new UmengPushHPI(this);
    KeyValueAdapter levelAdapter;
    KeyValueAdapter typeAdapter;
    private String receiveId;

    //受理人和后续受理人是否被选择
    boolean isSelectAccepter = false;
    boolean isSelectFollow = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        FinalActivity.initInjectedView(this);
        mRadioGroup= (RadioGroup) findViewById(R.id.radiogroup);
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radio_distribute_bill:
                        mLinearLayoutDisBill.setVisibility(View.VISIBLE);
                        mTvBillType.setText("派单");
                        break;
                    case R.id.radio_rob_bill:
                        mLinearLayoutDisBill.setVisibility(View.GONE);
                        mTvBillType.setText("抢单");
                        break;
                    default:
                        break;
                }
            }
        });
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("新增任务");
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.mipmap.common_btn_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        dialogUtil = DialogUtil.getInstance(this);

        receiveId = getIntent().getStringExtra("receiveId");

        initView();
    }

    /**
     * 初始化视图
     */
    private void initView(){

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

    public void onFollowSelect(View view){
        Intent intent = new Intent(this, MemberSelectorActivity.class);
        if(view.getId() == R.id.aat_ll_accepter){
            startActivityForResult(intent, 1);
        }else{
            startActivityForResult(intent, 2);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK){
            if(requestCode == 1){
                tvAccepter.setText(data.getStringExtra("name"));
                isSelectAccepter = true;
            }else if (requestCode == 2) {
                tvFollow.setText(data.getStringExtra("name"));
                isSelectFollow = true;
            }
        }
    }

    /**
     * 提交
     * @param view
     */
    public void onSubmit(View view){
        //校验
//        if(!isSelectAccepter){
//            dialogUtil.showCommitDialog("", "请选择受理人");
//            return;
//        }
//        if(!isSelectFollow){
//            dialogUtil.showCommitDialog("", "请选择后续受理人");
//            return;
//        }
        if(etTitle.getText().toString().equals("")){
            dialogUtil.showCommitDialog("", "请填写任务标题");
            return;
        }
        if(etContent.getText().toString().equals("")){
            dialogUtil.showCommitDialog("", "请填写任务描述");
            return;
        }
        dialogUtil.showProgressDialog("","正在新增任务",false);
        AddTaskHPI addTaskHPI = new AddTaskHPI(this);
        addTaskHPI.setTitle(etTitle.getText().toString());
        KeyValueBean level = (KeyValueBean) spnLevel.getSelectedItem();
        KeyValueBean type = (KeyValueBean) spnType.getSelectedItem();
        addTaskHPI.setLevel(level.getKey());
        addTaskHPI.setType(type.getKey());
        //受理人
        addTaskHPI.setReceiver(tvAccepter.getText().toString());
        addTaskHPI.setFollowReceiver(tvFollow.getText().toString());
        addTaskHPI.setAccountability(etAccount.getText().toString());
        addTaskHPI.setThirdPart(et3rPart.getText().toString());
        addTaskHPI.setContent(etContent.getText().toString());
        addTaskHPI.setAnswerTime(dtpAnswereTime.getDateText("yyyy-MM-dd HH:mm:ss"));
        addTaskHPI.setPromiseTime(dtpPromiseTime.getDateText("yyyy-MM-dd HH:mm:ss"));
        addTaskHPI.setHandleTime(dtpHandleTime.getDateText("yyyy-MM-dd HH:mm:ss"));
        addTaskHPI.setTaskTime(dtpTaskTime.getDateText("yyyy-MM-dd HH:mm:ss"));
        addTaskHPI.setReceiveId(receiveId);
        addTaskHPI.setJdlx(mTvBillType.getText().toString());
        addTaskHPI.setCreateName(ShareUtil.getInstance(this).getLoginInfo().getUserName());
        addTaskHPI.setListener(new WebAPIListener() {
            @Override
            public void onLoadSuccess(int successNum) {

                dialogUtil.dismissProgressDialog();
                dialogUtil.showShortToast("提交成功");
                finish();
            }

            @Override
            public void onLoadFail(int errNum, String errMessage) {
                dialogUtil.dismissProgressDialog();
                dialogUtil.showCommitDialog("", errMessage);
            }
        });
        addTaskHPI.doConnectInBackground();

    }

}
