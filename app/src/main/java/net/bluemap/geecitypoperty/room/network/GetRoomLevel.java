package net.bluemap.geecitypoperty.room.network;

import android.content.Context;

import net.bluemap.geecitypoperty.common.HttpPostAPI;
import net.bluemap.geecitypoperty.common.model.KeyValueBean;

import java.util.List;

/**
 * Created by LiuPeng on 2015/11/20.
 */
public abstract class GetRoomLevel extends HttpPostAPI {

    public GetRoomLevel(Context context) {
        super(context);
    }

    abstract public void setIds(String userName,String courtId,String buildingId,String unitId);
    abstract public List<KeyValueBean> getList();

}
