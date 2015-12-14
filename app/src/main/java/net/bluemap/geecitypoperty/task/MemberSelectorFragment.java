package net.bluemap.geecitypoperty.task;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import net.bluemap.geecitypoperty.common.ShareUtil;
import net.bluemap.geecitypoperty.task.model.MemberAdapter;
import net.bluemap.geecitypoperty.task.model.MemberBean;
import net.bluemap.geecitypoperty.task.network.GetMembersHPI;

import java.util.List;

import hz.toollib.interfaces.WebAPIListener;
import hz.toollib.widget.ListPagingFragment;

/**
 * 成员选择fragment
 * Created by LiuPeng on 2015/11/9.
 */
public class MemberSelectorFragment extends ListPagingFragment implements MemberAdapter.OnSeletedListener {

    GetMembersHPI getMembersHPI;

    public static MemberSelectorFragment newInstance(String positionId, String searchName){
        MemberSelectorFragment fragment = new MemberSelectorFragment();
        Bundle bundle = new Bundle();
        bundle.putString("positionId", positionId);
        bundle.putString("searchName", searchName);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getMembersHPI = new GetMembersHPI(getActivity());
        getMembersHPI.setPositionId(getArguments().getString("positionId"));
        getMembersHPI.setSearchName(getArguments().getString("searchName"));
        getMembersHPI.setUserName(ShareUtil.getInstance(getActivity()).getLoginInfo().getUserName());
    }

    @Override
    public void onStart() {
        super.onStart();
        setRefreshEnable(true,false);
    }

    @Override
    public void loadData(int pageIndex) {
        getMembersHPI.setPage(pageIndex);
        getMembersHPI.setListener(new WebAPIListener() {
            @Override
            public void onLoadSuccess(int successNum) {
                onLoaded(true);
            }

            @Override
            public void onLoadFail(int errNum, String errMessage) {
                onLoaded(false);
            }
        });
        getMembersHPI.doConnectInBackground();
    }

    @Override
    public List getDataList() {
        return getMembersHPI.getMembers();
    }

    @Override
    public RecyclerView.Adapter getAdapter(List list) {
        return new MemberAdapter(list, this);
    }

    @Override
    public void onSelected(MemberBean memberBean) {
        //选中一个员工，返回
        Intent intent = new Intent();
        intent.putExtra("name", memberBean.getName());
        getActivity().setResult(Activity.RESULT_OK, intent);
        getActivity().finish();
    }
}
