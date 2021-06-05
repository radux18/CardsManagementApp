package com.example.dam_proiect_var_activity.util.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.dam_proiect_var_activity.R;
import com.example.dam_proiect_var_activity.model.Transaction;
import com.example.dam_proiect_var_activity.util.DateConverter;

import java.util.Date;
import java.util.List;

public class TransactionAdapter extends ArrayAdapter<Transaction> {

    private Context context;
    private List<Transaction> transactions;
    private LayoutInflater inflater;
    private int resource;

    public TransactionAdapter(@NonNull Context context, int resource, @NonNull List<Transaction> objects, LayoutInflater inflater) {
        super(context, resource, objects);
        this.context = context;
        this.transactions = objects;
        this.inflater = inflater;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = inflater.inflate(resource, parent, false);
        Transaction transaction = transactions.get(position);
        if( transaction != null){
            setTransactionDescription(view, transaction.getDescription());
            setTransactionAmount(view, transaction.getAmount());
            setTransactionCategory(view, transaction.getCategory());
            setTransactionDate(view, transaction.getDate());
            setTransactionPerson(view, transaction.getPerson());
        }
        return view;

    }

    private void setTransactionPerson(View view, String person) {
        TextView tv = view.findViewById(R.id.tr_lv_Person);
        if(person != null)
            tv.setText(person);
    }

    private void setTransactionCategory(View view, String category) {
        TextView textView = view.findViewById(R.id.tr_lv_category);
        if(category != null){
            textView.setText(category);
        }
    }

    private void setTransactionDate(View view, Date date) {
        TextView tv = view.findViewById(R.id.tr_lv_date);
        tv.setText(DateConverter.fromDate(date));
    }

    private void setTransactionAmount(View view, Double amount) {
        TextView tv = view.findViewById(R.id.tr_lv_amount);
        String value = null;
        if(amount != null){
            value = context.getString(R.string.lv_transaction_amount_value, amount.toString());
        }
        tv.setText(value);
    }

    private void setTransactionDescription(View view, String description) {
        TextView tv = view.findViewById(R.id.tr_lv_description);
        if(description != null)
        tv.setText(description);
    }

}
