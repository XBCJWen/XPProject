package com.jm.xpproject.utils;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.jm.xpproject.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 时间选择器工具类
 *
 * @author jinXiong.Xie
 */

public class TimePickerUtil {

    private TimePickerView pvTime;
    private Context context;
    private TimePickerSelectListener timePickerSelectListener;

    public void setTimePickerSelectListener(TimePickerSelectListener timePickerSelectListener) {
        this.timePickerSelectListener = timePickerSelectListener;
    }

    public TimePickerUtil(Context context) {
        this.context = context;
        initTimePicker();
    }

    /**
     * 显示时间选择器
     */
    public void showTimePicker() {
        if (pvTime == null) {
            initTimePicker();
        }
        //弹出时间选择器，传递参数过去，回调的时候则可以绑定此view
        pvTime.show(false);
    }


    /**
     * 初始化时间选择器
     */
    private void initTimePicker() {
        //控制时间范围(如果不设置范围，则使用默认时间1900-2100年，此段代码可注释)
        //因为系统Calendar的月份是从0-11的,所以如果是调用Calendar的set方法来设置时间,月份的范围也要是从0-11
        Calendar selectedDate = Calendar.getInstance();

        Calendar startDate = Calendar.getInstance();
        startDate.set(1900, 0, 1);

//        Calendar endDate = Calendar.getInstance();
//        endDate.set(2019, 11, 28);
        //时间选择器
        pvTime = new TimePickerBuilder(context, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                // 这里回调过来的v,就是show()方法里面所添加的 View 参数，如果show的时候没有添加参数，v则为null
                /*btn_Time.setText(getTime(date));*/
                if (timePickerSelectListener != null) {
                    timePickerSelectListener.onTimeSelect(date, getTime(date), v);
                }
            }
        })
                .setLayoutRes(R.layout.pickerview_custom_time, new CustomListener() {

                    @Override
                    public void customLayout(View v) {
                        final TextView tvSubmit = (TextView) v.findViewById(R.id.tv_finish);
                        tvSubmit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvTime.returnData();
                                pvTime.dismiss();
                            }
                        });
                    }
                })
                .setType(new boolean[]{true, true, true, false, false, false})
//                .setLabel("", "", "", "", "", "") //设置空字符串以隐藏单位提示   hide label
                .setDividerColor(Color.DKGRAY)
                .setContentTextSize(15)
                .setDate(selectedDate)
                .setRangDate(startDate, selectedDate)
                .setTextColorCenter(context.getResources().getColor(R.color.black))
                .setDividerColor(context.getResources().getColor(R.color.transparent))
                .setGravity(Gravity.CENTER)
//                .isDialog(true)
//                .setDecorView(mFrameLayout)//非dialog模式下,设置ViewGroup, pickerView将会添加到这个ViewGroup中
                .setBackgroundId(0x00000000)
                .setOutSideCancelable(true)
                .build();

        //系统返回键监听屏蔽掉
        pvTime.setKeyBackCancelable(true);
    }


    /**
     * 更改时间样式
     *
     * @param date
     * @return
     */
    private String getTime(Date date) {
        //可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

    /**
     * 时间选择器选择回调
     */
    public interface TimePickerSelectListener {

        /**
         * 选择时间
         *
         * @param date
         * @param strDate
         * @param v
         */
        void onTimeSelect(Date date, String strDate, View v);
    }
}
