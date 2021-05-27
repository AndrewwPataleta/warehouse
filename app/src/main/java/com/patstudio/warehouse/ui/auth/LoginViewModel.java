package com.patstudio.warehouse.ui.auth;

import android.annotation.SuppressLint;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.patstudio.warehouse.data.models.UserModel;
import com.patstudio.warehouse.data.user.UserRepository;
import com.patstudio.warehouse.di.scopes.AppScoped;
import com.patstudio.warehouse.util.Constants;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static com.patstudio.warehouse.ui.auth.ActionLoginScreenModel.SHOW_MAIN;

@AppScoped
public final class LoginViewModel extends ViewModel {

    @NonNull
    private final UserRepository userRepository;

    public MutableLiveData<String> EmailAddress = new MutableLiveData<>();
    public MutableLiveData<String> Password = new MutableLiveData<>();
    public MutableLiveData<Boolean> needShowProgressLogin = new MutableLiveData<>();
    public MutableLiveData<ActionLoginScreenModel> actionScreenModelMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<LoginUserModel> userMutableLiveData;
    private LoginUserModel loginUser;

    @SuppressLint("CheckResult")
    @Inject
    public LoginViewModel(@NonNull UserRepository mUserRepository) {
        userRepository = mUserRepository;
    }

    public MutableLiveData<LoginUserModel> getUser() {

        if (userMutableLiveData == null) {
            userMutableLiveData = new MutableLiveData<>();
        }
        return userMutableLiveData;

    }

    public void onClick(View view) {
        loginUser = new LoginUserModel(EmailAddress.getValue(), Password.getValue());
        userMutableLiveData.setValue(loginUser);
    }

    @SuppressLint("CheckResult")
    public void login() {
        userRepository.getToken(Constants.GRANT_TYPE_PASSWORD, EmailAddress.getValue(), Password.getValue())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(start-> needShowProgressLogin.postValue(true))
                .subscribe(sub-> {
                    UserModel userModel = new UserModel(sub.user.id, sub.user.full_name, sub.access_token);
                    userRepository.saveUser(userModel);
                    actionScreenModelMutableLiveData.postValue(new ActionLoginScreenModel(SHOW_MAIN));
                    needShowProgressLogin.postValue(false);
                },throwable -> throwable.printStackTrace());

    }

    public LiveData<ActionLoginScreenModel> getScreen() {
        return actionScreenModelMutableLiveData;
    }

}
