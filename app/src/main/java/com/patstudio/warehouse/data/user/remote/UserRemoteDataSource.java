package com.patstudio.warehouse.data.user.remote;

import androidx.annotation.NonNull;


import com.google.gson.JsonObject;
import com.patstudio.warehouse.data.models.TokenModel;
import com.patstudio.warehouse.data.models.UserModel;
import com.patstudio.warehouse.data.interfaces.UserDataSource;
import com.patstudio.warehouse.di.scopes.AppScoped;

import javax.inject.Inject;

import io.reactivex.Single;

/**
 * Remote Data Source implementation
 */
@AppScoped
public class UserRemoteDataSource implements UserDataSource {

    @NonNull
    private UserApiService mApiService;

    @Inject
    public UserRemoteDataSource(@NonNull UserApiService apiService) {
        mApiService = apiService;
    }


    @NonNull
    @Override
    public Single<TokenModel> getToken(String grant_type, String username, String password) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("grant_type", grant_type);
        jsonObject.addProperty("username", username);
        jsonObject.addProperty("password", password);
        return mApiService.getToken(jsonObject);
    }

    @NonNull
    @Override
    public Single<UserModel> getUser() {
        return null;
    }

    @Override
    public void saveUser(@NonNull UserModel userModel) {

    }

    @Override
    public String getAccessToken() {
        return null;
    }

    @Override
    public void logout() {

    }

}
