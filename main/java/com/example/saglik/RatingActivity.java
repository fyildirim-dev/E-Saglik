package com.example.saglik;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.vishnusivadas.advanced_httpurlconnection.PutData;

import static com.example.saglik.LoginActivity.tc;
import static com.example.saglik.LoginActivity.ip;
import static com.example.saglik.MainPageActivity.receiver;
import static com.example.saglik.MessageActivity.rateUpdate;

public class RatingActivity extends AppCompatActivity {

    TextView textView;
    EditText mesaj;
    RatingBar ratingBar;
    Button btnSonlandir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);

        textView = findViewById(R.id.textView);
        mesaj = findViewById(R.id.mesaj);
        ratingBar = findViewById(R.id.ratingBar);
        btnSonlandir = findViewById(R.id.btnSonlandir);

        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),MessageActivity.class));
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                finish();
            }
        });

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                if (v < 1.5)
                    ratingBar.setProgressTintList(ColorStateList.valueOf(Color.rgb(225,32,37)));
                else if (v < 2.5)
                    ratingBar.setProgressTintList(ColorStateList.valueOf(Color.rgb(244,121,80)));
                else if (v < 3.5)
                    ratingBar.setProgressTintList(ColorStateList.valueOf(Color.rgb(252,176,64)));
                else if (v < 4.5)
                    ratingBar.setProgressTintList(ColorStateList.valueOf(Color.rgb(145,202,97)));
                else
                    ratingBar.setProgressTintList(ColorStateList.valueOf(Color.rgb(58,181,74)));

            }
        });

        btnSonlandir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (rateUpdate){
                    String text = mesaj.getText().toString();
                    String puan = String.valueOf(ratingBar.getRating());

                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            String[] field = new String[4];
                            field[0] = "patient";
                            field[1] = "doctor";
                            field[2] = "mesaj";
                            field[3] = "point";

                            String[] data = new String[4];
                            data[0] = tc;
                            data[1] = receiver;
                            data[2] = text;
                            data[3] = puan;

                            PutData putData = new PutData("http://"+ip+"/Saglik/updateMeet.php", "POST", field, data);
                            if (putData.startPut()) {
                                if (putData.onComplete()) {
                                    String result = putData.getResult();
                                    if(result.equals("Meeting Success")){
                                        Toast.makeText(RatingActivity.this, "Mesajınız Güncellenmiştir", Toast.LENGTH_LONG).show();
                                        Intent isim = new Intent(RatingActivity.this, MainPageActivity.class);
                                        startActivity(isim);
                                    }
                                    else{
                                        Toast.makeText(RatingActivity.this, "Mesaj İletilemedi!", Toast.LENGTH_LONG).show();
                                    }
                                }
                            }
                        }
                    });
                }
                else{
                    String text = mesaj.getText().toString();
                    String puan = String.valueOf(ratingBar.getRating());

                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            String[] field = new String[4];
                            field[0] = "patient";
                            field[1] = "doctor";
                            field[2] = "mesaj";
                            field[3] = "point";

                            String[] data = new String[4];
                            data[0] = tc;
                            data[1] = receiver;
                            data[2] = text;
                            data[3] = puan;

                            PutData putData = new PutData("http://"+ip+"/Saglik/postMeet.php", "POST", field, data);
                            if (putData.startPut()) {
                                if (putData.onComplete()) {
                                    String result = putData.getResult();
                                    if(result.equals("Meeting Success")){
                                        Toast.makeText(RatingActivity.this, "Mesajınız İletilmiştir", Toast.LENGTH_LONG).show();
                                        Intent isim = new Intent(RatingActivity.this, MainPageActivity.class);
                                        startActivity(isim);
                                    }
                                    else{
                                        Toast.makeText(RatingActivity.this, "Mesaj İletilemedi!", Toast.LENGTH_LONG).show();
                                    }
                                }
                            }
                        }
                    });
                }

            }
        });
    }
}