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
import com.example.dam_proiect_var_activity.model.PartnerBank;

import java.util.List;

public class PartnerBankAdapter extends ArrayAdapter<PartnerBank> {

    private Context context;
    private List<PartnerBank> banks;
    private LayoutInflater inflater;
    private int resource;

    public PartnerBankAdapter(@NonNull Context context, int resource, @NonNull List<PartnerBank> objects, LayoutInflater inflater) {
        super(context, resource, objects);
        this.context = context;
        this.banks = objects;
        this.inflater = inflater;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = inflater.inflate(resource,parent,false);
        PartnerBank bank = banks.get(position);
        if(bank != null){
            addBankName(view, bank.getBankName());
            addNrEmployee(view, bank.getNrEmployee());
            addOrigin(view, bank.getMainOffice().getOrigin());
            addNetIncome(view, bank.getMainOffice().getNetIncome());
            addName(view, bank.getMainOffice().getBankManager().getName());
            addAge(view, bank.getMainOffice().getBankManager().getAge());
            addNetWorth(view, bank.getMainOffice().getBankManager().getNetWorth());
        }
        return view;
    }

    private void addNetWorth(View view, int netWorth) {
        TextView textView = view.findViewById(R.id.bank_view_netWorth);
        populateTVContent(String.valueOf(netWorth), textView);
    }

    private void addAge(View view, int age) {
        TextView textView = view.findViewById(R.id.bank_view_age);
        populateTVContent(String.valueOf(age), textView);
    }

    private void addName(View view, String name) {
        TextView textView = view.findViewById(R.id.bank_view_name);
        populateTVContent(name, textView);
    }

    private void addNetIncome(View view, double netIncome) {
        TextView textView = view.findViewById(R.id.bank_view_netIncome);
        populateTVContent(String.valueOf(netIncome), textView);
    }

    private void addOrigin(View view, String origin) {
        TextView textView = view.findViewById(R.id.bank_view_origin);
        populateTVContent(origin, textView);
    }

    private void addNrEmployee(View view, int nrEmployee) {
        TextView textView = view.findViewById(R.id.bank_view_employee);
        populateTVContent(String.valueOf(nrEmployee), textView);
    }

    private void addBankName(View view, String bankName) {
        TextView textView = view.findViewById(R.id.bank_view_bankName);
        populateTVContent(bankName, textView);
    }


    private void populateTVContent(String val, TextView textView) {
        if(val != null && !val.isEmpty()){
            textView.setText(val);
        }else{
            textView.setText(R.string.lv_card_row_default_value);
        }
    }
}
