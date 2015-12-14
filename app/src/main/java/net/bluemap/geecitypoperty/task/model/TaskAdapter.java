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
 * 任务列表adapter
 * Created by LiuPeng on 2015/10/30.
 */
public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder>{

    List<TaskBean> list;

    public TaskAdapter(List<TaskBean> list) {
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final TaskBean item = list.get(position);
        //填充数据
        holder.title.setText("["+(position + 1)+"]["+ item.getState()+"]");
        holder.room.setText(item.getRoom()+"\u3000联系人："+item.getContact());
        holder.content.setText(item.getContent());
        holder.time.setText("受理时间："+item.getTime());

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
        @ViewInject(id = R.id.it_tv_title)
        TextView title;
        @ViewInject(id = R.id.it_tv_room)
        TextView room;
        @ViewInject(id = R.id.it_tv_content)
        TextView content;
        @ViewInject(id = R.id.it_tv_time)
        TextView time;
        public ViewHolder(View itemView) {
            super(itemView);
            FinalActivity.initInjectedView(this, itemView);
        }
    }
}
