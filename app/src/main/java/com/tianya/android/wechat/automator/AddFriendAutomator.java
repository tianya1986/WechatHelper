package com.tianya.android.wechat.automator;

import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import com.tianya.android.wechat.util.AccessibilityHelper;
import com.tianya.android.wechat.util.Logger;

/**
 * 添加朋友界面
 */
public class AddFriendAutomator extends DefaultAutomator {

    private static final String TAG = "AddFriendAutomator";

    @Override
    public boolean dispatchEvent(AccessibilityNodeInfo root, AccessibilityEvent event) {
        Logger.d(TAG, "dispatchEvent, event: " + event.toString());

        AccessibilityNodeInfo addFriendRadar = AccessibilityHelper.findViewByText(root, "雷达加朋友");
        return addFriendRadar != null;
    }

    @Override
    public void handlerEvent(AccessibilityNodeInfo root, AccessibilityEvent event) {
        Logger.d(TAG, "handlerEvent, event: " + event.toString());

        AccessibilityNodeInfo listView = AccessibilityHelper.findViewByViewId(root, "android:id/list"); // list列表控件
        if (listView != null) {
            int count = listView.getChildCount();
            Logger.e(TAG, "List view child count " + count);
            if (count > 3) {
                AccessibilityNodeInfo child2 = listView.getChild(1);
                child2.performAction(AccessibilityNodeInfo.ACTION_CLICK);
            }
        }

        AccessibilityNodeInfo inputNumberView = AccessibilityHelper.findViewByViewId(root, "com.tencent.mm:id/j2"); // 添加朋友界面-输入微信号控件
        if (inputNumberView != null) {
            Logger.d(TAG, "handlerEvent, start to input account.");
            inputNumberView.performAction(AccessibilityNodeInfo.ACTION_CLICK);

            inputNumberView.getParent().performAction(AccessibilityNodeInfo.ACTION_CLICK);
            inputNumberView.getParent().getParent().performAction(AccessibilityNodeInfo.ACTION_CLICK);
        }
    }


}
