package net.bluemap.geecitypoperty.common;

import hz.toollib.util.DateTimeUtil;

/**
 * 通用工具类
 * Created by LiuPeng on 2015/8/11.
 */
public class Util {

    /**
     * 格式化为客户端格式的日期
     * @param dateText yyyy-MM-dd HH:mm:ss 格式的字符串
     * @return yyyy年M月d日格式的字符串
     */
    public static String getClientDate(String dateText){
        return DateTimeUtil.formatDateTime(Options.DATETIME_FORMAT_SERVER,dateText, Options.DATE_FORMAT_CLIENT);
    }

    /**
     * 格式化为客户端格式的日期
     * @param dateText yyyy-MM-dd HH:mm:ss 格式的字符串
     * @return yyyy年M月d日 HH:mm格式的字符串
     */
    public static String getClientDatetime(String dateText){
        return DateTimeUtil.formatDateTime(Options.DATETIME_FORMAT_SERVER,dateText, Options.DATETIME_FORMAT_CLIENT);
    }

    /**
     * 格式化为服务器格式的日期
     * @param dateText yyyy年M月d日 HH:mm格式的字符串
     * @return yyyy-MM-dd HH:mm:ss 格式的字符串
     */
    public static String getServerDatetime(String dateText){
        return DateTimeUtil.formatDateTime(Options.DATETIME_FORMAT_CLIENT,dateText, Options.DATETIME_FORMAT_SERVER);
    }

}
