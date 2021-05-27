package com.patstudio.warehouse.data.interfaces;

import androidx.annotation.NonNull;

import com.patstudio.warehouse.data.models.SettingsModel;
import com.patstudio.warehouse.data.models.TokenModel;
import com.patstudio.warehouse.data.models.UserModel;

import io.reactivex.Single;


public interface SettingsDataSource {


    @NonNull
    Single<SettingsModel> getSettings();

    String getBaseUrl();

    void insertSettings(SettingsModel settingsModel);

}
