package com.patstudio.warehouse.ui.container.boarder;

public class ActionBoarderScreenModel {

    public static final int SHOW_TABS = 0;

    private final int mAction;

    public ActionBoarderScreenModel(int action) {
        mAction = action;
    }

    public int getValue() {
        return mAction;
    }

}
