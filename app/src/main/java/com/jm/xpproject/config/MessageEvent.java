package com.jm.xpproject.config;

/**
 * 消息事件
 *
 * @author jinXiong.Xie
 */
public class MessageEvent {
    private static int messageIndex = 0;

    /**
     * 注册成功
     */
    public static final int REGISTER_SUCCESS = messageIndex++;

    /**
     * 找回密码成功
     */
    public static final int FIND_PSW_SUCCESS = messageIndex++;
    /**
     * 下载失败
     */
    public static final int DOWNLOAD_FAILED = messageIndex++;
    /**
     * 绑定手机号成功
     */
    public static final int BIND_MOBILE_SUCCESS = messageIndex++;
    /**
     * 支付成功
     */
    public static final int PAY_SUCCESS = messageIndex++;
    /**
     * 网络状态
     */
    public static final int NETWORK_STATE = messageIndex++;

    private int id;
    private Object[] message;

    public MessageEvent(int id, Object... message) {
        this.id = id;
        this.message = message;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Object[] getMessage() {
        return message;
    }

    public void setMessage(Object[] message) {
        this.message = message;
    }
}
