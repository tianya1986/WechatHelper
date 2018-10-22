package com.tianya.android.wechat.automator;

import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import com.tianya.android.wechat.entity.Account;
import com.tianya.android.wechat.service.AddFriendManager;
import com.tianya.android.wechat.util.AccessibilityHelper;
import com.tianya.android.wechat.util.Logger;

/**
 * 验证申请界面
 */
public class ValidateRequestAutomator extends DefaultAutomator {

    private static final String TAG = "ValidateAutomator";

    @Override
    protected boolean dispatchEvent(AccessibilityNodeInfo root, AccessibilityEvent event) {
        if (root != null) {
            if ("当前所在页面,验证申请".equals(root.getContentDescription())) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected void handlerEvent(AccessibilityNodeInfo root, AccessibilityEvent event) {
        Logger.d(TAG, "验证申请界面");
        Account account = AddFriendManager.instance().getAccountHanding(); // 获取当前处理中的帐号
        if (account == null) {
            Logger.e(TAG, "找不到正在处理中的帐号，请排查程序是否有异常");
            return;
        }

        AccessibilityNodeInfo sendValidateButton = AccessibilityHelper.findViewByViewId(root, "com.tencent.mm:id/hg"); // 发送验证按钮
        if (sendValidateButton != null) {
            Logger.d(TAG, "发送验证申请");
            AccessibilityHelper.sleep(1000);
            account.setStatus(Account.STATUS.SENDED);
            sendValidateButton.performAction(AccessibilityNodeInfo.ACTION_CLICK);
            return;
        }
    }

}
