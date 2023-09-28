package com.example.saglik;

import static com.example.saglik.LoginActivity.ip;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.vishnusivadas.advanced_httpurlconnection.PutData;

import java.util.Random;

public class ForgotPasswordActivity extends AppCompatActivity {

    EditText email, kod, tc;
    Button btnKod;
    TextView kodText;
    public static String ttc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);

        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}

        email = findViewById(R.id.email);
        kod = findViewById(R.id.kod);
        btnKod = findViewById(R.id.btnKod);
        tc = findViewById(R.id.tc);
        kodText = findViewById(R.id.textView4);


        btnKod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (kod.getVisibility() == View.VISIBLE){
                    if (email.getText().toString().isEmpty() || kod.getText().toString().isEmpty()){
                        Toast.makeText(ForgotPasswordActivity.this, "Boş Alan Bırakmayınız.", Toast.LENGTH_LONG).show();
                    }
                    else if(!email.getText().toString().contains("@")){
                        Toast.makeText(ForgotPasswordActivity.this, "Doğru Mail Adresini Giriniz!", Toast.LENGTH_LONG).show();
                    }
                    else if(tc.getText().toString().length() != 11){
                        Toast.makeText(ForgotPasswordActivity.this, "Kimlik numaranız 11 haneli olmalıdır!", Toast.LENGTH_LONG).show();
                    }
                    else{
                        String[] field = new String[2];
                        field[0] = "tc";
                        field[1] = "kod";

                        String[] data = new String[2];
                        data[0] = tc.getText().toString();
                        data[1] = kod.getText().toString();

                        PutData putData = new PutData("http://"+ip+"/Saglik/kodcheck.php", "POST", field, data);
                        if (putData.startPut()) {
                            if (putData.onComplete()) {
                                String result = putData.getResult();
                                if(result.equals("Code Correct")){
                                    ttc = tc.getText().toString();
                                    Intent isim = new Intent(ForgotPasswordActivity.this, PasswordUpdateActivity.class);
                                    startActivity(isim);
                                }
                                else{
                                    Toast.makeText(ForgotPasswordActivity.this, "Yanlış Kod Girdiniz!", Toast.LENGTH_LONG).show();
                                }
                            }
                        }
                    }
                }
                else {
                    if (email.getText().toString().isEmpty()){
                        Toast.makeText(ForgotPasswordActivity.this, "Boş Alan Bırakmayınız.", Toast.LENGTH_LONG).show();
                    }
                    else if(!email.getText().toString().contains("@")){
                        Toast.makeText(ForgotPasswordActivity.this, "Doğru Mail Adresini Giriniz!", Toast.LENGTH_LONG).show();
                    }
                    else if(tc.getText().toString().length() != 11){
                        Toast.makeText(ForgotPasswordActivity.this, "Kimlik numaranız 11 haneli olmalıdır!", Toast.LENGTH_LONG).show();
                    }
                    else{
                        String[] fieldd = new String[2];
                        fieldd[0] = "tc";
                        fieldd[1] = "kod";

                        String[] dataa = new String[2];
                        dataa[0] = tc.getText().toString();
                        dataa[1] = kod.getText().toString();

                        PutData putDataa = new PutData("http://"+ip+"/Saglik/kodvarmi.php", "POST", fieldd, dataa);
                        if (putDataa.startPut()) {
                            if (putDataa.onComplete()) {
                                String resultt = putDataa.getResult();
                                if(resultt.equals("Code Var")){
                                    Toast.makeText(ForgotPasswordActivity.this, "Daha önce kod gönderilmiş!", Toast.LENGTH_LONG).show();
                                    kod.setVisibility(View.VISIBLE);
                                    btnKod.setText("Şifre Değiştir");
                                }
                                else{
                                    Random r = new Random();
                                    int low = 100000;
                                    int high = 999999;
                                    int code = r.nextInt(high-low) + low;

                                    String[] field = new String[2];
                                    field[0] = "tc";
                                    field[1] = "kod";

                                    String[] data = new String[2];
                                    data[0] = tc.getText().toString();
                                    data[1] = String.valueOf(code);
                                    PutData putData = new PutData("http://"+ip+"/Saglik/kodsend.php", "POST", field, data);
                                    if (putData.startPut()) {
                                        if (putData.onComplete()) {
                                            String result = putData.getResult();
                                            if(result.equals("Code Send Success")){
                                                kod.setVisibility(View.VISIBLE);
                                                btnKod.setText("Şifre Değiştir");
                                                Toast.makeText(ForgotPasswordActivity.this, "Kod Gönderildi", Toast.LENGTH_LONG).show();
                                            }
                                            else{
                                                Toast.makeText(ForgotPasswordActivity.this, "Kod Gönderilemedi. Tekrar deneyiniz!", Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        });

        kodText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent isim = new Intent(ForgotPasswordActivity.this, LoginActivity.class);
                startActivity(isim);
            }
        });

    }
}