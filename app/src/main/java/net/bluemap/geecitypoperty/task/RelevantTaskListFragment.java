package net.bluemap.geecitypoperty.task;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import net.bluemap.geecitypoperty.receive.network.GetRelevantTaskListHPI;
import net.bluemap.geecitypoperty.task.model.RelevantTaskAdapter;

import java.util.List;

import hz.toollib.interfaces.WebAPIListener;
import hz.toollib.widget.ListPagingFragment;

/**
 * 相关任务列表fragment
 * Created by LiuPeng on 2015/10/25.
 */
public class RelevantTaskListFragment extends ListPagingFragment {

    //参数
    private String receiveId;

    //接口
    GetRelevantTaskListHPI getRelevantTaskListHPI;

    public static RelevantTaskListFragment newInstance(String receiveId){
        RelevantTaskListFragment fragment = new RelevantTaskListFragment();
        Bundle bundle = new Bundle();
        bundle.putString("receiveId", receiveId);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        receiveId = getArguments().getString("receiveId");
    }

    @Override
    public void loadData(int pageIndex) {
        getRelevantTaskListHPI = new GetRelevantTaskListHPI(getActivity());
        getRelevantTaskListHPI.setPage(pageIndex);
        getRelevantTaskListHPI.setReceiveId(receiveId);
        getRelevantTaskListHPI.setListener(new WebAPIListener() {
            @Override
            public void onLoadSuccess(int successNum) {
                onLoaded(true);
            }

            @Override
            public void onLoadFail(int errNum, String errMessage) {
                onLoaded(false);
            }
        });
        getRelevantTaskListHPI.doConnectInBackground();
    }

    @Override
    public List getDataList() {
        return getRelevantTaskListHPI.getTasks();
    }

    @Override
    public RecyclerView.Adapter getAdapter(List list) {
        return new RelevantTaskAdapter(list);
    }

    @Override
    public RecyclerView.ItemDecoration getItemDecoration() {
        return null;
    }
}
