package hz.toollib.widget;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import hz.toollib.R;
import hz.toollib.interfaces.ListPageable;
import hz.toollib.util.DialogUtil;

/**
 * 可分页List的Fragment，使用时继承此类，重写ListPageable接口中的方法来绑定Adapter、每次请求数据以及重写加载数据方法。
 * 在子类中加载数据完成后，必须调用本类中的onLoaded方法来通知刷新显示。
 * 可选择性重写个体LayoutManager等方法来定制RecyclerView外观
 * 可重写getIndexStart来定义页码的开始索引
 *
 * Created by LiuPeng on 15/8/5.
 */
public abstract class ListPagingFragment extends Fragment implements ListPageable {

    private View view;
    private SwipeRefreshLayout swipe;

    //加载更多的listener
    OnLoadMoreListener onLoadMoreListener;

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    public SwipeRefreshLayout getSwipe() {
        return swipe;
    }

    //列表
    private RecyclerView recyclerView;

    private List list = new ArrayList<>();
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    //当前是否正在加载
    private boolean isLoading = false;

    //分页
    private int pageIndex = 1;

    //记录上次滑动到的位置
    private int lastPosition = 0;

    public int getIndexStart(){
        return 1;
    }

    //是否有更多数据
    private boolean isHasMore = true;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(view == null){
            view = inflater.inflate(R.layout.fragment_page_list,container,false);
            initView();
        }
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //初始化页码
        this.pageIndex = getIndexStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        if(list.size() == 0 && this.pageIndex == getIndexStart()){
            list.clear();
            isLoading = true;
            setRefresh(true);
            loadData(pageIndex);
        }else{
            //移动到上次移动的位置
            layoutManager.scrollToPosition(lastPosition);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        lastPosition = ((LinearLayoutManager)layoutManager).findFirstVisibleItemPosition();
        layoutManager.computeVerticalScrollOffset(new RecyclerView.State());
    }

    /**
     * 是否是固定高度的List，默认true
     * @return
     */
    public boolean getHasFixedSize(){
        return true;
    }

    /**
     * 获取LayoutManager，默认LinearLayoutManager
     * @return
     */
    public RecyclerView.LayoutManager getLayoutManager(){
        return new LinearLayoutManager(getActivity());
    }

    /**
     * 获取ItemDecoration，默认水平分割线的ItemDecoration
     * @return
     */
    public RecyclerView.ItemDecoration getItemDecoration(){
        return new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL_LIST);
    }

    private void initView(){
        recyclerView = (RecyclerView) view.findViewById(R.id.fpl_rv_list);
        swipe = (SwipeRefreshLayout) view.findViewById(R.id.fpl_srl_swipe);

        //初始化加载更多监听
        onLoadMoreListener = new OnLoadMoreListener(recyclerView) {
            @Override
            protected void onLoadMore() {
                if(isLoading == false && isHasMore){
                    isLoading = true;
                    setRefresh(true);
                    loadData(pageIndex);
                }
            }
        };

        //开启下拉刷新，下拉加载更多
        setRefreshEnable(true, true);

        this.layoutManager = getLayoutManager();
        recyclerView.setLayoutManager(layoutManager);
        this.adapter = getAdapter(list);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(getHasFixedSize());
        RecyclerView.ItemDecoration itemDecoration = getItemDecoration();
        if(itemDecoration != null){
            recyclerView.addItemDecoration(getItemDecoration());
        }
    }

    /**
     * 在子类中加载完成后，必须调用此方法来刷新
     */
    @Override
    public void onLoaded(boolean isSuccess){
        setRefresh(false);
        if(!isSuccess){
            return;
        }
        //没有获取到数据
        if(getDataList().size() == 0){
            if(pageIndex == getIndexStart()){
                //TODO 没有数据，显示没有数据页面
                list.clear();
                adapter.notifyDataSetChanged();
                //DialogUtil.getInstance(getActivity()).showShortToast("没有查询到数据");

            }else{
                //是加载更多时没获取到数据，禁止加载更多
                DialogUtil.getInstance(getActivity()).showShortToast("没有更多数据了");
                isHasMore = false;
            }
        }else{  //获取到了数据
            if(pageIndex == getIndexStart()){
                list.clear();
            }
            list.addAll(getDataList());
            adapter.notifyDataSetChanged();
            pageIndex++;
            isHasMore = true;
        }
        isLoading = false;
    }

    @Override
    public void refreshData() {
        isLoading = true;
        pageIndex = getIndexStart();
        setRefresh(true);
        loadData(pageIndex);
    }

    private void setRefresh(final boolean isRefreshing){
        swipe.post(new Runnable() {
            @Override
            public void run() {
                swipe.setRefreshing(isRefreshing);
            }
        });
    }

    /**
     * 禁止刷新
     */
    public void setRefreshEnable(boolean refreshEnable,boolean loadMoreEnable){
        if(refreshEnable){
            //下拉刷新
            swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    refreshData();
                }
            });
            swipe.setEnabled(true);
        }else{
            swipe.setEnabled(false);
        }
        if(loadMoreEnable){
            //上滑加载更多
            recyclerView.addOnScrollListener(onLoadMoreListener);
        }else{
            recyclerView.removeOnScrollListener(onLoadMoreListener);
        }
    }
}
