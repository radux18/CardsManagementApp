package com.example.dam_proiect_var_activity.util;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.dam_proiect_var_activity.R;
import com.example.dam_proiect_var_activity.database.service.TransactionService;
import com.example.dam_proiect_var_activity.model.Transaction;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Date;

public class AddTransactionActivity extends AppCompatActivity {

    public static final String TRANSACTION_KEY = "transaction_key";
    private TextInputEditText tietDate;
    private TextInputEditText tietAmount;
    private TextInputEditText tietPerson;
    private Spinner spnCategory;
    private TextInputEditText tietDescription;
    private Button btnSave;
    public long owner_id;
    private Intent intent;
    private Transaction transaction = null;
    public TransactionService transactionService;
    private Button btnRememberDestination;
    public static final String SHARED_PREF = "shared pref";
    public static final String PERSON = "person";
    private String person;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_transaction);
        initComponents();
        intent = getIntent();
        transactionService = new TransactionService(getApplicationContext());
        owner_id = intent.getLongExtra("owner_id", -1);

        if(intent.hasExtra(TRANSACTION_KEY)){
            transaction = (Transaction) intent.getSerializableExtra(TRANSACTION_KEY);
            buildViewsFromTransaction(transaction);
        }

        btnRememberDestination.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
            }
        });
        if(!intent.hasExtra(TRANSACTION_KEY)){
            loadData();
            updateViews();}
    }

    private void buildViewsFromTransaction(Transaction transaction) {
        if (transaction == null) {
            return;
        }
        if (transaction.getDate() != null) {
            tietDate.setText(DateConverter.fromDate(transaction.getDate()));
        }

        tietPerson.setText(transaction.getPerson());
        tietAmount.setText(String.valueOf(transaction.getAmount()));
        tietDescription.setText(transaction.getDescription());
        selectCategory(transaction);
    }

    private void selectCategory(Transaction transaction) {
        ArrayAdapter adapter = (ArrayAdapter) spnCategory.getAdapter();
        for (int i = 0; i < adapter.getCount(); i++) {
            String item = (String) adapter.getItem(i);
            if (item != null && item.equals(transaction.getCategory())) {
                spnCategory.setSelection(i);
                break;
            }
        }
    }
    public void saveData(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(PERSON,tietPerson.getText().toString());


        editor.apply();
    }
    public void loadData(){
        SharedPreferences sharedPreferences= getSharedPreferences(SHARED_PREF,MODE_PRIVATE);
        person= sharedPreferences.getString(PERSON,"");


    }
    public void updateViews() {
        tietPerson.setText(person);
    }

    private void initComponents() {
        tietAmount = findViewById(R.id.add_tiet_tr_amount);
        tietDate = findViewById(R.id.add_tiet_tr_date);
        tietDescription = findViewById(R.id.add_tiet_tr_description);
        spnCategory = findViewById(R.id.spn_tr_category);
        tietPerson = findViewById(R.id.add_tiet_tr_person);
        btnSave = findViewById(R.id.add_tr_btn_save);
        btnRememberDestination=findViewById((R.id.btnRememberDestination));
        addCategoryAdapter(transaction);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validate()){
                    createFromViews();
                    transaction.setOwner_id(owner_id);
                    intent.putExtra(TRANSACTION_KEY, transaction);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });
    }

    private boolean validate() {
        if (tietDate.getText() == null || tietDate.getText().toString().trim().isEmpty()
                || DateConverter.fromString(tietDate.getText().toString()) == null) {
            Toast.makeText(getApplicationContext(),
                    R.string.add_transaction_date_error_message,
                    Toast.LENGTH_SHORT)
                    .show();
            return false;
        }
        if (tietAmount.getText() == null || tietAmount.getText().toString().isEmpty()
                || Double.parseDouble(tietAmount.getText().toString()) < 0) {
            Toast.makeText(getApplicationContext(),
                    R.string.add_transaction_amount_error_message,
                    Toast.LENGTH_SHORT)
                    .show();
            return false;
        }
        if (tietDescription.getText() == null ||  tietDescription.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(),
                    R.string.add_transaction_description_error_message,
                    Toast.LENGTH_SHORT)
                    .show();
            return false;
        }
        if (tietPerson.getText() == null ||  tietPerson.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(),
                    R.string.add_transaction_description_error_message,
                    Toast.LENGTH_SHORT)
                    .show();
            return false;
        }
        return true;
    }


    private void createFromViews() {
        Date date = DateConverter.fromString(tietDate.getText().toString());
        String category = spnCategory.getSelectedItem().toString();
        Double amount = Double.parseDouble(tietAmount.getText().toString());
        String description = tietDescription.getText().toString();
        String person = tietPerson.getText().toString();
        if (transaction == null) {
            transaction = new Transaction(amount,description,category,person,date);
        } else {
            transaction.setAmount(amount);
            transaction.setDescription(description);
            transaction.setCategory(category);
            transaction.setDate(date);
            transaction.setPerson(person);
        }
    }

    private void addCategoryAdapter(Transaction transaction) {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getApplicationContext(),
                R.array.add_transaction_category_values,
                android.R.layout.simple_spinner_dropdown_item);
        spnCategory.setAdapter(adapter);
    }






}
