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
import com.example.dam_proiect_var_activity.model.DiscountCoupon;

import java.util.List;

public class CouponAdapter extends ArrayAdapter<DiscountCoupon> {

    private Context context;
    private int resource;
    private List<DiscountCoupon> coupons;
    private LayoutInflater inflater;

    public CouponAdapter(@NonNull Context context, int resource, @NonNull List<DiscountCoupon> objects, LayoutInflater inflater) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.coupons = objects;
        this.inflater = inflater;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = inflater.inflate(resource, parent, false);
        DiscountCoupon coupon = coupons.get(position);
        if (coupon != null) {
            addName(view, coupon.getName());
            addDiscount(view, coupon.getDiscount());
            addExpDate(view, coupon.getExpirationDate());
        }
        return view;
    }

    private void addExpDate(View view, String expirationDate) {
        TextView textView = view.findViewById(R.id.coupon_view_expDate);
        populateTextViewContent(textView, expirationDate);
    }

    private void addDiscount(View view, String discount) {
        TextView textView = view.findViewById(R.id.coupon_view_discount);
        populateTextViewContent(textView, discount);
    }

    private void addName(View view, String name) {
        TextView textView = view.findViewById(R.id.coupon_view_name);
        populateTextViewContent(textView, name);
    }

    private void populateTextViewContent(TextView textView, String value) {
        if (value != null && !value.isEmpty()) {
            textView.setText(value);
        } else {
            textView.setText(R.string.lv_card_row_default_value);
        }
    }

}
