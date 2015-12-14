package net.bluemap.geecitypoperty.task;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import net.bluemap.geecitypoperty.R;
import net.tsz.afinal.FinalActivity;

import hz.toollib.util.DialogUtil;

/**
 * 联系记录列表Activity
 */
public class ContactListActivity extends AppCompatActivity {

    private String taskId;
    private String contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_single_fragment);
        FinalActivity.initInjectedView(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("联系客户记录");
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
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_single_add, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_add) {
            if(getIntent().getBooleanExtra("closed", false)){
                DialogUtil.getInstance(this).showCommitDialog("无法操作", "此任务已关闭，无法新增联系客户记录");
                return true;
            }
            if(getIntent().getBooleanExtra("canAdd", false)) {
                //跳转到新增联系记录页面
                Intent intent = new Intent(this, AddContactActivity.class);
                intent.putExtra("id", taskId);
                intent.putExtra("contact", contact);
                startActivity(intent);
            } else {
                DialogUtil.getInstance(this).showCommitDialog("权限不足", "只有受理人或后续受理人能够新增联系客户记录");
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadData(){
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        ContactListFragment fragment = ContactListFragment.newInstance(taskId);
        transaction.replace(R.id.fragmentContainer, fragment);
        transaction.commit();
    }
}
