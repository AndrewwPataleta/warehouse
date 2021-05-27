package com.patstudio.warehouse.data.settings.local;

import androidx.annotation.NonNull;
import androidx.room.EmptyResultSetException;

import com.patstudio.warehouse.BuildConfig;
import com.patstudio.warehouse.data.interfaces.SettingsDataSource;
import com.patstudio.warehouse.data.interfaces.UserDataSource;
import com.patstudio.warehouse.data.models.SettingsModel;
import com.patstudio.warehouse.data.models.TokenModel;
import com.patstudio.warehouse.data.models.UserModel;
import com.patstudio.warehouse.di.scopes.AppScoped;
import com.patstudio.warehouse.util.schedulers.BaseSchedulerProvider;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Single;

import static dagger.internal.Preconditions.checkNotNull;


@AppScoped
public class SettingsLocalDataSource implements SettingsDataSource {

    private final SettingsDao mSettingsDao;

    private BaseSchedulerProvider mSchedulerProvider;

    @Inject
    public SettingsLocalDataSource(@NonNull SettingsDao settingsDao,
                                   @NonNull BaseSchedulerProvider schedulerProvider) {
        checkNotNull(schedulerProvider, "scheduleProvider cannot be null");
        checkNotNull(settingsDao, "quakesDao cannot be null");
        mSettingsDao = settingsDao;
        mSchedulerProvider = schedulerProvider;
    }


    @NonNull
    @Override
    public Single<SettingsModel> getSettings() {
        return mSettingsDao.getSettings();
    }

    @Override
    public String getBaseUrl() {
        try {
            return mSettingsDao.getSettings().blockingGet().getBase_url();
        } catch (EmptyResultSetException emptyResultSetException) {
            return BuildConfig.BACKEND_URL;
        }
    }

    @Override
    public void insertSettings(SettingsModel settingsModel) {
        mSettingsDao.insertSettings(settingsModel);
    }
}
