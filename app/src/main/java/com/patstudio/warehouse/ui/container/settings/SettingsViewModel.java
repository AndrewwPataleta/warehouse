package com.patstudio.warehouse.ui.container.settings;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.webkit.URLUtil;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ViewModel;

import com.patstudio.warehouse.BuildConfig;
import com.patstudio.warehouse.R;
import com.patstudio.warehouse.data.models.PickerOrderModel;
import com.patstudio.warehouse.data.models.Resource;
import com.patstudio.warehouse.data.models.SettingsModel;
import com.patstudio.warehouse.data.models.UserModel;
import com.patstudio.warehouse.data.settings.SettingsRepository;
import com.patstudio.warehouse.data.user.UserRepository;
import com.patstudio.warehouse.di.scopes.AppScoped;
import com.patstudio.warehouse.root.WareHouseApplication;
import com.patstudio.warehouse.ui.splash.ActionSplashScreenModel;
import com.patstudio.warehouse.util.Event;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.schedulers.Schedulers;

@AppScoped
public final class SettingsViewModel extends ViewModel implements LifecycleObserver {

    @SuppressLint("StaticFieldLeak")
    private final Context context;;

    private UserRepository userRepository;
    private SettingsRepository settingsRepository;

    public  MutableLiveData<String> bearer = new MutableLiveData<>();
    public  MutableLiveData<String> urlBase = new MutableLiveData<>();
    public  MutableLiveData<Boolean> urlValidateError = new MutableLiveData<>();
    public  MutableLiveData<Event<String>> message = new MutableLiveData<>();
    private UserModel userModel;
    private SettingsModel settingsModel;

    @SuppressLint("CheckResult")
    private void initVariables() {

        userRepository.getUser()
                .subscribeOn(Schedulers.io())
                .subscribe(userModel -> {
                    this.userModel = userModel;
                    bearer.postValue(userModel.getAccess_token());
                },throwable -> {
                    throwable.printStackTrace();
                });

        settingsRepository.getSettings()
                .subscribeOn(Schedulers.io())
                .subscribe(settingsModel -> {
                    this.settingsModel = settingsModel;
                    urlBase.postValue(settingsModel.getBase_url());

                },throwable -> {
                    settingsModel = new SettingsModel(BuildConfig.BACKEND_URL);
                    urlBase.postValue(BuildConfig.BACKEND_URL);
                });
    }

    @SuppressLint("CheckResult")
    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    protected void onLifeCycleResume() {
        initVariables();
    }

    @SuppressLint("CheckResult")
    @Inject
    public SettingsViewModel(Application application, @NonNull UserRepository mUserRepository, @NonNull SettingsRepository settingsRepository) {
        this.context = application;
        this.userRepository = mUserRepository;
        this.settingsRepository = settingsRepository;
    }

    public void setUrlBase(CharSequence s) {
        String url = s.toString();
        if (settingsModel != null) {
            settingsModel.setBase_url(url);
            if (Patterns.WEB_URL.matcher(url).matches()) {
                urlValidateError.postValue(Boolean.FALSE);
            } else {
                urlValidateError.postValue(Boolean.TRUE);
            }
        }
    }

    public LiveData<Boolean>  getUrlBaseError() {
        return urlValidateError;
    }

    public LiveData<Event<String>>  getMessage() {
        return message;
    }

    public void setBearer(CharSequence s) {
        if (userModel != null)
            userModel.setAccess_token(s.toString());
    }

    public LiveData<String> getUrlBase() {
        return urlBase;
    }

    public LiveData<String> getBearer() {
        return bearer;
    }

    public void selectSave(View view) {
        if (URLUtil.isValidUrl(settingsModel.getBase_url())) {
            try {
                settingsRepository.insertSettings(settingsModel);
                userRepository.saveUser(userModel);
                message.setValue(new Event<>(context.getResources().getString(R.string.all_changes_saved)));
            } catch (Exception e) {
                message.postValue(new Event<>(context.getResources().getString(R.string.an_error_occurred_while_saving)));
                e.printStackTrace();
            }
        }
    }

}
