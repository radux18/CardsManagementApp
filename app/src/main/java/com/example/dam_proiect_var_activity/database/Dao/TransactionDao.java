package com.example.dam_proiect_var_activity.database.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.dam_proiect_var_activity.model.Card;
import com.example.dam_proiect_var_activity.model.Transaction;

import java.util.List;

@Dao
public interface TransactionDao {
    @Query("select * from transactions")
    List<Transaction> getAll();

    @Insert
    long insert(Transaction transaction);

    @Update
    int update(Transaction transaction);

    @Delete
    int delete(Transaction transaction);

    @Query("SELECT * FROM transactions WHERE owner_id=:owner_id")
    List<Transaction> getTransactionsForCard(long owner_id);

}
