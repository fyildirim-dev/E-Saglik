package com.example.saglik;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.vishnusivadas.advanced_httpurlconnection.PutData;
import static com.example.saglik.LoginActivity.ip;

public class RegisterActivity extends AppCompatActivity {

    EditText edtTc, edtSifre, edtAd, edtSoyad, edtEmail, edtDoktorKod;
    Button btnKayit;
    TextView btnGiris, btnDoktor, name;
    AutoCompleteTextView edtPoliklinik;
    String[] secenekler = {"Dahiliye", "Cildiye", "Diyet", "Çocuk", "Psikoloji", "Kardiyoloji", "Radyoloji"};
    ArrayAdapter<String> arrayadp;
    String item = "";
    TextInputLayout menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}

        edtAd = findViewById(R.id.ad);
        edtSoyad = findViewById(R.id.soyad);
        edtEmail = findViewById(R.id.email);
        edtTc = findViewById(R.id.tc);
        edtSifre = findViewById(R.id.passw);
        btnKayit = findViewById(R.id.btnKayit);
        btnGiris = findViewById(R.id.textView);
        btnDoktor = findViewById(R.id.doktor);
        edtDoktorKod = findViewById(R.id.doktorkodu);
        name = findViewById(R.id.name);
        menu = findViewById(R.id.menu);
        edtPoliklinik = findViewById(R.id.autoCompleteTextView);

        edtPoliklinik.setDropDownBackgroundDrawable(getResources().getDrawable(R.drawable.border));
        arrayadp = new ArrayAdapter<>(getApplicationContext(), R.layout.item_autocomplete, R.id.tvCustom, secenekler);
        edtPoliklinik.setAdapter(arrayadp);

        edtPoliklinik.setOnItemClickListener((parent, arg1, pos, id) -> {
            item = (String) parent.getItemAtPosition(pos);
        });

        btnKayit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String adTutucu = edtAd.getText().toString();
                String soyadTutucu = edtSoyad.getText().toString();
                String emailTutucu = edtEmail.getText().toString();
                String tcTutucu = edtTc.getText().toString();
                String sifreTutucu = edtSifre.getText().toString();
                String kodTutucu = edtDoktorKod.getText().toString();
                String poliklinik = item;

                if(tcTutucu.isEmpty() || sifreTutucu.isEmpty() || adTutucu.isEmpty() || soyadTutucu.isEmpty() || emailTutucu.isEmpty()){
                    Toast.makeText(RegisterActivity.this, "Boş Alan Bırakmayınız..", Toast.LENGTH_LONG).show();
                }
                else if(!emailTutucu.contains("@")){
                    Toast.makeText(RegisterActivity.this, "Doğru Mail Adresini Giriniz!", Toast.LENGTH_LONG).show();
                }
                else if(tcTutucu.length() != 11){
                    Toast.makeText(RegisterActivity.this, "Kimlik numaranız 11 haneli olmalıdır!", Toast.LENGTH_LONG).show();
                }
                else if(sifreTutucu.length() < 8){
                    Toast.makeText(RegisterActivity.this, "Sifreniz en az 8 haneli olmalıdır!", Toast.LENGTH_LONG).show();
                }
                else{
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
                            data[0] = tcTutucu;
                            data[1] = adTutucu;
                            data[2] = soyadTutucu;
                            data[3] = emailTutucu;
                            data[4] = sifreTutucu;
                            PutData putData = new PutData("http://"+ip+"/Saglik/signup.php", "POST", field, data);
                            if (putData.startPut()) {
                                if (putData.onComplete()) {
                                    String result = putData.getResult();
                                    if(result.equals("Sign Up Success")){
                                        Toast.makeText(RegisterActivity.this, "Kayıt Başarılı", Toast.LENGTH_LONG).show();
                                        Intent isim = new Intent(RegisterActivity.this, LoginActivity.class);
                                        startActivity(isim);
                                    }
                                    else{
                                        Toast.makeText(RegisterActivity.this, "Kayıt yapılamadı. Tekrar deneyiniz!", Toast.LENGTH_LONG).show();
                                    }
                                }
                            }
                        }
                    });

                    if (edtDoktorKod.getVisibility() == View.VISIBLE){
                        if(tcTutucu.isEmpty() || kodTutucu.isEmpty() || poliklinik.isEmpty()){
                            Toast.makeText(RegisterActivity.this, "Boş Alan Bırakmayınız", Toast.LENGTH_LONG).show();
                        }
                        else {
                            Handler handler1 = new Handler(Looper.getMainLooper());
                            handler1.post(new Runnable() {
                                @Override
                                public void run() {
                                    String[] field = new String[3];
                                    field[0] = "doctor";
                                    field[1] = "policlinic";
                                    field[2] = "doctorkod";

                                    String[] data = new String[3];
                                    data[0] = tcTutucu;
                                    data[1] = poliklinik;
                                    data[2] = kodTutucu;

                                    PutData putData = new PutData("http://"+ip+"/Saglik/signupPoliclinic.php", "POST", field, data);
                                    if (putData.startPut()) {
                                        if (putData.onComplete()) {
                                            String result = putData.getResult();
                                            System.out.println(result);
                                        }
                                    }
                                }
                            });
                        }
                    }
                }
            }
        });

        btnGiris.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent isim = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(isim);
            }
        });

        btnDoktor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edtDoktorKod.setVisibility(View.VISIBLE);
                menu.setVisibility(View.VISIBLE);
                btnDoktor.setVisibility(View.GONE);
                name.setVisibility(View.GONE);
            }
        });
    }

}