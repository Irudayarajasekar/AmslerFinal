package com.darkknight.amslerfinal.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.darkknight.amslerfinal.R;

import java.util.Locale;

/**
 * Created by iruda on 31-01-2018.
 */

public class SplashActivity extends AppCompatActivity {
    public static final String MyPREFERENCES = "AppPreferences" ;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Intent intent;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                sharedPreferences = getSharedPreferences(MyPREFERENCES,MODE_PRIVATE);
                String language = sharedPreferences.getString("Language","init");
                if(language.equals("init"))
                    intent = new Intent(SplashActivity.this,MainActivity.class);
                else {
                    Locale locale = new Locale(language);
                    Locale.setDefault(locale);
                    Configuration config = new Configuration();
                    config.locale = locale;
                    getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
                    intent = new Intent(SplashActivity.this, InitialActivity.class);
                }

                startActivity(intent);
            }
        },3000);
    }
}
