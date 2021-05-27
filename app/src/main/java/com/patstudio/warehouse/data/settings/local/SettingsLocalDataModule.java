package com.patstudio.warehouse.data.settings.local;


import android.app.Application;

import androidx.room.Room;

import com.patstudio.warehouse.di.scopes.AppScoped;
import com.patstudio.warehouse.util.Constants;

import dagger.Module;
import dagger.Provides;

@Module
public class SettingsLocalDataModule {

    @AppScoped
    @Provides
    SettingsDatabase provideDb(Application context) {
        return Room.databaseBuilder(context.getApplicationContext(),
                SettingsDatabase.class, Constants.SETTINGS_ROOM_DB_STRING)
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();
    }

    @AppScoped
    @Provides
    SettingsDao provideSettingsDao(SettingsDatabase db) {
        return db.settingsDao();
    }
}
