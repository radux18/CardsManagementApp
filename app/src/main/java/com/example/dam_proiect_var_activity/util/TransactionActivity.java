package com.example.dam_proiect_var_activity.util;

import android.content.Intent;
import android.os.Bundle;
import android.telecom.Call;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.dam_proiect_var_activity.MainActivity;
import com.example.dam_proiect_var_activity.R;
import com.example.dam_proiect_var_activity.asyncTask.Callback;
import com.example.dam_proiect_var_activity.database.service.TransactionService;
import com.example.dam_proiect_var_activity.model.Card;
import com.example.dam_proiect_var_activity.model.Transaction;
import com.example.dam_proiect_var_activity.util.adapters.TransactionAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class TransactionActivity extends AppCompatActivity {
    public static final int ADD_TRANSACTION_REQUEST_CODE = 301;
    public static final int UPDATE_TRANSACTION_REQUEST_CODE = 303;
    public static final String OWNER_ID = "owner_id";
    public long owner_id;
    private Intent intent;
    private ListView lvTransactions;
    private TransactionService transactionService;
    private List<Transaction> transactions = new ArrayList<>();
    private ImageButton ibAdd;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);
        initComponents();
        intent = getIntent();
        owner_id = intent.getLongExtra("owner_id",-1);
        transactionService = new TransactionService(getApplicationContext());
        transactionService.getAllWithId(getAllWithIdCallback(),owner_id);
    }

    private Callback<List<Transaction>> getAllWithIdCallback() {
        return new Callback<List<Transaction>>() {
            @Override
            public void runResultOnUiThread(List<Transaction> result) {
                if ( result != null){
                    transactions.clear();
                    transactions.addAll(result);
                    notifyAdapter();
                }
            }
        };
    }

    private void initComponents() {
        lvTransactions = findViewById(R.id.lv_transaction_tr);
        addTransactionAdapter();
        ibAdd = findViewById(R.id.btn_tr_add);
        ibAdd.setOnClickListener(addTransactionEventListener());
        lvTransactions.setOnItemClickListener(updateEventListener());
        lvTransactions.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                transactionService.delete(deleteToDbCallback(position), transactions.get(position));
                return true;
            }
        });
    }

    private Callback<Integer> deleteToDbCallback(final int position) {
        return new Callback<Integer>() {
            @Override
            public void runResultOnUiThread(Integer result) {
                if (result != -1) {
                    transactions.remove(position);
                    notifyAdapter();
                }
            }
        };
    }

    private AdapterView.OnItemClickListener updateEventListener() {
        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), AddTransactionActivity.class);
                intent.putExtra(OWNER_ID, transactions.get(position).getOwner_id());
                intent.putExtra(AddTransactionActivity.TRANSACTION_KEY, transactions.get(position));
                startActivityForResult(intent, UPDATE_TRANSACTION_REQUEST_CODE);
            }
        };
    }

    private View.OnClickListener addTransactionEventListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent = new Intent(getApplicationContext(), AddTransactionActivity.class);
               intent.putExtra(OWNER_ID,owner_id);
               startActivityForResult(intent, ADD_TRANSACTION_REQUEST_CODE);
            }
        };
    }

    private void addTransactionAdapter() {
        TransactionAdapter adapter = new TransactionAdapter(getApplicationContext(), R.layout.activity_transaction_view,
                transactions, getLayoutInflater());
        lvTransactions.setAdapter(adapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null){
            Transaction transaction = (Transaction) data.getSerializableExtra(AddTransactionActivity.TRANSACTION_KEY);
            if(requestCode == ADD_TRANSACTION_REQUEST_CODE){
                transactionService.insert(insertIntoDbCallback(), transaction);
            } else if (requestCode == UPDATE_TRANSACTION_REQUEST_CODE){
                transactionService.update(updateIntoDbCallback(), transaction);
            }
        }
    }

    private Callback<Transaction> updateIntoDbCallback() {
        return new Callback<Transaction>() {
            @Override
            public void runResultOnUiThread(Transaction result) {
                if (result != null) {
                    for (Transaction transaction : transactions) {
                        if (transaction.getTransaction_id() == result.getTransaction_id()) {
                            transaction.setDate(result.getDate());
                            transaction.setAmount(result.getAmount());
                            transaction.setCategory(result.getCategory());
                            transaction.setDescription(result.getDescription());
                            transaction.setPerson(result.getPerson());
                            break;
                        }
                    }
                    notifyAdapter();
                }
            }
        };
    }

    private Callback<Transaction> insertIntoDbCallback() {
        return new Callback<Transaction>() {
            @Override
            public void runResultOnUiThread(Transaction result) {
                if (result != null) {
                    transactions.add(result);
                    notifyAdapter();
                }
            }
        };
    }


    private void notifyAdapter() {
        TransactionAdapter adapter = (TransactionAdapter) lvTransactions.getAdapter();
        adapter.notifyDataSetChanged();

    }
}
