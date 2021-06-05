package com.example.dam_proiect_var_activity.database.service;

import android.content.Context;

import com.example.dam_proiect_var_activity.asyncTask.AsyncTaskRunner;
import com.example.dam_proiect_var_activity.asyncTask.Callback;
import com.example.dam_proiect_var_activity.database.Dao.TransactionDao;
import com.example.dam_proiect_var_activity.database.DatabaseManager;
import com.example.dam_proiect_var_activity.model.Transaction;

import java.util.List;
import java.util.concurrent.Callable;

public class TransactionService {

    private final TransactionDao transactionDao;
    private final AsyncTaskRunner taskRunner;

    public TransactionService(Context context) {
        transactionDao = DatabaseManager.getInstance(context).getTransactionDao();
        taskRunner = new AsyncTaskRunner();
    }


    public void getAll(Callback<List<Transaction>> callback) {
        Callable<List<Transaction>> callable = new Callable<List<Transaction>>() {
            @Override
            public List<Transaction> call() {
                return transactionDao.getAll();
            }
        };
        taskRunner.executeAsync(callable, callback);
    }

    public void getAllWithId(Callback<List<Transaction>> callback, long id) {
        Callable<List<Transaction>> callable = new Callable<List<Transaction>>() {
            @Override
            public List<Transaction> call() {
                return transactionDao.getTransactionsForCard(id);
            }
        };
        taskRunner.executeAsync(callable, callback);
    }

    public void insert(Callback<Transaction> callback, final Transaction transaction) {
        Callable<Transaction> callable = new Callable<Transaction>() {
            @Override
            public Transaction call() {
                if (transaction == null) {
                    return null;
                }
                long id = transactionDao.insert(transaction);
                if (id == -1) {
                    return null;
                }
                transaction.setTransaction_id(id);
                return transaction;
            }
        };
        taskRunner.executeAsync(callable, callback);
    }

    public void update(Callback<Transaction> callback, final Transaction transaction) {
        Callable<Transaction> callable = new Callable<Transaction>() {
            @Override
            public Transaction call() {
                if (transaction == null) {
                    return null;
                }
                int count = transactionDao.update(transaction);
                if (count < 1) {
                    return null;
                }
                return transaction;
            }
        };
        taskRunner.executeAsync(callable, callback);
    }

    public void delete(Callback<Integer> callback, final Transaction transaction) {
        Callable<Integer> callable = new Callable<Integer>() {
            @Override
            public Integer call() {
                if (transaction == null) {
                    return -1;
                }
                return transactionDao.delete(transaction);
            }
        };
        taskRunner.executeAsync(callable, callback);
    }
}
