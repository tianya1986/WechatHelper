package com.tianya.android.wechat.automator;

import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import com.tianya.android.wechat.UIAutomatorException;
import com.tianya.android.wechat.util.AccessibilityHelper;
import com.tianya.android.wechat.util.Logger;

/**
 * 右上角更多菜单界面
 */
public class MenuAutomator extends DefaultAutomator {

    private static final String TAG = "MenuAutomator";

    @Override
    protected boolean dispatchEvent(AccessibilityNodeInfo root, AccessibilityEvent event) {
        Logger.d(TAG, "dispatchEvent, event: " + event.toString());
        AccessibilityNodeInfo addFriend = AccessibilityHelper.findViewByText(root, "添加朋友"); // 搜索“添加朋友”按钮
        return addFriend != null;
    }

    @Override
    protected void handlerEvent(AccessibilityNodeInfo root, AccessibilityEvent event) {
        Logger.d(TAG, "handlerEvent, event: " + event.toString());

        try {
            autoClickAdd(root);
        } catch (UIAutomatorException e) {
            e.printStackTrace();
        }
    }

    private void autoClickAdd(AccessibilityNodeInfo root) throws UIAutomatorException {
        Logger.d(TAG, "Auto click add. ");
        AccessibilityNodeInfo listView = AccessibilityHelper.findViewByClassName(root, "android.widget.ListView");
        if (listView == null) {
            throw new UIAutomatorException(AddFriendAutomator.class + ", Can not found list view.");
        }

        AccessibilityNodeInfo addView = listView.getChild(1); // 添加朋友
        if (addView == null) {
            throw new UIAutomatorException(AddFriendAutomator.class + ", Can not found add friend view.");
        }
        Logger.d(TAG, "Auto click add, view: " + addView.toString());
        addView.performAction(AccessibilityNodeInfo.ACTION_CLICK);
    }


}
