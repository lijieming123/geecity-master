package hz.toollib.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.provider.Settings;
import android.telephony.TelephonyManager;

import java.lang.reflect.Method;

/**
 * 系统信息工具类，用于获取应用的包名、 版本信息等
 * @author LiuPeng
 *
 */
public class SystemInfoUtil {
	
	/**
	 * 获取应用包名
	 * @param context
	 * @return 包名，出现异常回传null
	 */
	public static String getPackageName(Context context) {
		PackageInfo info = null;
		try {
			info = context.getPackageManager().getPackageInfo(
					context.getPackageName(), 0);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
			return null;
		}
		return info.packageName;
	}
	
	/**
	 * 获取应用版本码
	 * @param context
	 * @return 版本码，出现异常回传0
	 */
	public static int getVersionCode(Context context){
		PackageInfo info = null;
		try {
			info = context.getPackageManager().getPackageInfo(
					context.getPackageName(), 0);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
			return 0;
		}
		return info.versionCode;
	}
	
	/**
	 * 获取应用版本名
	 * @param context
	 * @return 版本名，出现异常回传null
	 */
	public static String getVersionName(Context context){
		PackageInfo info = null;
		try {
			info = context.getPackageManager().getPackageInfo(
					context.getPackageName(), 0);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
			return null;
		}
		return info.versionName;
	}
	
	/**
	 * 获取设备id
	 * @param context
	 * @return 设备id，出现异常回传null
	 */
	public static String getDeviceId(Context context) {
		String id = null;
		try {
			Class<?> c = Class.forName("android.os.SystemProperties");
			Method get = c.getMethod("get", String.class, String.class);
			String serialnum = (String) (get
					.invoke(c, "ro.serialno", "unknown"));
			TelephonyManager telephonyManager;
			telephonyManager = (TelephonyManager) context
					.getSystemService(Context.TELEPHONY_SERVICE);
			String imeistring = telephonyManager.getDeviceId();
			String androidId = Settings.System.ANDROID_ID;
			id = serialnum + imeistring + androidId;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return id;
	}

}
