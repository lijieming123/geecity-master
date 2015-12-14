package net.bluemap.geecitypoperty.common;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import net.bluemap.geecitypoperty.R;

import java.util.Calendar;

import hz.toollib.util.DateTimeUtil;

/**
 * 日期和时间选择器
 * Created by LiuPeng on 2015/8/12.
 */
public class DateTimePicker extends LinearLayout {

    private static final String DATE_FORMAT = "yyyy-MM-dd";
    private static final String TIME_FORMAT = "HH : mm";
    Context mContext;
    public Calendar getCalendar() {
        return calendar;
    }

    //记录当前日期
    Calendar calendar;
    TextView tvDate;
    TextView tvTime;
    public DateTimePicker(Context context) {
        super(context);
        initPicker(context);
    }

    public DateTimePicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPicker(context);
    }

    /**
     * 初始化
     */
    private void initPicker(final Context context){
        this.mContext = context;
        //加载视图
        View view = LayoutInflater.from(context).inflate(R.layout.layout_datetime_picker,null);
        addView(view);
        tvDate = (TextView) view.findViewById(R.id.ldp_tv_date);
        tvTime = (TextView) view.findViewById(R.id.ldp_tv_time);
        //加载日历项，默认值为今天
        calendar = Calendar.getInstance();
        tvDate.setText(getDateText(DATE_FORMAT));
        tvTime.setText(getDateText(TIME_FORMAT));

        //点击事件
        tvDate.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        calendar.set(year,monthOfYear,dayOfMonth);
                        tvDate.setText(getDateText(DATE_FORMAT));
                    }
                },calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
                dialog.show();
            }
        });

        tvTime.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog dialog = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        calendar.set(Calendar.HOUR,hourOfDay);
                        calendar.set(Calendar.MINUTE,minute);
                        tvTime.setText(getDateText(TIME_FORMAT));
                    }
                },calendar.get(Calendar.HOUR),calendar.get(Calendar.MINUTE),false);
                dialog.show();
            }
        });
    }

    /**
     * 获取格式化的日期字符串
     * @param format 日期格式，传null为默认yyyy-MM-dd HH:mm:ss
     * @return 日期字符串，转换失败返回null
     */
    public String getDateText(String format){
        if(format == null){
            format = "yyyy-MM-dd HH:mm:ss";
        }
        //转化日期
        return DateTimeUtil.dateToString(calendar.getTime(),format);
    }

    public static final int MODE_DATE = 1;
    public static final int MODE_TIME = 2;
    public static final int MODE_DATETIME = 0;

    /**
     * 设置模式
     * @param mode 日期时间模式
     */
    public void setMode(int mode){
        if(mode == MODE_DATE){
            tvDate.setVisibility(VISIBLE);
            tvTime.setVisibility(View.GONE);
        }else if(mode == MODE_TIME){
            tvDate.setVisibility(View.GONE);
            tvTime.setVisibility(View.VISIBLE);
        }
    }

}
