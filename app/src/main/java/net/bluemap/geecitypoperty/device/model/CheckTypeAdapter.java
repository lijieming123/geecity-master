package net.bluemap.geecitypoperty.device.model;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import net.bluemap.geecitypoperty.R;

import java.util.List;

/**
 * 设备处理类型adapter
 * Created by LiuPeng on 2015/8/15.
 */
public class CheckTypeAdapter extends RecyclerView.Adapter<CheckTypeAdapter.ViewHolder> {

    public interface onTypeSelectListener{
        void onTypeSelect(String type);
    }
    Context context;
    List<String> list;
    onTypeSelectListener listener;

    public CheckTypeAdapter(Context context, List<String> list,onTypeSelectListener listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_device_handle,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.button.setText(list.get(position));
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onTypeSelect(list.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        View root;
        Button button;
        public ViewHolder(View itemView) {
            super(itemView);
            root = itemView;
            button = (Button) root.findViewById(R.id.idh_button);
        }
    }
}
