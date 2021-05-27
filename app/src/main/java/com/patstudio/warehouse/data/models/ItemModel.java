package com.patstudio.warehouse.data.models;

import java.io.Serializable;
import java.util.List;

public class ItemModel  implements Serializable {

    private String id;
    private String name;
    private String sku;
    private String ean;
    public String location;
    public int quantity;
    public int quantity_picked;
    public int bin_nr;
    public String bin_id;
    public List<CartonModel> cartons;
    private int bin_count = 0;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getEan() {
        return ean;
    }

    public void setEan(String ean) {
        this.ean = ean;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getQuantity_picked() {
        return quantity_picked;
    }

    public void setQuantity_picked(int quantity_picked) {
        this.quantity_picked = quantity_picked;
    }

    public int getBin_nr() {
        return bin_nr;
    }

    public void setBin_nr(int bin_nr) {
        this.bin_nr = bin_nr;
    }

    public String getBin_id() {
        return bin_id;
    }

    public void setBin_id(String bin_id) {
        this.bin_id = bin_id;
    }

    public List<CartonModel> getCartons() {
        return cartons;
    }

    public void setCartons(List<CartonModel> cartons) {
        this.cartons = cartons;
    }

    public int getBin_count() {
        return bin_count;
    }

    public void setBin_count(int bin_count) {
        this.bin_count = bin_count;
    }
}
