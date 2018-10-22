package com.tianya.android.wechat;

import com.tianya.android.wechat.entity.Account;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public final class AccountManager {

    private static final String TAG = "AccountManager";

    private static volatile AccountManager sInstance;

    private Queue<Account> mAccountQueue = new LinkedBlockingQueue<>();

    private AccountManager() {
        mAccountQueue.add(new Account("18065104745", 0));
        mAccountQueue.add(new Account("水电费", 0));
        mAccountQueue.add(new Account("1806510474336", 0));
        mAccountQueue.add(new Account("13850604260", 0));
        mAccountQueue.add(new Account("amm16520", 0));
//        mAccountQueue.add(new Account("amm16520", 0));
//        mAccountQueue.add(new Account("18065104746", 0));
//        mAccountQueue.add(new Account("13599055749", 0));
    }

    public static AccountManager instance() {
        if (sInstance == null) {
            synchronized (AccountManager.class) {
                if (sInstance == null) {
                    sInstance = new AccountManager();
                }
            }
        }
        return sInstance;
    }

    public boolean hasNext() {
        return mAccountQueue.size() != 0;
    }

    public Account peek() {
        if (mAccountQueue.size() == 0) {
            return null;
        }
        return mAccountQueue.peek();
    }

    public void remove() {
        mAccountQueue.remove();
    }

}
