package net.bluemap.geecitypoperty.order;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import net.bluemap.geecitypoperty.R;
import net.bluemap.geecitypoperty.common.FragmentAdapter;
import net.bluemap.geecitypoperty.common.ShareUtil;
import net.bluemap.geecitypoperty.common.model.LoginInfo;
import net.bluemap.geecitypoperty.order.model.StateManager;
import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.annotation.view.ViewInject;

import java.util.ArrayList;
import java.util.List;

public class OrderListActivity extends AppCompatActivity {

    @ViewInject(id = R.id.toolbar) Toolbar mToolbar;
    @ViewInject(id = R.id.tabs) TabLayout mTabs;
    @ViewInject(id = R.id.viewpager) ViewPager mViewPager;

    LoginInfo li;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_list);
        FinalActivity.initInjectedView(this);
        mToolbar.setTitle("派单列表");
        mToolbar.setNavigationIcon(R.mipmap.common_btn_back);
        setSupportActionBar(mToolbar);
        li = ShareUtil.getInstance(this).getLoginInfo();
        setupViewpager();
    }

    private void setupViewpager(){
        mTabs.setTabMode(TabLayout.MODE_SCROLLABLE);
        List<String> titles = new ArrayList<>();
        List<Fragment> fragments = new ArrayList<>();
        //TODO 这里需要判断用户权限，普通用户不显示未受理的派单。。置auth为1
        int auth = li.getAuth();
        for(int i = auth; i < 7; i++){
            String text = StateManager.getStateText(i);
            titles.add(text);
            mTabs.addTab(mTabs.newTab().setText(text));
            fragments.add(OrderListFragment.newInstance(i+""));
        }
        FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager(),fragments,titles);
        mViewPager.setAdapter(adapter);
        mTabs.setupWithViewPager(mViewPager);
        mTabs.setTabsFromPagerAdapter(adapter);

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
