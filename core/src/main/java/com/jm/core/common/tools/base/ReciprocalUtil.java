package com.jm.core.common.tools.base;

import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

/**
 * 轮询工具类
 *
 * @author jinXiong.Xie
 */

public class ReciprocalUtil {

    /**
     * 用于标识倒数
     */
    private final int RECIPROCAL = 0;
    /**
     * 用于标识循环
     */
    private final int CYCLE = 1;
    /**
     * 用于标识心跳循环
     */
    private final int CYCLE_HEART_BEAT = 2;

    /**
     * 记录倒计时
     */
    private int codeNum;
    /**
     * 隔多久循环一次
     */
    private int cycleDelayTime;
    /**
     * 获取验证码回调
     */
    private OnGetCodeCallBack reciprocalCallBack;
    /**
     * 循环回调
     */
    private OnCycleCallBack cycleCallBack;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
//      super.handleMessage(msg);
            switch (msg.what) {
                case RECIPROCAL:
                    if (codeNum > 0) {
                        if (reciprocalCallBack != null) {
                            reciprocalCallBack.onCode(codeNum--);
                        }
                        handler.sendEmptyMessageDelayed(RECIPROCAL, 1000);
                    } else {
                        if (reciprocalCallBack != null) {
                            reciprocalCallBack.onFinish();
                        }
                    }
                    break;
                case CYCLE:
                    if (cycleCallBack != null) {
                        cycleCallBack.onCycle();
                        handler.sendEmptyMessageDelayed(CYCLE, cycleDelayTime);
                    }
                    break;
                case CYCLE_HEART_BEAT:
                    if (cycleCallBack != null) {
                        cycleCallBack.onCycle();
                        long time = (long) (min + (Math.random() * (max - min + 1)) * 1000);
                        handler.sendEmptyMessageDelayed(CYCLE_HEART_BEAT, time);
//                        Log.e("LOG", "handleMessage: " + time);
                    }
                    break;
                default:
                    break;
            }
        }
    };

    /**
     * 心跳最低数
     */
    private int min = 1;
    /**
     * 心跳最高数
     */
    private int max = 10;

    /**
     * 倒数
     *
     * @param codeNum
     * @param callBack
     */
    public void reciprocal(int codeNum, OnGetCodeCallBack callBack) {
        this.codeNum = codeNum;
        this.reciprocalCallBack = callBack;

        if (callBack != null) {
            callBack.onStart();
        }
        handler.sendEmptyMessage(RECIPROCAL);

    }

    /**
     * 循环
     *
     * @param cycleDelayTime
     * @param callBack
     */
    public void cycle(int cycleDelayTime, OnCycleCallBack callBack) {
        this.cycleDelayTime = cycleDelayTime;
        this.cycleCallBack = callBack;

        if (callBack != null) {
            callBack.onStart();
        }
        handler.sendEmptyMessage(CYCLE);

    }

    /**
     * 心跳循环
     *
     * @param callBack
     */
    public void cycleHeartBeat(OnCycleCallBack callBack) {
        this.cycleCallBack = callBack;

        if (callBack != null) {
            callBack.onStart();
        }
        handler.sendEmptyMessage(CYCLE_HEART_BEAT);

    }

    /**
     * 心跳循环
     *
     * @param min
     * @param max
     * @param callBack
     */
    public void cycleHeartBeat(int min, int max, OnCycleCallBack callBack) {
        this.min = min;
        this.max = max;
        cycleHeartBeat(callBack);
    }

    /**
     * 获取验证码
     *
     * @param codeNum
     * @param btnGetCode
     */
    public <T extends TextView> void getCode(int codeNum, final T btnGetCode) {
        reciprocal(codeNum, new OnGetCodeCallBack() {
            @Override
            public void onStart() {

            }

            @Override
            public void onCode(int num) {
                btnGetCode.setText(num + "s");
                btnGetCode.setEnabled(false);
            }

            @Override
            public void onFinish() {
                btnGetCode.setText("获取验证码");
                btnGetCode.setEnabled(true);
            }
        });
    }


    /**
     * 关闭倒数
     */
    public void closeReciprocal() {
        if (handler.hasMessages(RECIPROCAL)) {
            handler.removeMessages(RECIPROCAL);
        }
    }

    /**
     * 关闭循环
     */
    public void closeCycle() {
        if (handler.hasMessages(CYCLE)) {
            handler.removeMessages(CYCLE);
        }
    }

    /**
     * 关闭全部
     */
    public void closeAll() {
        closeReciprocal();
        closeCycle();
    }

    /**
     * 循环回调
     *
     * @author jinXiong.Xie
     */

    public interface OnCycleCallBack {

        void onStart();

        void onCycle();
    }

    /**
     * 获取验证码回调
     *
     * @author jinXiong.Xie
     */

    public interface OnGetCodeCallBack {

        void onStart();

        void onCode(int num);

        void onFinish();
    }

}
