package net.bluemap.geecitypoperty.meter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import net.bluemap.geecitypoperty.R;
import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.annotation.view.ViewInject;

import hz.toollib.util.IntentUtil;

/**
 * 房屋信息Activity
 */
public class RoomInfoActivity extends AppCompatActivity {

    @ViewInject(id = R.id.toolbar)
    Toolbar mToolbar;

    @ViewInject(id = R.id.ari_tv_name)
    TextView tvName;
    @ViewInject(id = R.id.ari_tv_resident)
    TextView tvResident;
    @ViewInject(id = R.id.ari_tv_phone)
    TextView tvPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_info);
        FinalActivity.initInjectedView(this);

        mToolbar.setTitle("房间信息");
        mToolbar.setNavigationIcon(R.mipmap.common_btn_back);
        setSupportActionBar(mToolbar);

        fillData();
    }

    /**
     * 填充数据
     */
    private void fillData(){
        tvName.setText(getIntent().getStringExtra("roomFullName"));
        tvResident.setText(getIntent().getStringExtra("contact"));
        tvPhone.setText(getIntent().getStringExtra("tel"));
    }

    public void call(View view){
        IntentUtil intentUtil = new IntentUtil(this);
        intentUtil.makePhoneCall(getIntent().getStringExtra("tel"));
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
