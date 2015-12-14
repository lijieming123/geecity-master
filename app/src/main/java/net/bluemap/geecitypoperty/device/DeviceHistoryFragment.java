package net.bluemap.geecitypoperty.device;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.bluemap.geecitypoperty.R;
import net.bluemap.geecitypoperty.device.model.HistoryBean;
import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.annotation.view.ViewInject;

import java.util.List;

/**
 * 设备历史记录fragment
 */
public class DeviceHistoryFragment extends Fragment {

    @ViewInject(id = R.id.csl_rl_list)
    RecyclerView recyclerView;
    @ViewInject(id = R.id.csl_tv_nodata)
    TextView tvNoData;

    List<HistoryBean> list;

    public void setList(List<HistoryBean> list) {
        this.list = list;
    }

    public static DeviceHistoryFragment newInstance(){
        DeviceHistoryFragment fragment = new DeviceHistoryFragment();
        return fragment;
    }

    public DeviceHistoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.common_single_list, container, false);
        FinalActivity.initInjectedView(this, view);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        //加载数据
        if(list == null && list.size() == 0){
            tvNoData.setVisibility(View.VISIBLE);
            return;
        }
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        HistoryAdapter adapter = new HistoryAdapter();
        recyclerView.setAdapter(adapter);
    }

    class HistoryAdapter extends RecyclerView.Adapter<ViewHodler>{

        @Override
        public ViewHodler onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_device_history, parent,false);
            return new ViewHodler(view);
        }

        @Override
        public void onBindViewHolder(ViewHodler holder, int position) {
            HistoryBean item = list.get(position);
            holder.type.setText("类别：" + item.getType());
            holder.pretime.setText("上次时间：" + item.getLastTime());
            holder.state.setText("状态：" + item.getState());
            holder.status.setText("情况：" + item.getSituation());
        }

        @Override
        public int getItemCount() {
            return list.size();
        }
    }

    class ViewHodler extends RecyclerView.ViewHolder{

        TextView type;
        TextView pretime;
        TextView state;
        TextView status;

        public ViewHodler(View itemView) {
            super(itemView);
            type = (TextView) itemView.findViewById(R.id.idh_tv_type);
            pretime = (TextView) itemView.findViewById(R.id.idh_tv_pretime);
            state = (TextView) itemView.findViewById(R.id.idh_tv_state);
            status = (TextView) itemView.findViewById(R.id.idh_tv_status);
        }
    }

}
