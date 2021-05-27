package com.patstudio.warehouse.data.user;

import androidx.annotation.NonNull;


import com.patstudio.warehouse.data.models.TokenModel;
import com.patstudio.warehouse.data.models.UserModel;
import com.patstudio.warehouse.data.scopes.Local;
import com.patstudio.warehouse.data.scopes.Remote;

import com.patstudio.warehouse.data.interfaces.UserDataSource;
import com.patstudio.warehouse.di.scopes.AppScoped;
import com.patstudio.warehouse.util.ConnectivityUtils.OnlineChecker;

import javax.inject.Inject;

import io.reactivex.Single;


@AppScoped
public class UserRepository implements UserDataSource {

    private final UserDataSource mUserRemoteDataSource;

    private final UserDataSource mUserLocalDataSource;

    private final OnlineChecker mOnlineChecker;

    @Inject
    public UserRepository(@Remote UserDataSource mUserRemoteDataSource,
                          @Local UserDataSource mUserLocalDataSource,
                          OnlineChecker onlineChecker) {
        this.mUserRemoteDataSource = mUserRemoteDataSource;
        this.mUserLocalDataSource = mUserLocalDataSource;
        mOnlineChecker = onlineChecker;
    }


    @NonNull
    @Override
    public Single<TokenModel> getToken(String grant_type, String username, String password) {
        return mUserRemoteDataSource.getToken(grant_type, username, password);
    }

    @NonNull
    @Override
    public Single<UserModel> getUser() {
        return mUserLocalDataSource.getUser();
    }

    @Override
    public void saveUser(@NonNull UserModel userModel) {
        mUserLocalDataSource.saveUser(userModel);
    }

    @Override
    public String getAccessToken() {
        return mUserLocalDataSource.getAccessToken();
    }

    @Override
    public void logout() {
         mUserLocalDataSource.logout();
    }

}
