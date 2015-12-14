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
 * 进展列表Activity
 */
public class ProgressListActivity extends AppCompatActivity {

    private String taskId;
    private String receiveId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_single_fragment);
        FinalActivity.initInjectedView(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("汇报进展");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.mipmap.common_btn_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        taskId = getIntent().getStringExtra("id");
        receiveId = getIntent().getStringExtra("receiveId");
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
                DialogUtil.getInstance(this).showCommitDialog("无法操作", "此任务已关闭，无法汇报进展");
                return true;
            }
            if(getIntent().getBooleanExtra("canAdd", false)) {
                //跳转到新增汇报进展页面
                Intent intent = new Intent(this, AddProgressActivity.class);
                intent.putExtra("id", taskId);
                intent.putExtra("receiveId", receiveId);
                startActivity(intent);
                return true;
            }else{
                DialogUtil.getInstance(this).showCommitDialog("权限不足", "只有受理人或后续受理人能够汇报进展");
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadData(){
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        ProgressListFragment fragment = ProgressListFragment.newInstance(taskId);
        transaction.replace(R.id.fragmentContainer, fragment);
        transaction.commit();
    }
}
