package net.bluemap.geecitypoperty.message.model;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.bluemap.geecitypoperty.R;
import net.bluemap.geecitypoperty.message.MessageDetailActivity;

import java.util.List;

/**
 * Created by Administrator on 2015/7/26.
 */
public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {

    private Context mContext;
    private List<MessageBean> mList;

    public MessageAdapter(Context mContext, List<MessageBean> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final MessageBean mb = mList.get(position);
        holder.title.setText(mb.getTitle());
        holder.date.setText("["+mb.getDate()+"]");
        holder.sender.setText("["+mb.getSender()+"]");
        holder.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, MessageDetailActivity.class);
                intent.putExtra("id",mb.getId());
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
        TextView title;
        TextView date;
        TextView sender;
        public ViewHolder(View itemView) {
            super(itemView);
            root = itemView;
            title = (TextView) root.findViewById(R.id.im_tv_title);
            date = (TextView) root.findViewById(R.id.im_tv_date);
            sender = (TextView) root.findViewById(R.id.im_tv_sender);
        }
    }
}
