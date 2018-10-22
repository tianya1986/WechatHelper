package com.tianya.android.wechat.entity;

public class Account {

    public Account(String id, int status) {
        this.id = id;
        this.status = status;
    }

    private String id;

    /**
     * 帐号状态
     * 0：未添加，1：已添加， 2：已发送加好友申请，3：帐号不存在
     */
    private int status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public static class STATUS {
        public static final int NO_ADD    = 0;
        public static final int ADDED     = 1;
        public static final int SENDED    = 2;
        public static final int NO_EXISTS = 3;
    }
}
