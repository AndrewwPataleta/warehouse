package com.patstudio.warehouse.ui.container.tabs.pending;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ViewModel;

import com.patstudio.warehouse.R;
import com.patstudio.warehouse.data.models.PickerOrderModel;
import com.patstudio.warehouse.data.models.Resource;
import com.patstudio.warehouse.data.order.OrderRepository;
import com.patstudio.warehouse.di.scopes.AppScoped;
import com.patstudio.warehouse.util.Constants;
import com.patstudio.warehouse.util.Event;

import java.net.ConnectException;
import java.net.UnknownHostException;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

@AppScoped
public final class PendingViewModel extends ViewModel implements
        LifecycleObserver {

    @SuppressLint("StaticFieldLeak")
    private final Context context;
    private final OrderRepository orderRepository;
    private final MutableLiveData<Event<Resource<List<PickerOrderModel>>>>  pickerOderLiveData = new MutableLiveData<>();
    private final MutableLiveData<Event<PickerOrderModel>>  cancelPickerOrderModel = new MutableLiveData<>();
    private final MutableLiveData<Boolean>  loadingOrders = new MutableLiveData<>();
    private IPendingItemListener pendingItemListener;

    @SuppressLint("CheckResult")
    @Inject
    public PendingViewModel(Application application, @NonNull OrderRepository orderRepository) {
        this.context = application;
        this.orderRepository = orderRepository;
    }


    public void setPendingItemListener(IPendingItemListener pendingItemListener) {
        this.pendingItemListener = pendingItemListener;
    }

    @SuppressLint("CheckResult")
    private void getPendingOrders() {
        orderRepository.getPickerOrders(Constants.STATUS_PENDING)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(start-> loadingOrders.postValue(Boolean.TRUE))
                .subscribe(result-> {
                            pickerOderLiveData.postValue(new Event<>(Resource.success(result)));
                            loadingOrders.postValue(Boolean.FALSE);
                        }, throwable -> {
                            if (throwable instanceof UnknownHostException)
                                pickerOderLiveData.postValue(new Event<>(Resource.haveNoInternetConnection(context.getResources().getString(R.string.connect_server_error))));
                            if (throwable instanceof ConnectException)
                                pickerOderLiveData.postValue(new Event<>(Resource.haveNoInternetConnection(context.getResources().getString(R.string.have_no_internet_connection))));
                        }
                );
    }

    @SuppressLint("CheckResult")
    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    protected void onLifeCycleResume() {
       getPendingOrders();
    }

    public LiveData<Event<Resource<List<PickerOrderModel>>>> getPickerOrders() {
        return pickerOderLiveData;
    }

    public LiveData<Event<PickerOrderModel>> getCancelPickerOrder() {
        return cancelPickerOrderModel;
    }

    public LiveData<Boolean> loadingOrders() {
        return loadingOrders;
    }

    @SuppressLint("CheckResult")
    public void selectContinueItem(PickerOrderModel pickerOrderModel) {
        orderRepository.updatePickerOrderStatus(pickerOrderModel.getId(),Constants.STATUS_PENDING)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(start-> loadingOrders.postValue(Boolean.TRUE))
                .subscribe(result-> {
                            getPendingOrders();
                        }, throwable -> {
                            if (throwable instanceof UnknownHostException)
                                pickerOderLiveData.postValue(new Event<>(Resource.haveNoInternetConnection(context.getResources().getString(R.string.connect_server_error))));
                            if (throwable instanceof ConnectException)
                                pickerOderLiveData.postValue(new Event<>(Resource.haveNoInternetConnection(context.getResources().getString(R.string.have_no_internet_connection))));
                        }
                );
    }

    public void selectCancelItem(PickerOrderModel pickerOrderModel) {
        cancelPickerOrderModel.setValue(new Event<>(pickerOrderModel));
    }

    public void cancelPickerOrder(PickerOrderModel pickerOrderModel) {
        getPendingOrders();
    }

    public void selectItem(PickerOrderModel pickerOrderModel) {
   //    pendingItemListener.selectItem(pickerOrderModel);
    }

}
