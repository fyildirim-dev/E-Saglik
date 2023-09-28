package com.example.saglik;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

public class LoginActivity extends AppCompatActivity {

    EditText edtTc, edtPass;
    TextView btnKaydol, btnForgotPass;
    Button btnGiris;
    CheckBox remember;
    static public String tc;
    static public String ip = "192.168.1.33";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}

        btnKaydol = findViewById(R.id.textView);
        edtTc = findViewById(R.id.tc);
        edtPass = findViewById(R.id.pass);
        btnGiris = findViewById(R.id.btnGiris);
        remember = findViewById(R.id.checkBox);
        btnForgotPass = findViewById(R.id.forgotpassword);

        SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
        SharedPreferences giristc = getSharedPreferences("checkbox1", MODE_PRIVATE);
        String checkbox = preferences.getString("remember", "");
        String tcc = giristc.getString("tcc", "");


        if (checkbox.equals("true")){
            tc = tcc;
            Intent isim = new Intent(LoginActivity.this, MainPageActivity.class);
            startActivity(isim);
        }

        btnGiris.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tcTutucu = edtTc.getText().toString();
                tc = tcTutucu;
                String sifreTutucu = edtPass.getText().toString();

                if(tcTutucu.isEmpty() || sifreTutucu.isEmpty()){
                    Toast.makeText(LoginActivity.this, "Boş Alan Bırakmayınız..", Toast.LENGTH_LONG).show();
                }
                else if(tcTutucu.length() != 11){
                    Toast.makeText(LoginActivity.this, "Kimlik numaranız 11 haneli olmalıdır!", Toast.LENGTH_LONG).show();
                }
                else{
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            String[] field = new String[2];
                            field[0] = "tc";
                            field[1] = "sifre";

                            String[] data = new String[2];
                            data[0] = tcTutucu;
                            data[1] = sifreTutucu;
                            PutData putData = new PutData("http://"+ip+"/Saglik/login.php", "POST", field, data);
                            if (putData.startPut()) {
                                if (putData.onComplete()) {
                                    String result = putData.getResult();
                                    if(result.equals("Login Success")){
                                        Toast.makeText(LoginActivity.this, "Giriş Başarılı!", Toast.LENGTH_LONG).show();
                                        Intent isim = new Intent(LoginActivity.this, MainPageActivity.class);
                                        startActivity(isim);
                                    }
                                    else{
                                        Toast.makeText(LoginActivity.this, "Giriş yapılamadı. Tekrar deneyiniz!", Toast.LENGTH_LONG).show();
                                    }
                                }
                            }
                        }
                    });

                }
            }
        });

        remember.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (compoundButton.isChecked()){
                    SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
                    SharedPreferences giristc = getSharedPreferences("checkbox1", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("remember", "true");
                    editor.apply();
                    SharedPreferences.Editor editor1 = giristc.edit();
                    String tcTutucu = edtTc.getText().toString();
                    editor1.putString("tcc", tcTutucu);
                    editor1.apply();

                    Toast.makeText(LoginActivity.this, "Kaydedildi", Toast.LENGTH_SHORT).show();
                }
                else if (!compoundButton.isChecked()){
                    SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
                    SharedPreferences giristc = getSharedPreferences("checkbox1", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("remember", "false");
                    editor.apply();
                    SharedPreferences.Editor editor1 = giristc.edit();
                    editor1.putString("tcc", "");
                    editor1.apply();

                    Toast.makeText(LoginActivity.this, "İptal Edildi", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnKaydol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent isim = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(isim);
            }
        });

        btnForgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent isim = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
                startActivity(isim);
            }
        });
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);
    }
}