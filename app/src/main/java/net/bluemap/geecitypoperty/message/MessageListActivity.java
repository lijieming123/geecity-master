package net.bluemap.geecitypoperty.message;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import net.bluemap.geecitypoperty.R;
import net.bluemap.geecitypoperty.message.model.MessageAdapter;
import net.bluemap.geecitypoperty.message.network.GetMessageListWSI;
import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.annotation.view.ViewInject;

import java.util.List;

import hz.toollib.interfaces.WebAPIListener;
import hz.toollib.widget.ListPagingActivity;

public class MessageListActivity extends ListPagingActivity {

    @ViewInject(id = R.id.toolbar) Toolbar mToolbar;

    //接口
    GetMessageListWSI getMessageListWSI;

    @Override
    protected int getLayoutResId() {
        return R.layout.common_single_fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FinalActivity.initInjectedView(this);
        //toolbar
        mToolbar.setTitle("物业内部消息");
        mToolbar.setNavigationIcon(R.mipmap.common_btn_back);
        setSupportActionBar(mToolbar);
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
        getMessageListWSI = new GetMessageListWSI(this);
        getMessageListWSI.setListener(new WebAPIListener() {
            @Override
            public void onLoadSuccess(int successNum) {

            }

            @Override
            public void onLoadFail(int errNum, String errMessage) {

            }
        });
        getMessageListWSI.doConnectInBackground();
    }

    @Override
    public List getDataList() {
        return getMessageListWSI.getMessages();
    }

    @Override
    public RecyclerView.Adapter getAdapter(List list) {
        return new MessageAdapter(this, list);
    }
}
