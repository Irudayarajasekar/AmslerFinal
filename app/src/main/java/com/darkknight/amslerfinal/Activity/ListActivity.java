package com.darkknight.amslerfinal.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
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

public class ListActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ListAdapter listAdapter;
    ArrayList<ListDetails> chartList;
    ListDetails listDetails;
    DividerItemDecoration dividerItemDecoration;
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
                intent = new Intent(ListActivity.this,QuestionsActivity.class);
                startActivity(intent);
            }
        }));
    }
}
