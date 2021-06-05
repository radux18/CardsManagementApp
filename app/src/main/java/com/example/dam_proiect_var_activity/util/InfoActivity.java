package com.example.dam_proiect_var_activity.util;

import android.os.Bundle;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.dam_proiect_var_activity.R;

public class InfoActivity extends AppCompatActivity {
    private RatingBar ratingBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        Toast.makeText(getApplicationContext(), R.string.info_msgThanks,Toast.LENGTH_SHORT).show();
        ratingBar = findViewById(R.id.rb_info_rating);

    }
}
