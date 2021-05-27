package com.patstudio.warehouse.data.user.local;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.patstudio.warehouse.data.models.UserModel;

import io.reactivex.Single;

@Dao
public interface UserDao {

    @Query("SELECT * FROM user")
    Single<UserModel> getUser();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertUser(UserModel userModel);

    @Query("DELETE FROM user")
    void nukeTable();
}

