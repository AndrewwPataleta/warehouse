package com.patstudio.warehouse.data.models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "settings")
public final class SettingsModel {


    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    @SerializedName("id")
    @Expose
    private Integer id;

    @ColumnInfo(name = "base_url")
    @SerializedName("base_url")
    private String base_url;

    public SettingsModel() {}

    public SettingsModel(String base_url) {
        this.base_url = base_url;
    }

    public String getBase_url() {
        return base_url;
    }

    public void setBase_url(String base_url) {
        this.base_url = base_url;
    }

    @NonNull
    public Integer getId() {
        return id;
    }

    public void setId(@NonNull Integer id) {
        this.id = id;
    }
}
