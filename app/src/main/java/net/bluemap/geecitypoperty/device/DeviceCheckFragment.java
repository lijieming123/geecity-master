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
import net.bluemap.geecitypoperty.device.model.CheckBean;
import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.annotation.view.ViewInject;

import java.util.List;

/**
 * 设备巡检信息fragment
 */
public class DeviceCheckFragment extends Fragment {

    @ViewInject(id = R.id.csl_rl_list)
    RecyclerView recyclerView;
    @ViewInject(id = R.id.csl_tv_nodata)
    TextView tvNoData;

    List<CheckBean> list;

    public void setList(List<CheckBean> list) {
        this.list = list;
    }

    public static DeviceCheckFragment newInstance(){
        DeviceCheckFragment fragment = new DeviceCheckFragment();
        return fragment;
    }

    public DeviceCheckFragment() {
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
        CheckAdapter adapter = new CheckAdapter();
        recyclerView.setAdapter(adapter);
    }

    class CheckAdapter extends RecyclerView.Adapter<ViewHodler>{

        @Override
        public ViewHodler onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_device_check, parent,false);
            return new ViewHodler(view);
        }

        @Override
        public void onBindViewHolder(ViewHodler holder, int position) {
            CheckBean item = list.get(position);
            holder.type.setText("类别：" + item.getType());
            holder.preTime.setText("上次时间：" + item.getLastTime());
            holder.nextTime.setText("下次计划时间：" + item.getNextTime());
        }

        @Override
        public int getItemCount() {
            return list.size();
        }
    }

    class ViewHodler extends RecyclerView.ViewHolder{

        TextView type;
        TextView preTime;
        TextView nextTime;

        public ViewHodler(View itemView) {
            super(itemView);
            type = (TextView) itemView.findViewById(R.id.idc_tv_type);
            preTime = (TextView) itemView.findViewById(R.id.idc_tv_pretime);
            nextTime = (TextView) itemView.findViewById(R.id.idc_tv_nexttime);
        }
    }

}
