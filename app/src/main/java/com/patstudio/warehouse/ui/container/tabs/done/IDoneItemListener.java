package com.patstudio.warehouse.ui.container.tabs.done;

import com.patstudio.warehouse.data.models.PickerOrderModel;

public interface IDoneItemListener {

    void selectItem(PickerOrderModel pickerOrderModel);

    void selectRevise(PickerOrderModel pickerOrderModel);


}
