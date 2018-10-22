package com.tianya.android.wechat.automator;

import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import com.tianya.android.wechat.UIAutomatorException;
import com.tianya.android.wechat.util.AccessibilityHelper;
import com.tianya.android.wechat.util.Logger;

public abstract class DefaultAutomator implements Automator {

    private static final String TAG = "DefaultAutomator";

    /**
     * 事件处理方法
     *
     * @param root
     * @param event
     * @throws UIAutomatorException
     */
    protected abstract void handlerEvent(AccessibilityNodeInfo root, AccessibilityEvent event);

    @Override
    public boolean dispatch(AccessibilityNodeInfo root, AccessibilityEvent event) {
        if (root == null) {
            Logger.e(TAG, "Root node info is null. ");
            return false;
        }
        boolean consume = dispatchEvent(root, event);
        if (consume) {
            handlerEvent(root, event);
            return true;
        }
        return false;
    }

    protected abstract boolean dispatchEvent(AccessibilityNodeInfo root, AccessibilityEvent event);

    protected void onBack(AccessibilityNodeInfo root) {
        AccessibilityNodeInfo back = findBackButton(root);
        if (back != null) {
            back.performAction(AccessibilityNodeInfo.ACTION_CLICK);
        } else {
            Logger.e(TAG, "找不到返回按钮，异常。");
        }
    }

    /**
     * 查找返回按钮
     * 不同界面，按钮id不一样
     *
     * @return
     */
    private AccessibilityNodeInfo findBackButton(AccessibilityNodeInfo root) {
        AccessibilityNodeInfo back = AccessibilityHelper.findViewByViewId(root, "com.tencent.mm:id/hw"); // 搜索结果页面的返回按钮
        if (back != null) {
            return back;
        }
        back = AccessibilityHelper.findViewByViewId(root, "com.tencent.mm:id/hs"); // 个人资料详情页面的返回按钮
        if (back != null) {
            return back;
        }

        return null;
    }

}
