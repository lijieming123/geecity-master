package hz.toollib.widget;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import hz.toollib.interfaces.ListPageable;

/**
 * 分页Activity
 * Created by LiuPeng on 2015/8/5.
 */
public abstract class ListPagingActivity extends SingleFragmentActivity implements ListPageable {
    ListPagingFragment fragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        //使用activity的方法替换fragment中的方法
        fragment = new ListPagingFragment() {
            @Override
            public void loadData(int pageIndex) {
                ListPagingActivity.this.loadData(pageIndex);
            }

            @Override
            public List getDataList() {
                return ListPagingActivity.this.getDataList();
            }

            @Override
            public RecyclerView.Adapter getAdapter(List list) {
                return ListPagingActivity.this.getAdapter(list);
            }

            @Override
            public int getIndexStart() {
                return ListPagingActivity.this.getIndexStart();
            }

            @Override
            public RecyclerView.LayoutManager getLayoutManager() {
                return ListPagingActivity.this.getLayoutManager();
            }

            @Override
            public boolean getHasFixedSize() {
                return ListPagingActivity.this.getHasFixedSize();
            }

            @Override
            public RecyclerView.ItemDecoration getItemDecoration() {
                return ListPagingActivity.this.getItemDecoration();
            }
        };
        super.onCreate(savedInstanceState);
    }

    @Override
    protected Fragment createFragment() {
        return fragment;
    }

    @Override
    public void onLoaded(boolean isSuccess) {
        fragment.onLoaded(isSuccess);
    }

    @Override
    public void refreshData() {
        fragment.refreshData();
    }

    @Override
    public int getIndexStart() {
        return 1;
    }

    @Override
    public RecyclerView.LayoutManager getLayoutManager() {
        return new LinearLayoutManager(this);
    }

    @Override
    public boolean getHasFixedSize() {
        return true;
    }

    @Override
    public RecyclerView.ItemDecoration getItemDecoration() {
        return new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST);
    }
    public ListPagingFragment getFragment()
    {
        return fragment;
    }
}
