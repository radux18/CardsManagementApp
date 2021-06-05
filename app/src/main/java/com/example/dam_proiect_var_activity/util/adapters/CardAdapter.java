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
import com.example.dam_proiect_var_activity.model.Card;

import java.util.List;

public class CardAdapter extends ArrayAdapter<Card> {

    private Context context;
    private List<Card> cards;
    private LayoutInflater inflater;
    private int resource;

    public CardAdapter(@NonNull Context context, int resource, @NonNull List<Card> objects, LayoutInflater inflater) {
        super(context, resource, objects);
        this.context = context;
        this.cards = objects;
        this.inflater = inflater;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
       View view = inflater.inflate(resource,parent,false);
       Card card = cards.get(position);
       if(card != null){
           setAccountType(view, card.getType());
           setCardSerialNo(view,card.getSerialNo());
           setAccountCurrentBalance(view, card.getCurrent_balance());
           setAccountFirstName(view, card.getFirstName());
           setAccountSecondName(view, card.getSecondName());
           setAccountExpirationMonth(view, card.getExpirationMonth());
           setAccountExpirationYear(view, card.getExpirationYear());
       }
       return view;
    }

    private void setAccountExpirationYear(View view, int expirationYear) {
        TextView textView = view.findViewById(R.id.card_lv_expirationYear);
        addTextViewContent(textView, String.valueOf(expirationYear));
    }

    private void setAccountExpirationMonth(View view, int expirationMonth) {
        TextView textView = view.findViewById(R.id.card_lv_expirationMonth);
         addTextViewContent(textView, String.valueOf(expirationMonth));

    }

    private void setAccountSecondName(View view, String secondName) {
        TextView textView = view.findViewById(R.id.card_lv_secondName);
        addTextViewContent(textView, secondName);
    }

    private void setAccountFirstName(View view, String firstName) {
        TextView textView = view.findViewById(R.id.card_lv_firstName);
        addTextViewContent(textView, firstName);
    }

    private void setAccountCurrentBalance(View view, Double current_balance) {
        TextView textView = view.findViewById(R.id.card_lv_currentBalance);
           String value = null;
           if ( current_balance != null){
               value = context.getString(R.string.lv_card_row_amount_value, current_balance.toString());
           }
        addTextViewContent(textView, value);
    }

    private void addTextViewContent(TextView textView, String value) {
        if (value != null && !value.isEmpty()) {
            textView.setText(value);
        } else {
            textView.setText(R.string.lv_card_row_default_value);
        }
    }

    private void setCardSerialNo(View view, Integer serialNo) {
        TextView textView = view.findViewById(R.id.card_lv_SerialNo);
        String value = null;
        if ( serialNo != null){
            value = context.getString(R.string.lv_card_row_serial_value, serialNo.toString());
        }
        addTextViewContent(textView, value);
    }

    private void setAccountType(View view, String type) {
        TextView textView = view.findViewById(R.id.card_lv_tv_type);
        addTextViewContent(textView, type);
    }

    

}
