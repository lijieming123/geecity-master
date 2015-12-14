package net.bluemap.geecitypoperty.task;


import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import net.bluemap.geecitypoperty.common.ShareUtil;
import net.bluemap.geecitypoperty.task.model.TaskAdapter;
import net.bluemap.geecitypoperty.task.network.GetTaskListHPI;

import java.util.List;

import hz.toollib.interfaces.WebAPIListener;
import hz.toollib.widget.ListPagingFragment;

/**
 * 任务列表fragment
 */
public class TaskListFragment extends ListPagingFragment {

    private String courtId;
    private String remark;

    public static TaskListFragment newInstance(String courtId, String remark){
        TaskListFragment fragment = new TaskListFragment();
        Bundle bundle = new Bundle();
        bundle.putString("courtId", courtId);
        bundle.putString("remark", remark);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        courtId = getArguments().getString("courtId");
        remark = getArguments().getString("remark");
    }

    //接口
    GetTaskListHPI getTaskListHPI;

    @Override
    public void loadData(int pageIndex) {
        getTaskListHPI = new GetTaskListHPI(getActivity());
        getTaskListHPI.setListener(new WebAPIListener() {
            @Override
            public void onLoadSuccess(int successNum) {
                onLoaded(true);
            }

            @Override
            public void onLoadFail(int errNum, String errMessage) {
                onLoaded(false);
            }
        });
        getTaskListHPI.setPage(pageIndex);
        getTaskListHPI.setCourtId(courtId);
        getTaskListHPI.setRemark(remark);
        getTaskListHPI.setUserName(ShareUtil.getInstance(getActivity()).getLoginInfo().getUserName());
        getTaskListHPI.doConnectInBackground();
    }

    @Override
    public List getDataList() {
        return getTaskListHPI.getTasks();
    }

    @Override
    public RecyclerView.Adapter getAdapter(List list) {
        return new TaskAdapter(list);
    }

    @Override
    public RecyclerView.ItemDecoration getItemDecoration() {
        return null;
    }
}
