package com.example.dam_proiect_var_activity.model;

import java.io.Serializable;

public class PartnerBank implements Serializable {
    private String bankName;
    private int nrEmployee;
    private MainOffice mainOffice;

    public PartnerBank(String bankName, int nrEmployee, MainOffice mainOffice) {
        this.bankName = bankName;
        this.nrEmployee = nrEmployee;
        this.mainOffice = mainOffice;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public int getNrEmployee() {
        return nrEmployee;
    }

    public void setNrEmployee(int nrEmployee) {
        this.nrEmployee = nrEmployee;
    }

    public MainOffice getMainOffice() {
        return mainOffice;
    }

    public void setMainOffice(MainOffice mainOffice) {
        this.mainOffice = mainOffice;
    }

    @Override
    public String toString() {
        return "PartnerBank{" +
                "bankName='" + bankName + '\'' +
                ", nrEmployee=" + nrEmployee +
                ", mainOffice=" + mainOffice +
                '}';
    }
}
