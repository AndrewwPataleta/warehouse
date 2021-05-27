package com.patstudio.warehouse.ui.container;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.patstudio.warehouse.R;
import com.patstudio.warehouse.data.models.PickerOrderModel;
import com.patstudio.warehouse.data.user.UserRepository;
import com.patstudio.warehouse.di.scopes.AppScoped;
import com.patstudio.warehouse.util.Event;
import com.patstudio.warehouse.util.ScanUtil;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static com.patstudio.warehouse.ui.container.ActionContainerScreenModel.SHOW_DETAIL;
import static com.patstudio.warehouse.ui.container.ActionContainerScreenModel.SHOW_LOGIN;
import static com.patstudio.warehouse.ui.container.ActionContainerScreenModel.SHOW_MENU;
import static com.patstudio.warehouse.ui.container.ActionContainerScreenModel.SHOW_PICKING_ORDER;
import static com.patstudio.warehouse.ui.container.ActionContainerScreenModel.SHOW_SETTINGS;
import static com.patstudio.warehouse.ui.container.ActionContainerScreenModel.SHOW_TABS;

@AppScoped
public final class ContainerViewModel extends ViewModel {

    private final Context context;

    public MutableLiveData<String> toolbarTittle = new MutableLiveData<>();
    public MutableLiveData<String> LastSync = new MutableLiveData<>();
    public MutableLiveData<Boolean> needShowSync = new MutableLiveData<>();
    private MutableLiveData<ActionContainerScreenModel> actionScreenModelMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<PickerOrderModel> pickResult = new MutableLiveData<>();
    private MutableLiveData<Event<String>> mockBarcode = new MutableLiveData<>();
    private ActionContainerScreenModel actionContainerScreenModel;
    private UserRepository userRepository;
    private ScanUtil scanUtil;

    private void initLastSync() {
        LastSync.postValue(context.getResources().getString(R.string.last_synch_mock));
    }

    private void initToolbar() {
        toolbarTittle.postValue(context.getResources().getString(R.string.picking_status));
    }

    private void initStartPage() {
        actionContainerScreenModel = new ActionContainerScreenModel(SHOW_MENU);
        actionScreenModelMutableLiveData.setValue(actionContainerScreenModel);
    }

    @SuppressLint("CheckResult")
    @Inject
    public ContainerViewModel(Application application, UserRepository userRepository) {
        this.context = application;
        this.userRepository = userRepository;
        this.scanUtil = new ScanUtil(application);
        this.scanUtil.setScanMode(0);
        initToolbar();
        initStartPage();
        initLastSync();
    }

    public LiveData<ActionContainerScreenModel> getScreen() {
        return actionScreenModelMutableLiveData;
    }

    public LiveData<Boolean> getNeedShowSync() {
        return needShowSync;
    }

    public void selectSync(View view) {

    }

    public void selectMenu() {
        if (actionContainerScreenModel.getValue() != SHOW_MENU)  {
            toolbarTittle.setValue(context.getResources().getString(R.string.picking_status));
            actionScreenModelMutableLiveData.setValue(actionContainerScreenModel.setValue(SHOW_MENU));
            needShowSync.setValue(Boolean.FALSE);
        }
    }

    public void selectPickerOrderDetail(PickerOrderModel pickerOrderModel) {

//        actionContainerScreenModel.setItemId(pickerOrderModel.getId());
//        actionScreenModelMutableLiveData.setValue(actionContainerScreenModel.setValue(SHOW_DETAIL));
//        String batch = context.getResources().getString(R.string.batch_number);
//        if (pickerOrderModel.getBatch() == null) {
//            batch += context.getResources().getString(R.string.not_available);
//        } else {
//            batch += pickerOrderModel.getBatch();
//        }
//        toolbarTittle.setValue(batch);
//        needShowSync.setValue(Boolean.FALSE);

//        actionScreenModelMutableLiveData.setValue(actionContainerScreenModel.setValue(SHOW_PICKING_ORDER));
//        needShowSync.setValue(Boolean.FALSE);
    }

    public void selectMenuPickingPacking(View view) {
        toolbarTittle.setValue(context.getResources().getString(R.string.picking_status));
        actionScreenModelMutableLiveData.setValue(actionContainerScreenModel.setValue(SHOW_TABS));
        needShowSync.setValue(Boolean.TRUE);
    }

    public void selectSettings(View view) {
        toolbarTittle.setValue(context.getResources().getString(R.string.settings));
        actionScreenModelMutableLiveData.setValue(actionContainerScreenModel.setValue(SHOW_SETTINGS));
        needShowSync.setValue(Boolean.FALSE);
    }

    public LiveData<PickerOrderModel> getPickResult() {
        return pickResult;
    }

    public void selectLogout(View view) {
       logout();
    }

    @SuppressLint("CheckResult")
    private void logout() {
        userRepository.logout();
        actionScreenModelMutableLiveData.postValue(actionContainerScreenModel.setValue(SHOW_LOGIN));
    }

    public void pickingResult(PickerOrderModel pickerOrderModel) {
        pickResult.setValue(pickerOrderModel);
        needShowSync.setValue(Boolean.FALSE);
    }

    public LiveData<Event<String>> getMockBarcode() {
        return mockBarcode;
    }

    public void boarderReadyToScan() {
        scanUtil.scan();
    }

    public void boarderStopToScan() {
        scanUtil.stopScan();
    }

    public void needEan13() {
        mockBarcode.postValue(new Event<>("1234567891234"));
    }

    public void needCode128() {
        mockBarcode.postValue(new Event<>("12367891234"));
    }
}
