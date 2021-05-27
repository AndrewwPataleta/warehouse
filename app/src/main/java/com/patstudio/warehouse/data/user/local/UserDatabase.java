package com.patstudio.warehouse.data.user.local;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.patstudio.warehouse.data.models.UserModel;

/**
 * The Room Database that contains the Quakes table.
 */

@Database(entities = {UserModel.class}, version = 1, exportSchema = false)
public abstract class UserDatabase extends RoomDatabase {
    public abstract UserDao userDao();
}
