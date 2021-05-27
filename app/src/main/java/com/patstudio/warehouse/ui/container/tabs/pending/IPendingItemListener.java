package com.patstudio.warehouse.ui.container.tabs.pending;

import com.patstudio.warehouse.data.models.PickerOrderModel;

public interface IPendingItemListener {

    void selectItem(PickerOrderModel pickerOrderModel);

    void selectContinue(PickerOrderModel pickerOrderModel);

    void selectCancel(PickerOrderModel pickerOrderModel);

}
