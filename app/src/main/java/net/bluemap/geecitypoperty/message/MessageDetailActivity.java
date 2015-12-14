package net.bluemap.geecitypoperty.message;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import net.bluemap.geecitypoperty.R;
import net.bluemap.geecitypoperty.common.ReplyDialog;
import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.annotation.view.ViewInject;

/**
 * 消息详细Activity
 */
public class MessageDetailActivity extends AppCompatActivity {

    @ViewInject(id = R.id.toolbar) Toolbar mToolbar;
    @ViewInject(id = R.id.amd_tv_title) TextView tvTitle;
    @ViewInject(id = R.id.amd_tv_detail) TextView tvDetail;
    @ViewInject(id = R.id.amd_tv_checkDate) TextView tvCheckDate;
    @ViewInject(id = R.id.amd_fab_reply,click = "onReplyClick") FloatingActionButton fabReply;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_detail);
        FinalActivity.initInjectedView(this);

        mToolbar.setTitle("消息详细");
        mToolbar.setNavigationIcon(R.mipmap.common_btn_back);
        setSupportActionBar(mToolbar);
        loadData();
    }

    private void loadData(){
        tvTitle.setText("标题");
    }

    /**
     * 点击回复按钮，显示回复对话框
     * @param view
     */
    public void onReplyClick(View view){
        ReplyDialog replyDialog = new ReplyDialog(this);
        replyDialog.setTitle("回复：");
        replyDialog.setHint("请输入回复内容");
        replyDialog.setOnSubmitListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO 提交回复
            }
        });
        replyDialog.show();
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
