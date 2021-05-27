package com.patstudio.warehouse.ui.splash;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.patstudio.warehouse.data.user.UserRepository;
import com.patstudio.warehouse.di.scopes.AppScoped;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.schedulers.Schedulers;

@AppScoped
public final class SplashViewModel extends ViewModel {

    @NonNull
    private final UserRepository userRepository;

    private MutableLiveData<ActionSplashScreenModel> actionScreenModelMutableLiveData = new MutableLiveData<>();


    @SuppressLint("CheckResult")
    @Inject
    public SplashViewModel(@NonNull UserRepository mUserRepository) {
        userRepository = mUserRepository;
        userRepository.getUser()
                .subscribeOn(Schedulers.io())
                .delay(2, TimeUnit.SECONDS)
                .subscribe(userModel -> {
                    System.out.println("ser model "+userModel.getAccess_token());
                    actionScreenModelMutableLiveData.postValue(new ActionSplashScreenModel(ActionSplashScreenModel.SHOW_MAIN));
                },throwable -> {
                    throwable.printStackTrace();
                    actionScreenModelMutableLiveData.postValue(new ActionSplashScreenModel(ActionSplashScreenModel.SHOW_LOGIN));
                });
    }

    public LiveData<ActionSplashScreenModel> getScreen() {
        return actionScreenModelMutableLiveData;
    }

}
