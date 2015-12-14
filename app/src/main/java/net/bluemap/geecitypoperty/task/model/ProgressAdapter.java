package net.bluemap.geecitypoperty.task.model;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.bluemap.geecitypoperty.R;
import net.bluemap.geecitypoperty.common.model.PhotoAdapter;
import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.annotation.view.ViewInject;

import java.util.List;

/**
 * 进展adapter
 * Created by LiuPeng on 15/10/29.
 */
public class ProgressAdapter extends RecyclerView.Adapter<ProgressAdapter.ViewHolder>{

    List<ProgressBean> list;

    public ProgressAdapter(List<ProgressBean> list) {
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_progress,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ProgressBean item = list.get(position);
        holder.title.setText(item.getNumber()+"\u3000"+item.getCreateName()+"\u3000"+item.getCreateTime());
        holder.state.setText("进展情况："+item.getState());
        holder.content.setText("进展说明："+item.getContent());
        holder.remark1.setText("答复客户超时备注："+item.getRemark1());
        holder.remark2.setText("承诺时间超时备注："+item.getRemark2());
        holder.remark3.setText("任务处理超时备注："+item.getRemark3());
        // 图片
        if(item.getImages().size() == 0){
            holder.photos.setVisibility(View.GONE);
        }else{
            PhotoAdapter adapter = new PhotoAdapter();
            adapter.getPhotos().addAll(item.getImages());
            holder.photos.setAdapter(adapter);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        @ViewInject(id = R.id.ip_tv_title)
        TextView title;
        @ViewInject(id = R.id.ip_tv_state)
        TextView state;
        @ViewInject(id = R.id.ip_tv_content)
        TextView content;
        @ViewInject(id = R.id.ip_tv_remark1)
        TextView remark1;
        @ViewInject(id = R.id.ip_tv_remark2)
        TextView remark2;
        @ViewInject(id = R.id.ip_tv_remark3)
        TextView remark3;
        @ViewInject(id = R.id.ip_rv_photo)
        RecyclerView photos;

        public ViewHolder(View itemView) {
            super(itemView);
            FinalActivity.initInjectedView(this, itemView);
            //初始化图片列表
            photos.setHasFixedSize(true);
            LinearLayoutManager llm = new LinearLayoutManager(itemView.getContext());
            llm.setOrientation(LinearLayoutManager.HORIZONTAL);
            photos.setLayoutManager(llm);
        }
    }
}
