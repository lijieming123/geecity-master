package net.bluemap.geecitypoperty.quality.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import net.bluemap.geecitypoperty.R;
import net.bluemap.geecitypoperty.quality.model.QualityCheckItem;

import java.util.ArrayList;
import java.util.List;

/**
 * 品质检查adapter
 * Created by LiuPeng on 2015/9/14.
 */
public class QualityAdapter extends RecyclerView.Adapter<QualityAdapter.ViewHolder> {

    List<QualityCheckItem> list = new ArrayList<>();

    public List<QualityCheckItem> getList() {
        return list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_quality,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        QualityCheckItem item = list.get(position);
        holder.project.setText("项目：" + item.getProject());
        holder.content.setText("内容：" + item.getScoreDetail());
        holder.amScore.setText(item.getScoreTotal()+"");
        holder.pmScore.setText(item.getScoreTotal()+"");
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView project;
        TextView content;
        EditText amScore;
        EditText pmScore;
        EditText remark;
        public ViewHolder(View itemView) {
            super(itemView);
            project = (TextView) itemView.findViewById(R.id.iq_tv_project);
            content = (TextView) itemView.findViewById(R.id.iq_tv_content);
            amScore = (EditText) itemView.findViewById(R.id.iq_et_am);
            pmScore = (EditText) itemView.findViewById(R.id.iq_et_pm);
            remark = (EditText) itemView.findViewById(R.id.iq_et_remark);
        }
    }
}
