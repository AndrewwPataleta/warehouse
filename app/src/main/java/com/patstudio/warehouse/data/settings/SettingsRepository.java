package com.patstudio.warehouse.data.settings;

import androidx.annotation.NonNull;

import com.patstudio.warehouse.data.interfaces.SettingsDataSource;
import com.patstudio.warehouse.data.models.SettingsModel;
import com.patstudio.warehouse.data.scopes.Local;
import com.patstudio.warehouse.di.scopes.AppScoped;
import com.patstudio.warehouse.util.ConnectivityUtils.OnlineChecker;

import javax.inject.Inject;

import io.reactivex.Single;


@AppScoped
public class SettingsRepository implements SettingsDataSource {

    private final SettingsDataSource settingsDataSource;


    private final OnlineChecker mOnlineChecker;

    @Inject
    public SettingsRepository(@Local SettingsDataSource settingsDataSource,
                              OnlineChecker onlineChecker) {
        this.settingsDataSource = settingsDataSource;
        mOnlineChecker = onlineChecker;
    }


    @NonNull
    @Override
    public Single<SettingsModel> getSettings() {
        return settingsDataSource.getSettings();
    }

    @Override
    public String getBaseUrl() {
        return settingsDataSource.getBaseUrl();
    }

    @Override
    public void insertSettings(SettingsModel settingsModel) {
        settingsDataSource.insertSettings(settingsModel);
    }
}
