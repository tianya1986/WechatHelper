package com.tianya.android.wechat.automator;

import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import com.tianya.android.wechat.UIAutomatorException;

public interface Automator {

    /**
     * 事件分发方法
     *
     * @param root
     * @param event
     * @return true 代表事件被消费，不再往下分发
     * @throws UIAutomatorException
     */
    boolean dispatch(AccessibilityNodeInfo root, AccessibilityEvent event);
}
