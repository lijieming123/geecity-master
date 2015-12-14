package net.bluemap.geecitypoperty.task;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;

import net.bluemap.geecitypoperty.R;
import net.bluemap.geecitypoperty.common.model.KeyValueAdapter;
import net.bluemap.geecitypoperty.common.model.KeyValueBean;
import net.bluemap.geecitypoperty.receive.network.GetPositionsHPI;
import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.annotation.view.ViewInject;

import hz.toollib.interfaces.WebAPIListener;
import hz.toollib.util.DialogUtil;

public class MemberSelectorActivity extends AppCompatActivity {

    @ViewInject(id = R.id.ams_spn_position)
    Spinner spnPosition;
    @ViewInject(id = R.id.ams_et_username)
    EditText etUsername;

    KeyValueAdapter posAdapter;

    //获取部门接口
    GetPositionsHPI getPositionsHPI;

    //记录当前选中的部门
    private String mSelectedPositionId = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_selector);
        FinalActivity.initInjectedView(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("成员选择");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.mipmap.common_btn_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        getPositionsHPI = new GetPositionsHPI(this);
        getPositionsHPI.setListener(new WebAPIListener() {
            @Override
            public void onLoadSuccess(int successNum) {
                fillPosition();
            }

            @Override
            public void onLoadFail(int errNum, String errMessage) {
                DialogUtil.getInstance(MemberSelectorActivity.this).showShortToast(errMessage);
            }
        });
        getPositionsHPI.doConnectInBackground();
        loadData();
    }

    private void fillPosition(){
        posAdapter = new KeyValueAdapter();
        posAdapter.getList().clear();
        posAdapter.getList().add(new KeyValueBean("", "全部部门"));
        posAdapter.getList().addAll(getPositionsHPI.getList());
        spnPosition.setAdapter(posAdapter);
        spnPosition.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                KeyValueBean kv = (KeyValueBean) parent.getSelectedItem();
                mSelectedPositionId = kv.getKey();
                loadData();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    private void loadData(){
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        MemberSelectorFragment fragment = MemberSelectorFragment.
                newInstance(mSelectedPositionId, etUsername.getText().toString());
        transaction.replace(R.id.ams_fl, fragment);
        transaction.commit();
    }

    public void onSearch(View view){
        loadData();
    }

}
