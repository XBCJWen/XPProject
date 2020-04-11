package com.jm.core.common.widget.toast;

import android.content.Context;
import android.widget.Toast;

/**
 * 自定义Toast
 *
 * @author jinXiong.Xie
 */
public class MyToast {

    private MyToast() {

    }

//    private static final Object SYNC_LOCK = new Object();
//    private static Toast mToast;
//    /**
//     * 上下文
//     */
//    private static WeakReference<Context> wrContext;

//    /**
//     * 获取toast环境，为toast加锁
//     */
//    private static void initToastInstance() {
//
//        if (mToast == null) {
//            synchronized (SYNC_LOCK) {
//                if (mToast == null) {
//                    mToast = Toast.makeText(wrContext.get(), "", Toast.LENGTH_SHORT);
//                }
//            }
//        }
//    }

    /**
     * 展示吐司
     *
     * @param context 环境
     * @param text    内容
     */
    public static void showToast(Context context, String text) {
//        if (wrContext == null || wrContext.get() == null) {
//            wrContext = new WeakReference<>(context);
//        }
//        if (text != null) {
//            initToastInstance();
//            mToast.setDuration(Toast.LENGTH_SHORT);
//            mToast.setText(text);
//            mToast.show();
//        }
        showToast(context, text, false);
    }

    /**
     * 显示Toast
     *
     * @param context      环境
     * @param text         内容
     * @param isLengthLong 是否长时间展示
     */
    public static void showToast(Context context, String text, boolean isLengthLong) {
        if (isLengthLong) {
            com.jm.core.common.widget.toast.Toast.makeText(context, text, Toast.LENGTH_LONG).show();
        } else {
            com.jm.core.common.widget.toast.Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
        }
//        if (wrContext == null || wrContext.get() == null) {
//            wrContext = new WeakReference<>(context);
//        }
//        if (text != null) {
//            if (isLength_Long) {
//                initToastInstance();
//                mToast.setDuration(Toast.LENGTH_LONG);
//                mToast.setText(text);
//                mToast.show();
//            } else {
//                showToast(context, text);
//            }
//        }

    }
}
