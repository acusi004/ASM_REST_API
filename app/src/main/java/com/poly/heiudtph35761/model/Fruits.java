package com.poly.heiudtph35761.model;

import java.util.ArrayList;

public class Fruits {
    private String _id;
    private String name;
    private int quantity;
    private String type;
    private int price;

    private String size, origin, status;
    private String  image;
    private String createAt, updateAt;


    public Fruits() {
    }

    public Fruits(String _id, String name, int quantity, String type, int price, String size, String origin, String status, String image, String createAt, String updateAt) {
        this._id = _id;
        this.name = name;
        this.quantity = quantity;
        this.type = type;
        this.price = price;
        this.size = size;
        this.origin = origin;
        this.status = status;
        this.image = image;
        this.createAt = createAt;
        this.updateAt = updateAt;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }

    public String getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(String updateAt) {
        this.updateAt = updateAt;
    }
}
