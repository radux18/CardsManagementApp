package com.example.dam_proiect_var_activity.database.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.dam_proiect_var_activity.model.Card;

import java.util.List;

@Dao
public interface CardDao {
    @Query("select * from cards")
    List<Card> getAll();

    @Insert
    long insert(Card card);

    @Update
    int update(Card card);

    @Delete
    int delete(Card card);

}
