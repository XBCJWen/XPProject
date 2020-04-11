package com.jm.xpproject.bean;

import android.text.TextUtils;

/**
 * 将登录信息保存到缓存中
 *
 * @author jinXiong.Xie
 */
public class UserData {

    private UserData() {
    }

    private static UserData instance;

    public static UserData getInstance() {
        if (instance == null) {
            instance = new UserData();
        }
        return instance;
    }

    //以下为登录后返回的信息，需要重新覆盖下面字段
    private String ryToken;
    private String mobile;
    private String sessionId;
    private int type;
    private int fz;
    private String createTime;
    private String loginIp;
    private int id;
    private String email;
    private String account;
    private String nick;
    private String head;
    /**
     * 存储聊天室名字(自定义)
     */
    private String chatRoomName;

    /**
     * (暂时不知道需要哪些，需要再加上去)
     * "ryToken": null,
     * "mobile": "18826237020",
     * "sessionId": "889c5925467d4ff2adb08e65d26cb88d",
     * "type": 1,
     * "fz": 0,
     * "createTime": "2017-12-01 18:07:40",
     * "loginIp": "223.104.63.115",
     * "id": 16,
     * "email": null,
     * "account": null
     */

    public String getRyToken() {
        return ryToken;
    }

    public void setRyToken(String ryToken) {
        this.ryToken = ryToken;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getFz() {
        return fz;
    }

    public void setFz(int fz) {
        this.fz = fz;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getLoginIp() {
        return loginIp;
    }

    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getChatRoomName() {
        return chatRoomName;
    }

    public void setChatRoomName(String chatRoomName) {
        this.chatRoomName = chatRoomName;
    }

    public boolean isLogin() {
        return !TextUtils.isEmpty(getSessionId());
    }

    public void clear() {
        UserData userData = UserData.getInstance();
        userData.setSessionId(null);
        userData.setId(0);
        userData.setEmail(null);
        userData.setType(0);
        userData.setLoginIp(null);
        userData.setRyToken(null);
        userData.setFz(0);
        userData.setAccount(null);
        userData.setCreateTime(null);
        userData.setHead(null);
        userData.setNick(null);
        userData.setChatRoomName(null);
        userData.setMobile(null);
    }

    public void save(UserData userData1) {
        UserData userData = UserData.getInstance();
        userData.setSessionId(userData1.getSessionId());
        userData.setId(userData1.getId());
        userData.setEmail(userData1.getEmail());
        userData.setType(userData1.getType());
        userData.setLoginIp(userData1.getLoginIp());
        userData.setRyToken(userData1.getRyToken());
        userData.setFz(userData1.getFz());
        userData.setAccount(userData1.getAccount());
        userData.setCreateTime(userData1.getCreateTime());
        userData.setHead(userData1.getHead());
        userData.setNick(userData1.getNick());
        userData.setChatRoomName(userData1.getChatRoomName());
        userData.setMobile(userData1.getMobile());
    }
}