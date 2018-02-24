package com.darkknight.amslerfinal.Activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.darkknight.amslerfinal.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

import Utils.DrawOverView;

/**
 * Created by iruda on 31-01-2018.
 */

public class QuestionsActivity extends AppCompatActivity implements View.OnClickListener{
    RelativeLayout mainLayout,drawLayout1,drawLayout2;
    LinearLayout buttonlayout;
    FrameLayout instructionFrame;
    TextView subinstructiontext,question;
    Button yes,no,closeins,done;
    ImageView chart1,chart2;
    int position,currentposition;
    String[] questionslist,codeArray;
    boolean isComplete,isFirstIns=true,isdefect=false,isdefectleft=false,isdefectright=false,isfromchart1=false;
    String instruction;
    Intent intent,reportintent;
    ArrayList<Integer> arrayList;
    DrawOverView drawOverView1,drawOverView2;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);
        mainLayout = (RelativeLayout)findViewById(R.id.mainLayout);
        drawLayout1 = (RelativeLayout)findViewById(R.id.drawlayout1);
        drawOverView1 = new DrawOverView(this);
        drawLayout1.addView(drawOverView1);
        drawOverView1.setVisibility(View.INVISIBLE);
        drawLayout2 = (RelativeLayout)findViewById(R.id.drawlayout2);
        drawOverView2 = new DrawOverView(this);
        drawLayout2.addView(drawOverView2);
        drawOverView2.setVisibility(View.INVISIBLE);
        isComplete = getIntent().getBooleanExtra("iscomplete",false);
        isdefect = getIntent().getBooleanExtra("isdefect",false);
        isdefectleft = getIntent().getBooleanExtra("isdefectleft",false);
        isdefectright = getIntent().getBooleanExtra("isdefectright",false);
        isfromchart1 = getIntent().getBooleanExtra("isfromchart1",false);
        if(!isComplete) {
            drawLayout1.setVisibility(View.VISIBLE);
            drawLayout2.setVisibility(View.INVISIBLE);
        }
        else {
            drawLayout2.setVisibility(View.VISIBLE);
            drawLayout1.setVisibility(View.INVISIBLE);
        }
        buttonlayout = (LinearLayout)findViewById(R.id.buttonlayout);
        instructionFrame = (FrameLayout)findViewById(R.id.instructionframe);
        subinstructiontext = (TextView)findViewById(R.id.subinstructiontext);
        yes = (Button)findViewById(R.id.yes);
        no = (Button)findViewById(R.id.no);
        closeins = (Button)findViewById(R.id.closeins);
        done = (Button)findViewById(R.id.done);
        yes.setOnClickListener(this);
        no.setOnClickListener(this);
        closeins.setOnClickListener(this);
        done.setOnClickListener(this);
        question = (TextView)findViewById(R.id.question);
        chart1 = (ImageView)findViewById(R.id.chart1);
        chart1.setImageResource(getIntent().getIntExtra("chartimage",R.drawable.settings));
        chart1.setMaxHeight(400);
        chart2 = (ImageView)findViewById(R.id.chart2);
        chart2.setImageResource(getIntent().getIntExtra("chartimage",R.drawable.settings));
        chart2.setMaxHeight(400);
        instructionFrame.setVisibility(View.VISIBLE);
        mainLayout.setAlpha(0.3f);
        position = getIntent().getIntExtra("position",1);
        questionslist = getResources().getStringArray(getIntent().getIntExtra("chartnumber",R.array.chart1));
        instruction = getString(getIntent().getIntExtra("eyeside",R.string.righteyeside))+questionslist[0];
        subinstructiontext.setText(instruction);
        currentposition = 1;
        question.setText(questionslist[currentposition]);
        reportintent = new Intent(QuestionsActivity.this,ReportActivity.class);
        if(getIntent().hasExtra("lefteyereport"))
            reportintent.putExtra("lefteyereport",getIntent().getByteArrayExtra("lefteyereport"));
        else if(getIntent().hasExtra("righteyereport"))
            reportintent.putExtra("righteyereport",getIntent().getByteArrayExtra("righteyereport"));
        codeArray = getResources().getStringArray(R.array.codes);
        String currentCode = codeArray[position-1];
        arrayList = new ArrayList<>();
        StringTokenizer stringTokenizer = new StringTokenizer(currentCode,"|");
        while (stringTokenizer.hasMoreTokens()){
            arrayList.add(Integer.parseInt(stringTokenizer.nextToken()));
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.closeins:
                if(isFirstIns){
                    isFirstIns =false;
                 subinstructiontext.setText(questionslist[currentposition]);
                }else {
                    mainLayout.setAlpha(1.0f);
                    done.setVisibility(View.INVISIBLE);
                    buttonlayout.setVisibility(View.VISIBLE);
                    drawOverView1.setVisibility(View.INVISIBLE);
                    drawOverView2.setVisibility(View.INVISIBLE);
                    instructionFrame.setVisibility(View.INVISIBLE);
                }
                break;
            case R.id.no:
                if(position == 1 && currentposition == 1){
                    intent = getIntent();
                    intent.putExtra("position",2);
                    intent.putExtra("chartnumber",R.array.chart2);
                    intent.putExtra("chartimage",R.drawable.chart2);
                    intent.putExtra("isdefect",isdefect);
                    intent.putExtra("isdefectleft",isdefectleft);
                    intent.putExtra("isdefectright",isdefectright);
                    if(isComplete) {
                        intent.putExtra("isfromchart1",true);
                        intent.putExtra("iscomplete", true);
                        if(getIntent().getIntExtra("eyeside",R.string.righteyeside) == R.string.lefteyeside)
                            intent.putExtra("eyeside", R.string.righteyeside);
                        else
                            intent.putExtra("eyeside", R.string.lefteyeside);
                        if(reportintent.hasExtra("lefteyereport"))
                            intent.putExtra("lefteyereport",reportintent.getByteArrayExtra("lefteyereport"));
                        else if(reportintent.hasExtra("righteyereport"))
                            intent.putExtra("righteyereport",reportintent.getByteArrayExtra("righteyereport"));
                    }
                    finish();
                    startActivity(intent);
                }
                else if(currentposition+1<questionslist.length){
                    currentposition+=1;
                    question.setText(questionslist[currentposition]);
                    subinstructiontext.setText(questionslist[currentposition]);
                    instructionFrame.setVisibility(View.VISIBLE);
                    mainLayout.setAlpha(0.3f);
                }
                else{
                    if(isComplete) {
                        if(!isdefect)
                            isdefect = drawOverView2.isdefect;
                        if(isfromchart1) {
                            if (getIntent().getIntExtra("eyeside", R.string.righteyeside) == R.string.lefteyeside)
                                isdefectleft = drawOverView2.isdefect;
                            else
                                isdefectright = drawOverView2.isdefect;
                        }else{
                            if (getIntent().getIntExtra("eyeside", R.string.righteyeside) == R.string.lefteyeside)
                                isdefectright = drawOverView2.isdefect;
                            else
                                isdefectleft = drawOverView2.isdefect;
                        }
                        drawOverView2.setVisibility(View.VISIBLE);
                        drawLayout2.setDrawingCacheEnabled(true);
                        Bitmap b = drawLayout2.getDrawingCache();
                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        b.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                        if(reportintent.hasExtra("lefteyereport")) {
                            subinstructiontext.setText(getString(R.string.righteyeside) + questionslist[0]);
                            reportintent.putExtra("righteyereport",byteArrayOutputStream.toByteArray());
                        }
                        else {
                            subinstructiontext.setText(getString(R.string.lefteyeside) + questionslist[0]);
                            reportintent.putExtra("lefteyereport",byteArrayOutputStream.toByteArray());
                        }
                        reportintent.putExtra("isdefect",isdefect);
                        reportintent.putExtra("isdefectleft",isdefectleft);
                        reportintent.putExtra("isdefectright",isdefectright);
                        startActivity(reportintent);
                    }
                    else{
                        isComplete = true;
                        isdefect = drawOverView1.isdefect;
                        if(getIntent().getIntExtra("eyeside",R.string.righteyeside) == R.string.lefteyeside)
                            isdefectleft = drawOverView1.isdefect;
                        else
                            isdefectright = drawOverView1.isdefect;
                        drawOverView1.setVisibility(View.VISIBLE);
                        drawLayout1.setDrawingCacheEnabled(true);
                        Bitmap b = drawLayout1.getDrawingCache();
                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        b.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                        drawOverView1.clear();
                        currentposition = 1;
                        instructionFrame.setVisibility(View.VISIBLE);
                        if(getIntent().getIntExtra("eyeside",R.string.righteyeside) == R.string.lefteyeside) {
                            subinstructiontext.setText(getString(R.string.righteyeside) + questionslist[0]);
                            reportintent.putExtra("lefteyereport",byteArrayOutputStream.toByteArray());
                        }
                        else {
                            subinstructiontext.setText(getString(R.string.lefteyeside) + questionslist[0]);
                            reportintent.putExtra("righteyereport",byteArrayOutputStream.toByteArray());
                        }
                        drawLayout1.setVisibility(View.INVISIBLE);
                        drawOverView1.setVisibility(View.INVISIBLE);
                        drawLayout2.setVisibility(View.VISIBLE);
                        question.setText(questionslist[currentposition]);
                        mainLayout.setAlpha(0.3f);
                        isFirstIns = true;
                    }
                }
                break;
            case R.id.yes:
                if(position == 1 && currentposition == 1){
                    if(currentposition+1<questionslist.length){
                        currentposition+=1;
                        question.setText(questionslist[currentposition]);
                        subinstructiontext.setText(questionslist[currentposition]);
                        instructionFrame.setVisibility(View.VISIBLE);
                        mainLayout.setAlpha(0.3f);
                    }
                }
                else{
                    question.setText(R.string.mark);
                    if(!isComplete) {
                        drawOverView1.setAttributes(arrayList.get(currentposition));
                        drawOverView1.setVisibility(View.VISIBLE);
                    }
                    else {
                        drawOverView2.setAttributes(arrayList.get(currentposition));
                        drawOverView2.setVisibility(View.VISIBLE);
                    }
                    buttonlayout.setVisibility(View.INVISIBLE);
                    done.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.done:
                if(currentposition+1<questionslist.length){
                    currentposition+=1;
                    done.setVisibility(View.INVISIBLE);
                    buttonlayout.setVisibility(View.VISIBLE);
                    question.setText(questionslist[currentposition]);
                    subinstructiontext.setText(questionslist[currentposition]);
                    instructionFrame.setVisibility(View.VISIBLE);
                    mainLayout.setAlpha(0.3f);
                    if(!isComplete)
                        drawOverView1.setVisibility(View.INVISIBLE);
                    else
                        drawOverView2.setVisibility(View.INVISIBLE);
                }else{
                    if(isComplete) {
                        if(!isdefect)
                            isdefect = drawOverView2.isdefect;
                        if(isfromchart1) {
                            if (getIntent().getIntExtra("eyeside", R.string.righteyeside) == R.string.lefteyeside)
                                isdefectleft = drawOverView2.isdefect;
                            else
                                isdefectright = drawOverView2.isdefect;
                        }else{
                            if (getIntent().getIntExtra("eyeside", R.string.righteyeside) == R.string.lefteyeside)
                                isdefectright = drawOverView2.isdefect;
                            else
                                isdefectleft = drawOverView2.isdefect;
                        }
                        drawOverView2.setVisibility(View.VISIBLE);
                        drawLayout2.setDrawingCacheEnabled(true);
                        Bitmap b = drawLayout2.getDrawingCache();
                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        b.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                        if(reportintent.hasExtra("lefteyereport")) {
                            subinstructiontext.setText(getString(R.string.righteyeside) + questionslist[0]);
                            reportintent.putExtra("righteyereport",byteArrayOutputStream.toByteArray());
                        }
                        else {
                            subinstructiontext.setText(getString(R.string.lefteyeside) + questionslist[0]);
                            reportintent.putExtra("lefteyereport",byteArrayOutputStream.toByteArray());
                        }
                        reportintent.putExtra("isdefect",isdefect);
                        reportintent.putExtra("isdefectleft",isdefectleft);
                        reportintent.putExtra("isdefectright",isdefectright);
                        startActivity(reportintent);
                    }
                    else{
                        isComplete = true;
                        isdefect = drawOverView1.isdefect;
                        if(getIntent().getIntExtra("eyeside",R.string.righteyeside) == R.string.lefteyeside)
                            isdefectleft = drawOverView1.isdefect;
                        else
                            isdefectright = drawOverView1.isdefect;
                        drawOverView1.setVisibility(View.VISIBLE);
                        drawLayout1.setDrawingCacheEnabled(true);
                        Bitmap b = drawLayout1.getDrawingCache();
                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        b.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                        drawOverView1.clear();
                        currentposition = 1;
                        instructionFrame.setVisibility(View.VISIBLE);
                        if(getIntent().getIntExtra("eyeside",R.string.righteyeside) == R.string.lefteyeside) {
                            subinstructiontext.setText(getString(R.string.righteyeside) + questionslist[0]);
                            reportintent.putExtra("lefteyereport",byteArrayOutputStream.toByteArray());
                        }
                        else {
                            subinstructiontext.setText(getString(R.string.lefteyeside) + questionslist[0]);
                            reportintent.putExtra("righteyereport",byteArrayOutputStream.toByteArray());
                        }
                        drawLayout1.setVisibility(View.INVISIBLE);
                        drawOverView1.setVisibility(View.INVISIBLE);
                        drawLayout2.setVisibility(View.VISIBLE);
                        mainLayout.setAlpha(0.3f);
                        question.setText(questionslist[currentposition]);
                        isFirstIns = true;
                    }
                }
                break;
            default:
                break;
        }
    }
    @Override
    public void onBackPressed() {
        if(instructionFrame.getVisibility() == View.VISIBLE){
            instructionFrame.setVisibility(View.INVISIBLE);
            mainLayout.setAlpha(1.0f);
        }else{
             new AlertDialog.Builder(this)
                    .setTitle(R.string.exit)
                    .setMessage(R.string.exmsg)
                    .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            finish();
                        }
                    })
                    .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    }).show();
        }
    }
}
