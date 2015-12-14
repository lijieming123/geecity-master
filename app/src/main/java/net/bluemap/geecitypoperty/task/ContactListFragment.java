package net.bluemap.geecitypoperty.task;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import net.bluemap.geecitypoperty.task.model.ContactAdapter;
import net.bluemap.geecitypoperty.task.network.GetContactListHPI;

import java.util.List;

import hz.toollib.interfaces.WebAPIListener;
import hz.toollib.widget.ListPagingFragment;

/**
 * 联系人列表
 * Created by LiuPeng on 2015/10/31.
 */
public class ContactListFragment extends ListPagingFragment {

    private String taskId;

    //接口
    GetContactListHPI getContactListHPI;

    public static ContactListFragment newInstance(String taskId){
        ContactListFragment fragment = new ContactListFragment();
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
        getContactListHPI = new GetContactListHPI(getActivity());
        getContactListHPI.setTaskId(taskId);
        getContactListHPI.setPage(pageIndex);
        getContactListHPI.setListener(new WebAPIListener() {
            @Override
            public void onLoadSuccess(int successNum) {
                onLoaded(true);
            }

            @Override
            public void onLoadFail(int errNum, String errMessage) {
                onLoaded(false);
            }
        });
        getContactListHPI.doConnectInBackground();
    }

    @Override
    public List getDataList() {
        return getContactListHPI.getContacts();
    }

    @Override
    public RecyclerView.Adapter getAdapter(List list) {
        return new ContactAdapter(list);
    }
}
