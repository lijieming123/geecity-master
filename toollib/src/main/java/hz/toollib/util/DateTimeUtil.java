package hz.toollib.util;

import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期时间工具类
 * @author LiuPeng
 *
 */
@SuppressLint("SimpleDateFormat") 
public class DateTimeUtil {

	/**
	 * 将日期转换成给定格式的字符串
	 * @param date 待转换时间，如果为null，则默认为现在时间
	 * @param format 目标格式，如果为null，则默认格式为yyyy-MM-dd HH:mm:ss
	 * @return 转换完成的字符串
	 */
	public static String dateToString(Date date, String format) {
		String toFormat = format == null ? "yyyy-MM-dd HH:mm:ss" : format;
		SimpleDateFormat simpleFormat = new SimpleDateFormat(toFormat);
		if(date == null){
			date = new Date();
		}
		return simpleFormat.format(date);
	}
	
	/**
	 * 按照格式转换日期字符串<br/>
	 * 如果出现异常，则原样返回数据
	 * @param fromFormat 原格式
	 * @param fromText 原日期字符串，必须符合原格式
	 * @param toFormat 目标格式
	 * @return 目标格式的日期字符串
	 */
	public static String formatDateTime(String fromFormat,String fromText,String toFormat) {
		SimpleDateFormat fromSDF = new SimpleDateFormat(fromFormat);
		SimpleDateFormat toSDK = new SimpleDateFormat(toFormat);
		try {
			Date date = fromSDF.parse(fromText);
			return toSDK.format(date);
		} catch (ParseException e) {
			e.printStackTrace();
			return fromText;
		}
	}
	
	/**
	 * 以 yyyy/MM/dd HH:mm:ss 格式转换日期字符串
	 * @param fromFormat 原格式
	 * @param fromText 符合原格式的日期字符串
	 * @return yyyy-MM-dd HH:mm:ss格式的日期字符串
	 */
	public static String formatDateTime(String fromFormat,String fromText){
		return formatDateTime(fromFormat, fromText, "yyyy-MM-dd HH:mm:ss");
	}
	
	/**
	 * 获取输入时间的时间段描述，如刚刚、N分钟前、N小时前等等<br/>
	 * 1分钟内显示刚刚<br/>
	 * 1小时内显示XX分钟前<br/>
	 * 1天内显示XX小时前<br/>
	 * 超过一天显示yyyy/MM/dd格式日期
	 * @param dateString 日期字符串的格式必须为yyyy-MM-dd HH:mm:ss
	 * @return 日期描述，或原始字符串
	 */
	public static String getTimeDescription(String dateString) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Date date = sdf.parse(dateString);
			Date nowDate = new Date();
			long diff = (nowDate.getTime() - date.getTime()) / 1000;
			if (diff < 60) {
				return "刚刚";
			}
			if (diff < 3600) {
				return (diff / 60) + "分钟前";
			}
			if (diff < 24 * 3600) {
				return (diff / 3600) + "小时前";
			}
			SimpleDateFormat returnSdf = new SimpleDateFormat("yyyy-MM-dd");
			return returnSdf.format(date);
		} catch (ParseException e) {
			e.printStackTrace();
			return dateString;
		}
	}

	/**
	 * 按照给定格式，将字符串转换为Date类型对象
	 * @param fromFormat 给定的日期格式
	 * @param fromString 给定的字符串
	 * @return Date对象，如果转换失败，返回null
	 */
	public static Date getDateFromString(String fromFormat,String fromString){
		SimpleDateFormat sdf = new SimpleDateFormat(fromFormat);
		try {
			return sdf.parse(fromString);
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * 从给定的格式的字符串中获取时间是上午还是下午
	 * @param fromFormat 时间格式
	 * @param fromString 时间字符串
	 * @return 0：上午、1：下午,-1：异常
	 */
	public static int getAMPMFromString(String fromFormat,String fromString){
		if(StringUtil.isEmpty(fromFormat)){
			return -1;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(fromFormat);
		Date date = null;
		try {
			date = sdf.parse(fromString);
		} catch (ParseException e) {
			e.printStackTrace();
			return -1;
		}
		return date.getHours() > 12 ? 1:0;
	}

	public static String getAMPMText(String fromFormat,String fromString){
		int ampm = getAMPMFromString(fromFormat,fromString);
		if(ampm == 0){
			return "AM";
		}else if(ampm == 1){
			return "PM";
		}else{
			return null;
		}
	}

}
