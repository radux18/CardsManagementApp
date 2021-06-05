package com.example.dam_proiect_var_activity.util;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.dam_proiect_var_activity.R;
import com.example.dam_proiect_var_activity.asyncTask.AsyncTaskRunner;
import com.example.dam_proiect_var_activity.asyncTask.Callback;
import com.example.dam_proiect_var_activity.model.PartnerBank;
import com.example.dam_proiect_var_activity.network.HttpManager;
import com.example.dam_proiect_var_activity.util.adapters.PartnerBankAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

public class PartnerBankActivity extends AppCompatActivity {
    private static final String URL_BANKS = "https://jsonkeeper.com/b/45WM";
    private AsyncTaskRunner asyncTaskRunner = new AsyncTaskRunner();
    private ListView lvPartnerBanks;

    private List<PartnerBank> banks = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partnerbank);
        initComponents();
        getBanksFromHttp();
    }

    private void initComponents() {
        lvPartnerBanks = findViewById(R.id.lv_banks);
        banksAdapter();
    }

    private void banksAdapter() {
        PartnerBankAdapter adapter = new PartnerBankAdapter(getApplicationContext(),
                R.layout.activity_bank_view, banks , getLayoutInflater());
        lvPartnerBanks.setAdapter(adapter);
    }

    private void getBanksFromHttp() {
        Callable<String> asyncOperation = new HttpManager(URL_BANKS);
        Callback<String> mainThreadOperation = getMainThreadOperationForBanks();
        asyncTaskRunner.executeAsync(asyncOperation,mainThreadOperation);
    }

    private Callback<String> getMainThreadOperationForBanks() {
        return new Callback<String>() {
            @Override
            public void runResultOnUiThread(String result) {
                banks.addAll(JsonParser.fromJson(result));
                notifyAdapter();
            }
        };
    }

    private void notifyAdapter() {
        ArrayAdapter adapter = (ArrayAdapter) lvPartnerBanks.getAdapter();
        adapter.notifyDataSetChanged();
    }


}
