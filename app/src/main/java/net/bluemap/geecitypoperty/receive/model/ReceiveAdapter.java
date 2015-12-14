package net.bluemap.geecitypoperty.receive.model;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.bluemap.geecitypoperty.R;
import net.bluemap.geecitypoperty.receive.ReceiveDetailActivity;

import java.util.List;

/**
 * Created by LiuPeng on 2015/10/23.
 */
public class ReceiveAdapter extends RecyclerView.Adapter<ReceiveAdapter.ViewHolder> {

//    List<ReceiveBean> list;
List<ReceiveBean> list;
//    public ReceiveAdapter(List<ReceiveBean> list) {
//        this.list = list;
//    }

    public ReceiveAdapter(List<ReceiveBean> list) {
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_receive, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ReceiveAdapter.ViewHolder holder, int position) {
       final ReceiveBean item=list.get(position);
        holder.title.setText("["+item.getSource()+"]["+item.getType()+"]"+item.getContent());
        holder.address.setText(item.getAddress()+"\u3000"+item.getContact());
        holder.accepter.setText(item.getAccepter());
        holder.time.setText(item.getTime());
        holder.receiver.setText(item.getReceiver());
        holder.state.setText(item.getState());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(holder.itemView.getContext(), ReceiveDetailActivity.class);
                intent.putExtra("id",String.valueOf(item.getId()));
                holder.itemView.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    class ViewHolder extends  RecyclerView.ViewHolder{
        TextView title;
        TextView address;
        TextView time;
        TextView receiver;
        TextView accepter;
        TextView state;
        public ViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.ir_tv_title);
            address = (TextView) itemView.findViewById(R.id.ir_tv_addr);
            time = (TextView) itemView.findViewById(R.id.ir_tv_time);
            receiver = (TextView) itemView.findViewById(R.id.ir_tv_receiver);
            accepter = (TextView) itemView.findViewById(R.id.ir_tv_accepter);
            state = (TextView) itemView.findViewById(R.id.ir_tv_state);
        }
    }

//    @Override
//    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_receive, parent, false);
//        return new ViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(final ViewHolder holder, int position) {
//        final ReceiveBean item = list.get(position);
//        holder.title.setText("["+item.getSource()+"]["+item.getType()+"]"+item.getContent());
//        holder.address.setText(item.getAddress()+"\u3000"+item.getContact());
//        holder.time.setText(item.getTime());
//        holder.receiver.setText(item.getReceiver());
//        holder.accepter.setText(item.getAccepter());
//        holder.state.setText(item.getState());
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(holder.itemView.getContext(), ReceiveDetailActivity.class);
//                intent.putExtra("id", String.valueOf(item.getId()));
//                holder.itemView.getContext().startActivity(intent);
//            }
//        });
//    }
//
//    @Override
//    public int getItemCount() {
//        return list.size();
//    }
//
//    class ViewHolder extends RecyclerView.ViewHolder{
//
//        TextView title;
//        TextView address;
//        TextView time;
//        TextView receiver;
//        TextView accepter;
//        TextView state;
//
//        public ViewHolder(View itemView) {
//            super(itemView);
//            title = (TextView) itemView.findViewById(R.id.ir_tv_title);
//            address = (TextView) itemView.findViewById(R.id.ir_tv_addr);
//            time = (TextView) itemView.findViewById(R.id.ir_tv_time);
//            receiver = (TextView) itemView.findViewById(R.id.ir_tv_receiver);
//            accepter = (TextView) itemView.findViewById(R.id.ir_tv_accepter);
//            state = (TextView) itemView.findViewById(R.id.ir_tv_state);
//        }
//    }
}
