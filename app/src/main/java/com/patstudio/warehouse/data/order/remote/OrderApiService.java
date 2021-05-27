package com.patstudio.warehouse.data.order.remote;


import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.patstudio.warehouse.data.models.PickerOrderModel;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;


public interface OrderApiService {

    @GET("api/picking_orders")
    Single<List<PickerOrderModel>> getPickerOrder(@Body JsonObject jsonObject);

    @POST("api/picking_orders/status")
    Single<PickerOrderModel> updatePickerOrderStatus(@Body JsonObject jsonObject);

    @GET("api/picking_orders/{order_id}")
    Single<PickerOrderModel> getPickerOrderById(@Path("order_id") String orderId);
}
