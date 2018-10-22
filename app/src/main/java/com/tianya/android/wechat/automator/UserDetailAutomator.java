package com.tianya.android.wechat.automator;

import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import com.tianya.android.wechat.entity.Account;
import com.tianya.android.wechat.service.AddFriendManager;
import com.tianya.android.wechat.util.AccessibilityHelper;
import com.tianya.android.wechat.util.Logger;

/**
 * 用户详细资料界面
 */
public class UserDetailAutomator extends DefaultAutomator {

    private static final String TAG = "UserDetailAutomator";

    @Override
    protected void handlerEvent(AccessibilityNodeInfo root, AccessibilityEvent event) {
        Account account = AddFriendManager.instance().getAccountHanding(); // 获取当前处理中的帐号
        if (account == null) {
            Logger.e(TAG, "找不到正在处理中的帐号，请排查程序是否有异常");
            return;
        }

        /**
         * 根据当前处理的帐号状态，判断需要如何处理
         */
        if (account.getStatus() == Account.STATUS.NO_ADD) {
            AccessibilityNodeInfo addButton = findAddButton(root);
            if (addButton != null) {
                // 1. 找到“添加到通信录”按钮， 帐号未添加
                Logger.i(TAG, "用户详情界面，跳转到申请验证界面，帐号：" + account.getId());
                AccessibilityHelper.sleep(1000);
                addButton.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                return;
                // 不需要再往下处理了。
            }

            // 2. 找到"发消息"按钮，说明帐号已添加，设置当前帐号状态为：已添加
            AccessibilityNodeInfo sendMessage = findSendMessageButton(root);
            if (sendMessage != null) {
                account.setStatus(Account.STATUS.ADDED);
                Logger.i(TAG, "用户详情界面，已经添加过帐号：" + account.getId());
                onBack(root);
                return;
            } else {
                Logger.e(TAG, "找不到发消息按钮，请排查程序是否有异常");
            }
        } else if (account.getStatus() == Account.STATUS.SENDED) {
            // 帐号已经发送申请，目前程序没有判断用户是否同意，所以把帐号状态设置为已添加，并返回到输入帐号界面。
            Logger.d(TAG, "帐号已经发送申请，返回上一级。");
            account.setStatus(Account.STATUS.ADDED);
            onBack(root);
        }
    }

    @Override
    protected boolean dispatchEvent(AccessibilityNodeInfo root, AccessibilityEvent event) {
        AccessibilityNodeInfo sendMessage = findSendMessageButton(root);
        if (sendMessage != null) {
            return true;
        }
        AccessibilityNodeInfo addButton = findAddButton(root);
        if (addButton != null) {
            return true;
        }
        return false;
    }

    private AccessibilityNodeInfo findSendMessageButton(AccessibilityNodeInfo root) {
        AccessibilityNodeInfo accountEditText = AccessibilityHelper.findViewByViewId(root, "com.tencent.mm:id/ap1"); // 发送消息按钮
        if (accountEditText != null) {
            CharSequence value = accountEditText.getText();
            if ("发消息".equals(value)) {
                return accountEditText;
            }
        }
        return null;
    }

    private AccessibilityNodeInfo findAddButton(AccessibilityNodeInfo root) {
        AccessibilityNodeInfo accountEditText = AccessibilityHelper.findViewByViewId(root, "com.tencent.mm:id/ap0"); // 添加到通讯录按钮
        if (accountEditText != null) {
            CharSequence value = accountEditText.getText();
            if ("添加到通讯录".equals(value)) {
                return accountEditText;
            }
        }
        return null;
    }
}
