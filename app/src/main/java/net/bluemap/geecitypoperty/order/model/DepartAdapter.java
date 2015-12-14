package net.bluemap.geecitypoperty.order.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import net.bluemap.geecitypoperty.R;

import java.util.List;

/**
 * 处理部门adapter
 * Created by LiuPeng on 2015/8/12.
 */
public class DepartAdapter extends BaseAdapter {
    Context context;
    List<DepartBean> list;

    public DepartAdapter(Context context,List<DepartBean> list){
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.item_department,parent,false);
        TextView tv = (TextView)convertView.findViewById(R.id.id_tv_department);
        tv.setText(list.get(position).getName());
        return convertView;
    }
}
