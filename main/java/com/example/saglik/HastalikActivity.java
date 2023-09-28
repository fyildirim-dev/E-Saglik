package com.example.saglik;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HastalikActivity extends AppCompatActivity {

    public ArrayList<Hastalik> hastaliklar;
    RecyclerView mRecyclerView;
    HastalikRVAdapter adapter;
    AppCompatButton btnHastalik;
    EditText aranan;
    ArrayList<String> letters = new ArrayList<String>();
    public String[] upLetter = new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hastalik);

        letters.addAll(Arrays.asList(upLetter));

        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}

        mRecyclerView = findViewById(R.id.hastalikRV);
        btnHastalik = findViewById(R.id.btnHastalik);
        aranan = findViewById(R.id.hastailac);

        hastaliklar = new ArrayList<>();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setSelectedItemId(R.id.bottom_note);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.bottom_home:
                    startActivity(new Intent(getApplicationContext(), MainPageActivity.class));
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                    finish();
                    return true;
                case R.id.bottom_note:
                    return true;
                case R.id.bottom_favorite:
                    startActivity(new Intent(getApplicationContext(), FavoriActivity.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    finish();
                    return true;
                case R.id.bottom_profile:
                    startActivity(new Intent(getApplicationContext(), ProfilActivity.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    finish();
                    return true;
            }
            return false;
        });

        btnHastalik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hastaliklar.clear();
                String ara = aranan.getText().toString();
                aranan.setText("");

                InputMethodManager imm = (InputMethodManager) HastalikActivity.this.getSystemService(HastalikActivity.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

                OkHttpClient client = new OkHttpClient();

                Request request = new Request.Builder()
                        .url("https://endlessmedicalapi1.p.rapidapi.com/GetOutcomes")
                        .get()
                        .addHeader("X-RapidAPI-Key", "ece2133608msh5d7115e9b548366p1aaaeejsnf00c7c441ab4")
                        .addHeader("X-RapidAPI-Host", "endlessmedicalapi1.p.rapidapi.com")
                        .build();


                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        if (response.isSuccessful()){
                            final String myResponse = response.body().string();
                            HastalikActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        JSONObject obj = new JSONObject(myResponse);
                                        JSONArray array = obj.getJSONArray("data");
                                        for (int i=0; i<array.length(); i++){
                                            String hastalik = array.getString(i);
                                            String deneme = "";
                                            if (hastalik.contains(ara)){
                                                for (int j=0;j<hastalik.length();j++){
                                                    if (j == hastalik.length()-1){
                                                        /*if (hastalik.charAt(j) == 'A'){
                                                            break;
                                                        }
                                                        else {*/
                                                            deneme += hastalik.charAt(j);
                                                            break;
                                                        //}
                                                    }
                                                    char c = hastalik.charAt(j);
                                                    char d = hastalik.charAt(j+1);
                                                    if (letters.contains(String.valueOf(c)) && letters.contains(String.valueOf(d))){
                                                        deneme += String.valueOf(c);
                                                    }
                                                    else{
                                                        if (!letters.contains(String.valueOf(c))){
                                                            if (letters.contains(String.valueOf(d))){
                                                                deneme += String.valueOf(c) + " ";
                                                            }
                                                            else{
                                                                deneme += String.valueOf(c);
                                                            }
                                                        }
                                                        else{
                                                            deneme += String.valueOf(c);
                                                        }
                                                    }
                                                }
                                                hastalik = deneme;
                                                hastaliklar.add(new Hastalik(hastalik));
                                            }
                                        }
                                        adapter = new HastalikRVAdapter(HastalikActivity.this, hastaliklar,"");
                                        mRecyclerView = findViewById(R.id.hastalikRV);
                                        mRecyclerView.setHasFixedSize(true);
                                        LinearLayoutManager lm = new LinearLayoutManager(HastalikActivity.this);
                                        mRecyclerView.setLayoutManager(lm);
                                        mRecyclerView.setAdapter(adapter);

                                    } catch (JSONException e) {
                                        adapter.clear();
                                        mRecyclerView.setAdapter(adapter);
                                    }
                                }
                            });
                        }
                        else{
                            System.out.println(response.code());
                        }
                    }
                });
            }
        });
    }
}