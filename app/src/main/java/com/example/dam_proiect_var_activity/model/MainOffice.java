package com.example.dam_proiect_var_activity.model;

import java.io.Serializable;

public class MainOffice implements Serializable {
    private String origin;
    private double netIncome;
    private Manager bankManager;

    public MainOffice(String origin, double netIncome, Manager bankManager) {
        this.origin = origin;
        this.netIncome = netIncome;
        this.bankManager = bankManager;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public double getNetIncome() {
        return netIncome;
    }

    public void setNetIncome(double netIncome) {
        this.netIncome = netIncome;
    }

    public Manager getBankManager() {
        return bankManager;
    }

    public void setBankManager(Manager bankManager) {
        this.bankManager = bankManager;
    }

    @Override
    public String toString() {
        return "MainOffice{" +
                "origin='" + origin + '\'' +
                ", netIncome=" + netIncome +
                ", bankManager=" + bankManager +
                '}';
    }
}
