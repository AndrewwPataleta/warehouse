package com.patstudio.warehouse.data.models;

import java.io.Serializable;
import java.util.List;

public class PickerOrderModel implements Serializable {

    public String id;
    public PickerModel picker;
    public Integer batch;
    public boolean priority;
    public String status;
    public List<OrderModel> orders;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public PickerModel getPicker() {
        return picker;
    }

    public void setPicker(PickerModel picker) {
        this.picker = picker;
    }

    public Integer getBatch() {
        return batch;
    }

    public void setBatch(Integer batch) {
        this.batch = batch;
    }

    public boolean isPriority() {
        return priority;
    }

    public void setPriority(boolean priority) {
        this.priority = priority;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<OrderModel> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderModel> orders) {
        this.orders = orders;
    }
}
