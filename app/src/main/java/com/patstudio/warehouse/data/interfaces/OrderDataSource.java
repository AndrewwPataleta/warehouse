package com.patstudio.warehouse.data.interfaces;

import androidx.annotation.NonNull;

import com.google.gson.JsonElement;
import com.patstudio.warehouse.data.models.PickerOrderModel;

import java.util.List;

import io.reactivex.Single;


public interface OrderDataSource {

    @NonNull
    Single<List<PickerOrderModel>> getPickerOrders(String status);

    @NonNull
    Single<PickerOrderModel> updatePickerOrderStatus(String id,String status);

    @NonNull
    Single<PickerOrderModel> getByOrderId(String orderId);

}
