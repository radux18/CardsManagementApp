package com.example.dam_proiect_var_activity.database.service;

import android.content.Context;
import android.widget.ArrayAdapter;

import com.example.dam_proiect_var_activity.asyncTask.AsyncTaskRunner;
import com.example.dam_proiect_var_activity.asyncTask.Callback;
import com.example.dam_proiect_var_activity.database.Dao.CardDao;
import com.example.dam_proiect_var_activity.database.Dao.TransactionDao;
import com.example.dam_proiect_var_activity.database.DatabaseManager;
import com.example.dam_proiect_var_activity.model.Card;
import com.example.dam_proiect_var_activity.model.Transaction;

import java.util.List;
import java.util.concurrent.Callable;

public class CardService {

    private final CardDao cardDao;
    private final AsyncTaskRunner taskRunner;

    public CardService(Context context) {
        cardDao = DatabaseManager.getInstance(context).getCardDao();
        taskRunner = new AsyncTaskRunner();
    }

    public void getAll(Callback<List<Card>> callback) {
        Callable<List<Card>> callable = new Callable<List<Card>>() {
            @Override
            public List<Card> call() {
                return cardDao.getAll();
            }
        };
        taskRunner.executeAsync(callable, callback);
    }

    public void insert(Callback<Card> callback, final Card card) {
        Callable<Card> callable = new Callable<Card>() {
            @Override
            public Card call() {
                if (callback == null) {
                    return null;
                }
                long id = cardDao.insert(card);
                if (id == -1) {
                    return null;
                }
                card.setCard_id(id);
                return card;
            }
        };
        taskRunner.executeAsync(callable, callback);
    }

    public void update(Callback<Card> callback, final Card card) {
        Callable<Card> callable = new Callable<Card>() {
            @Override
            public Card call() {
                if (card == null) {
                    return null;
                }
                int count = cardDao.update(card);
                if (count < 1) {
                    return null;
                }
                return card;
            }
        };
        taskRunner.executeAsync(callable, callback);
    }

    public void delete(Callback<Integer> callback, final Card card) {
        Callable<Integer> callable = new Callable<Integer>() {
            @Override
            public Integer call() {
                if (card == null) {
                    return -1;
                }
                return cardDao.delete(card);
            }
        };
        taskRunner.executeAsync(callable, callback);
    }
}
