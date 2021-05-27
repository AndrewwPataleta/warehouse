package com.patstudio.warehouse.data.user.local;


import android.app.Application;

import androidx.room.Room;


import com.patstudio.warehouse.di.scopes.AppScoped;
import com.patstudio.warehouse.util.Constants;

import dagger.Module;
import dagger.Provides;

@Module
public class        UserLocalDataModule {
    @AppScoped
    @Provides
    UserDatabase provideDb(Application context) {
        return Room.databaseBuilder(context.getApplicationContext(),
                UserDatabase.class, Constants.USER_ROOM_DB_STRING)
                .fallbackToDestructiveMigration()
                .build();
    }

    @AppScoped
    @Provides
    UserDao provideQuakesDao(UserDatabase db) {
        return db.userDao();
    }
}
