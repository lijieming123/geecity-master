package hz.toollib.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则表达式工具类
 * 
 * @author LiuPeng
 * 
 */
public class RegExpUtil {

	public RegExpUtil() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * 判断是否是邮件格式
	 * @param text 待判断字符串
	 * @return 是否为邮箱格式
	 */
	public static boolean isEmail(String text) {
		String str = "^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+\\.[a-zA-Z0-9_-]+$";
		Pattern p = Pattern.compile(str);
		Matcher m = p.matcher(text);
		return m.matches();
	}

	/**
	 * 判断是否是手机号格式
	 * @param text 待判断字符串
	 * @return 是否为手机号码格式
	 */
	public static boolean isPhoneNumber(String text) {
		String telRegex = "^1[3|5|7|8|][0-9]{9}";
		Pattern p = Pattern.compile(telRegex);
		Matcher m = p.matcher(text);
		return m.matches();
	}
	
	/**
	 * 判断是否是数字
	 * @param text 待判断字符串
	 * @return 是否为数字
	 */
	public static boolean isNumber(String text) {
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher match = pattern.matcher(text);
		return match.matches();
	}

}
