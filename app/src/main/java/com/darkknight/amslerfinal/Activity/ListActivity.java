package com.darkknight.amslerfinal.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.darkknight.amslerfinal.R;

import java.util.ArrayList;
import java.util.Arrays;

import Utils.ListAdapter;
import Utils.ListDetails;
import Utils.RecyclerItemClickListener;

/**
 * Created by iruda on 10-02-2018.
 */

public class ListActivity extends AppCompatActivity implements View.OnClickListener{
    RecyclerView recyclerView;
    ListAdapter listAdapter;
    ArrayList<ListDetails> chartList;
    ListDetails listDetails;
    DividerItemDecoration dividerItemDecoration;
    RelativeLayout mainLayout;
    FrameLayout eyeframe;
    CardView lefteye,righteye;
    int chartnumber;
    Intent intent;
    TextView title;
    int[] imageArray = {R.drawable.instructions,R.drawable.instructions,R.drawable.instructions,
            R.drawable.instructions,R.drawable.instructions,R.drawable.instructions,R.drawable.instructions};
    String[] nameArray;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        title = (TextView)findViewById(R.id.title);
        mainLayout = (RelativeLayout)findViewById(R.id.mainLayout);
        eyeframe = (FrameLayout)findViewById(R.id.eyeframe);
        lefteye = (CardView)findViewById(R.id.lefteye);
        righteye = (CardView)findViewById(R.id.righteye);
        lefteye.setOnClickListener(this);
        righteye.setOnClickListener(this);
        title.setText(getIntent().getIntExtra("title",R.string.homeamsler));
        recyclerView = (RecyclerView)findViewById(R.id.recycler);
        chartList = new ArrayList<>();
        nameArray = getResources().getStringArray(getIntent().getIntExtra("testname",R.array.homelist));
        for (int i=0;i<nameArray.length;i++){
            listDetails = new ListDetails();
            listDetails.setName(nameArray[i]);
            listDetails.setImage(imageArray[i]);
            chartList.add(listDetails);
        }
        listAdapter = new ListAdapter(this);
        dividerItemDecoration = new DividerItemDecoration(getApplicationContext(),DividerItemDecoration.VERTICAL);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setAdapter(listAdapter);
        listAdapter.setChartList(chartList);
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                chartnumber = position+1;
                eyeframe.setVisibility(View.VISIBLE);
                mainLayout.setAlpha(0.3f);
            }
        }));
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.lefteye:
                eyeframe.setVisibility(View.INVISIBLE);
                mainLayout.setAlpha(1.0f);
                intent = new Intent(ListActivity.this,QuestionsActivity.class);
                intent.putExtra("chartnumber",chartnumber);
                intent.putExtra("eyeside","left");
                startActivity(intent);
                break;
            case R.id.righteye:
                eyeframe.setVisibility(View.INVISIBLE);
                mainLayout.setAlpha(1.0f);
                intent = new Intent(ListActivity.this,QuestionsActivity.class);
                intent.putExtra("chartnumber",chartnumber);
                intent.putExtra("eyeside","right");
                startActivity(intent);
                break;
            default:
                break;
        }
    }
    @Override
    public void onBackPressed() {
        if(eyeframe.getVisibility() == View.VISIBLE){
            eyeframe.setVisibility(View.INVISIBLE);
            mainLayout.setAlpha(1.0f);
        }else{
            super.onBackPressed();
        }
    }
}
