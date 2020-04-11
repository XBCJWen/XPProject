package com.jm.api.widget;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jm.api.R;
import com.jm.core.common.tools.base.PixelsTools;

/**
 * 自定义Dialog
 *
 * @author jinXiong.Xie
 */
public class MySpecificDialog extends Dialog {

    /** 屏幕高度像素 */
    // private int height;
    /**
     * 屏幕宽度像素
     */
    private int width;
    /**
     * 自定义对话框
     */
    private MySpecificDialog myDialog;
    /**
     * 对话框中的标题
     */
    private TextView tvTitle;
    /**
     * 对话框中的内容
     */
    private TextView tvMessage;
    /**
     * 对话框的内容布局
     */
    private LinearLayout llContent;
    /**
     * 对话框中的左键
     */
    private TextView tvLeft;
    /**
     * 对话框中的右键
     */
    private TextView tvRight;
    // /** 显示Dialog的Activity */
    // private Activity activity;
    /**
     * 上下文
     */
    private Context context;
    /**
     * dialog左右键的回调类
     */
    private MyDialogCallBack myDialogCallBack;
    /**
     * dialog中间键回调类
     */
    private MyDialogCenterCallBack myDialogCenterCallBack;

    /**
     * 主按钮位置
     */
    private int mainBtnDirection = LEFT;

    private static final int LEFT = 0;
    private static final int RIGHT = 1;

    public TextView getTvTitle() {
        return tvTitle;
    }

    public TextView getTvMessage() {
        return tvMessage;
    }

    public LinearLayout getLlContent() {
        return llContent;
    }

    public TextView getTvLeft() {
        return tvLeft;
    }

    public TextView getTvRight() {
        return tvRight;
    }

    private MySpecificDialog(Context context) {
        this(context, R.style.MyDialogStyle);
    }

    private MySpecificDialog(Context context, int theme) {
        super(context, theme);
        // this.activity = (Activity) context;
        this.context = context;
    }

//    /**
//     * 初始化屏幕像素
//     */
//    public void InitPixels() {
//        width = PixelsTools.getWidthPixels(context);
//        // DisplayMetrics metric = new DisplayMetrics();
//        // this.activity.getWindowManager().getDefaultDisplay().getMetrics(metric);
//        // height = metric.heightPixels;
//        // width = metric.widthPixels;
//    }

//    /**
//     * 初始化对话框（两个按钮）
//     *
//     * @param strTitle
//     * @param strMessage
//     * @param strLeft
//     * @param strRight
//     */
//    public MySpecificDialog initDialog(String strTitle, String strMessage, String strLeft, String strRight,
//                                       MyDialogCallBack myDialogCallBack) {
//
//        return initDialog(strTitle, strMessage, null, strLeft, strRight, myDialogCallBack);
//    }
//
//    /**
//     * 初始化对话框（两个按钮）
//     *
//     * @param strTitle
//     * @param view
//     * @param strLeft
//     * @param strRight
//     */
//    public MySpecificDialog initDialog(String strTitle, View view, String strLeft, String strRight,
//                                       MyDialogCallBack myDialogCallBack) {
//
//        return initDialog(strTitle, null, view, strLeft, strRight, myDialogCallBack);
//    }

    /**
     * 初始化对话框
     *
     * @param strTitle
     * @param view
     * @param strLeft
     * @param strRight
     */
    private MySpecificDialog initDialog(String strTitle, String strMessage, View view, String strLeft, String strRight) {

        // myDialog = new MyDialog(context);
        myDialog = this;
//        InitPixels();
        initDialogUI(this.context);
        initDialogInfo(strTitle, strMessage, view, strLeft, strRight);

        myDialog.setCanceledOnTouchOutside(false);

        return myDialog;
    }

    /**
     * 显示dialog
     */
    public void showDialog() {
        if (myDialog != null) {

            myDialog.setCancelable(true);
            myDialog.setCanceledOnTouchOutside(true);

            myDialog.show();
        }
    }

    /**
     * 显示dialog
     */
    public void showDialogOutSlideNoClose() {
        if (myDialog != null) {

            myDialog.setCancelable(false);
            myDialog.setCanceledOnTouchOutside(false);

            if (!((Activity)context).isFinishing()){
                myDialog.show();
            }
        }
    }

    public static class Builder {
        private final Context context;
        private String strMessage;
        private String strLeft;
        private String strRight;
        private String strCenter;
        private View view;
        private String strTitle;
        private int width;
        private MyDialogCallBack myDialogCallBack;
        private MyDialogCenterCallBack myDialogCenterCallBack;

        public Builder(Context context) {
            this.context = context;

            getDefault();
        }

        /**
         * 获取默认值
         */
        private void getDefault() {

            strMessage = "";
            strLeft = "";
            strRight = "";
            strCenter = "";
            view = null;
            strTitle = "";
            width = 0;
            myDialogCallBack = null;
            myDialogCenterCallBack = null;
        }

        public MySpecificDialog.Builder strMessage(String strMessage) {
            this.strMessage = strMessage;
            return this;
        }

        public MySpecificDialog.Builder strLeft(String strLeft) {
            this.strLeft = strLeft;
            return this;
        }

        public MySpecificDialog.Builder strRight(String strRight) {
            this.strRight = strRight;
            return this;
        }

        public MySpecificDialog.Builder strCenter(String strCenter) {
            this.strCenter = strCenter;
            return this;
        }

        public MySpecificDialog.Builder view(View view) {
            this.view = view;
            return this;
        }

        public MySpecificDialog.Builder strTitle(String strTitle) {
            this.strTitle = strTitle;
            return this;
        }

        public MySpecificDialog.Builder width(int width) {
            this.width = width;
            return this;
        }

        public MySpecificDialog.Builder myDialogCallBack(MyDialogCallBack myDialogCallBack) {
            this.myDialogCallBack = myDialogCallBack;
            return this;
        }

        public MySpecificDialog.Builder myDialogCenterCallBack(MyDialogCenterCallBack myDialogCenterCallBack) {
            this.myDialogCenterCallBack = myDialogCenterCallBack;
            return this;
        }


        public MySpecificDialog buildDialog() {
            MySpecificDialog mySpecificDialog = new MySpecificDialog(context);
            if (!TextUtils.isEmpty(strCenter)) {
                strLeft = strCenter;
            }

            mySpecificDialog.myDialogCallBack = myDialogCallBack;
            mySpecificDialog.myDialogCenterCallBack = myDialogCenterCallBack;

            if (width == 0) {
                width = (int) (PixelsTools.getWidthPixels(context) * 0.8);
            }
            mySpecificDialog.width = width;

            return mySpecificDialog.initDialog(strTitle, strMessage, view, strLeft, strRight);
//            return new MySpecificDialog(this);
        }

//        public MySpecificDialog buildCenterDialog() {
//            MySpecificDialog mySpecificDialog = new MySpecificDialog(context);
//            return mySpecificDialog.initDialog(strTitle, strMessage, view, strLeft, strRight, myDialogCallBack);
////            return new MySpecificDialog(this);
//        }
    }
//
//    /**
//     * 初始化对话框（两个按钮）
//     *
//     * @param strMessage
//     * @param strLeft
//     * @param strRight
//     */
//    public MySpecificDialog initDialog(String strMessage, String strLeft, String strRight,
//                                       MyDialogCallBack myDialogCallBack) {
//        return initDialog("", strMessage, strLeft, strRight, myDialogCallBack);
//    }
//
//    /**
//     * 初始化对话框（两个按钮）
//     *
//     * @param view
//     * @param strLeft
//     * @param strRight
//     */
//    public MySpecificDialog initDialog(View view, String strLeft, String strRight,
//                                       MyDialogCallBack myDialogCallBack) {
//        return initDialog("", view, strLeft, strRight, myDialogCallBack);
//    }
//
//    /**
//     * 初始化对话框（单个按钮）
//     *
//     * @param strTitle
//     * @param strMessage
//     * @param strCenter
//     * @param myDialogCenterCallBack
//     */
//    public MySpecificDialog initCenterDialog(String strTitle, String strMessage, String strCenter,
//                                             MyDialogCenterCallBack myDialogCenterCallBack) {
//
//        this.myDialogCenterCallBack = myDialogCenterCallBack;
//        return initDialog(strTitle, strMessage, strCenter, "", null);
//    }
//
//    /**
//     * 初始化对话框（单个按钮）
//     *
//     * @param strTitle
//     * @param view
//     * @param strCenter
//     * @param myDialogCenterCallBack
//     */
//    public MySpecificDialog initCenterDialog(String strTitle, View view, String strCenter,
//                                             MyDialogCenterCallBack myDialogCenterCallBack) {
//
//        this.myDialogCenterCallBack = myDialogCenterCallBack;
//        return initDialog(strTitle, view, strCenter, "", null);
//    }
//
//    /**
//     * 初始化对话框（单个按钮）
//     *
//     * @param strMessage
//     * @param strCenter
//     * @param myDialogCenterCallBack
//     */
//    public MySpecificDialog initCenterDialog(String strMessage, String strCenter,
//                                             MyDialogCenterCallBack myDialogCenterCallBack) {
//
//        return initCenterDialog("", strMessage, strCenter, myDialogCenterCallBack);
//    }
//
//    /**
//     * 初始化对话框（单个按钮）
//     *
//     * @param view
//     * @param strCenter
//     * @param myDialogCenterCallBack
//     */
//    public MySpecificDialog initCenterDialog(View view, String strCenter,
//                                             MyDialogCenterCallBack myDialogCenterCallBack) {
//
//        return initCenterDialog("", view, strCenter, myDialogCenterCallBack);
//    }

    /**
     * 初始化UI
     */
    private void initDialogUI(Context context) {

        View view = LayoutInflater.from(context).inflate(
                R.layout.dialog_layout, null, false);
        initDialogListener(view);

        Window window = myDialog.getWindow();
        window.setContentView(view);
        // 获取对话框当前的参数值
        WindowManager.LayoutParams p = window.getAttributes();
        p.height = LayoutParams.WRAP_CONTENT;
        p.width = width;
        window.setAttributes(p);
    }

    /**
     * 初始化普通对话框并显示内容
     *
     * @param view
     */
    private void initDialogListener(View view) {
        // 对话框中的内容
        tvMessage = (TextView) view.findViewById(R.id.tv_message);
        llContent = (LinearLayout) view.findViewById(R.id.ll_content);
        // 对话框中的标题
        tvTitle = (TextView) view.findViewById(R.id.tv_title);

        // 对话框中的左键
        tvLeft = (TextView) view.findViewById(R.id.tv_left);

        // 对话框中的右键
        tvRight = (TextView) view.findViewById(R.id.tv_right);

        //左右键监听
        tvLeft.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // dialogLeftBtnFunction();
                if (myDialogCallBack != null) {
                    myDialogCallBack.onLeftBtnFun(MySpecificDialog.this);
                }

//                myDialog.dismiss();
            }
        });

        tvRight.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // dialogRightBtnFunction();
                if (myDialogCallBack != null) {
                    myDialogCallBack.onRightBtnFun(MySpecificDialog.this);
                }

//                myDialog.dismiss();
            }
        });

        //有左右键
        if (myDialogCallBack != null) {

        } else {
            //只有中键
            switch (mainBtnDirection) {
                case LEFT:
                    tvRight.setVisibility(View.GONE);
                    tvLeft.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            myDialogCenterCallBack.onCenterBtnFun(MySpecificDialog.this);
                        }
                    });
                    break;
                case RIGHT:
                    tvLeft.setVisibility(View.GONE);
                    tvRight.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            myDialogCenterCallBack.onCenterBtnFun(MySpecificDialog.this);
                        }
                    });
                    break;
                default:
            }

            View viewCenter = view.findViewById(R.id.view_center);
            viewCenter.setVisibility(View.GONE);
        }
    }

    /**
     * 关闭dialog
     */
    public void closeDialog() {
        if (myDialog != null) {
            myDialog.dismiss();
        }
    }

    /**
     * 设置dialog的信息
     *
     * @param strTitle
     * @param strMessage
     * @param view
     * @param strLeft
     * @param strRight
     */
    private void initDialogInfo(String strTitle, String strMessage, View view, String strLeft,
                                String strRight) {
        if (TextUtils.isEmpty(strTitle)) {
            tvTitle.setVisibility(View.GONE);
        } else {
            tvTitle.setVisibility(View.VISIBLE);
            tvTitle.setText(strTitle);
        }
        if (TextUtils.isEmpty(strMessage)) {
            tvMessage.setVisibility(View.GONE);
            llContent.setVisibility(View.VISIBLE);

            if (view != null) {
                llContent.addView(view);
            }
        } else {
            tvMessage.setVisibility(View.VISIBLE);
            llContent.setVisibility(View.GONE);
        }
        tvMessage.setText(strMessage);
        tvLeft.setText(strLeft);
        tvRight.setText(strRight);

        //只有中键的情况
        if (TextUtils.isEmpty(strRight)) {
            switch (mainBtnDirection) {
                case LEFT:
                    tvLeft.setText(strLeft);
                    break;
                case RIGHT:
                    tvRight.setText(strLeft);
                    break;
                default:
            }
        }
    }


    /**
     * 用于实现dialog左右按钮的点击功能
     *
     * @author xiejinxiong
     */
    public interface MyDialogCallBack {

        /**
         * dialog左键功能
         */
        void onLeftBtnFun(Dialog dialog);

        /**
         * dialog右键功能
         */
        void onRightBtnFun(Dialog dialog);
    }

    /**
     * 用于实现dialog中间键按钮的点击功能
     *
     * @author xiejinxiong
     */
    public interface MyDialogCenterCallBack {

        /**
         * dialog中间键功能
         */
        void onCenterBtnFun(Dialog dialog);

    }

}
