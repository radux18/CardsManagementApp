package com.example.dam_proiect_var_activity.util;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dam_proiect_var_activity.R;
import com.example.dam_proiect_var_activity.asyncTask.Callback;
import com.example.dam_proiect_var_activity.firebase.FirebaseService;
import com.example.dam_proiect_var_activity.model.DiscountCoupon;
import com.example.dam_proiect_var_activity.util.adapters.CouponAdapter;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

public class DiscountCouponsActivity extends AppCompatActivity {
    private TextInputEditText tietName;
    private TextInputEditText tietDiscount;
    private TextInputEditText tietExpDate;
    private Button btnSave;
    private Button btnDelete;
    private Button btnClear;

    private ListView lvCoupons;
    private List<DiscountCoupon> coupons = new ArrayList<>();

    private int selectedCouponIndex = -1;
    private FirebaseService firebaseService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coupon);
        initComponents();
        firebaseService = FirebaseService.getInstance();
        firebaseService.attachDataChangeEventListener(dataChangeCallback());
    }

    private void initComponents() {
        tietName = findViewById(R.id.coupon_tiet_name);
        tietExpDate = findViewById(R.id.coupon_tiet_expDate);
        tietDiscount = findViewById(R.id.coupon_tiet_discount);
        btnClear = findViewById(R.id.btn_coupon_clear);
        btnDelete = findViewById(R.id.btn_coupon_delete);
        btnSave = findViewById(R.id.btn_coupon_save);
        lvCoupons = findViewById(R.id.lv_coupons);
        setLvAdapter();

        btnClear.setOnClickListener(clearFieldsEventListener());
        btnDelete.setOnClickListener(deleteEventListener());
        btnSave.setOnClickListener(saveEventListener());
        lvCoupons.setOnItemClickListener(couponSelectEventListener());

    }

    private AdapterView.OnItemClickListener couponSelectEventListener() {
        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedCouponIndex = position;
                tietName.setText(coupons.get(selectedCouponIndex).getName());
                tietDiscount.setText(coupons.get(selectedCouponIndex).getDiscount());
                tietExpDate.setText(coupons.get(selectedCouponIndex).getExpirationDate());
            }
        };
    }

    private View.OnClickListener saveEventListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             //   if (validate()) {
                    DiscountCoupon coupon = createCouponFromView();
                    firebaseService.upsert(coupon);
             //   }
            }
        };
    }

    private DiscountCoupon createCouponFromView() {
        //selectedCouponIndex este o variabila care retine pozitia din ListView pe care a dat click
        // utilizatorul sau -1 in caz contrar
        //este important sa avem acest id pentru a putea executa corect logica din FirebaseService.upsert
        String id = selectedCouponIndex >= 0 ? coupons.get(selectedCouponIndex).getId() : null;
        String name = tietName.getText().toString();
        String discount = tietDiscount.getText().toString();
        String expDate = tietExpDate.getText().toString();
        return new DiscountCoupon(id,name,discount,expDate);
    }

    private View.OnClickListener deleteEventListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedCouponIndex != -1) {
                    firebaseService.delete(coupons.get(selectedCouponIndex));
                }
            }
        };
    }

    private View.OnClickListener clearFieldsEventListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearFields();
            }
        };
    }

    private void setLvAdapter() {
        CouponAdapter adapter = new CouponAdapter(getApplicationContext(), R.layout.activity_coupon_view,
                coupons, getLayoutInflater());
        lvCoupons.setAdapter(adapter);
    }

    private Callback<List<DiscountCoupon>> dataChangeCallback() {
        return new Callback<List<DiscountCoupon>>() {
            @Override
            public void runResultOnUiThread(List<DiscountCoupon> result) {
                //primim raspunsul de la attachDataChangeEventListener
                //declansat de fiecare data cand se produc modificari asupra bazei de date
                if (result != null) {
                    coupons.clear();
                    coupons.addAll(result);
                    notifyLvAdapter();
                    clearFields();
                }
            }
        };
    }

    private void clearFields() {
        tietName.setText(null);
        tietDiscount.setText(null);
        tietExpDate.setText(null);
    }

    private void notifyLvAdapter() {
        CouponAdapter adapter = (CouponAdapter) lvCoupons.getAdapter();
        adapter.notifyDataSetChanged();
    }

}
