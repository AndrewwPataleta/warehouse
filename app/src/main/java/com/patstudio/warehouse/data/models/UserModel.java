package com.patstudio.warehouse.data.models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "user")
public final class UserModel {

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "id")
    @SerializedName("code")
    @Expose
    private String id;

    @ColumnInfo(name = "full_name")
    @SerializedName("full_name")
    private String full_name;

    @ColumnInfo(name = "access_token")
    @SerializedName("access_token")
    private String access_token;

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public UserModel(String id, String full_name, String access_token) {
        this.id = id;
        this.full_name = full_name;
        this.access_token = access_token;
    }
}
