package com.patstudio.warehouse.util;

import android.app.Activity;

import com.patstudio.warehouse.R;
import com.shreyaspatil.MaterialDialog.BottomSheetMaterialDialog;

public class MessageManager {

    private Activity activity;

    public MessageManager(Activity activity) {
        this.activity = activity;
    }

    public void showBottomMessage(String tittle, String message) {
        BottomSheetMaterialDialog mBottomSheetDialog = new BottomSheetMaterialDialog.Builder(activity)
                .setTitle(tittle)
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton(activity.getResources().getString(R.string.ok), (dialogInterface, which) -> {
                    dialogInterface.dismiss();

                })
                .build();
        mBottomSheetDialog.show();
    }

}
