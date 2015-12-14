package net.bluemap.geecitypoperty.device;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import net.bluemap.geecitypoperty.R;
import net.bluemap.geecitypoperty.zxing.activity.CaptureActivity;
import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.annotation.view.ViewInject;

public class DeviceActivity extends AppCompatActivity {

    @ViewInject(id = R.id.toolbar)
    Toolbar mToolbar;

    @ViewInject(id = R.id.ad_btn_scan, click = "onScan")
    Button btnScan;
    @ViewInject(id = R.id.ad_btn_update, click = "onUpdate")
    Button btnUpdate;
    @ViewInject(id = R.id.ad_btn_submit, click = "onSubmit")
    Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device);
        FinalActivity.initInjectedView(this);
        mToolbar.setNavigationIcon(R.mipmap.common_btn_back);
        mToolbar.setTitle("设备计划");
        setSupportActionBar(mToolbar);
        getSupportFragmentManager();
    }

    /**
     * 跳转到扫描页面
     * @param view
     */
    public void onScan(View view){
        Intent intent = new Intent(this, CaptureActivity.class);
        startActivity(intent);
    }

    /**
     * 更新
     * @param view
     */
    public void onUpdate(View view){
        UpdateDialog dialog = new UpdateDialog(this);
        dialog.show();
    }

    /**
     * 一键提交
     * @param view
     */
    public void onSubmit(View view){
        SubmitDialog dialog = new SubmitDialog(this);
        dialog.show();
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
