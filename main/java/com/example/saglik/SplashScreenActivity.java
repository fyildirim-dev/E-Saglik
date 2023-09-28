package com.example.saglik;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ilksayfa);

        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                Intent isim = new Intent(SplashScreenActivity.this, LoginActivity.class);
                startActivity(isim);
            }
        }, 3500);

    }
}