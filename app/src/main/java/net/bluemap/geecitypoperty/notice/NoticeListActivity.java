package net.bluemap.geecitypoperty.notice;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import net.bluemap.geecitypoperty.R;
import net.bluemap.geecitypoperty.common.ShareUtil;
import net.bluemap.geecitypoperty.common.model.LoginInfo;
import net.bluemap.geecitypoperty.notice.model.NoticeAdapter;
import net.bluemap.geecitypoperty.notice.network.GetNoticeListHPI;
import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.annotation.view.ViewInject;

import java.util.List;

import hz.toollib.interfaces.WebAPIListener;
import hz.toollib.widget.ListPagingActivity;

/**
 * 通知列表Activity
 * created by Liu Peng in 2015/7/26
 */
public class NoticeListActivity extends ListPagingActivity {

    @ViewInject(id = R.id.toolbar) Toolbar mToolbar;
    //接口
    GetNoticeListHPI getNoticeListHPI;
    //参数
    LoginInfo li;

    @Override
    protected int getLayoutResId() {
        return R.layout.common_single_fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FinalActivity.initInjectedView(this);
        mToolbar.setTitle("物业通知公告");
        mToolbar.setNavigationIcon(R.mipmap.common_btn_back);
        setSupportActionBar(mToolbar);
        li= ShareUtil.getInstance(this).getLoginInfo();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void loadData(int pageIndex) {
        getNoticeListHPI = new GetNoticeListHPI(this);
        getNoticeListHPI.setUsername(li.getUserName());
        getNoticeListHPI.setDepartId(li.getDepartId());
        Log.d("pageIndex", "" + pageIndex);
        getNoticeListHPI.setPage(pageIndex);
        Log.d("pageIndex", "" + pageIndex);
        Log.d("username", "" + li.getUserName());
        Log.d("departId",""+li.getDepartId());
        getNoticeListHPI.setListener(new WebAPIListener() {
            @Override
            public void onLoadSuccess(int successNum) {
                onLoaded(true);
            }

            @Override
            public void onLoadFail(int errNum, String errMessage) {
                onLoaded(false);
            }
        });
        getNoticeListHPI.doConnectInBackground();
    }

    @Override
    public List getDataList() {
        return getNoticeListHPI.getNotices();
    }

    @Override
    public RecyclerView.Adapter getAdapter(List list) {
        return new NoticeAdapter(this,list);
    }

    @Override
    public RecyclerView.ItemDecoration getItemDecoration() {
        return null;
    }
}
