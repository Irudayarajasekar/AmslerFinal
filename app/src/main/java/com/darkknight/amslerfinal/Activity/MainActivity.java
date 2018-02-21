package com.darkknight.amslerfinal.Activity;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;

import com.darkknight.amslerfinal.R;

import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    CardView cardView1,cardView2,cardView3;
    public static final String MyPREFERENCES = "AppPreferences" ;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialiseUI();
    }

    private void initialiseUI() {
        cardView1 = (CardView)findViewById(R.id.card_view1);
        cardView2 = (CardView)findViewById(R.id.card_view2);
        cardView3 = (CardView)findViewById(R.id.card_view3);
        cardView1.setOnClickListener(this);
        cardView2.setOnClickListener(this);
        cardView3.setOnClickListener(this);
    }

    private void setLanguage(String language)
    {
        sharedPreferences = getSharedPreferences(MyPREFERENCES,MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putString("Language",language);
        editor.commit();
        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        Intent i = new Intent(MainActivity.this,InstructionsActivity.class);
        startActivity(i);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId() /*to get clicked view id**/) {
            case R.id.card_view1:
                setLanguage("en");
                break;
            case R.id.card_view2:
                setLanguage("ta");
                break;
            case R.id.card_view3:
                setLanguage("hi");
                break;
            default:
                break;
        }
    }

}
