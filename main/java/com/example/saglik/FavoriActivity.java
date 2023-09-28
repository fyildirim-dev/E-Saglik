package com.example.saglik;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import static com.example.saglik.LoginActivity.ip;

import java.util.ArrayList;

public class FavoriActivity extends AppCompatActivity {

    EditText text;
    AppCompatButton btnDoktor;
    RecyclerView recyclerView;
    FavoriRVAdapter adapter;
    ArrayList<Favori> doctorList;
    String doctorName = "";

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favori);

        recyclerView = findViewById(R.id.pointRV);
        btnDoktor = findViewById(R.id.btnDoctor);
        text = findViewById(R.id.doctorname);

        doctorList = new ArrayList<>();

        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setSelectedItemId(R.id.bottom_favorite);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.bottom_home:
                    startActivity(new Intent(getApplicationContext(), MainPageActivity.class));
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                    finish();
                    return true;
                case R.id.bottom_note:
                    startActivity(new Intent(getApplicationContext(), HastalikActivity.class));
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                    finish();
                    return true;
                case R.id.bottom_favorite:
                    return true;
                case R.id.bottom_profile:
                    startActivity(new Intent(getApplicationContext(), ProfilActivity.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    finish();
                    return true;
            }
            return false;
        });

        getDoctor();

        btnDoktor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doctorName = text.getText().toString();
                if(doctorName.isEmpty()){
                    getDoctor();
                }
                else
                    getDoctorName();
            }
        });

    }


    public void getDoctor(){
        doctorList.clear();
        RequestQueue queue= Volley.newRequestQueue(FavoriActivity.this);
        String url="http://"+ip+"/Saglik/getPointDoctors.php";
        StringRequest stringRequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray arr = new JSONArray(response);
                    for(int i=0;i<arr.length();i++) {
                        JSONObject object1=arr.getJSONObject(i);
                        doctorList.add(new Favori(object1.getString("tc"),object1.getString("ad"),object1.getString("soyad"),object1.getString("email"),object1.getString("point")));
                    }
                    adapter = new FavoriRVAdapter(FavoriActivity.this, doctorList,"","", "", "", "");
                    recyclerView = findViewById(R.id.pointRV);
                    recyclerView.setHasFixedSize(true);
                    LinearLayoutManager lm = new LinearLayoutManager(FavoriActivity.this);
                    recyclerView.setLayoutManager(lm);
                    recyclerView.setAdapter(adapter);

                } catch (JSONException e) {
                    adapter.clear();
                    recyclerView.setAdapter(adapter);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(FavoriActivity.this,"Error in c=vollleyy",Toast.LENGTH_SHORT).show();
            }

        });

        queue.add(stringRequest);

    }

    public void getDoctorName(){
        doctorList.clear();
        RequestQueue queue= Volley.newRequestQueue(FavoriActivity.this);
        String url="http://"+ip+"/Saglik/getNameDoctorPoint.php?doctorName="+doctorName;
        StringRequest stringRequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray arr = new JSONArray(response);
                    for(int i=0;i<arr.length();i++) {
                        JSONObject object1=arr.getJSONObject(i);
                        doctorList.add(new Favori(object1.getString("tc"),object1.getString("ad"),object1.getString("soyad"),object1.getString("email"),object1.getString("point")));
                    }
                    adapter = new FavoriRVAdapter(FavoriActivity.this, doctorList,"","", "", "", "");
                    recyclerView = findViewById(R.id.pointRV);
                    recyclerView.setHasFixedSize(true);
                    LinearLayoutManager lm = new LinearLayoutManager(FavoriActivity.this);
                    recyclerView.setLayoutManager(lm);
                    recyclerView.setAdapter(adapter);

                } catch (JSONException e) {
                    adapter.clear();
                    recyclerView.setAdapter(adapter);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(FavoriActivity.this,"Error in c=vollleyy",Toast.LENGTH_SHORT).show();
            }

        });

        queue.add(stringRequest);

    }
}