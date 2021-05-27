package com.patstudio.warehouse.di.modules;

import com.patstudio.warehouse.data.interfaces.SettingsDataSource;
import com.patstudio.warehouse.data.interfaces.UserDataSource;
import com.patstudio.warehouse.data.scopes.Local;
import com.patstudio.warehouse.data.scopes.Remote;
import com.patstudio.warehouse.data.settings.local.SettingsDao;
import com.patstudio.warehouse.data.settings.local.SettingsLocalDataModule;
import com.patstudio.warehouse.data.settings.local.SettingsLocalDataSource;
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

@Module(includes = {SettingsLocalDataModule.class})
public class SettingsRepositoryModule {

    @Provides
    @Local
    @AppScoped
    SettingsDataSource provideLocalSettingsDataSource(SettingsDao settingsDao, BaseSchedulerProvider schedulerProvider) {
        return new SettingsLocalDataSource(settingsDao, schedulerProvider);
    }

}
