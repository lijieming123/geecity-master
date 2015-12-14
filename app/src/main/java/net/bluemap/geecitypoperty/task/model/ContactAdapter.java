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
 * 联系记录adapter
 * Created by LiuPeng on 15/10/29.
 */
public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder>{

    List<ContactBean> list;

    public ContactAdapter(List<ContactBean> list) {
        this.list = list;
    }

    @Override
    public ContactAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contact, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ContactAdapter.ViewHolder holder, int position) {
        ContactBean item = list.get(position);
        holder.number.setText(item.getNumber());
        holder.createName.setText("登记人："+item.getCreateName());
        holder.createTime.setText("联系时间："+item.getCreateTime());
        holder.content.setText("沟通内容："+item.getContent());
        holder.contact.setText("联系人："+item.getContact());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        @ViewInject(id = R.id.ic_tv_number)
        TextView number;
        @ViewInject(id = R.id.ic_tv_createName)
        TextView createName;
        @ViewInject(id = R.id.ic_tv_createTime)
        TextView createTime;
        @ViewInject(id = R.id.ic_tv_content)
        TextView content;
        @ViewInject(id = R.id.ic_tv_contact)
        TextView contact;

        public ViewHolder(View itemView) {
            super(itemView);
            FinalActivity.initInjectedView(this, itemView);
        }
    }
}
