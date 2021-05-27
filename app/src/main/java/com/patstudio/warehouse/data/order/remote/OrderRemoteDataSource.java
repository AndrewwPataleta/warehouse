package com.patstudio.warehouse.data.order.remote;

import androidx.annotation.NonNull;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.patstudio.warehouse.data.interfaces.OrderDataSource;
import com.patstudio.warehouse.data.models.PickerOrderModel;
import com.patstudio.warehouse.di.scopes.AppScoped;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;


@AppScoped
public class OrderRemoteDataSource implements OrderDataSource {

    @NonNull
    private OrderApiService mApiService;

    @Inject
    public OrderRemoteDataSource(@NonNull OrderApiService apiService) {
        mApiService = apiService;
    }

    @NonNull
    @Override
    public Single<List<PickerOrderModel>> getPickerOrders(String status) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("status", status);
        return mApiService.getPickerOrder(jsonObject);
    }

    @NonNull
    @Override
    public Single<PickerOrderModel> updatePickerOrderStatus(String id, String status) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("status", status);
        jsonObject.addProperty("id", id);
        return mApiService.updatePickerOrderStatus(jsonObject);
    }

    @NonNull
    @Override
    public Single<PickerOrderModel> getByOrderId(String orderId) {
        return mApiService.getPickerOrderById(orderId);
    }
}
