package com.patstudio.warehouse.data.models;

import java.io.Serializable;

public class PickerModel implements Serializable {

    public String id;
    public String full_name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }
}
