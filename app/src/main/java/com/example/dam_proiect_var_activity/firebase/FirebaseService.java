package com.example.dam_proiect_var_activity.firebase;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.dam_proiect_var_activity.asyncTask.Callback;
import com.example.dam_proiect_var_activity.model.DiscountCoupon;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FirebaseService {

    public static final String COUPON_TABLE_NAME = "coupons";
    private  static FirebaseService firebaseService;
    private DatabaseReference database;

    private FirebaseService(){
        //acces firebase prin FirebaseDatabase.getInstance
        //initializare conexiune nod referit prin getReference(COUPON_TABLE_NAME)
        database = FirebaseDatabase.getInstance()
                .getReference(COUPON_TABLE_NAME);
    }

    public static FirebaseService getInstance(){
        if(firebaseService == null){
            synchronized (FirebaseService.class){
                if(firebaseService == null){
                    firebaseService = new FirebaseService();
                }
            }
        }
        return firebaseService;
    }

    public void upsert(DiscountCoupon coupon){
        if(coupon == null){
            return;
        }
        //verificam daca obiectul coupon nu are id
        if(coupon.getId() == null || coupon.getId().trim().isEmpty()){
            //adaugam o cheie noua in firebase, mare atentie aceasta nu contine si valorile obiectului coupon
            String id = database.push().getKey();
            coupon.setId(id);
        }
        //ne pozitionam pe un copil din coupons (cel pe care l-am adaugat  daca coupon.getId == null,
        //altfel pe cel pe care l-am primit in obiectul coupon)
        //setValue asigura adaugarea/suprascrierea informatiilor stocate in copilul pozitionat
        database.child(coupon.getId()).setValue(coupon);
    }

    public void delete(DiscountCoupon coupon){
        if (coupon == null || coupon.getId() == null
                        || coupon.getId().trim().isEmpty()){
            return;
        }
        //ne pozitionam pe un copil din coaches (cel pe care l-am primit in obiectul coupon)
        //removeValue asigura stergerea informatiilor stocate in copilul pozitionat
        database.child(coupon.getId()).removeValue();
    }

    public void attachDataChangeEventListener(final Callback<List<DiscountCoupon>> callback) {
        //evenimentul este atasat la nivel de coupons (reference) prin urmare asculta orice modificare
        // de insert/update/delete executata asupra acestui nod
        //apelul metodelor upsert si delete de mai sus forteaza primirea unei notificari de la Firebase in acest eveniment
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<DiscountCoupon> coupons = new ArrayList<>();
                for (DataSnapshot data : snapshot.getChildren()) {
                    DiscountCoupon coupon = data.getValue(DiscountCoupon.class);
                    if (coupon != null) {
                        coupons.add(coupon);
                    }
                }
                //trimitem lista catre activitate prin intermediul callback-ului
                callback.runResultOnUiThread(coupons);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.i("FirebaseService", "Data is not available");
            }
        });
    }


}
