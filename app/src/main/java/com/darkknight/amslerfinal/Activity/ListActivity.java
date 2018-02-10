package com.darkknight.amslerfinal.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.darkknight.amslerfinal.R;

import java.util.ArrayList;
import java.util.Arrays;

import Utils.ListAdapter;
import Utils.RecyclerItemClickListener;

/**
 * Created by iruda on 10-02-2018.
 */

public class ListActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ListAdapter listAdapter;
    ArrayList<String> chartList;
    DividerItemDecoration dividerItemDecoration;
    Intent intent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        chartList= new ArrayList<String>
                (Arrays.asList(getResources().getStringArray(getIntent().getIntExtra("testname",R.array.homelist))));
        recyclerView = (RecyclerView)findViewById(R.id.recycler);
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
