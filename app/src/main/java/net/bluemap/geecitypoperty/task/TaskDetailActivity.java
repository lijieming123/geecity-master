package net.bluemap.geecitypoperty.task;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import net.bluemap.geecitypoperty.R;
import net.bluemap.geecitypoperty.common.ShareUtil;
import net.bluemap.geecitypoperty.common.model.LoginInfo;
import net.bluemap.geecitypoperty.receive.ReceiveDetailActivity;
import net.bluemap.geecitypoperty.task.model.TaskBean;
import net.bluemap.geecitypoperty.task.network.GetTaskDetailHPI;
import net.bluemap.geecitypoperty.task.network.UpdateAccepterHPI;
import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.annotation.view.ViewInject;

import hz.toollib.interfaces.WebAPIListener;
import hz.toollib.util.DialogUtil;
import hz.toollib.util.IntentUtil;
import hz.toollib.widget.OnLoadingView;

/**
 * 任务详细页
 */
public class TaskDetailActivity extends AppCompatActivity {
    //UIs
    @ViewInject(id = R.id.onLoadingView)
    OnLoadingView onLoadingView;
    @ViewInject(id = R.id.atd_tv_room)
    TextView tvRoom;
    @ViewInject(id = R.id.atd_tv_contact)
    TextView tvContact;
    @ViewInject(id = R.id.atd_tv_receiveContent)
    TextView tvReceiveContent;
    @ViewInject(id = R.id.atd_tv_state)
    TextView tvState;
    @ViewInject(id = R.id.atd_tv_type)
    TextView tvType;
    @ViewInject(id = R.id.atd_tv_level)
    TextView tvLevel;
    @ViewInject(id = R.id.atd_tv_receiver)
    TextView tvReceiver;
    @ViewInject(id = R.id.atd_tv_time)
    TextView tvTime;
    @ViewInject(id = R.id.atd_tv_ttime)
    TextView tvTaskTime;
    @ViewInject(id = R.id.atd_tv_title)
    TextView tvTitle;
    @ViewInject(id = R.id.atd_tv_follow)
    TextView tvFollow;
    @ViewInject(id = R.id.atd_ibtn_edit)
    ImageButton ibtnEdit;
    @ViewInject(id = R.id.atd_tv_account)
    TextView tvAccount;
    @ViewInject(id = R.id.atd_tv_3rd)
    TextView tv3rdPart;
    @ViewInject(id = R.id.atd_tv_atime)
    TextView tvAnswerTime;
    @ViewInject(id = R.id.atd_tv_ptime)
    TextView tvPromiseTime;
    @ViewInject(id = R.id.atd_tv_htime)
    TextView tvHandleTime;
    @ViewInject(id = R.id.atd_tv_content)
    TextView tvContent;

    private String taskId;

    //接口
    GetTaskDetailHPI getTaskDetailHPI;

    LoginInfo loginInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);
        FinalActivity.initInjectedView(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("任务详细");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.mipmap.common_btn_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        taskId = getIntent().getStringExtra("id");

        loginInfo = ShareUtil.getInstance(this).getLoginInfo();

        onLoadingView.setOnReloadClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadData();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }


    private void loadData(){
        onLoadingView.showOnloading();
        getTaskDetailHPI = new GetTaskDetailHPI(this);
        getTaskDetailHPI.setTaskId(taskId);
        getTaskDetailHPI.setListener(new WebAPIListener() {
            @Override
            public void onLoadSuccess(int successNum) {
                fillData();
                onLoadingView.hide();
            }

            @Override
            public void onLoadFail(int errNum, String errMessage) {
                onLoadingView.showError();
            }
        });
        getTaskDetailHPI.doConnectInBackground();
    }

    private void fillData(){
        TaskBean task = getTaskDetailHPI.getTask();
        tvRoom.setText("房        间："+task.getRoom());
        tvContact.setText("联  系  人："+task.getContact()+"\u3000电话："+task.getTel());
        tvReceiveContent.setText("接待描述："+task.getrContent());
        tvState.setText(task.getState());
        tvType.setText(task.getType());
        tvLevel.setText(task.getLevel());
        tvReceiver.setText("受  理  人："+task.getReceiver());
        tvTime.setText("受理时间："+task.getCreateTime());
        tvTaskTime.setText("派工时间："+task.getTaskTime());
        tvTitle.setText("任务主题："+task.getTitle());
        tvFollow.setText("后续受理人："+task.getFollowReceiver());
        tvAccount.setText("责任单位："+task.getAccountability());
        tv3rdPart.setText("第三方处理单位："+task.getThirdPart());
        tvAnswerTime.setText("答复客户时限："+task.getAnswerTime());
        tvPromiseTime.setText("承诺完成时间："+task.getPromiseTime());
        tvHandleTime.setText("任务处理时间："+task.getHandleTime());
        tvContent.setText("任务详情："+task.getContent());
    }

    //点击事件处理

    /**
     * 上方四个按钮的点击事件
     * @param view
     */
    public void onClick(View view){
        Intent intent = new Intent();
        switch (view.getId()){
            case R.id.atd_btn_receive:
                intent.setClass(this, ReceiveDetailActivity.class);
                intent.putExtra("id", getTaskDetailHPI.getTask().getReceiveId());
                break;
            case R.id.atd_btn_contact:
                intent.setClass(this, ContactListActivity.class);
                intent.putExtra("id", taskId);
                intent.putExtra("closed", getTaskDetailHPI.getTask().getState().equals("已关闭"));
                intent.putExtra("canAdd", loginInfo.getUserName().equals(getTaskDetailHPI.getTask().getReceiver()) ||
                loginInfo.getUserName().equals(getTaskDetailHPI.getTask().getFollowReceiver()));
                intent.putExtra("contact", getTaskDetailHPI.getTask().getContact());
                break;
            case R.id.atd_btn_progress:
                intent.setClass(this, ProgressListActivity.class);
                intent.putExtra("id", taskId);
                intent.putExtra("closed", getTaskDetailHPI.getTask().getState().equals("已关闭"));
                intent.putExtra("canAdd", loginInfo.getUserName().equals(getTaskDetailHPI.getTask().getReceiver()) ||
                        loginInfo.getUserName().equals(getTaskDetailHPI.getTask().getFollowReceiver()));
                intent.putExtra("receiveId",getTaskDetailHPI.getTask().getReceiveId());
                break;
            case R.id.atd_btn_close:
                if(getTaskDetailHPI.getTask().getState().equals("已关闭")){
                    DialogUtil.getInstance(this).showCommitDialog("", "此任务已是关闭状态");
                    return;
                }
                if(loginInfo.getUserName().equals(getTaskDetailHPI.getTask().getFollowReceiver())){
                    intent.setClass(this, CloseTaskActivity.class);
                    intent.putExtra("id", taskId);
                }else{
                    DialogUtil.getInstance(this).showCommitDialog("权限不足", "只有后续受理人能够执行关闭任务操作");
                    return;
                }
                break;
            default:return;
        }
        startActivity(intent);
    }

    /**
     * 电话号码的点击事件
     * @param view
     */
    public void onContact(View view){
        IntentUtil iu = new IntentUtil(this);
        iu.makePhoneCall(getTaskDetailHPI.getTask().getTel());
    }

    /**
     * 编辑后续联系人的点击事件
     * @param view
     */
    public void onEdit(View view){
        //判断是否是后续受理人
        if(loginInfo.getUserName().equals(getTaskDetailHPI.getTask().getFollowReceiver())){
            Intent intent = new Intent(this, MemberSelectorActivity.class);
            startActivityForResult(intent, 1);
        }else{
            DialogUtil.getInstance(this).showCommitDialog("权限不足", "只有后续受理人能够修改后续受理人");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && resultCode == Activity.RESULT_OK){
            //保存更改后的信息
            final String name = data.getStringExtra("name");
            DialogUtil.getInstance(this).showProgressDialog("","正在提交修改",false);
            UpdateAccepterHPI updateAccepterHPI = new UpdateAccepterHPI(this);
            updateAccepterHPI.setTaskId(taskId);
            updateAccepterHPI.setUserName(name);
            updateAccepterHPI.setListener(new WebAPIListener() {
                @Override
                public void onLoadSuccess(int successNum) {
                    DialogUtil.getInstance(TaskDetailActivity.this).dismissProgressDialog();
                    DialogUtil.getInstance(TaskDetailActivity.this).showShortToast("修改成功");
                    tvFollow.setText("后续受理人：" + name);
                    tvFollow.setVisibility(View.VISIBLE);
                }

                @Override
                public void onLoadFail(int errNum, String errMessage) {
                    DialogUtil.getInstance(TaskDetailActivity.this).dismissProgressDialog();
                    DialogUtil.getInstance(TaskDetailActivity.this).showCommitDialog("错误", errMessage);
                }
            });
            updateAccepterHPI.doConnectInBackground();
        }
    }
}
