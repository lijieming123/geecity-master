package net.bluemap.geecitypoperty.robbill;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import net.bluemap.geecitypoperty.R;
import net.bluemap.geecitypoperty.task.RelevantTaskListFragment;

public class RobbillListActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_robbill_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("抢单列表");
        toolbar.setNavigationIcon(R.mipmap.common_btn_back);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }
    private void loadData(){
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        RelevantTaskListFragment fragment = RelevantTaskListFragment.newInstance(getIntent().getStringExtra("receiveId"));
        Log.d("received",""+getIntent().getStringExtra("receiveId"));
        transaction.replace(R.id.fragmentContainerRobbill, fragment);
        transaction.commit();
    }

}
