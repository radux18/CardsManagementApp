package com.example.dam_proiect_var_activity.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.dam_proiect_var_activity.database.Dao.CardDao;
import com.example.dam_proiect_var_activity.database.Dao.TransactionDao;
import com.example.dam_proiect_var_activity.model.Card;
import com.example.dam_proiect_var_activity.model.Transaction;
import com.example.dam_proiect_var_activity.util.DateConverter;


@Database(entities = {Card.class, Transaction.class}, exportSchema = false, version = 1)
@TypeConverters({DateConverter.class})
public abstract class DatabaseManager extends RoomDatabase {
    private static final String AndroidBankDb = "AndroidBankDb";

    private static DatabaseManager databaseManager;

    public static DatabaseManager getInstance(Context context) {
        if (databaseManager == null) {
            synchronized (DatabaseManager.class) {
                if (databaseManager == null) {
                    databaseManager = Room.databaseBuilder(context,
                            DatabaseManager.class, AndroidBankDb)
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return databaseManager;
    }

    public abstract CardDao getCardDao();
    public abstract TransactionDao getTransactionDao();
}
