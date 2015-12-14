package net.bluemap.geecitypoperty.quality;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SimpleAdapter;
import android.widget.Spinner;

import net.bluemap.geecitypoperty.R;
import net.bluemap.geecitypoperty.common.DateTimePicker;
import net.bluemap.geecitypoperty.quality.network.GetQualityServersHPI;
import net.bluemap.geecitypoperty.quality.network.GetQualityServicesHPI;
import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.annotation.view.ViewInject;

import hz.toollib.interfaces.WebAPIListener;
import hz.toollib.util.DialogUtil;

public class QualityCheckActivity extends AppCompatActivity {

    @ViewInject(id = R.id.toolbar)
    Toolbar mToolbar;

    @ViewInject(id = R.id.aqc_spn_servers)
    Spinner spnServers;

    @ViewInject(id = R.id.aqc_spn_services)
    Spinner spnServices;

    @ViewInject(id = R.id.aqc_btn_submit, click = "submit")
    Button btnSubmit;

    @ViewInject(id = R.id.aqc_dtp_datetime)
    DateTimePicker dtpCheckDate;

    //接口
    GetQualityServersHPI getQualityServersHPI;
    GetQualityServicesHPI getQualityServicesHPI;

    SimpleAdapter serverAdapter;
    SimpleAdapter serviceAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quality_check);
        FinalActivity.initInjectedView(this);

        mToolbar.setTitle("品质检查");
        mToolbar.setNavigationIcon(R.mipmap.common_btn_back);
        setSupportActionBar(mToolbar);

        getQualityServersHPI = new GetQualityServersHPI(this);
        getQualityServicesHPI = new GetQualityServicesHPI(this);
        initView();
        //初始化数据
        loadData();
    }

    private void initView(){
        dtpCheckDate.setMode(DateTimePicker.MODE_DATE);

        //spinner的adapter
        serverAdapter = new SimpleAdapter(this,getQualityServersHPI.getList(),R.layout.common_single_item,
                new String[]{"name"},new int[]{R.id.csi_tv});
        spnServers.setAdapter(serverAdapter);

        serviceAdapter = new SimpleAdapter(this,getQualityServicesHPI.getList(),R.layout.common_single_item,
                new String[]{"name"}, new int[]{R.id.csi_tv});
        spnServices.setAdapter(serviceAdapter);
    }

    private void loadData(){

        getQualityServersHPI.setListener(new WebAPIListener() {
            @Override
            public void onLoadSuccess(int successNum) {
                serverAdapter.notifyDataSetChanged();
            }

            @Override
            public void onLoadFail(int errNum, String errMessage) {
                DialogUtil.getInstance(QualityCheckActivity.this).showCommitDialogWithFinish("错误", errMessage);
            }
        });
        getQualityServersHPI.doConnectInBackground();

        getQualityServicesHPI.setListener(new WebAPIListener() {
            @Override
            public void onLoadSuccess(int successNum) {
                serviceAdapter.notifyDataSetChanged();
            }

            @Override
            public void onLoadFail(int errNum, String errMessage) {
                DialogUtil.getInstance(QualityCheckActivity.this).showCommitDialogWithFinish("错误",errMessage);
            }
        });
        getQualityServicesHPI.doConnectInBackground();
    }

    /**
     * 提交
     * @param view
     */
    public void submit(View view){
        Intent intent = new Intent(this, QualityDetailActivity.class);
        startActivity(intent);
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
