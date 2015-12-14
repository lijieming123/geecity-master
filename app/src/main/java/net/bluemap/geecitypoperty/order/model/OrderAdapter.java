package net.bluemap.geecitypoperty.order.model;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.bluemap.geecitypoperty.R;
import net.bluemap.geecitypoperty.order.OrderDetailActivity;

import java.util.List;

/**
 * 派单列表项
 * Created by Administrator on 2015/7/30.
 */
public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {

    private Context mContext;
    private List<OrderBean> mList;

    public OrderAdapter(Context mContext, List<OrderBean> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final OrderBean ob = mList.get(position);
        holder.type.setText(ob.getType());
        holder.room.setText(ob.getRoom());
        holder.contact.setText(ob.getContact());
        holder.tel.setText(ob.getPhone());
        holder.submitTime.setText("提交时间：" + ob.getSubmitTime());
        holder.orderTime.setText("预约时间：" + ob.getOrderTime());
        holder.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, OrderDetailActivity.class);
                intent.putExtra("id", ob.getId());
                intent.putExtra("state", ob.getState());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        View root;
        TextView type;
        TextView room;
        TextView contact;
        TextView tel;
        TextView submitTime;
        TextView orderTime;

        public ViewHolder(View itemView) {
            super(itemView);
            root = itemView;
            type = (TextView) root.findViewById(R.id.io_tv_type);
            room = (TextView) root.findViewById(R.id.io_tv_room);
            contact = (TextView) root.findViewById(R.id.io_tv_contact);
            tel = (TextView) root.findViewById(R.id.io_tv_tel);
            submitTime = (TextView) root.findViewById(R.id.io_tv_submit);
            orderTime = (TextView) root.findViewById(R.id.io_tv_order);
        }
    }

}
