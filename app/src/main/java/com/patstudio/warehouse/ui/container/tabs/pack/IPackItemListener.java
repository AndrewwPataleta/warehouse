package com.patstudio.warehouse.ui.container.tabs.pack;

import com.patstudio.warehouse.data.models.PickerOrderModel;

public interface IPackItemListener {

    void selectItem(PickerOrderModel pickerOrderModel);

    void selectPack(PickerOrderModel pickerOrderModel);

    void selectCancel(PickerOrderModel pickerOrderModel);

}
