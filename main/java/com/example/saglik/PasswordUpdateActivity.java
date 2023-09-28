package com.example.saglik;

import static com.example.saglik.LoginActivity.ip;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import static com.example.saglik.ForgotPasswordActivity.ttc;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

public class PasswordUpdateActivity extends AppCompatActivity {

    EditText kod1, kod2;
    Button btnUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_update);

        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}

        kod1 = findViewById(R.id.pass1);
        kod2 = findViewById(R.id.pass2);
        btnUpdate = findViewById(R.id.btnKod1);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (kod1.getText().toString().isEmpty() || kod2.getText().toString().isEmpty()){
                    Toast.makeText(PasswordUpdateActivity.this, "Boş Alan Bırakmayınız.", Toast.LENGTH_LONG).show();
                }
                else if(!kod1.getText().toString().equals(kod2.getText().toString())){
                    Toast.makeText(PasswordUpdateActivity.this, "Aynı şifreyi giriniz!", Toast.LENGTH_LONG).show();
                }
                else if(kod1.getText().toString().length() < 8 || kod2.getText().toString().length() < 8 ){
                    Toast.makeText(PasswordUpdateActivity.this, "Sifreniz en az 8 haneli olmalıdır!", Toast.LENGTH_LONG).show();
                }
                else{
                    String[] field = new String[2];
                    field[0] = "tc";
                    field[1] = "sifre";

                    String[] data = new String[2];
                    data[0] = ttc;
                    data[1] = kod1.getText().toString();

                    PutData putData = new PutData("http://"+ip+"/Saglik/updatePassword.php", "POST", field, data);
                    if (putData.startPut()) {
                        if (putData.onComplete()) {
                            String result = putData.getResult();
                            if(result.equals("Password Update Success")){
                                Toast.makeText(PasswordUpdateActivity.this, "Şifreniz başarıyla güncellendi!", Toast.LENGTH_LONG).show();
                                Intent isim = new Intent(PasswordUpdateActivity.this, LoginActivity.class);
                                startActivity(isim);
                            }
                            else{
                                Toast.makeText(PasswordUpdateActivity.this, "Şifreniz güncellenemedi! Tekrar Deneyiniz.", Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                }
            }
        });
    }
}