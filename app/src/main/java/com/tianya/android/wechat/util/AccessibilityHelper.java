package com.tianya.android.wechat.util;

import android.content.Context;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.accessibility.AccessibilityNodeInfo;

import com.tianya.android.wechat.UIAutomatorService;

import java.util.List;

public final class AccessibilityHelper {

    private final static String TAG = "AccessibilityHelper";

    /**
     * 是否开启无障碍功能
     */
    public static boolean isAccessibilityOpen(Context context) {
        int accessibilityEnabled = 0;
        final String service = context.getPackageName() + "/" + UIAutomatorService.class.getCanonicalName();
        try {
            accessibilityEnabled = Settings.Secure.getInt(context.getApplicationContext().getContentResolver(), android.provider.Settings.Secure.ACCESSIBILITY_ENABLED);
            Logger.v(TAG, "accessibilityEnabled = " + accessibilityEnabled);
        } catch (Settings.SettingNotFoundException e) {
            Logger.e(TAG, "Error finding setting, default accessibility to not found: " + e.getMessage());
        }
        TextUtils.SimpleStringSplitter mStringColonSplitter = new TextUtils.SimpleStringSplitter(':');
        if (accessibilityEnabled == 1) {
            Logger.v(TAG, "***ACCESSIBILITY IS ENABLED*** -----------------");
            String settingValue = Settings.Secure.getString(context.getApplicationContext().getContentResolver(), Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES);
            if (settingValue != null) {
                mStringColonSplitter.setString(settingValue);
                while (mStringColonSplitter.hasNext()) {
                    String accessibilityService = mStringColonSplitter.next();
                    Logger.v(TAG, "-------------- > accessibilityService :: " + accessibilityService + " " + service);
                    if (accessibilityService.equalsIgnoreCase(service)) {
                        Logger.v(TAG, "We've found the correct setting - accessibility is switched on!");
                        return true;
                    }
                }
            }
        } else {
            Logger.v(TAG, "***ACCESSIBILITY IS DISABLED***");
        }
        return false;
    }

    public static AccessibilityNodeInfo findViewByViewId(AccessibilityNodeInfo node, String viewId) {
        if (node == null || TextUtils.isEmpty(viewId)) {
            return null;
        }

        List<AccessibilityNodeInfo> list = node.findAccessibilityNodeInfosByViewId(viewId);
        if (list != null && list.size() > 0) {
            return list.get(0);
        }

        int count = node.getChildCount();
        if (count > 0) {
            for (int i = 0; i < count; i++) {
                AccessibilityNodeInfo child = node.getChild(i);

                AccessibilityNodeInfo target = findViewByViewId(child, viewId);
                if (target != null) {
                    return target;
                }
            }
        }
        return null;
    }

    public static AccessibilityNodeInfo findViewByClassName(AccessibilityNodeInfo node, String className) {
        if (node == null || TextUtils.isEmpty(className)) {
            return null;
        }
        int count = node.getChildCount();
        if (count > 0) {
            for (int i = 0; i < count; i++) {
                AccessibilityNodeInfo child = node.getChild(i);
                String viewName = (String) child.getClassName();
                if (className.equals(viewName)) {
                    return child;
                }

                AccessibilityNodeInfo target = findViewByClassName(child, className);
                if (target != null) {
                    return target;
                }
            }
        }
        return null;
    }

    /**
     * 查询控件text
     *
     * @param node
     * @param value
     * @return AccessibilityNodeInfo
     */
    public static AccessibilityNodeInfo findViewByText(AccessibilityNodeInfo node, String value) {
        if (node == null || TextUtils.isEmpty(value)) {
            return null;
        }
        List<AccessibilityNodeInfo> targetNode = node.findAccessibilityNodeInfosByText(value);
        if (targetNode != null && targetNode.size() > 0) {
            return targetNode.get(0);
        }
        int count = node.getChildCount();
        if (count > 0) {
            for (int i = 0; i < count; i++) {
                AccessibilityNodeInfo child = node.getChild(i);
                CharSequence text = child.getText();
                if (text != null) {
                    if (value.equals(text.toString())) {
                        return child;
                    }
                }

                AccessibilityNodeInfo target = findViewByText(child, value);
                if (target != null) {
                    return target;
                }
            }
        }
        return null;
    }

    /**
     * 查询控件 ContentDescription
     *
     * @param node
     * @param content
     * @return AccessibilityNodeInfo
     */
    public static AccessibilityNodeInfo findViewByDescription(AccessibilityNodeInfo node, String content) {
        if (node == null || TextUtils.isEmpty(content)) {
            return null;
        }
        int count = node.getChildCount();
        if (count > 0) {
            for (int i = 0; i < count; i++) {
                AccessibilityNodeInfo child = node.getChild(i);
                String text = (String) child.getContentDescription();
                if (content.equals(text)) {
                    return child;
                }

                AccessibilityNodeInfo target = findViewByDescription(child, content);
                if (target != null) {
                    return target;
                }
            }
        }
        return null;
    }

    public static void sleep(long seconds) {
        try {
            Thread.sleep(seconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void setText(AccessibilityNodeInfo node, String text) {
        Bundle arguments = new Bundle();
        arguments.putCharSequence(AccessibilityNodeInfo.ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE, text);
        node.performAction(AccessibilityNodeInfo.ACTION_SET_TEXT, arguments);
    }
}
