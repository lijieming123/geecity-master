package net.bluemap.geecitypoperty.task;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import net.bluemap.geecitypoperty.task.model.ProgressAdapter;
import net.bluemap.geecitypoperty.task.network.GetProgressListHPI;

import java.util.List;

import hz.toollib.interfaces.WebAPIListener;
import hz.toollib.widget.ListPagingFragment;

/**
 * 进展列表
 * Created by LiuPeng on 2015/10/31.
 */
public class ProgressListFragment extends ListPagingFragment {

    private String taskId;

    //接口
    GetProgressListHPI getProgressListHPI;

    public static ProgressListFragment newInstance(String taskId){
        ProgressListFragment fragment = new ProgressListFragment();
        Bundle bundle = new Bundle();
        bundle.putString("id", taskId);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        taskId = getArguments().getString("id");
    }

    @Override
    public void loadData(int pageIndex) {
        getProgressListHPI = new GetProgressListHPI(getActivity());
        getProgressListHPI.setTaskId(taskId);
        getProgressListHPI.setPage(pageIndex);
        getProgressListHPI.setListener(new WebAPIListener() {
            @Override
            public void onLoadSuccess(int successNum) {
                onLoaded(true);
            }

            @Override
            public void onLoadFail(int errNum, String errMessage) {
                onLoaded(false);
            }
        });
        getProgressListHPI.doConnectInBackground();
    }

    @Override
    public List getDataList() {
        return getProgressListHPI.getProgressList();
    }

    @Override
    public RecyclerView.Adapter getAdapter(List list) {
        return new ProgressAdapter(list);
    }
}
