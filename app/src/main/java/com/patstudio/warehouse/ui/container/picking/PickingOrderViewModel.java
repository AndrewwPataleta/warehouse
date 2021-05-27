package com.patstudio.warehouse.ui.container.picking;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.patstudio.warehouse.R;
import com.patstudio.warehouse.data.models.PickerOrderModel;
import com.patstudio.warehouse.data.models.Resource;
import com.patstudio.warehouse.data.order.OrderRepository;
import com.patstudio.warehouse.di.scopes.AppScoped;
import com.patstudio.warehouse.util.Event;

import java.net.ConnectException;
import java.net.UnknownHostException;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

@AppScoped
public final class PickingOrderViewModel extends ViewModel implements
        LifecycleObserver {

    @SuppressLint("StaticFieldLeak")
    private final Context context;;
    private OrderRepository orderRepository;
    private final MutableLiveData<Boolean> loadingOrders = new MutableLiveData<>();
    private final MutableLiveData<Event<Resource<List<PickerOrderModel>>>> pickerOderLiveData = new MutableLiveData<>();

    @SuppressLint("CheckResult")
    @Inject
    public PickingOrderViewModel(Application application, OrderRepository orderRepository) {
        this.context = application;
        this.orderRepository = orderRepository;
    }

    public LiveData<Event<Resource<List<PickerOrderModel>>>> getPickerOrders() {
        return pickerOderLiveData;
    }

    public LiveData<Boolean> loadingOrders() {
        return loadingOrders;
    }

}
