package com.patstudio.warehouse.data.interfaces;

import androidx.annotation.NonNull;

import com.patstudio.warehouse.data.models.TokenModel;
import com.patstudio.warehouse.data.models.UserModel;

import io.reactivex.Single;


public interface UserDataSource {


    Single<TokenModel> getToken(String grant_type, String username, String password);

    @NonNull
    Single<UserModel> getUser();

    void saveUser(@NonNull UserModel userModel);

    String getAccessToken();

    void logout();

}
