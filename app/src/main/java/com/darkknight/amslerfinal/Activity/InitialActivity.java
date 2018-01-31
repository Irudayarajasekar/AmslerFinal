package com.darkknight.amslerfinal.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;

import com.darkknight.amslerfinal.R;

/**
 * Created by iruda on 31-01-2018.
 */

public class InitialActivity extends AppCompatActivity implements View.OnClickListener {
    CardView cardView1,cardView2,cardView3,cardView4;
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
        cardView1.setOnClickListener(this);
        cardView2.setOnClickListener(this);
        cardView3.setOnClickListener(this);
        cardView4.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.card_view1:
                intent = new Intent(InitialActivity.this,QuestionsActivity.class);
                startActivity(intent);
                break;
            case R.id.card_view2:
                intent = new Intent(InitialActivity.this,QuestionsActivity.class);
                startActivity(intent);
                break;
            case R.id.card_view3:
                intent = new Intent(InitialActivity.this,InstructionsActivity.class);
                intent.putExtra("finishScreen",true);
                startActivity(intent);
                break;
            case R.id.card_view4:
                //yet to be done...
                break;
            default:
                break;
        }
    }
}
