package net.bluemap.geecitypoperty.meter.model;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import net.bluemap.geecitypoperty.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 房间筛选的spinner的Adapter
 * Created by Liu Peng on 2015/7/26.
 */
public class RoomFilterAdapter extends BaseAdapter {

    private List<RoomFilterBean> mList = new ArrayList<>();

    public List<RoomFilterBean> getList() {
        return mList;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_room_filter,parent,false);
        TextView text = (TextView) convertView.findViewById(R.id.irf_tv_text);
        text.setText(mList.get(position).getText());
        return convertView;
    }
}
