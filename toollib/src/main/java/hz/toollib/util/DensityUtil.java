package hz.toollib.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;

/**
 * 分辨率相关的工具类
 * @author LiuPeng
 *
 */
public class DensityUtil {

	/**
	 * dp转px
	 * @param context
	 * @param dpValue dp值
	 * @return px值
	 */
	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}
	
	/**
	 * px转dp
	 * @param context
	 * @param pxValue px值
	 * @return dp值
	 */
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}
	
	/**
	 * 获取状态栏高度<br/>
	 * 此方法不要在onCreate中调用，否则状态栏高度返回0
	 * @param activity 当前活动
	 * @return 状态栏高度
	 */
	public static int getStateBarHeight(Activity activity){
		Rect frame = new Rect();
		activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
		return frame.top;
	}
}