package com.patstudio.warehouse.ui.container;

public class ActionContainerScreenModel {

    public static final int SHOW_MENU = 0;
    public static final int SHOW_TABS = 1;
    public static final int SHOW_DETAIL = 2;
    public static final int SHOW_SETTINGS = 3;
    public static final int SHOW_LOGIN = 4;
    public static final int SHOW_PICKING_ORDER = 5;
    private String itemId;

    private  int mAction;

    public ActionContainerScreenModel(int action) {
        mAction = action;
    }

    public int getValue() {
        return mAction;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public ActionContainerScreenModel setValue(int value) {
        mAction = value;
        return this;
    }

}
