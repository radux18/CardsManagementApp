package com.example.dam_proiect_var_activity.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "cards")
public class Card implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "card_id")
    private long card_id;
    @ColumnInfo(name = "firstName")
    private String firstName;

    @ColumnInfo(name = "secondName")
    private String secondName;

    @ColumnInfo(name = "expirationMonth")
    private int expirationMonth;
    @ColumnInfo(name = "expirationYear")
    private int expirationYear;
    @ColumnInfo(name = "serialNo")
    private int serialNo;
    @ColumnInfo(name = "type")
    private String type;
    @ColumnInfo(name = "current_balance")
    private double current_balance;

    public Card(long card_id, String firstName, String secondName, int expirationMonth, int expirationYear, int serialNo, String type, double current_balance) {
        this.card_id = card_id;
        this.firstName = firstName;
        this.secondName = secondName;
        this.expirationMonth = expirationMonth;
        this.expirationYear = expirationYear;
        this.serialNo = serialNo;
        this.type = type;
        this.current_balance = current_balance;
    }

    @Ignore
    public Card(String firstName, String secondName, int expirationMonth, int expirationYear, int serialNo, String type, double current_balance) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.expirationMonth = expirationMonth;
        this.expirationYear = expirationYear;
        this.serialNo = serialNo;
        this.type = type;
        this.current_balance = current_balance;
    }

    public long getCard_id() {
        return card_id;
    }

    public void setCard_id(long card_id) {
        this.card_id = card_id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public int getExpirationMonth() {
        return expirationMonth;
    }

    public void setExpirationMonth(int expirationMonth) {
        this.expirationMonth = expirationMonth;
    }

    public int getExpirationYear() {
        return expirationYear;
    }

    public void setExpirationYear(int expirationYear) {
        this.expirationYear = expirationYear;
    }

    public int getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(int serialNo) {
        this.serialNo = serialNo;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getCurrent_balance() {
        return current_balance;
    }

    public void setCurrent_balance(double current_balance) {
        this.current_balance = current_balance;
    }

    @Override
    public String toString() {
        return "Card{" +
                "firstName='" + firstName + '\'' +
                ", secondName='" + secondName + '\'' +
                ", expirationMonth=" + expirationMonth +
                ", expirationYear=" + expirationYear +
                ", serialNo=" + serialNo +
                ", type='" + type + '\'' +
                ", current_balance=" + current_balance +
                '}';
    }
}
