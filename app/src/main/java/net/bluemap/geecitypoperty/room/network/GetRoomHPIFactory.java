package net.bluemap.geecitypoperty.room.network;

import android.content.Context;

import net.bluemap.geecitypoperty.common.ShareUtil;

/**
 * Created by LiuPeng on 2015/11/20.
 */
public class GetRoomHPIFactory {

    /**
     * 获取获取房间4级菜单接口类
     * @param level 层级，1=小区，2=楼座，3=单元，4=房间
     * @return
     */
    public static GetRoomLevel getRoomHPIInstance(Context context, int level, String courtId,String buildingId,String unitId){
        GetRoomLevel getRoomHPI;
        switch (level){
            case 1:
                getRoomHPI = new GetCourtsHPI(context);
                break;
            case 2:
                getRoomHPI = new GetBuildingHPI(context);
                break;
            case 3:
                getRoomHPI = new GetUnitHPI(context);
                break;
            case 4:
                getRoomHPI = new GetRoomHPI(context);
                break;
            default:
                getRoomHPI = new GetCourtsHPI(context);
                break;
        }
        getRoomHPI.setIds(ShareUtil.getInstance(context).getLoginInfo().getUserName(),courtId,buildingId,unitId);
        return getRoomHPI;
    }

    public static String GetLevelName(int level){
        switch (level){
            case 1:
            default:
                return "小区选择";
            case 2:
                return "楼座选择";
            case 3:
                return "单元选择";
            case 4:
                return "房间选择";
        }
    }
}
