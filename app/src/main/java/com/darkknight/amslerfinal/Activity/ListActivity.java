package com.darkknight.amslerfinal.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.darkknight.amslerfinal.R;

import java.util.ArrayList;

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
    FrameLayout eyeframe,nameframe,nameclinicframe;
    Button name,nameclinic;
    RadioGroup radiosex;
    RadioButton selectedsex;
    EditText nameofpatient,nameofpatientclinic,ageofpatientclinic,placeofpatientclinic,uidofpatientclinic;
    CardView lefteye,righteye;
    int chartnumber;
    public static final String MyPREFERENCES = "AppPreferences" ;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Intent intent;
    TextView title;
    int[] imageArray = {R.drawable.chart1,R.drawable.chart2,R.drawable.chart3,
            R.drawable.chart4,R.drawable.chart5a,R.drawable.chart5b,R.drawable.chart6a,R.drawable.chart6b,R.drawable.chart7};
    int[] chartnames = {R.array.chart1,R.array.chart2,R.array.chart3,R.array.chart4,R.array.chart5a,R.array.chart5b,
            R.array.chart6a,R.array.chart6b,R.array.chart7};
    String[] nameArray;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        title = (TextView)findViewById(R.id.title);
        mainLayout = (RelativeLayout)findViewById(R.id.mainLayout);
        eyeframe = (FrameLayout)findViewById(R.id.eyeframe);
        nameframe = (FrameLayout)findViewById(R.id.nameframe);
        nameclinicframe = (FrameLayout)findViewById(R.id.nameclinicframe);
        lefteye = (CardView)findViewById(R.id.lefteye);
        righteye = (CardView)findViewById(R.id.righteye);
        name = (Button)findViewById(R.id.nameok);
        nameclinic = (Button)findViewById(R.id.nameclinicok);
        nameofpatient = (EditText)findViewById(R.id.nameofpatient);
        nameofpatientclinic = (EditText)findViewById(R.id.nameofpatientclinic);
        ageofpatientclinic = (EditText)findViewById(R.id.ageofpatientclinic);
        placeofpatientclinic = (EditText)findViewById(R.id.placeofpatientclinic);
        uidofpatientclinic = (EditText)findViewById(R.id.uidofpatientclinic);
        radiosex = (RadioGroup)findViewById(R.id.radioSex);
        name.setOnClickListener(this);
        nameclinic.setOnClickListener(this);
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
                chartnumber = position;
                if(getIntent().getIntExtra("title",R.string.homeamsler) == R.string.homeamsler){
                    nameframe.setVisibility(View.VISIBLE);
                }else{
                    nameclinicframe.setVisibility(View.VISIBLE);
                }
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
                intent.putExtra("chartimage",imageArray[chartnumber]);
                intent.putExtra("chartnumber",chartnames[chartnumber]);
                intent.putExtra("eyeside",R.string.lefteyeside);
                intent.putExtra("position",chartnumber+1);
                startActivity(intent);
                break;
            case R.id.righteye:
                eyeframe.setVisibility(View.INVISIBLE);
                mainLayout.setAlpha(1.0f);
                intent = new Intent(ListActivity.this,QuestionsActivity.class);
                intent.putExtra("chartimage",imageArray[chartnumber]);
                intent.putExtra("chartnumber",chartnames[chartnumber]);
                intent.putExtra("eyeside",R.string.righteyeside);
                intent.putExtra("position",chartnumber+1);
                startActivity(intent);
                break;
            case R.id.nameok:
                if(!nameofpatient.getText().toString().trim().isEmpty()){
                    nameframe.setVisibility(View.INVISIBLE);
                    sharedPreferences = getSharedPreferences(MyPREFERENCES,MODE_PRIVATE);
                    editor = sharedPreferences.edit();
                    editor.putString("nameofpatient",nameofpatient.getText().toString().trim());
                    editor.commit();
                    eyeframe.setVisibility(View.VISIBLE);
                    nameofpatient.setText("");
                }else{
                    Snackbar.make(view,"Please fill your name",Snackbar.LENGTH_SHORT).show();
                }
                break;
            case R.id.nameclinicok:
                if(!nameofpatientclinic.getText().toString().trim().isEmpty()||
                        !ageofpatientclinic.getText().toString().trim().isEmpty()||
                        !placeofpatientclinic.getText().toString().trim().isEmpty()||
                        !uidofpatientclinic.getText().toString().trim().isEmpty()){
                    int selectedId = radiosex.getCheckedRadioButtonId();
                    selectedsex = (RadioButton) findViewById(selectedId);
                    nameclinicframe.setVisibility(View.INVISIBLE);
                    sharedPreferences = getSharedPreferences(MyPREFERENCES,MODE_PRIVATE);
                    editor = sharedPreferences.edit();
                    editor.putString("nameofpatient",nameofpatientclinic.getText().toString().trim());
                    editor.putString("ageofpatient",ageofpatientclinic.getText().toString().trim());
                    editor.putString("placeofpatient",placeofpatientclinic.getText().toString().trim());
                    editor.putString("sexofpatient",selectedsex.getText().toString().trim());
                    editor.putString("uidofpatient",uidofpatientclinic.getText().toString().trim());
                    editor.commit();
                    eyeframe.setVisibility(View.VISIBLE);
                    nameofpatientclinic.setText("");
                    ageofpatientclinic.setText("");
                    placeofpatientclinic.setText("");
                    uidofpatientclinic.setText("");
                }else{
                    Snackbar.make(view,"Please fill all fields",Snackbar.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }
    @Override
    public void onBackPressed() {
        if(eyeframe.getVisibility() == View.VISIBLE || nameframe.getVisibility() ==View.VISIBLE ||
                nameclinicframe.getVisibility() == View.VISIBLE){
            eyeframe.setVisibility(View.INVISIBLE);
            nameframe.setVisibility(View.INVISIBLE);
            nameclinicframe.setVisibility(View.INVISIBLE);
            mainLayout.setAlpha(1.0f);
        }else{
            super.onBackPressed();
        }
    }
}
