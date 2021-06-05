package com.example.dam_proiect_var_activity.model;

import java.io.Serializable;

public class DiscountCoupon implements Serializable {

    private String id;
    private String name;
    private String discount;
    private String expirationDate;

    public DiscountCoupon(){
    }


    public DiscountCoupon(String id, String name, String discount, String expirationDate) {
        this.id = id;
        this.name = name;
        this.discount = discount;
        this.expirationDate = expirationDate;
    }

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

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    @Override
    public String toString() {
        return "DiscountCoupon{" +
                "name='" + name + '\'' +
                ", discount='" + discount + '\'' +
                ", expirationDate='" + expirationDate + '\'' +
                '}';
    }
}
