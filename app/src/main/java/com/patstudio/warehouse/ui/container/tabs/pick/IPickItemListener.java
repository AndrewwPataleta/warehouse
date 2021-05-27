package com.patstudio.warehouse.ui.container.tabs.pick;

import com.patstudio.warehouse.data.models.PickerOrderModel;

public interface IPickItemListener {

    void selectItem(PickerOrderModel pickerOrderModel);

    void selectStart(PickerOrderModel pickerOrderModel);

}
