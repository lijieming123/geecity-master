package net.bluemap.geecitypoperty.notice;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.TextView;

import net.bluemap.geecitypoperty.R;
import net.bluemap.geecitypoperty.common.ShareUtil;
import net.bluemap.geecitypoperty.common.model.LoginInfo;
import net.bluemap.geecitypoperty.notice.model.NoticeBean;
import net.bluemap.geecitypoperty.notice.network.GetNoticeDetailHPI;
import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.annotation.view.ViewInject;

import hz.toollib.interfaces.WebAPIListener;
import hz.toollib.util.DialogUtil;

/**
 * 通知详情Activity
 * created by Liu Peng in 2015/7/26
 */
public class NoticeDetailActivity extends AppCompatActivity {

    @ViewInject(id = R.id.toolbar) Toolbar mToolbar;
    @ViewInject(id = R.id.and_tv_title) TextView tvTitle;
    @ViewInject(id = R.id.and_tv_detail) TextView tvDetail;
    @ViewInject(id = R.id.and_wv_detail) WebView wvDetail;
    @ViewInject(id = R.id.and_tv_checkDate) TextView tvCheckDate;

    private String id;

    private LoginInfo li;

    private DialogUtil dialogUtil;

    //接口
    GetNoticeDetailHPI getNoticeDetailHPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_detail);
        FinalActivity.initInjectedView(this);
        id = getIntent().getStringExtra("id");
        li= ShareUtil.getInstance(this).getLoginInfo();
        dialogUtil = DialogUtil.getInstance(this);
        mToolbar.setTitle("通知详细");
        mToolbar.setNavigationIcon(R.mipmap.common_btn_back);
        setSupportActionBar(mToolbar);
        loadData();
    }

    private void loadData(){
        dialogUtil.showProgressDialog("", "正在加载...", false);
        getNoticeDetailHPI = new GetNoticeDetailHPI(this);
        getNoticeDetailHPI.setUsername(li.getUserName());
        getNoticeDetailHPI.setId(id);
        getNoticeDetailHPI.setRealName(li.getRealName());
        /*

        */

        getNoticeDetailHPI.setListener(new WebAPIListener() {

            @Override
            public void onLoadSuccess(int successNum) {
                dialogUtil.dismissProgressDialog();
                NoticeBean notice = getNoticeDetailHPI.getNotice();
                tvTitle.setText(notice.getTitle() + "-" + notice.getDate());
                tvDetail.setText(Html.fromHtml(notice.getDetail()));
                //wvDetail.loadDataWithBaseURL(null,"<p>&nbsp;测试。</p><p><img src=\"http://img1.imgtn.bdimg.com/it/u=17450837,1439926586&fm=23&gp=0.jpg\" alt=\"\" /><img src=\"http://img4.imgtn.bdimg.com/it/u=52754568,3369504778&fm=23&gp=0.jpg\" width=\"100\" height=\"38\" alt=\"\" /></p>", "text/html", "UTF-8",null);
                tvCheckDate.setText("查看时间："+notice.getCheckDate());
            }

            @Override
            public void onLoadFail(int errNum, String errMessage) {
                dialogUtil.dismissProgressDialog();
                dialogUtil.showCommitDialogWithFinish("错误", errMessage);
            }
        });
        getNoticeDetailHPI.doConnectInBackground();
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
