package com.tianya.android.wechat.automator;

import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import com.tianya.android.wechat.UIAutomatorException;
import com.tianya.android.wechat.util.AccessibilityHelper;
import com.tianya.android.wechat.util.Logger;
import com.tianya.android.wechat.wechat.ViewID;

/**
 * 首页自动机
 */
public class HomeAutomator extends DefaultAutomator {

    private static final String TAG = "HomeAutomator";

    @Override
    protected boolean dispatchEvent(AccessibilityNodeInfo root, AccessibilityEvent event) {
        Logger.d(TAG, "dispatchEvent, event: " + event.toString());

        AccessibilityNodeInfo listView = AccessibilityHelper.findViewByViewId(root, "com.tencent.mm:id/c9b");
        if (listView != null) {
            String className = (String) listView.getClassName();
            if ("android.widget.ListView".equals(className)) {
                return true;
            }
        }

        AccessibilityNodeInfo viewPager = AccessibilityHelper.findViewByViewId(root, "com.tencent.mm:id/b5a");
        return false;
    }

    @Override
    protected void handlerEvent(AccessibilityNodeInfo root, AccessibilityEvent event) {
        Logger.d(TAG, "handlerEvent, event: " + event.toString());

        try {
            autoClickMenuMore(root);
        } catch (UIAutomatorException e) {
            e.printStackTrace();
        }
    }

    /**
     * 自动点击右上角更多菜单
     */
    private void autoClickMenuMore(AccessibilityNodeInfo root) throws UIAutomatorException {
        Logger.d(TAG, "Auto click more menu. ");
        // 微信头部控件
        AccessibilityNodeInfo headView = AccessibilityHelper.findViewByViewId(root, ViewID.HOME_HEADER);
        if (headView == null) {
            throw new UIAutomatorException("Can not found head view, viewid: " + ViewID.HOME_HEADER);
        }

        Logger.d(TAG, "Head view child view size: " + headView.getChildCount());

        for (int i = 0; i < headView.getChildCount(); i++) {
            AccessibilityNodeInfo childView = headView.getChild(i);
            Logger.d(TAG, "Head view child view:  " + childView.toString());
        }

        // 增加好友按钮
        AccessibilityNodeInfo addView = headView.getChild(2);
        if (addView == null) {
            throw new UIAutomatorException("Can not found add view.");
        }

        addView.performAction(AccessibilityNodeInfo.ACTION_CLICK); // 触发点击时间
    }
}
