package net.bluemap.geecitypoperty.room;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.bluemap.geecitypoperty.R;

/**
 * 房间选择fragment
 */
public class RoomListFragment extends Fragment {

    //参数
    private int id;

    public static RoomListFragment newInstance(int id){
        RoomListFragment fragment = new RoomListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("id", id);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        id = getArguments().getInt("id");
    }

    public RoomListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.common_single_list,container,false);
        return view;
    }


}
