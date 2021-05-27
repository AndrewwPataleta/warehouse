package com.patstudio.warehouse.data.settings.local;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.patstudio.warehouse.data.models.SettingsModel;
import com.patstudio.warehouse.data.models.UserModel;

/**
 * The Room Database that contains the Quakes table.
 */

@Database(entities = {SettingsModel.class}, version = 1, exportSchema = false)
public abstract class SettingsDatabase extends RoomDatabase {
    public abstract SettingsDao settingsDao();
}
