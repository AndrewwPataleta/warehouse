package com.patstudio.warehouse.data.order;

import androidx.annotation.NonNull;

import com.google.gson.JsonElement;
import com.patstudio.warehouse.data.interfaces.OrderDataSource;
import com.patstudio.warehouse.data.models.PickerOrderModel;
import com.patstudio.warehouse.di.scopes.AppScoped;
import com.patstudio.warehouse.util.ConnectivityUtils.OnlineChecker;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;


@AppScoped
public class OrderRepository implements OrderDataSource {

    private final OrderDataSource mOrderDataSource;
    private final OnlineChecker mOnlineChecker;

    @Inject
    public OrderRepository(OrderDataSource mOrderDataSource,
                           OnlineChecker onlineChecker) {
        this.mOrderDataSource = mOrderDataSource;
        mOnlineChecker = onlineChecker;
    }


    @NonNull
    @Override
    public Single<List<PickerOrderModel>> getPickerOrders(String status) {
        return mOrderDataSource.getPickerOrders(status);
    }

    @NonNull
    @Override
    public Single<PickerOrderModel> updatePickerOrderStatus(String id, String status) {
        return mOrderDataSource.updatePickerOrderStatus(id,status.toLowerCase());
    }

    @NonNull
    @Override
    public Single<PickerOrderModel> getByOrderId(String orderId) {
        return mOrderDataSource.getByOrderId(orderId);
    }
}
