package net.bluemap.geecitypoperty.task;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import net.bluemap.geecitypoperty.R;
import net.bluemap.geecitypoperty.common.ShareUtil;
import net.bluemap.geecitypoperty.task.network.AddContactHPI;
import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.annotation.view.ViewInject;

import hz.toollib.interfaces.WebAPIListener;
import hz.toollib.util.DialogUtil;

public class AddContactActivity extends AppCompatActivity {

    //UIs
    @ViewInject(id = R.id.aac_tv_contact)
    TextView tvContact;
    @ViewInject(id = R.id.aac_et_content)
    EditText etContent;

    private String taskId;
    private String contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        FinalActivity.initInjectedView(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("新增联系记录");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.mipmap.common_btn_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        taskId = getIntent().getStringExtra("id");
        contact = getIntent().getStringExtra("contact");
        tvContact.setText("联系人："+contact);
    }

    /**
     * 提交操作
     * @param view
     */
    public void onSubmit(View view){
        final DialogUtil dialogUtil = DialogUtil.getInstance(this);
        dialogUtil.showProgressDialog("", "正在提交", false);
        AddContactHPI addContactHPI = new AddContactHPI(this);
        addContactHPI.setTaskId(taskId);
        addContactHPI.setCreateName(ShareUtil.getInstance(this).getLoginInfo().getUserName());
        addContactHPI.setContent(etContent.getText().toString());
        addContactHPI.setContact(contact);
        addContactHPI.setListener(new WebAPIListener() {
            @Override
            public void onLoadSuccess(int successNum) {
                dialogUtil.dismissProgressDialog();
                dialogUtil.showShortToast("提交成功");
                finish();
            }

            @Override
            public void onLoadFail(int errNum, String errMessage) {
                dialogUtil.dismissProgressDialog();
                dialogUtil.showCommitDialog("",errMessage);
            }
        });
        addContactHPI.doConnectInBackground();
    }
}
