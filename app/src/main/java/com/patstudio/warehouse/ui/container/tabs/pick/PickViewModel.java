package com.patstudio.warehouse.ui.container.tabs.pick;

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
public final class PickViewModel extends ViewModel implements
        LifecycleObserver {

    @SuppressLint("StaticFieldLeak")
    private final Context context;
    private final OrderRepository orderRepository;
    private final MutableLiveData<Event<Resource<List<PickerOrderModel>>>> pickerOderLiveData = new MutableLiveData<>();
    private final MutableLiveData<Event<PickerOrderModel>> pickedResult = new MutableLiveData<>();
    private final MutableLiveData<Boolean>  loadingOrders = new MutableLiveData<>();
    private IPickItemListener pickItemListener;

    @SuppressLint("CheckResult")
    @Inject
    public PickViewModel(Application application, @NonNull OrderRepository orderRepository) {
        this.context = application;
        this.orderRepository = orderRepository;
    }

    public void setPickItemListener(IPickItemListener itemListener) {
        this.pickItemListener = itemListener;
    }

    @SuppressLint("CheckResult")
    private void getPickersOrders() {
        orderRepository.getPickerOrders(Constants.STATUS_PICK)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(start-> loadingOrders.postValue(Boolean.TRUE))
                .subscribe(result-> {
                            pickerOderLiveData.postValue(new Event<>(Resource.success(result)));
                            loadingOrders.postValue(Boolean.FALSE);
                        }, throwable -> {

                            if (throwable instanceof UnknownHostException) {
                                pickerOderLiveData.postValue(new Event<>(Resource.haveNoInternetConnection(context.getResources().getString(R.string.connect_server_error))));
                            }

                            if (throwable instanceof ConnectException) {
                                pickerOderLiveData.postValue(new Event<>(Resource.haveNoInternetConnection(context.getResources().getString(R.string.server_connection_error))));
                            }
                        }
                );
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    protected void onLifeCycleResume() {
        getPickersOrders();
    }

    public LiveData<Event<Resource<List<PickerOrderModel>>>> getPickerOrders() {
        return pickerOderLiveData;
    }

    @SuppressLint("CheckResult")
    public void selectStartItem(PickerOrderModel pickerOrderModel) {
        orderRepository.updatePickerOrderStatus(pickerOrderModel.getId(),Constants.STATUS_PICK)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(start-> loadingOrders.postValue(Boolean.TRUE))
                .subscribe(result-> {
                            pickedResult.postValue(new Event<>(result));
                        }, throwable -> {
                            if (throwable instanceof UnknownHostException)
                                pickerOderLiveData.postValue(new Event<>(Resource.haveNoInternetConnection(context.getResources().getString(R.string.connect_server_error))));
                            if (throwable instanceof ConnectException)
                                pickerOderLiveData.postValue(new Event<>(Resource.haveNoInternetConnection(context.getResources().getString(R.string.server_connection_error))));
                        }
                );
    }

    public LiveData<Event<PickerOrderModel>> getPickedResult() {
        return pickedResult;
    }

    public void selectItem(PickerOrderModel pickerOrderModel) {
        pickItemListener.selectItem(pickerOrderModel);
    }

    public LiveData<Boolean> loadingOrders() {
        return loadingOrders;
    }

}
