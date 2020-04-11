package com.jm.xpproject.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import com.alipay.sdk.app.PayTask;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.jm.api.bean.PayResult;
import com.jm.core.common.tools.utils.AndroidShare;
import com.jm.core.common.widget.toast.MyToast;
import com.jm.xpproject.config.MessageEvent;
import com.jm.xpproject.config.change.DataConfig;
import com.jm.xpproject.utils.xp.XPBaseUtil;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.Map;

/**
 * 支付工具类
 * <p>
 * 在支付界面监听EventBus的PAY_SUCCESS回调，进行后续操作
 *
 * @author jinXiong.Xie
 */

public class PayUtil extends XPBaseUtil {

    public PayUtil(Context context) {
        super(context);
    }

    /**
     * 支付宝支付
     */
    private static final int SDK_PAY_FLAG = 0;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG:
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    // 同步返回需要验证的信息
                    String resultInfo = payResult.getResult();
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        MyToast.showToast(getContext(), "支付成功");
//                        Toast.makeText(getContext(), "支付成功", Toast.LENGTH_SHORT).show();

                        EventBus.getDefault()
                                .post(new MessageEvent(MessageEvent.PAY_SUCCESS));
//            httpMemberAliNotifyUrl();
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        MyToast.showToast(getContext(), "支付失败");
//                        Toast.makeText(getContext(), "支付失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
                default:
                    break;
            }
        }
    };


    /**
     * 开启支付宝支付
     */
    public void startAlipayPay(final String orderInfo) {

        if (!AndroidShare.isAvilible(getContext(), "com.eg.android.AlipayGphone")) {
            showToast("请先安装支付宝后再试");
            return;
        }

        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask(getActivity());
                Map<String, String> result = alipay.payV2(orderInfo, true);
                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                handler.sendMessage(msg);
            }
        };
        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }


    /**
     * get the sdk version. 获取SDK版本号
     *
     */
    public void getSDKVersion() {
        PayTask payTask = new PayTask(getActivity());
        String version = payTask.getVersion();
        MyToast.showToast(getContext(), version);
//        Toast.makeText(getContext(), version, Toast.LENGTH_SHORT).show();
    }

    /**
     * 微信支付
     *
     * @param jsonObject
     */
    public void startWXPay(JSONObject jsonObject) {

        if (!AndroidShare.isAvilible(getContext(), "com.tencent.mm")) {
            showToast("请先安装微信后再试");
            return;
        }

        if (jsonObject == null) {
            return;
        }

        final IWXAPI msgApi = WXAPIFactory.createWXAPI(getContext(), null);
        // 将该app注册到微信
        msgApi.registerApp(DataConfig.WECHAT_APP_ID);

        PayReq request = new PayReq();
        request.appId = jsonObject.optString("appid");
        request.partnerId = jsonObject.optString("partnerid");
        request.prepayId = jsonObject.optString("prepayid");
        request.packageValue = jsonObject.optString("package");
        request.nonceStr = jsonObject.optString("noncestr");
        request.timeStamp = jsonObject.optString("timestamp");
        request.sign = jsonObject.optString("appid");
        msgApi.sendReq(request);
    }

    /**
     * 跳转到个人转账页面
     *
     * @param qrCode
     */
    private void openAliPayPay(String qrCode) {
        if (openAlipayPayPage(getContext(), qrCode)) {
//            Toast.makeText(this, "跳转成功", Toast.LENGTH_SHORT).show();
        } else {
//            Toast.makeText(this, "跳转失败", Toast.LENGTH_SHORT).show();
        }
    }

    public static boolean openAlipayPayPage(Context context, String qrcode) {
        try {
            qrcode = URLEncoder.encode(qrcode, "utf-8");
        } catch (Exception e) {
        }
        try {
            final String alipayqr = "alipayqr://platformapi/startapp?saId=10000007&clientVersion=3.7.0.0718&qrcode=" + qrcode;
            openUri(context, alipayqr + "%3F_s%3Dweb-other&_t=" + System.currentTimeMillis());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 发送一个intent
     *
     * @param context
     * @param s
     */
    private static void openUri(Context context, String s) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(s));
        context.startActivity(intent);
    }

}
