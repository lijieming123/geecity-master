package net.bluemap.geecitypoperty.root;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import net.bluemap.geecitypoperty.R;
import net.bluemap.geecitypoperty.common.ShareUtil;
import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.annotation.view.ViewInject;

public class SettingActivity extends AppCompatActivity {

    @ViewInject(id = R.id.toolbar)
    Toolbar mToolbar;

    @ViewInject(id = R.id.as_et_url)
    EditText etUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        FinalActivity.initInjectedView(this);
        mToolbar.setTitle("系统设置");
        mToolbar.setNavigationIcon(R.mipmap.common_btn_back);
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        etUrl.setText(ShareUtil.getInstance(this).getHostUrl());
    }

    /**
     * 提交操作
     * @param view
     */
    public void onSubmit(View view){
        ShareUtil.getInstance(this).saveHostUrl(etUrl.getText().toString());
        finish();
    }
}
