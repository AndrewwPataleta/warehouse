package com.patstudio.warehouse.data.models;

import java.io.Serializable;
import java.util.List;

public class OrderModel implements Serializable {

    public String id;
    public int order_nr;
    public int progress;
    public List<CartonModel> cartons;
    public List<ItemModel> items;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getOrder_nr() {
        return order_nr;
    }

    public void setOrder_nr(int order_nr) {
        this.order_nr = order_nr;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public List<CartonModel> getCartons() {
        return cartons;
    }

    public void setCartons(List<CartonModel> cartons) {
        this.cartons = cartons;
    }

    public List<ItemModel> getItems() {
        return items;
    }

    public void setItems(List<ItemModel> items) {
        this.items = items;
    }
}
