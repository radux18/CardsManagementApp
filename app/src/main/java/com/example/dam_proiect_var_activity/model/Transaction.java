package com.example.dam_proiect_var_activity.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.example.dam_proiect_var_activity.util.DateConverter;

import java.io.Serializable;
import java.util.Date;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "transactions" ,foreignKeys = @ForeignKey(entity = Card.class,
        parentColumns = "card_id",
        childColumns = "owner_id",
        onDelete = CASCADE))
public class Transaction implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "transaction_id")
    private long transaction_id;
    @ColumnInfo(name = "owner_id")
    private long owner_id;
    @ColumnInfo(name = "amount")
    private double amount;
    @ColumnInfo(name = "description")
    private String description;
    @ColumnInfo(name = "category")
    private String category;
    @ColumnInfo(name = "date")
    private Date date;
    @ColumnInfo(name = "person")
    private String person;

    public Transaction(long transaction_id, double amount, String description, String category, Date date, String person, long owner_id) {
        this.transaction_id = transaction_id;
        this.amount = amount;
        this.description = description;
        this.category = category;
        this.date = date;
        this.owner_id = owner_id;
        this.person = person;
    }

    @Ignore
    public Transaction(double amount, String description, String category,String person, Date date) {
        this.amount = amount;
        this.description = description;
        this.category = category;
        this.date = date;
        this.person = person;
    }

    public String getPerson() {
        return person;
    }

    public void setPerson(String person) {
        this.person = person;
    }

    public long getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(long transaction_id) {
        this.transaction_id = transaction_id;
    }

    public long getOwner_id() {
        return owner_id;
    }

    public void setOwner_id(long owner_id) {
        this.owner_id = owner_id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "transaction_id=" + transaction_id +
                ", owner_id=" + owner_id +
                ", amount=" + amount +
                ", description='" + description + '\'' +
                ", category='" + category + '\'' +
                ", date=" + DateConverter.fromDate(date) +
                ", person=" + person +
                '}';
    }
}
