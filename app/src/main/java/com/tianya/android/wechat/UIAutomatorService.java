package com.tianya.android.wechat;

import android.accessibilityservice.AccessibilityService;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import com.tianya.android.wechat.automator.AddFriendAutomator;
import com.tianya.android.wechat.automator.AddFriendInputAccountAutomator;
import com.tianya.android.wechat.automator.Automator;
import com.tianya.android.wechat.automator.HomeAutomator;
import com.tianya.android.wechat.automator.MenuAutomator;
import com.tianya.android.wechat.automator.UserDetailAutomator;
import com.tianya.android.wechat.automator.ValidateRequestAutomator;
import com.tianya.android.wechat.util.Logger;
import com.tianya.android.wechat.wechat.ClassName;

import java.util.ArrayList;
import java.util.List;

/**
 * 界面控制服务
 */
public class UIAutomatorService extends AccessibilityService {

    private static final String TAG = "UIAutomatorService";

    private List<Automator> mAutomator = new ArrayList<>();

    public UIAutomatorService() {
//        mAutomator.add(new AddFriendAutomator());
//        mAutomator.add(new HomeAutomator());
//        mAutomator.add(new MenuAutomator());
        mAutomator.add(new AddFriendInputAccountAutomator());
        mAutomator.add(new UserDetailAutomator());
        mAutomator.add(new ValidateRequestAutomator());
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        int eventType = event.getEventType();
        Logger.d(TAG, "onAccessibilityEvent, event: " + event.toString());

        if (eventType == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED || eventType == AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED || eventType == AccessibilityEvent.TYPE_WINDOWS_CHANGED) {
            for (Automator automator : mAutomator) {
                boolean dispatch = automator.dispatch(getRootInActiveWindow(), event);

                if (dispatch) {
                    Logger.d(TAG, "dispatch: " + dispatch + ", Automator name: " + automator.getClass().getName());
                    break;
                }
            }
        }
    }

    @Override
    public void onInterrupt() {

    }
}
