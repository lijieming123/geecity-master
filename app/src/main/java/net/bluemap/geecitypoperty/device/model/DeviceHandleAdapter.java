package net.bluemap.geecitypoperty.device.model;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.bluemap.geecitypoperty.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 待提交设备的adapter
 * Created by LiuPeng on 2015/9/9.
 */
public class DeviceHandleAdapter extends RecyclerView.Adapter<DeviceHandleAdapter.ViewHolder> {

    List<DeviceHandleBean> list = new ArrayList<>();

    public List<DeviceHandleBean> getList() {
        return list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_device_submit_dialog,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        DeviceHandleBean dhb = list.get(position);
        holder.name.setText(dhb.getDeviceName());
        holder.state.setText(dhb.getState());
        holder.type.setText(dhb.getType());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView name;
        TextView type;
        TextView state;
        public ViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.idsd_tv_name);
            type = (TextView) itemView.findViewById(R.id.idsd_tv_type);
            state = (TextView) itemView.findViewById(R.id.idsd_tv_state);
        }
    }
}
