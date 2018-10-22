package com.tianya.android.wechat;

public class UIAutomatorException extends Exception {

    public UIAutomatorException(String error) {
        super(error);
    }

    public UIAutomatorException(Throwable throwable) {
        super(throwable);
    }

    public UIAutomatorException(String error, Throwable throwable) {
        super(error, throwable);
    }
}
