package hz.toollib.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import hz.toollib.R;


/**
 * 等待加载view
 * @author LP
 *
 */
public class OnLoadingView extends LinearLayout {
	
	View v;
	private LinearLayout llLoading;
	private RelativeLayout rlError;
	
	public OnLoadingView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs);
		init(context);
	}

	public OnLoadingView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public OnLoadingView(Context context) {
		super(context);
		init(context);
	}
	
	private void init(Context context){
		v = LayoutInflater.from(context).inflate(R.layout.common_onloading, null);
		this.addView(v);
		//初始化UI
		llLoading = (LinearLayout)findViewById(R.id.opl_ll_loading);
		rlError = (RelativeLayout)findViewById(R.id.opl_rl_error);
	}
	
	public void setOnReloadClickListener(OnClickListener listener){
		this.rlError.setOnClickListener(listener);
	}
	
	/**
	 * 显示加载中
	 */
	public void showOnloading(){
		this.setVisibility(View.VISIBLE);
		this.llLoading.setVisibility(View.VISIBLE);
		this.rlError.setVisibility(View.GONE);
	}
	
	/**
	 * 显示加载错误
	 */
	public void showError(){
		this.setVisibility(View.VISIBLE);
		this.llLoading.setVisibility(View.GONE);
		this.rlError.setVisibility(View.VISIBLE);
	}
	
	/**
	 * 隐藏
	 */
	public void hide(){
		this.setVisibility(View.GONE);
	}

}
