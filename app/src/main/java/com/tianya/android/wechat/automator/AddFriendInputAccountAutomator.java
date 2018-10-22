package com.tianya.android.wechat.automator;

import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import com.tianya.android.wechat.entity.Account;
import com.tianya.android.wechat.service.AddFriendManager;
import com.tianya.android.wechat.util.AccessibilityHelper;
import com.tianya.android.wechat.util.Logger;

/**
 * 添加好友输入号码界面
 *
 * 判断界面标准：
 * 是否有id为"com.tencent.mm:id/hz"的EditText
 */
public class AddFriendInputAccountAutomator extends DefaultAutomator {

    private static final String TAG = "InputAccountAutomator";

    @Override
    protected boolean dispatchEvent(AccessibilityNodeInfo root, AccessibilityEvent event) {
        AccessibilityNodeInfo accountEditText = findInputEditText(root);
        if (accountEditText != null) {
            return true;
        }

        // 当从用户详细资料返回（EditText已经输入帐号了），findInputEditText(root)返回为空
        AccessibilityNodeInfo node = AccessibilityHelper.findViewByViewId(root, "com.tencent.mm:id/bcq"); // 搜索结果界面
        if (node != null) {
            return true;
        }
        return false;
    }

    @Override
    protected void handlerEvent(AccessibilityNodeInfo root, AccessibilityEvent event) {
        Logger.d(TAG, "handlerEvent, event: " + event.toString());

        AccessibilityNodeInfo accountEditText = AccessibilityHelper.findViewByViewId(root, "com.tencent.mm:id/hz"); // 帐号输入框
        if (accountEditText != null) {
            // 获取下一个未添加的帐号
            Account account = next();
            if (account == null) {
                Logger.e(TAG, "帐号都添加完了，程序结束。");
                onBack(root);
                return;
            }

            Logger.d(TAG, "输入帐号: " + account.getId());
            AccessibilityHelper.setText(accountEditText, account.getId());

            // 此时，界面状态不会改变，也就是说系统不会再发“AccessibilityEvent”事件，所以这里需要等待"搜索结果界面"加载出来
            while (true) {
                AccessibilityHelper.sleep(1000);
                AccessibilityNodeInfo node = AccessibilityHelper.findViewByViewId(root, "com.tencent.mm:id/bcq"); // 搜索结果界面
                if (node != null) {
                    int count = node.getChildCount();
                    Logger.d(TAG, "Search result, count: " + count);
                    AccessibilityNodeInfo result = node.getChild(0);
                    if (result != null) {
                        // 点击
                        Logger.d(TAG, "点击搜索用户");
                        result.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                        break;
                    }
                } else {
                    Logger.e(TAG, "输入帐号，但是没有显示结果框，程序异常");
                    return;
                }
            }

            // 等待到搜索结果UI消失
            while (true) {
                AccessibilityHelper.sleep(1000);
                AccessibilityNodeInfo node = AccessibilityHelper.findViewByViewId(root, "com.tencent.mm:id/b0w"); //
                if (node == null) {
                    Logger.d(TAG, "查询到结果，搜索UI消失");
                    break;
                }
            }

            // 如果帐号搜索不到，当前页面会出现“该用户不存在”提示
            // 首先要搜索结果页面，然后从结果页面中搜索结果信息，直接从root查找结果信息，会查不到，奇怪！！！
            AccessibilityNodeInfo resultView = AccessibilityHelper.findViewByViewId(root, "com.tencent.mm:id/bci"); // 结果页面
            AccessibilityNodeInfo userNoExists = AccessibilityHelper.findViewByText(resultView, "该用户不存在");
            if (userNoExists != null) {
                Logger.d(TAG, "搜索不到用户，查找下一个用户");
                account.setStatus(Account.STATUS.NO_EXISTS);
                AccessibilityHelper.setText(accountEditText, "");
                return;
            }

            AccessibilityNodeInfo restrict = AccessibilityHelper.findViewByText(resultView, "操作过于频繁，请稍后再试");
            if (restrict != null) {
                Logger.e(TAG, "操作过于频繁，请稍后再试，退出添加好友界面");
                onBack(root);
                return;
            }
        }

    }

    /**
     * 查找帐号输入框，必须满足两条条件：
     * 1. 视图id 为"com.tencent.mm:id/hz"
     * 2. 视图text "微信号/QQ号/手机号"
     *
     * 因为在添加好友第一个界面，有个TextView的id也是为"com.tencent.mm:id/hz"
     *
     * @param root
     * @return
     */
    private AccessibilityNodeInfo findInputEditText(AccessibilityNodeInfo root) {
        AccessibilityNodeInfo accountEditText = AccessibilityHelper.findViewByViewId(root, "com.tencent.mm:id/hz"); // 帐号输入框
        if (accountEditText != null) {
            CharSequence value = accountEditText.getText();
            if ("微信号/QQ号/手机号".equals(value)) {
                return accountEditText;
            }
        }
        return null;
    }

    private Account next() {
        Account account = AddFriendManager.instance().getAccountHanding();
        if (account != null) {
            if (account.getStatus() != Account.STATUS.NO_ADD) {
                AddFriendManager.instance().remove();
                account = null;
            }
        }

        if (account == null) {
            while (AddFriendManager.instance().hasNext()) {
                account = AddFriendManager.instance().next();
                if (account != null && account.getStatus() == Account.STATUS.NO_ADD) {
                    break;
                }
            }
        }
        return account;
    }

}
