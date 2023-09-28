package com.example.saglik;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class AboutUsActivity extends AppCompatActivity {

    TextView googleplay, communacition, shareapp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}

        ImageView back = findViewById(R.id.backbtn);
        shareapp = findViewById(R.id.shareapp);
        googleplay = findViewById(R.id.googleplay);
        communacition = findViewById(R.id.communacition);

        shareapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "E-Sağlık");
                    String shareMessage= "\nBu uygulamayı indirir misin lütfenn \uD83E\uDD7A\uD83D\uDC49\uD83C\uDFFB\uD83D\uDC48\uD83C\uDFFB\n\n";
                    shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID +"\n\n";
                    shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                    startActivity(Intent.createChooser(shareIntent, "Birini Seçiniz"));
                } catch(Exception e) {
                    //e.toString();
                }
            }
        });

        googleplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(android.content.Intent.ACTION_VIEW);
                i.setData(Uri.parse("https://play.google.com/store/apps/details?id=esaglikapp"));
                startActivity(i);
            }
        });

        communacition.setOnClickListener(new View.OnClickListener() {
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

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),ProfilActivity.class));
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                finish();
            }
        });
    }
}