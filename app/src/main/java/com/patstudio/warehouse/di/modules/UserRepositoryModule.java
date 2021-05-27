package com.patstudio.warehouse.di.modules;

import com.patstudio.warehouse.data.scopes.Local;
import com.patstudio.warehouse.data.scopes.Remote;
import com.patstudio.warehouse.data.interfaces.UserDataSource;
import com.patstudio.warehouse.data.user.local.UserDao;
import com.patstudio.warehouse.data.user.local.UserLocalDataModule;
import com.patstudio.warehouse.data.user.local.UserLocalDataSource;
import com.patstudio.warehouse.data.user.remote.UserApiService;
import com.patstudio.warehouse.data.user.remote.UserRemoteDataModule;
import com.patstudio.warehouse.data.user.remote.UserRemoteDataSource;
import com.patstudio.warehouse.di.scopes.AppScoped;
import com.patstudio.warehouse.util.schedulers.BaseSchedulerProvider;

import dagger.Module;
import dagger.Provides;

@Module(includes = {UserLocalDataModule.class, UserRemoteDataModule.class})
public class UserRepositoryModule {

    @Provides
    @Local
    @AppScoped
    UserDataSource provideUserLocalDataSource(UserDao userDao, BaseSchedulerProvider schedulerProvider) {
        return new UserLocalDataSource(userDao, schedulerProvider);
    }

    @Provides
    @Remote
    @AppScoped
    UserDataSource provideUserRemoteDataSource(UserApiService apiService) {
        return new UserRemoteDataSource(apiService);
    }
}
