package com.example.dam_proiect_var_activity.util;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.dam_proiect_var_activity.MainActivity;
import com.example.dam_proiect_var_activity.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class Register extends AppCompatActivity {
    private EditText mFullName, mEmail, mPassword1, mPassword2;
    private Button mRegisterBtn;
    private TextView mLoginBtn;

    private FirebaseFirestore fStore;
    private FirebaseAuth fAuth;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mFullName = findViewById(R.id.tiet_register_fullname);
        mEmail = findViewById(R.id.tiet_register_email);
        mPassword1 = findViewById(R.id.tiet_register_password1);
        mPassword2 = findViewById(R.id.tiet_register_password2);
        mRegisterBtn = findViewById(R.id.btn_register_register);
        mLoginBtn = findViewById(R.id.tv_register_text);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        progressBar = findViewById(R.id.pb_register_progress);

        if (fAuth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }

        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = mEmail.getText().toString().trim();
                String password1 = mPassword1.getText().toString().trim();
                String password2 = mPassword2.getText().toString().trim();
                final String fullName = mFullName.getText().toString();

                //validari
                if (TextUtils.isEmpty(fullName)) {
                    mFullName.setError("Name is required.");
                    return;
                }
                if (TextUtils.isEmpty(email)) {
                    mEmail.setError("Email is required.");
                    return;
                }
                if (TextUtils.isEmpty(password1)) {
                    mPassword1.setError("Password is required.");
                    return;
                }
                if (TextUtils.isEmpty(password2)) {
                    mPassword2.setError("Confirm password.");
                    return;
                }
                if (!password2.equals(password1)) {
                    mPassword2.setError("The password is not the same.");
                    return;
                }
                if (password1.length() < 6) {
                    mPassword1.setError("Password must have at least 6 Characters.");
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                //inregistrare user in baza de date
                fAuth.createUserWithEmailAndPassword(email, password1).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(Register.this, "User Created.", Toast.LENGTH_SHORT).show();
                            FirebaseUser user = fAuth.getCurrentUser();
                            startActivity(new Intent(getApplicationContext(), Login.class));
                        } else {
                            Toast.makeText(Register.this, "Error! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });

            }
        });

        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Login.class));
            }
        });


    }
}
