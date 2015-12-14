package net.bluemap.geecitypoperty.order.model;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import net.bluemap.geecitypoperty.R;

import java.util.List;

/**
 * 人员多选选择列表
 * Created by LiuPeng on 2015/7/30.
 */
public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeAdapter.ViewHolder> {

    private Context mContext;
    private List<EmployeeBean> mList;

    public EmployeeAdapter(Context mContext, List<EmployeeBean> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_employee,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final EmployeeBean eb = mList.get(position);
        holder.employee.setChecked(eb.isSelect());
        holder.employee.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                eb.setSelect(isChecked);
            }
        });
        holder.employee.setText(eb.getName());

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        View root;
        CheckBox employee;
        public ViewHolder(View itemView) {
            super(itemView);
            root = itemView;
            employee = (CheckBox) root.findViewById(R.id.ie_cb_employee);
        }
    }

}
