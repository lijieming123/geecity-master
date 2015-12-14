package net.bluemap.geecitypoperty.common.model;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import net.bluemap.geecitypoperty.R;
import net.bluemap.geecitypoperty.common.PhotoActivity;
import net.tsz.afinal.FinalBitmap;

import java.util.ArrayList;
import java.util.List;

/**
 * 用于显示图片的adapter
 * Created by LiuPeng on 2015/8/22.
 */
public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.ViewHolder> {

    List<String> photos = new ArrayList<>();

    boolean editable = false;

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    public List<String> getPhotos() {
        return photos;
    }

    FinalBitmap fb;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_photo,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        fb = FinalBitmap.create(holder.itemView.getContext());
        final String url = photos.get(position);
        Log.d("PhotoAdapter","图片:"+url);
        fb.display(holder.photo, url);
        //是否可编辑
        if(editable){
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    new AlertDialog.Builder(holder.itemView.getContext())
                            .setMessage("是否删除此照片？").setPositiveButton("是", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            photos.remove(url);
                            notifyDataSetChanged();
                        }
                    })
                            .setNegativeButton("否", null)
                            .create().show();
                    return true;
                }
            });
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.itemView.getContext(), PhotoActivity.class);
                intent.putExtra("image", url);
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return photos.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        ImageView photo;
        public ViewHolder(View itemView) {
            super(itemView);
            photo = (ImageView) itemView.findViewById(R.id.ip_iv_photo);
        }
    }
}
