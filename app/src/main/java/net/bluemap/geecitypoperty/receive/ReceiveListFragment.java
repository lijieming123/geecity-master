package net.bluemap.geecitypoperty.receive;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import net.bluemap.geecitypoperty.common.ShareUtil;
import net.bluemap.geecitypoperty.common.model.LoginInfo;
import net.bluemap.geecitypoperty.receive.model.ReceiveAdapter;
import net.bluemap.geecitypoperty.receive.network.GetReceiveListHPI;

import java.util.List;

import hz.toollib.interfaces.WebAPIListener;
import hz.toollib.widget.ListPagingFragment;

/**
 * 接待列表
 * Created by LiuPeng on 2015/10/23.
 */
public class ReceiveListFragment extends ListPagingFragment {

    //入参
    private String courtId;
    private String remark;

    private LoginInfo li;

    public static ReceiveListFragment newInstance(String courtId, String remark){
        ReceiveListFragment fragment = new ReceiveListFragment();
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

        li = ShareUtil.getInstance(getActivity()).getLoginInfo();
    }

    //定义接口
    GetReceiveListHPI getReceiveListHPI;

    @Override
    public void loadData(int pageIndex) {
        getReceiveListHPI = new GetReceiveListHPI(getActivity());
        getReceiveListHPI.setPage(pageIndex);
        getReceiveListHPI.setCourtId(courtId);
        getReceiveListHPI.setRemark(remark);
        getReceiveListHPI.setUserName(li.getUserName());
        getReceiveListHPI.setListener(new WebAPIListener() {
            @Override
            public void onLoadSuccess(int successNum) {
                onLoaded(true);
            }

            @Override
            public void onLoadFail(int errNum, String errMessage) {
                onLoaded(false);
            }
        });
        getReceiveListHPI.doConnectInBackground();
    }

    @Override
    public List getDataList() {
        return getReceiveListHPI.getReceiveList();
    }

    @Override
    public RecyclerView.Adapter getAdapter(List list) {
        ReceiveAdapter adapter = new ReceiveAdapter(list);
        return adapter;
    }

    @Override
    public RecyclerView.ItemDecoration getItemDecoration() {
        return null;
    }
}
