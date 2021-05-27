package com.patstudio.warehouse.ui.splash;

public class ActionSplashScreenModel {

    public static final int SHOW_LOGIN = 0;
    public static final int SHOW_MAIN = 1;
    private final int mAction;

    public ActionSplashScreenModel(int action) {
        mAction = action;
    }

    public int getValue() {
        return mAction;
    }

}
