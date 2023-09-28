package com.example.saglik;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.HorizontalScrollView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.OffsetTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import static com.example.saglik.LoginActivity.tc;
import static com.example.saglik.LoginActivity.ip;
import static com.example.saglik.ProfilActivity.gece;

import pl.droidsonroids.gif.GifImageView;


public class MainPageActivity extends AppCompatActivity {

    private DatePickerDialog datePickerDialog;
    Button dateButton, timeButton;
    int hour, minute;
    GifImageView gifimage;
    Button btnFilter, btnFilter0, btnFilter1, btnFilter2, btnFilter3, btnFilter4, btnFilter5;
    RecyclerView recyclerView;
    DoctorRVAdapter adapter;
    ArrayList<Doctor> doctorList;
    HorizontalScrollView filtreler;
    public static String receiver, receiverName, policlinic;

    @SuppressLint("NonConstantResourceId")
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainmenu);
        initDatePicker();

        gifimage = findViewById(R.id.gifimage);
        dateButton = findViewById(R.id.date);
        dateButton.setText(getTodaysDate());
        timeButton = findViewById(R.id.time);
        timeButton.setText(getTodaysTime());

        btnFilter = findViewById(R.id.btnFilter);
        btnFilter0 = findViewById(R.id.btnFilter0);
        btnFilter1 = findViewById(R.id.btnFilter1);
        btnFilter2 = findViewById(R.id.btnFilter2);
        btnFilter3 = findViewById(R.id.btnFilter3);
        btnFilter4 = findViewById(R.id.btnFilter4);
        btnFilter5 = findViewById(R.id.btnFilter5);

        btnFilter.setBackgroundColor(Color.TRANSPARENT);
        btnFilter0.setBackgroundColor(Color.TRANSPARENT);
        btnFilter1.setBackgroundColor(Color.TRANSPARENT);
        btnFilter2.setBackgroundColor(Color.TRANSPARENT);
        btnFilter3.setBackgroundColor(Color.TRANSPARENT);
        btnFilter4.setBackgroundColor(Color.TRANSPARENT);
        btnFilter5.setBackgroundColor(Color.TRANSPARENT);

        recyclerView = findViewById(R.id.doctorsRV);
        doctorList = new ArrayList<>();

        btnFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                policlinic = btnFilter.getText().toString();
                getPoliclinic();
            }
        });
        btnFilter0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                policlinic = btnFilter0.getText().toString();
                getPoliclinic();
            }
        });
        btnFilter1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                policlinic = btnFilter1.getText().toString();
                getPoliclinic();
            }
        });
        btnFilter2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                policlinic = btnFilter2.getText().toString();
                getPoliclinic();
            }
        });
        btnFilter3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                policlinic = btnFilter3.getText().toString();
                getPoliclinic();
            }
        });
        btnFilter4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                policlinic = btnFilter4.getText().toString();
                getPoliclinic();
            }
        });
        btnFilter5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                policlinic = btnFilter5.getText().toString();
                getPoliclinic();
            }
        });

        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}

        Boolean doctormu = true;

        String[] field = new String[1];
        field[0] = "tc";

        String[] data = new String[1];
        data[0] = tc;

        PutData putData = new PutData("http://"+ip+"/Saglik/userOrDoctor.php", "POST", field, data);
        if (putData.startPut()) {
            if (putData.onComplete()) {
                String result = putData.getResult();
                if (result.equals("1")){
                    doctormu = true;
                    ViewGroup.LayoutParams params=recyclerView.getLayoutParams();
                    params.height=820;
                    recyclerView.setLayoutParams(params);
                    getUser();
                }
                else{
                    doctormu = false;
                    filtreler = findViewById(R.id.horizontalScrollView);
                    filtreler.setVisibility(View.VISIBLE);
                    getDoctor();
                }
            }
        }

        Boolean finalDoctormu = doctormu;
        gifimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (finalDoctormu){
                    ViewGroup.LayoutParams params=recyclerView.getLayoutParams();
                    params.height=820;
                    recyclerView.setLayoutParams(params);
                    getUser();
                }
                else{
                    filtreler = findViewById(R.id.horizontalScrollView);
                    filtreler.setVisibility(View.VISIBLE);
                    getDoctor();
                }
            }
        });

        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                receiver = doctorList.get(position).getTc();
                receiverName = doctorList.get(position).getAd() + " " + doctorList.get(position).getSoyad();
                Intent isim = new Intent(MainPageActivity.this, MessageActivity.class);
                startActivity(isim);
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setSelectedItemId(R.id.bottom_home);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.bottom_home:
                    return true;
                case R.id.bottom_note:
                    startActivity(new Intent(getApplicationContext(), HastalikActivity.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    finish();
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
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (gece){
            gifimage.setImageResource(R.drawable.night);
        }
        else{
            gifimage.setImageResource(R.drawable.building);
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private String getTodaysTime() {
        OffsetTime offset = OffsetTime.now();
        String saat = "";
        if (offset.getHour() < 10 && offset.getMinute() < 10)
            saat = "0" + String.valueOf(offset.getHour() + ":0" + offset.getMinute());
        else if (offset.getMinute() < 10)
            saat = String.valueOf(offset.getHour() + ":0" + offset.getMinute());
        else if (offset.getHour() < 10)
            saat = "0" + String.valueOf(offset.getHour() + ":" + offset.getMinute());
        else
            saat = String.valueOf(offset.getHour() + ":" + offset.getMinute());

        return saat;
    }

    private String getTodaysDate() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month, year);
    }

    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String date = makeDateString(day, month, year);
                dateButton.setText(date);
            }
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int style = AlertDialog.THEME_HOLO_LIGHT;

        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
        datePickerDialog.getDatePicker().setMinDate(cal.getTimeInMillis());
    }

    private String makeDateString(int day, int month, int year) {
        return day + " " + getMonthFormat(month) + " " + year;
    }

    private String getMonthFormat(int month) {
        if(month == 1)
            return "OCA";
        if(month == 2)
            return "ŞUB";
        if(month == 3)
            return "MAR";
        if(month == 4)
            return "NİS";
        if(month == 5)
            return "MAY";
        if(month == 6)
            return "HAZ";
        if(month == 7)
            return "TEM";
        if(month == 8)
            return "AĞU";
        if(month == 9)
            return "EYL";
        if(month == 10)
            return "EKİ";
        if(month == 11)
            return "KAS";
        if(month == 12)
            return "ARA";

        return "OCA";
    }

    public void openDatePicker(View view) {
        datePickerDialog.show();
    }

    public void popTimePicker(View view) {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {

                hour = selectedHour;
                minute = selectedMinute;
                timeButton.setText(String.format(Locale.getDefault(), "%02d:%02d", hour, minute));
            }
        };

        int style = AlertDialog.THEME_HOLO_LIGHT;
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, style, onTimeSetListener, hour, minute, true);
        timePickerDialog.show();
    }

    public void getDoctor(){
        doctorList.clear();
        RequestQueue queue= Volley.newRequestQueue(MainPageActivity.this);
        String url="http://"+ip+"/Saglik/getDoctors.php";
        StringRequest stringRequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray arr = new JSONArray(response);
                    for(int i=0;i<arr.length();i++) {
                        JSONObject object1=arr.getJSONObject(i);
                        doctorList.add(new Doctor(object1.getString("tc"),object1.getString("ad"),object1.getString("soyad"),object1.getString("email")));
                    }
                    adapter = new DoctorRVAdapter(MainPageActivity.this, doctorList,"","", "", "");
                    recyclerView = findViewById(R.id.doctorsRV);
                    recyclerView.setHasFixedSize(true);
                    LinearLayoutManager lm = new LinearLayoutManager(MainPageActivity.this);
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
                Toast.makeText(MainPageActivity.this,"Error in c=vollleyy",Toast.LENGTH_SHORT).show();
            }

        });

        queue.add(stringRequest);

    }

    public void getUser(){
        doctorList.clear();
        RequestQueue queue= Volley.newRequestQueue(MainPageActivity.this);
        String url="http://"+ip+"/Saglik/getUsers.php";
        StringRequest stringRequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray arr = new JSONArray(response);
                    for(int i=0;i<arr.length();i++) {
                        JSONObject object1=arr.getJSONObject(i);
                        doctorList.add(new Doctor(object1.getString("tc"),object1.getString("ad"),object1.getString("soyad"),object1.getString("email")));

                    }
                    adapter = new DoctorRVAdapter(MainPageActivity.this, doctorList,"","", "", "");
                    recyclerView = findViewById(R.id.doctorsRV);
                    recyclerView.setHasFixedSize(true);
                    LinearLayoutManager lm = new LinearLayoutManager(MainPageActivity.this);
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
                Toast.makeText(MainPageActivity.this,"Error in c=vollleyy",Toast.LENGTH_SHORT).show();
            }

        });

        queue.add(stringRequest);

    }

    public void getPoliclinic(){
        doctorList.clear();
        RequestQueue queue= Volley.newRequestQueue(MainPageActivity.this);
        String url="http://"+ip+"/Saglik/getPoliclinic.php?policlinic="+policlinic;
        StringRequest stringRequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray arr = new JSONArray(response);
                    for(int i=0;i<arr.length();i++) {
                        JSONObject object1=arr.getJSONObject(i);
                        doctorList.add(new Doctor(object1.getString("tc"),object1.getString("ad"),object1.getString("soyad"),object1.getString("email")));
                    }
                    adapter = new DoctorRVAdapter(MainPageActivity.this, doctorList,"","", "", "");
                    recyclerView = findViewById(R.id.doctorsRV);
                    recyclerView.setHasFixedSize(true);
                    LinearLayoutManager lm = new LinearLayoutManager(MainPageActivity.this);
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
                Toast.makeText(MainPageActivity.this,"Error in c=vollleyy",Toast.LENGTH_SHORT).show();
            }

        });

        queue.add(stringRequest);

    }
}