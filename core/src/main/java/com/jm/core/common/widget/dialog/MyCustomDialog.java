package com.jm.core.common.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Point;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.jm.core.R;
import com.jm.core.common.tools.base.PixelsTools;

/**
 * 万能自定义Dialog
 * <p>
 * 采用Builder链式调用, 传入layoutID与位置即可实现自定义Dialog
 *
 * @author ZhaoLei
 * @date 2018/09/08 16:55
 */

public class MyCustomDialog extends Dialog {

    private Context context;
    private View view;
    private boolean isClickOutSide;
    private int width;
    private int height;
    private int layoutX;
    private int layoutY;
    private DialogGravity gravity;
    private float alpha;

    /**
     * 设置Dialog布局排版
     *
     * @author xiejinxiong
     */
    public enum DialogGravity {
        LEFT_TOP, RIGHT_TOP, CENTER_TOP, CENTER, LEFT_BOTTOM, RIGHT_BOTTOM, CENTER_BOTTOM
    }

    private Display display;

    /**
     * Dialog的Window
     */
    private Window dialogWindow;

    /**
     * Dialog的布局数据
     */
    private WindowManager.LayoutParams dialogLayoutParams;


    public View getRootView() {
        return view;
    }

    /**
     * 设置视图宽高
     */
    private void setLayoutWidthHeight() {
        if (height != -1) {
            dialogLayoutParams.height = height;
        }

        if (width != -1) {
            dialogLayoutParams.width = width;
        } else {
            dialogLayoutParams.width = (int) (PixelsTools.getWidthPixels(context) * 0.8);
        }
        dialogWindow.setAttributes(dialogLayoutParams);
    }

    /**
     * 设置dialog的绝对定位
     */
    private void setLayoutXY() {
        dialogWindow.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.TOP);
        dialogLayoutParams.x = layoutX;
        dialogLayoutParams.y = layoutY;
        dialogWindow.setAttributes(dialogLayoutParams);
    }

    /**
     * 设置dialog的相对定位
     */
    private void setLayoutGravity() {
        switch (gravity) {
            case LEFT_TOP:
                dialogWindow.setGravity(Gravity.LEFT | Gravity.TOP);
                break;
            case RIGHT_TOP:
                dialogWindow.setGravity(Gravity.RIGHT | Gravity.TOP);
                break;
            case CENTER_TOP:
                dialogWindow.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.TOP);
                break;
            case CENTER:
                dialogWindow.setGravity(Gravity.CENTER);
                break;
            case LEFT_BOTTOM:
                dialogWindow.setGravity(Gravity.LEFT | Gravity.BOTTOM);
                break;
            case RIGHT_BOTTOM:
                dialogWindow.setGravity(Gravity.RIGHT | Gravity.BOTTOM);
                break;
            case CENTER_BOTTOM:
                dialogWindow.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM);
                break;
            default:
                break;
        }
    }

    /**
     * 初始化dialog
     */
    private void initDialog() {

        WindowManager windowManager = (WindowManager) getContext()
                .getSystemService(Context.WINDOW_SERVICE);
        display = windowManager.getDefaultDisplay();
        dialogWindow = getWindow();
        //设置背景透明度
        if (alpha != -1) {
            dialogWindow.setDimAmount(alpha);
        }
        dialogLayoutParams = dialogWindow.getAttributes();
        // 设置点击透明背景是否退出
        setCanceledOnTouchOutside(isClickOutSide);

        //设置视图的最小显示宽度
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        view.setMinimumWidth(width);
        //设置视图
        setContentView(view);

    }

    private MyCustomDialog(Builder builder) {
        super(builder.context, builder.themeResId);
        context = builder.context;
        view = builder.view;
        isClickOutSide = builder.isClickOutSide;
        width = builder.width;
        height = builder.height;
        layoutX = builder.layoutX;
        layoutY = builder.layoutY;
        gravity = builder.gravity;
        alpha = builder.alpha;

        createDialog();
    }

    private void createDialog() {

        initDialog();
        if (gravity != null) {
            setLayoutGravity();
        } else {
            setLayoutXY();
        }
        if (width != -1 || height != -1) {
            setLayoutWidthHeight();
        }
    }

    public static final class Builder {
        private Context context;
        private int themeResId;
        private View view;
        private boolean isClickOutSide;
        private int width;
        private int height;
        private int layoutX;
        private int layoutY;
        private DialogGravity gravity;
        private float alpha;

        public Builder(Context context) {
            this.context = context;

            getDefault();
        }

        /**
         * 获取默认值
         */
        private void getDefault() {

            themeResId = R.style.MyDialogStyle;
            view = null;
            isClickOutSide = false;
            width = -1;
            height = -1;
            layoutX = 0;
            layoutY = 0;
            gravity = null;
            alpha = -1;
        }

        /**
         * 设置自定义主题style
         *
         * @param themeResId styleID
         * @return
         */
        public Builder style(int themeResId) {
            this.themeResId = themeResId;
            return this;
        }

        /**
         * 设置Layout文件ID
         *
         * @param resView
         * @return
         */
        public Builder view(int resView) {
            this.view = LayoutInflater.from(context).inflate(resView, null);
            return this;
        }

        /**
         * 设置Layout
         *
         * @return
         */
        public Builder view(View view) {
            this.view = view;
            return this;
        }

        /**
         * 设置是否允许点击dialog外部关闭Dialog
         *
         * @param isClickOutSide
         * @return
         */
        public Builder isClickOutSide(boolean isClickOutSide) {
            this.isClickOutSide = isClickOutSide;
            return this;
        }

        /**
         * 设置dialog的绝对定位(如设置了gravity则不用设置)
         *
         * @param layoutX X坐标
         * @param layoutY Y坐标
         * @return
         */
        public Builder layoutXY(int layoutX, int layoutY) {
            this.layoutX = layoutX;
            this.layoutY = layoutY;
            return this;
        }

        /**
         * 设置dialog 视图宽度和高度
         *
         * @param width
         * @return
         */
        public Builder setWidthAndHeight(int width, int height) {
            this.width = width;
            this.height = height;
            return this;
        }

        /**
         * 设置dialog 视图高度
         *
         * @param height
         * @return
         */
        public Builder setHeight(int height) {
            this.height = height;
            return this;
        }

        /**
         * 设置dialog 视图宽度
         *
         * @param width
         * @return
         */
        public Builder setWidth(int width) {
            this.width = width;
            return this;
        }

        /**
         * 设置dialog视图外的背景透明度
         *
         * @param alpha 透明 0f ~ 1.0f 不透明
         * @return
         */
        public Builder alpha(float alpha) {
            this.alpha = alpha;
            return this;
        }

        /**
         * 设置dialog的相对定位(如设置了layoutXY则不用设置)
         *
         * @param gravity
         * @return
         */
        public Builder gravity(DialogGravity gravity) {
            this.gravity = gravity;
            return this;
        }

        /**
         * 设置Dialog布局里控件view的监听
         *
         * @param viewRes  控件ID
         * @param listener 监听事件
         * @return
         */
        public Builder viewOnclick(int viewRes, View.OnClickListener listener) {
            this.view.findViewById(viewRes).setOnClickListener(listener);
            return this;
        }

        /**
         * 获取Dialog布局里的控件
         *
         * @param viewRes 控件ID
         * @return
         */
        public View findViewById(int viewRes) {
            return this.view.findViewById(viewRes);
        }

        public MyCustomDialog build() {
            return new MyCustomDialog(this);
        }
    }

}
