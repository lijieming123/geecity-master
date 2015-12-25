package net.bluemap.geecitypoperty.task.model;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.bluemap.geecitypoperty.R;
import net.bluemap.geecitypoperty.task.TaskDetailActivity;
import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.annotation.view.ViewInject;

import java.util.List;

/**
 * 相关任务列表adapter
 * Created by LiuPeng on 2015/10/30.
 */
public class RelevantTaskAdapter extends RecyclerView.Adapter<RelevantTaskAdapter.ViewHolder>{

    List<TaskBean> list;

    public RelevantTaskAdapter(List<TaskBean> list) {
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_relevant_task, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final TaskBean item = list.get(position);
        holder.title.setText("["+(position + 1)+"]["+ item.getState()+"]["+item.getType()+"]["+item.getLevel()+"]");
        if(item.getReceiver().equals("无")){
            holder.receiver.setText(item.getJdlx());
        }else {
            holder.receiver.setText("派单："+"受理人：" + item.getReceiver() + "\u3000后续受理人：" + item.getFollowReceiver());
        }
        holder.content.setText(item.getContent());
        holder.handleTime.setText(item.getHandleTime());
        holder.answerTime.setText(item.getAnswerTime());
        holder.promiseTime.setText(item.getPromiseTime());
        //跳转到详情
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.itemView.getContext(), TaskDetailActivity.class);
                intent.putExtra("id",item.getId());
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        @ViewInject(id = R.id.irt_tv_title)
        TextView title;
        @ViewInject(id = R.id.irt_tv_receiver)
        TextView receiver;
        @ViewInject(id = R.id.irt_tv_content)
        TextView content;
        @ViewInject(id = R.id.irt_tv_htime)
        TextView handleTime;
        @ViewInject(id = R.id.irt_tv_atime)
        TextView answerTime;
        @ViewInject(id = R.id.irt_tv_ptime)
        TextView promiseTime;

        public ViewHolder(View itemView) {
            super(itemView);
            FinalActivity.initInjectedView(this, itemView);
        }
    }
}
