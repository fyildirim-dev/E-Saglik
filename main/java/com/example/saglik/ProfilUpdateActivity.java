package com.example.saglik;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import static com.example.saglik.LoginActivity.tc;
import static com.example.saglik.LoginActivity.ip;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ProfilUpdateActivity extends AppCompatActivity {

    EditText edtTc, edtSifre, edtAd, edtSoyad, edtEmail;
    Button btnUpdate;
    TextView btnProfil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil_update);

        edtAd = findViewById(R.id.ad);
        edtSoyad = findViewById(R.id.soyad);
        edtEmail = findViewById(R.id.email);
        edtTc = findViewById(R.id.tc);
        edtSifre = findViewById(R.id.passw);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnProfil = findViewById(R.id.textView4);

        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}

        btnProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent isim = new Intent(ProfilUpdateActivity.this, ProfilActivity.class);
                startActivity(isim);
            }
        });

        edtTc.setText(tc);
        getUser();

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String adTutucu = edtAd.getText().toString();
                String soyadTutucu = edtSoyad.getText().toString();
                String emailTutucu = edtEmail.getText().toString();
                String sifreTutucu = edtSifre.getText().toString();

                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        String[] field = new String[5];
                        field[0] = "tc";
                        field[1] = "ad";
                        field[2] = "soyad";
                        field[3] = "email";
                        field[4] = "sifre";

                        String[] data = new String[5];
                        data[0] = tc;
                        data[1] = adTutucu;
                        data[2] = soyadTutucu;
                        data[3] = emailTutucu;
                        data[4] = sifreTutucu;
                        PutData putData = new PutData("http://"+ip+"/Saglik/updateProfil.php", "POST", field, data);
                        if (putData.startPut()) {
                            if (putData.onComplete()) {
                                String result = putData.getResult();
                                if(result.equals("Profil Update Success")){
                                    Toast.makeText(ProfilUpdateActivity.this, "Bilgiler Güncellendi", Toast.LENGTH_LONG).show();
                                    Intent isim = new Intent(ProfilUpdateActivity.this, ProfilActivity.class);
                                    startActivity(isim);
                                }
                                else{
                                    Toast.makeText(ProfilUpdateActivity.this, "Bilgiler güncellenemedi. Tekrar deneyiniz!", Toast.LENGTH_LONG).show();
                                }
                            }
                        }
                    }
                });
            }
        });

    }

    public void getUser(){
        RequestQueue queue= Volley.newRequestQueue(ProfilUpdateActivity.this);
        String url="http://"+ip+"/Saglik/getProfil.php?tc="+tc;
        StringRequest stringRequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray arr = new JSONArray(response);
                    for(int i=0;i<arr.length();i++) {
                        JSONObject object1=arr.getJSONObject(i);
                        edtAd.setText(object1.getString("ad"));
                        edtSoyad.setText(object1.getString("soyad"));
                        edtEmail.setText(object1.getString("email"));
                    }

                } catch (JSONException e) {
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ProfilUpdateActivity.this,"Error in c=vollleyy",Toast.LENGTH_SHORT).show();
            }

        });

        queue.add(stringRequest);

    }
}