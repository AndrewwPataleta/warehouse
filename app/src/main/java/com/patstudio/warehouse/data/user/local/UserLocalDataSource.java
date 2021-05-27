package com.patstudio.warehouse.data.user.local;

import androidx.annotation.NonNull;
import androidx.room.EmptyResultSetException;

import com.patstudio.warehouse.data.interfaces.UserDataSource;
import com.patstudio.warehouse.data.models.TokenModel;
import com.patstudio.warehouse.data.models.UserModel;
import com.patstudio.warehouse.di.scopes.AppScoped;
import com.patstudio.warehouse.util.schedulers.BaseSchedulerProvider;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Single;

import static dagger.internal.Preconditions.checkNotNull;



@AppScoped
public class UserLocalDataSource implements UserDataSource {

    private final UserDao mUserDao;

    private BaseSchedulerProvider mSchedulerProvider;

    @Inject
    public UserLocalDataSource(@NonNull UserDao userDao,
                               @NonNull BaseSchedulerProvider schedulerProvider) {
        checkNotNull(schedulerProvider, "scheduleProvider cannot be null");
        checkNotNull(userDao, "quakesDao cannot be null");
        mUserDao = userDao;
        mSchedulerProvider = schedulerProvider;
    }


    @Override
    public Single<TokenModel> getToken(String grant_type, String username, String password) {
        return null;
    }

    @NonNull
    @Override
    public Single<UserModel> getUser() {
        return mUserDao.getUser();
    }

    @Override
    public void saveUser(@NonNull UserModel userModel) {
        Completable.fromRunnable(() -> mUserDao.insertUser(userModel))
                .subscribeOn(mSchedulerProvider.io()).subscribe();
    }

    @Override
    public String getAccessToken() {
        try {
            return mUserDao.getUser().blockingGet().getAccess_token();
        } catch (EmptyResultSetException emptyResultSetException) {
            return null;
        }

    }

    @Override
    public void logout() {
        Completable.fromRunnable(() -> mUserDao.nukeTable())
                .subscribeOn(mSchedulerProvider.io()).subscribe();
    }


}
