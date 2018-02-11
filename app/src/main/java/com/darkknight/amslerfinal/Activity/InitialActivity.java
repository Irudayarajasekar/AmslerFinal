package com.darkknight.amslerfinal.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ListAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.darkknight.amslerfinal.R;

import java.util.Locale;

/**
 * Created by iruda on 31-01-2018.
 */

public class InitialActivity extends AppCompatActivity implements View.OnClickListener {
    CardView cardView1,cardView2,cardView3,cardView4,english,tamil,hindi;
    TextView hometext,clinictext,instructions,languagetext;
    FrameLayout frameLayout;
    RelativeLayout mainLayout;
    public static final String MyPREFERENCES = "AppPreferences" ;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Intent intent;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial);
        initialiseUI();
    }

    private void initialiseUI() {
        cardView1 = (CardView)findViewById(R.id.card_view1);
        cardView2 = (CardView)findViewById(R.id.card_view2);
        cardView3 = (CardView)findViewById(R.id.card_view3);
        cardView4 = (CardView)findViewById(R.id.card_view4);
        hometext = (TextView) findViewById(R.id.hometext);
        clinictext = (TextView) findViewById(R.id.clinictext);
        instructions = (TextView) findViewById(R.id.instructions);
        languagetext = (TextView) findViewById(R.id.languagetext);
        english = (CardView)findViewById(R.id.english);
        tamil = (CardView)findViewById(R.id.tamil);
        hindi = (CardView)findViewById(R.id.hindi);
        frameLayout = (FrameLayout)findViewById(R.id.languageLayout);
        mainLayout = (RelativeLayout)findViewById(R.id.mainLayout);
        cardView1.setOnClickListener(this);
        cardView2.setOnClickListener(this);
        cardView3.setOnClickListener(this);
        cardView4.setOnClickListener(this);
        english.setOnClickListener(this);
        tamil.setOnClickListener(this);
        hindi.setOnClickListener(this);
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
        hometext.setText(R.string.homeamsler);
        clinictext.setText(R.string.clinicamsler);
        instructions.setText(R.string.instruct);
        languagetext.setText(R.string.lang);
        mainLayout.setAlpha(1.0f);
        frameLayout.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.card_view1:
                intent = new Intent(InitialActivity.this,ListActivity.class);
                intent.putExtra("title",R.string.homeamsler);
                intent.putExtra("testname",R.array.homelist);
                startActivity(intent);
                break;
            case R.id.card_view2:
                intent = new Intent(InitialActivity.this,ListActivity.class);
                intent.putExtra("title",R.string.clinicamsler);
                intent.putExtra("testname",R.array.cliniclist);
                startActivity(intent);
                break;
            case R.id.card_view3:
                intent = new Intent(InitialActivity.this,InstructionsActivity.class);
                intent.putExtra("finishScreen",true);
                startActivity(intent);
                break;
            case R.id.card_view4:
                mainLayout.setAlpha(0.3f);
                frameLayout.setVisibility(View.VISIBLE);
                break;
            case R.id.english:
                setLanguage("en");
                break;
            case R.id.tamil:
                setLanguage("ta");
                break;
            case R.id.hindi:
                setLanguage("hi");
                break;
            default:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if(frameLayout.getVisibility() == View.VISIBLE){
            frameLayout.setVisibility(View.INVISIBLE);
            mainLayout.setAlpha(1.0f);
        }else{
            super.onBackPressed();
        }
    }
}
