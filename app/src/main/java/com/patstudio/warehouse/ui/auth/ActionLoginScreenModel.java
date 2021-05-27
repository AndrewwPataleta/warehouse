package com.patstudio.warehouse.ui.auth;

public class ActionLoginScreenModel {

    public static final int SHOW_MAIN = 0;
    public static final int SHOW_FORGOT_PASSWORD = 1;
    private final int mAction;

    public ActionLoginScreenModel(int action) {
        mAction = action;
    }

    public int getValue() {
        return mAction;
    }

}
