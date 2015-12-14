package net.bluemap.geecitypoperty.order;


import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import net.bluemap.geecitypoperty.common.ShareUtil;
import net.bluemap.geecitypoperty.common.model.LoginInfo;
import net.bluemap.geecitypoperty.order.model.OrderAdapter;
import net.bluemap.geecitypoperty.order.network.GetOrderListHPI;

import java.util.List;

import hz.toollib.interfaces.WebAPIListener;
import hz.toollib.widget.ListPagingFragment;

/**
 * 派单列表fragment
 */
public class OrderListFragment extends ListPagingFragment {

    static final private String STATE = "state";

    //接口
    private String state;
    private LoginInfo li;
    private GetOrderListHPI getOrderListHPI;

    static public OrderListFragment newInstance(String state){
        OrderListFragment fragment = new OrderListFragment();
        Bundle bundle = new Bundle();
        bundle.putString(STATE, state);
        fragment.setArguments(bundle);
        return fragment;
    }

    public OrderListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshData();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments()!=null){
            state = getArguments().getString(STATE);
        }
        li = ShareUtil.getInstance(getActivity()).getLoginInfo();
    }

    @Override
    public void loadData(int pageIndex) {
        getOrderListHPI = new GetOrderListHPI(getActivity());
        getOrderListHPI.setPage(pageIndex);
        getOrderListHPI.setAreaId(li.getAreaId());
        getOrderListHPI.setState(state);
        //抢单和未受理不需要userId
        if(!state.equals("1") && !state.equals("0")){
            getOrderListHPI.setUserId(li.getId());
        }
        getOrderListHPI.setListener(new WebAPIListener() {
            @Override
            public void onLoadSuccess(int successNum) {
                onLoaded(true);
            }

            @Override
            public void onLoadFail(int errNum, String errMessage) {
                onLoaded(false);
            }
        });
        getOrderListHPI.doConnectInBackground();
    }

    @Override
    public List getDataList() {
        return getOrderListHPI.getOrders();
    }

    @Override
    public RecyclerView.Adapter getAdapter(List list) {
        return new OrderAdapter(getActivity(),list);
    }
}
