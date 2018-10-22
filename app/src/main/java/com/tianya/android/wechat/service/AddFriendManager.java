package com.tianya.android.wechat.service;

import com.tianya.android.wechat.AccountManager;
import com.tianya.android.wechat.entity.Account;

/**
 * 自动添加好友管理器
 */
public class AddFriendManager {

    private static AddFriendManager sInstance;

    /**
     * 处理中的帐号
     */
    private Account mAcountHanding;

    private AddFriendManager() {
    }

    public static AddFriendManager instance() {
        if (sInstance == null) {
            synchronized (AddFriendManager.class) {
                if (sInstance == null) {
                    sInstance = new AddFriendManager();
                }
            }
        }
        return sInstance;
    }

    public boolean hasNext() {
        return AccountManager.instance().hasNext();
    }

    public Account next() {
        return mAcountHanding = AccountManager.instance().peek();
    }

    public void remove() {
        AccountManager.instance().remove();
        mAcountHanding = null;
    }

    public Account getAccountHanding() {
        return mAcountHanding;
    }


}
