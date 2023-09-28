package com.example.saglik;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.SwitchCompat;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.vishnusivadas.advanced_httpurlconnection.PutData;
import static com.example.saglik.LoginActivity.tc;
import static com.example.saglik.LoginActivity.ip;

import de.hdodenhof.circleimageview.CircleImageView;


public class ProfilActivity extends AppCompatActivity {

    private final int GALLERY_REQ_CODE = 1000;
    final int[] checkedItem = {0};
    CircleImageView ChangeImage;
    TextView name;
    SwitchCompat switchGece;
    AppCompatButton profilUpdate;
    public static boolean gece;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setSelectedItemId(R.id.bottom_profile);
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
                    startActivity(new Intent(getApplicationContext(), FavoriActivity.class));
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                    finish();
                    return true;
                case R.id.bottom_profile:
                    return true;
            }
            return false;
        });

        ChangeImage = findViewById(R.id.imageView1);
        profilUpdate = findViewById(R.id.profilUpdate);
        ImageButton chngImage = findViewById(R.id.changeImage);
        RelativeLayout dil = findViewById(R.id.dil);
        RelativeLayout mesaj = findViewById(R.id.mesaj);
        RelativeLayout aboutus = findViewById(R.id.aboutus);
        RelativeLayout exit = findViewById(R.id.cikis);
        name = findViewById(R.id.name);


        String[] field = new String[1];
        field[0] = "tc";

        String[] data = new String[1];
        data[0] = tc;

        PutData putData = new PutData("http://"+ip+"/Saglik/name.php", "POST", field, data);
        if (putData.startPut()) {
            if (putData.onComplete()) {
                String result = putData.getResult();
                name.setText(result);
            }
        }


        SwitchCompat switchBildirim = (SwitchCompat) findViewById(R.id.bildirim);
        switchBildirim.setChecked(false);

        chngImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iGallery = new Intent(Intent.ACTION_PICK);
                iGallery.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(iGallery, GALLERY_REQ_CODE);
            }
        });

        profilUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent isim = new Intent(ProfilActivity.this, ProfilUpdateActivity.class);
                startActivity(isim);
            }
        });


        dil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String[] language =
                        {
                                "Türkçe", "English", "Arabic", "Pashto"
                        };

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(ProfilActivity.this);
                alertDialog.setTitle("                   Dil Seçiniz");

                alertDialog.setSingleChoiceItems(language, checkedItem[0], (dialog, which) -> {
                    checkedItem[0] = which;
                });

                alertDialog.setPositiveButton("Uygula", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        Toast.makeText(getApplicationContext(), "Uygulama Güncellendi", Toast.LENGTH_SHORT).show();
                    }
                });

                alertDialog.show();
            }
        });

        mesaj.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("IntentReset")
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"esaglik027@gmail.com"});
                intent.putExtra(Intent.EXTRA_SUBJECT, "E-Sağlık Ocağı Uygulaması");
                intent.setType("message/rfc822");
                intent.setPackage("com.google.android.gm");
                startActivity(intent);
            }
        });

        aboutus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), MainPageActivity.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
                Intent isim = new Intent(ProfilActivity.this, AboutUsActivity.class);
                startActivity(isim);
            }
        });


        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("remember", "false");
                editor.apply();

                Toast.makeText(ProfilActivity.this, "Çıkış Başarılı!", Toast.LENGTH_LONG).show();
                Intent isim = new Intent(ProfilActivity.this, LoginActivity.class);
                startActivity(isim);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        switchGece = findViewById(R.id.gece);

        switchGece.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    gece = true;
                    ChangeImage.setImageResource(R.drawable.nightman);
                }
                else{
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    gece = false;
                    ChangeImage.setImageResource(R.drawable.man);
                }
            }
        });

        switchGece.setChecked(gece);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode==RESULT_OK){
            if (requestCode==GALLERY_REQ_CODE){
                ChangeImage.setImageURI(data.getData());
            }
        }
    }
}