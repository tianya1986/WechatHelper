package com.tianya.android.wechat;

public final class UIAutomatorManager {

    private static final String TAG = "UIAutomatorManager";

    private static volatile UIAutomatorManager sInstance;

    public static UIAutomatorManager instance() {
        if (sInstance == null) {
            synchronized (UIAutomatorManager.class) {
                if (sInstance != null) {
                    sInstance = new UIAutomatorManager();
                }
            }
        }
        return sInstance;
    }

    private boolean mStart;

    public boolean isStart() {
        return mStart;
    }

    public void setStart(boolean start) {
        mStart = start;
    }
}
