package net.bluemap.geecitypoperty.notice.model;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import net.bluemap.geecitypoperty.R;
import net.bluemap.geecitypoperty.notice.NoticeDetailActivity;

import java.util.List;

/**
 * 通知adapter
 * Created by LiuPeng on 2015/7/26.
 */
public class NoticeAdapter extends RecyclerView.Adapter<NoticeAdapter.ViewHolder> {

    private Context mContext;
    private List<NoticeBean> mList;

    public NoticeAdapter(Context mContext, List<NoticeBean> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notice,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final NoticeBean nb = mList.get(position);
        holder.title.setText(nb.getTitle());
        if(!nb.isRead()){
            holder.title.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));//加粗
            holder.icon.setImageResource(R.mipmap.notice_ic_unread);
        }else{
            holder.title.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));//取消加粗
            holder.icon.setImageResource(R.mipmap.notice_ic_read);
        }
        holder.date.setText(nb.getDate());
        holder.content.setText(nb.getDetail());
        holder.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nb.setRead(true);
                Intent intent = new Intent(mContext, NoticeDetailActivity.class);
                intent.putExtra("id", nb.getId());
                mContext.startActivity(intent);
                notifyDataSetChanged();
            }
        });
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        View root;
        ImageView icon;
        TextView date;
        TextView title;
        TextView content;
        public ViewHolder(View itemView) {
            super(itemView);
            root = itemView;
            icon = (ImageView) root.findViewById(R.id.in_iv_icon);
            date = (TextView) root.findViewById(R.id.in_tv_date);
            title = (TextView) root.findViewById(R.id.in_tv_title);
            content = (TextView) root.findViewById(R.id.in_tv_content);
        }
    }
}
