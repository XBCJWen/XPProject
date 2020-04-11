package com.jm.xpproject.base.impl;

import com.jm.xpproject.config.MessageEvent;

/**
 * EventBus接口
 *
 * @author jinXiong.Xie
 */
public interface EventBusInterface {

    /**
     * 注册
     */
    void registerEventBus();

    /**
     * 解绑
     */
    void unregisterEventBus();

    /**
     * 推送消息
     *
     * @param eventId
     * @param message
     */
    void postEvent(int eventId, Object... message);

    /**
     * 事件回调
     *
     * @param event
     */
    void onEventCallBack(MessageEvent event);

}
