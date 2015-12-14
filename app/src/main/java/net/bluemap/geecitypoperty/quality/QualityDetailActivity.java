package net.bluemap.geecitypoperty.quality;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import net.bluemap.geecitypoperty.R;
import net.bluemap.geecitypoperty.quality.adapter.QualityAdapter;
import net.bluemap.geecitypoperty.quality.network.CheckQualityHPI;
import net.bluemap.geecitypoperty.quality.network.GetQualityHPI;
import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.annotation.view.ViewInject;

import hz.toollib.interfaces.WebAPIListener;
import hz.toollib.util.DialogUtil;

public class QualityDetailActivity extends AppCompatActivity {

    @ViewInject(id = R.id.toolbar)
    Toolbar mToolbar;

    @ViewInject(id = R.id.aqd_rv_list)
    RecyclerView rvList;

    GetQualityHPI getQualityHPI;

    QualityAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quality_detail);
        FinalActivity.initInjectedView(this);
        mToolbar.setTitle("品质检查");
        mToolbar.setNavigationIcon(R.mipmap.common_btn_back);
        setSupportActionBar(mToolbar);

        getQualityHPI = new GetQualityHPI(this);

        initView();
        loadData();
    }

    private void initView(){
        adapter = new QualityAdapter();
        rvList.setHasFixedSize(false);
        rvList.setLayoutManager(new LinearLayoutManager(this));
        rvList.setAdapter(adapter);

    }

    private void loadData(){
        DialogUtil.getInstance(this).showProgressDialog("","正在加载...",false);
        getQualityHPI.setListener(new WebAPIListener() {
            @Override
            public void onLoadSuccess(int successNum) {
                DialogUtil.getInstance(QualityDetailActivity.this).dismissProgressDialog();
                adapter.getList().clear();
                adapter.getList().addAll(getQualityHPI.getList());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onLoadFail(int errNum, String errMessage) {
                DialogUtil.getInstance(QualityDetailActivity.this).dismissProgressDialog();
                DialogUtil.getInstance(QualityDetailActivity.this).showCommitDialogWithFinish("错误",errMessage);
            }
        });
        getQualityHPI.doConnectInBackground();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_quality_detail, menu);
        return true;
    }

    /**
     * 提交
     *
     */
    private void submit(){
        DialogUtil.getInstance(this).showProgressDialog("","正在提交...",false);
        CheckQualityHPI checkQualityHPI = new CheckQualityHPI(this);
        checkQualityHPI.setListener(new WebAPIListener() {
            @Override
            public void onLoadSuccess(int successNum) {
                DialogUtil.getInstance(QualityDetailActivity.this).dismissProgressDialog();
                adapter.getList().clear();
                adapter.getList().addAll(getQualityHPI.getList());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onLoadFail(int errNum, String errMessage) {
                DialogUtil.getInstance(QualityDetailActivity.this).dismissProgressDialog();
                DialogUtil.getInstance(QualityDetailActivity.this).showCommitDialogWithFinish("错误", errMessage);
            }
        });
        checkQualityHPI.doConnectInBackground();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        if(item.getItemId() == R.id.action_submit){
            submit();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
