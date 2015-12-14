package hz.toollib.interfaces;

import android.support.v7.widget.RecyclerView;

import java.util.List;

/**
 * 可分页的列表，为ListPagingFragment和ListPagingActivity定义接口
 * Created by LiuPeng on 2015/8/5.
 */
public interface ListPageable {

    /**
     * 加载数据
     * @param pageIndex
     */
    void loadData(int pageIndex);

    /**
     * 刷新数据，如果客户端需要刷新数据，调用此公共方法
     */
    void refreshData();

    /**
     * 获取请求数据列表（如每次网络访问返回的数据）
     * @return
     */
    List getDataList();

    /**
     * 获取Adapter
     * @param list
     * @return
     */
    RecyclerView.Adapter getAdapter(List list);

    /**
     * 加载完成后的回调，在抽象类中实现，子类中调用
     * @param isSuccess 是否成功
     */
    void onLoaded(boolean isSuccess);


    //可选的重写方法

    int getIndexStart();

    boolean getHasFixedSize();

    RecyclerView.LayoutManager getLayoutManager();

    RecyclerView.ItemDecoration getItemDecoration();

}
