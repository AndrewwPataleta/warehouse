package com.patstudio.warehouse.ui.container.boarder;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.view.View;

import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.patstudio.warehouse.R;
import com.patstudio.warehouse.data.models.ItemModel;
import com.patstudio.warehouse.data.models.PickerOrderModel;
import com.patstudio.warehouse.data.models.Resource;
import com.patstudio.warehouse.data.order.OrderRepository;
import com.patstudio.warehouse.di.scopes.AppScoped;
import com.patstudio.warehouse.ui.auth.ActionLoginScreenModel;
import com.patstudio.warehouse.util.Constants;
import com.patstudio.warehouse.util.Event;

import java.net.ConnectException;
import java.net.UnknownHostException;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

@AppScoped
public final class BoarderViewModel extends ViewModel implements
        LifecycleObserver {

    @SuppressLint("StaticFieldLeak")
    private final Context context;;
    private OrderRepository orderRepository;
    private final MutableLiveData<Boolean> loadingOrders = new MutableLiveData<>();
    private final MutableLiveData<Event<Boolean>> needUpdate = new MutableLiveData<>();
    private final MutableLiveData<Event<ItemModel>> needUpdateItem = new MutableLiveData<>();
    private final MutableLiveData<Event<ItemModel>> needUpdateItemBin = new MutableLiveData<>();
    private final MutableLiveData<Event<Resource<PickerOrderModel>>> pickerOderLiveData = new MutableLiveData<>();
    public MutableLiveData<Event<ActionBoarderScreenModel>> actionBoarderScreenModelMutableLiveData = new MutableLiveData<>();
    private PickerOrderModel pickResult;
    private ItemModel currentItemModel;
    private List<ItemModel> itemModels;

    @SuppressLint("CheckResult")
    @Inject
    public BoarderViewModel(Application application, OrderRepository orderRepository) {
        this.context = application;
        this.orderRepository = orderRepository;
    }

    @SuppressLint("CheckResult")
    public void setPickResult(PickerOrderModel pickResult) {
        this.pickResult = pickResult;
        orderRepository.getByOrderId(pickResult.getId())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(start-> loadingOrders.postValue(Boolean.TRUE))
                .subscribe(result-> {
                            itemModels = result.getOrders().get(0).getItems();
                            pickerOderLiveData.postValue(new Event<>(Resource.success(result)));
                            loadingOrders.postValue(Boolean.FALSE);
                        }, throwable -> {
                            throwable.printStackTrace();
                            if (throwable instanceof UnknownHostException) {
                                pickerOderLiveData.postValue(new Event<>(Resource.haveNoInternetConnection(context.getResources().getString(R.string.connect_server_error))));
                            }
                            if (throwable instanceof ConnectException) {
                                pickerOderLiveData.postValue(new Event<>(Resource.haveNoInternetConnection(context.getResources().getString(R.string.server_connection_error))));
                            }
                        }
                );
    }

    public LiveData<Event<Resource<PickerOrderModel>>> getPickerOrders() {
        return pickerOderLiveData;
    }

    public LiveData<Event<Boolean>> needUpdate() {
        return needUpdate;
    }



    public void plusPickItem(ItemModel itemModel) {

        if (itemModel.quantity_picked +1 <= itemModel.quantity) {
            itemModel.quantity_picked += 1;
            needUpdate.postValue(new Event<>(Boolean.TRUE));
        }
    }

    public void selectEditItem(ItemModel itemModel) {
        needUpdateItem.postValue(new Event<>(itemModel));
    }

    public LiveData<Event<ItemModel>> getNeedUpdateItemQuantityPicked() {
        return needUpdateItem;
    }

    public LiveData<Event<ItemModel>> getNeedUpdateItemBinCount() {
        return needUpdateItemBin;
    }

    public void minusPickItem(ItemModel itemModel) {

        if (itemModel.quantity_picked -1 >= 0) {
            itemModel.quantity_picked += -1;
            needUpdate.postValue(new Event<>(Boolean.TRUE));
        }
    }

    public void setCurrentItemPosition(int position) {
        currentItemModel = itemModels.get(position);
    }

    public void selectDone() {
        actionBoarderScreenModelMutableLiveData.setValue(new Event<>(new ActionBoarderScreenModel(ActionBoarderScreenModel.SHOW_TABS)));
    }

    public void selectCancel() {
        actionBoarderScreenModelMutableLiveData.setValue(new Event<>(new ActionBoarderScreenModel(ActionBoarderScreenModel.SHOW_TABS)));
    }

    public LiveData<Event<ActionBoarderScreenModel>> getScreen() {
        return actionBoarderScreenModelMutableLiveData;
    }
    public void updateItemModelCount(ItemModel itemModel, String count) {
        itemModel.quantity_picked = Integer.valueOf(count);
        needUpdate.postValue(new Event<>(Boolean.TRUE));
    }

    public void updateItemModelCountBin(ItemModel itemModel, String count) {
        itemModel.setBin_count(Integer.valueOf(count));
        needUpdate.postValue(new Event<>(Boolean.TRUE));
    }

    public void setCurrentEAN13Barcode(String barcode) {
        needUpdateItemBin.postValue(new Event<>(currentItemModel));
    }

    public void setCurrentCode128Barcode(String barcode) {
        needUpdateItem.postValue(new Event<>(currentItemModel));
    }


    public LiveData<Boolean> loadingOrders() {
        return loadingOrders;
    }

}
