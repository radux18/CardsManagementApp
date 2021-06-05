package com.example.dam_proiect_var_activity.util;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.dam_proiect_var_activity.MainActivity;
import com.example.dam_proiect_var_activity.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {

    private EditText mEmail,mPassword;
    private Button mLoginBtn;
    private TextView mCreateBtn;
    private ProgressBar progressBar;
    private FirebaseAuth fAuth;
    private Button btnRemember;
    private ImageButton imgBtnInfo;
    public static final String SHARED_PREF = "shared pref";
    public static final String EMAIL = "email";
    public static final String PASSWORD = "password";
    public static final String REMEMBER = "remember";
    private String email;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        fAuth = FirebaseAuth.getInstance();
        initcomponents();
        FirebaseApp.initializeApp(this);
        progressBar.setVisibility(View.INVISIBLE);
        mCreateBtn.setOnClickListener(createAccountEventListener());
        mLoginBtn.setOnClickListener(loginEventListener());
        btnRemember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
            }
        });
        loadData();
        updateViews();

    }

    private View.OnClickListener loginEventListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();

                //validari
                if(TextUtils.isEmpty(email)){
                    mEmail.setError("Email is required.");
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    mPassword.setError("Password is required.");
                    return;
                }
                if(password.length() < 6){
                    mPassword.setError("Password must have at least 6 Characters.");
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);

                //autentificare user
                fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            //logare cu succes
                            Toast.makeText(Login.this, "Login succes.", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        }else {
                            Toast.makeText(Login.this,"Error! " +task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });

            }
        };
    }

    private View.OnClickListener createAccountEventListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Register.class));
            }
        };
    }


    public void saveData(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EMAIL,mEmail.getText().toString());
        editor.putString(PASSWORD,mPassword.getText().toString());

        editor.apply();
    }
    public void loadData(){
        SharedPreferences sharedPreferences= getSharedPreferences(SHARED_PREF,MODE_PRIVATE);
        email= sharedPreferences.getString(EMAIL,"");
        password= sharedPreferences.getString(PASSWORD,"");
        //  btnRemember= sharedPreferences.getBoolean(REMEMBER,false);
    }

    public void updateViews(){
        mEmail.setText(email);
        mPassword.setText((password));
    }

    private void initcomponents() {
        imgBtnInfo = findViewById(R.id.ib_main_info);
        mEmail = findViewById(R.id.tiet_login_email);
        mPassword = findViewById(R.id.tiet_login_password);
        progressBar = findViewById(R.id.pb_login_progress);
        mLoginBtn = findViewById(R.id.btn_login_Log);
        mCreateBtn = findViewById(R.id.tv_login_newAccount);
        btnRemember= findViewById(R.id.btn_remember);

        imgBtnInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),InfoActivity.class);
                startActivity(intent);
            }
        });
    }
}