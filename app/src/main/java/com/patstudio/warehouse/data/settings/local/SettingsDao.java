package com.patstudio.warehouse.data.settings.local;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.patstudio.warehouse.data.models.SettingsModel;
import com.patstudio.warehouse.data.models.UserModel;

import io.reactivex.Single;

@Dao
public interface SettingsDao {

    @Query("SELECT * FROM settings")
    Single<SettingsModel> getSettings();


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertSettings(SettingsModel settingsModel);
}

