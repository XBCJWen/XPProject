package com.jm.core.common.widget.calendar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;

import com.jm.core.common.tools.base.DateUtil;
import com.jm.core.common.tools.base.PixelsTools;

/**
 * 日历类
 *
 * @author xiejinxiong
 */
public class MyCalendar extends View {

    /**
     * 使用枚举表示日期状态（今天、本月、非本月）
     *
     * @author xiejinxiong
     */
    public enum CalendarState {
        TODAY, CURRENT_MONTH, NO_CURRENT_MONTH
    }

    /**
     * 屏幕宽度
     */
    private int width;
    /**
     * 屏幕高度
     */
    private int height;
    /**
     * 日历数组
     */
    private int[][] dateNum;
    /**
     * 日历日期状态数组
     */
    private CalendarState[][] calendarStates;
    /**
     * 年
     */
    private int year;
    /**
     * 月
     */
    private int month;
    /**
     * 绘画类
     */
    private DrawCalendar drawCalendar;
    /**
     * 日历表格宽度
     */
    private float dateNumWidth;
    /**
     * 记录触发滑动的最小距离
     */
    private int touchSlop;
    /**
     * 点击的X轴坐标
     */
    private float touchX;
    /**
     * 点击的Y轴坐标
     */
    private float touchY;
    /**
     * 日历日期点击监听
     */
    private OnCalendarClickListener onCalendarClickListener;
    /**
     * 日历字体大小
     */
    private int fontSize = 15;
    /**
     * 本月字体颜色
     */
    private int currentMonthFontColor = Color.BLACK;
    /**
     * 非本月字体颜色
     */
    private int noCurrentMonthFontColor = Color.GRAY;
    /**
     * 今天字体颜色
     */
    private int todayFontColor = Color.WHITE;

    public MyCalendar(Context context) {
        super(context);
        initUI(context);
    }

    public MyCalendar(Context context, AttributeSet attrs) {
        super(context, attrs);
        initUI(context);
    }

    public MyCalendar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initUI(context);
    }

    /**
     * 设置日历字体颜色
     *
     * @param currentMonthFontColor   本月字体颜色
     * @param noCurrentMonthFontColor 非本月字体颜色
     * @param todayFontColor          今天字体颜色
     */
    public void setCalendarFontColor(int currentMonthFontColor,
                                     int noCurrentMonthFontColor, int todayFontColor) {

        this.currentMonthFontColor = currentMonthFontColor;
        this.noCurrentMonthFontColor = noCurrentMonthFontColor;
        this.todayFontColor = todayFontColor;
    }

    /**
     * 更新日历控件视图
     */
    public void updateCalendarView() {
        invalidate();
    }

    /**
     * 设置日历字体大小
     *
     * @param fontSize
     */
    public void setFontSize(int fontSize) {
        this.fontSize = PixelsTools.dip2Px(this.getContext(), fontSize);
//        this.fontSize = fontSize;
    }

    /**
     * 设置日历监听
     *
     * @param onCalendarClickListener
     */
    public void setOnCalendarClickListener(
            OnCalendarClickListener onCalendarClickListener) {
        this.onCalendarClickListener = onCalendarClickListener;
    }

    /**
     * 初始化UI
     *
     * @param context
     */
    private void initUI(Context context) {
        // 初始化日期
        year = DateUtil.getYear();
        month = DateUtil.getMonth();

        this.fontSize = PixelsTools.dip2Px(this.getContext(), fontSize);

        calendarStates = new CalendarState[6][7];

        drawCalendar = new DrawCalendar(year, month);

        touchSlop = ViewConfiguration.get(context).getScaledTouchSlop();

    }

    /**
     * 设置日历时间并刷新日历视图
     *
     * @param year
     * @param month
     */
    public void setYearMonth(int year, int month) {
        this.year = year;
        this.month = month;
        drawCalendar = new DrawCalendar(year, month);
        invalidate();
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // 记录点击的坐标
                touchX = event.getX();
                touchY = event.getY();
                break;
            case MotionEvent.ACTION_UP:
                float touchLastX = event.getX();
                float touchLastY = event.getY();
                // 判断是否符合正常点击
                if (Math.abs(touchLastX - touchX) < touchSlop
                        && Math.abs(touchLastY - touchY) < touchSlop) {
                    // 计算出所点击的数组序列
                    int dateNumX = (int) (touchLastX / dateNumWidth);
                    int dateNumY = (int) (touchLastY / dateNumWidth);
                    // 使用回调函数响应点击日历日期
                    if (onCalendarClickListener != null) {
                        onCalendarClickListener.onCalendarClick(
                                dateNum[dateNumY][dateNumX],
                                calendarStates[dateNumY][dateNumX]);
                    }
                    System.out.println(dateNum[dateNumY][dateNumX] + "  "
                            + calendarStates[dateNumY][dateNumX]);

                }
                break;
            default:
                break;
        }
        return true;
    }

    /**
     * 日历监听类
     *
     * @author xiejinxiong
     */
    public interface OnCalendarClickListener {

        /**
         * 日历日期点击监听
         *
         * @param dateNum       日期数字
         * @param calendarState 日期状态
         */
        void onCalendarClick(int dateNum, CalendarState calendarState);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // 获得控件宽度
        width = getMeasuredWidth();
        // 计算日历表格宽度
        dateNumWidth = width / 7.0f;
        // 计算日历高度
        height = (int) (dateNumWidth * 6);
        // 设置控件宽高
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawCalendar.drawCalendarCanvas(canvas);

    }

    /**
     * 封装绘画日历方法的绘画类
     *
     * @author xiejinxiong
     */
    class DrawCalendar {

        /**
         * 绘画日期画笔
         */
        private Paint mPaintText;
        /**
         * 绘画本日的蓝圆 背景的画笔
         */
        private Paint mPaintCircle;
        /**
         * 字体高度
         */
        private float fontHeight;

        public DrawCalendar(int year, int month) {
            // 获得月份日期排布数组
            dateNum = DateUtil.getMonthNumFromDate(year, month);
            // 初始化绘画文本的画笔
            mPaintText = new Paint();
            mPaintText.setTextSize(fontSize);
            // 设置灰色
            mPaintText.setColor(noCurrentMonthFontColor);
            // 设置画笔的锯齿效果。
            mPaintText.setAntiAlias(true);
            // 获得字体高度
            FontMetrics fm = mPaintText.getFontMetrics();
            fontHeight = (float) Math.ceil(fm.descent - fm.top) / 2;

            // 初始化绘画圆圈的画笔
            mPaintCircle = new Paint();
            // 设置蓝色
            mPaintCircle.setColor(Color.argb(100, 112, 199, 244));
            // 设置画笔的锯齿效果。
            mPaintCircle.setAntiAlias(true);
        }

        /**
         * 绘画日历
         *
         * @param canvas
         */
        public void drawCalendarCanvas(Canvas canvas) {
            // canvas.drawCircle(width/2, width/2, width/2, mPaint);// 画圆
            for (int i = 0; i < dateNum.length; i++) {
                for (int j = 0; j < dateNum[i].length; j++) {

                    // 上个月的日期
                    if (i == 0 && dateNum[i][j] > 20) {
                        drawCalendarCell(i, j, CalendarState.NO_CURRENT_MONTH,
                                canvas);
                        // 下个月的日期
                    } else if ((i == 5 || i == 4) && dateNum[i][j] < 20) {
                        drawCalendarCell(i, j, CalendarState.NO_CURRENT_MONTH,
                                canvas);
                    } else {// 本月日期
                        // 是否为今天的日期号
                        if (dateNum[i][j] == DateUtil.getCurrentMonthDay()) {
                            // 是否为今年今月
                            if (year == DateUtil.getYear()
                                    && month == DateUtil.getMonth()) {
                                drawCalendarCell(i, j, CalendarState.TODAY,
                                        canvas);
                                continue;
                            }
                        }
                        drawCalendarCell(i, j, CalendarState.CURRENT_MONTH,
                                canvas);
                    }
                }
            }
        }

        /**
         * 绘画日历表格
         *
         * @param i      横序号
         * @param j      列序号
         * @param state  状态
         * @param canvas 画布
         */
        private void drawCalendarCell(int i, int j, CalendarState state,
                                      Canvas canvas) {
            switch (state) {
                // 今天
                case TODAY:
                    calendarStates[i][j] = CalendarState.TODAY;
                    mPaintText.setColor(todayFontColor);
                    canvas.drawCircle(dateNumWidth * j + dateNumWidth / 2,
                            dateNumWidth * i + dateNumWidth / 2, dateNumWidth / 2,
                            mPaintCircle);
                    break;
                // 本月
                case CURRENT_MONTH:
                    calendarStates[i][j] = CalendarState.CURRENT_MONTH;
                    mPaintText.setColor(currentMonthFontColor);
                    break;
                // 非本月
                case NO_CURRENT_MONTH:
                    calendarStates[i][j] = CalendarState.NO_CURRENT_MONTH;
                    mPaintText.setColor(noCurrentMonthFontColor);
                    break;
                default:
                    break;
            }
            // 绘画日期
            canvas.drawText(dateNum[i][j] + "", dateNumWidth * j + dateNumWidth
                            / 2 - mPaintText.measureText(dateNum[i][j] + "") / 2,
                    dateNumWidth * i + dateNumWidth / 2 + fontHeight / 2.0f,
                    mPaintText);
        }
    }

}
