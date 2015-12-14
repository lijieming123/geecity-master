package net.bluemap.geecitypoperty.task.model;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.bluemap.geecitypoperty.R;
import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.annotation.view.ViewInject;

import java.util.List;

/**
 * 成员adapter
 * Created by LiuPeng on 2015/11/9.
 */
public class MemberAdapter extends RecyclerView.Adapter<MemberAdapter.ViewHolder>{

    public interface OnSeletedListener{
        void onSelected(MemberBean memberBean);
    }
    OnSeletedListener onSeletedListener;

    List<MemberBean> list;

    public MemberAdapter(List<MemberBean> list,OnSeletedListener onSeletedListener) {
        this.onSeletedListener = onSeletedListener;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_member, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final MemberBean item = list.get(position);
        holder.name.setText(item.getName());
        holder.depart.setText(item.getDepart());
        holder.pos.setText(item.getPosition());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSeletedListener.onSelected(item);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        @ViewInject(id = R.id.im_tv_name)
        TextView name;
        @ViewInject(id = R.id.im_tv_depart)
        TextView depart;
        @ViewInject(id = R.id.im_tv_position)
        TextView pos;

        public ViewHolder(View itemView) {
            super(itemView);
            FinalActivity.initInjectedView(this,itemView);
        }
    }
}
