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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import Utils.DrawOverView;

/**
 * Created by iruda on 31-01-2018.
 */

public class QuestionsActivity extends AppCompatActivity implements View.OnClickListener{
    RelativeLayout mainLayout,drawLayout;
    LinearLayout buttonlayout;
    FrameLayout instructionFrame;
    TextView subinstructiontext,question;
    Button yes,no,closeins,done;
    ImageView chart;
    int position,currentposition;
    String[] questionslist;
    boolean isComplete;
    String instruction;
    Intent intent;
    DrawOverView drawOverView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);
        mainLayout = (RelativeLayout)findViewById(R.id.mainLayout);
        drawLayout = (RelativeLayout)findViewById(R.id.drawlayout);
        drawOverView = new DrawOverView(this);
        drawLayout.addView(drawOverView);
        drawOverView.setVisibility(View.INVISIBLE);
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
        chart = (ImageView)findViewById(R.id.chart);
        chart.setImageResource(getIntent().getIntExtra("chartimage",R.drawable.settings));
        chart.setMaxHeight(400);
        instructionFrame.setVisibility(View.VISIBLE);
        mainLayout.setAlpha(0.3f);
        position = getIntent().getIntExtra("position",1);
        questionslist = getResources().getStringArray(getIntent().getIntExtra("chartnumber",R.array.chart1));
        instruction = getString(getIntent().getIntExtra("eyeside",R.string.righteyeside))+questionslist[0];
        subinstructiontext.setText(instruction);
        currentposition = 1;
        isComplete = getIntent().getBooleanExtra("iscomplete",false);
        question.setText(questionslist[currentposition]);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.closeins:
                mainLayout.setAlpha(1.0f);
                done.setVisibility(View.INVISIBLE);
                buttonlayout.setVisibility(View.VISIBLE);
                drawOverView.setVisibility(View.INVISIBLE);
                instructionFrame.setVisibility(View.INVISIBLE);
                break;
            case R.id.no:
                if(position == 1 && currentposition == 1){
                    intent = getIntent();
                    intent.putExtra("position",2);
                    intent.putExtra("chartnumber",R.array.chart2);
                    intent.putExtra("chartimage",R.drawable.chart1);
                    if(isComplete) {
                        intent.putExtra("iscomplete", true);
                        if(getIntent().getIntExtra("eyeside",R.string.righteyeside) == R.string.lefteyeside)
                            intent.putExtra("eyeside",R.string.righteyeside);
                        else
                            intent.putExtra("eyeside",R.string.lefteyeside);
                    }
                    finish();
                    startActivity(intent);
                }
                else if(currentposition+1<questionslist.length){
                    currentposition+=1;
                    question.setText(questionslist[currentposition]);
                }
                else{
                    if(isComplete) {
                        drawOverView.setVisibility(View.VISIBLE);
                        drawLayout.setDrawingCacheEnabled(true);
                        Bitmap b = drawLayout.getDrawingCache();
                        FileOutputStream out = null;
                        File file=new File(Environment.getExternalStoragePublicDirectory(
                                Environment.DIRECTORY_PICTURES), "sampleimage.png");
                        try {
                            out = new FileOutputStream(file);
                            b.compress(Bitmap.CompressFormat.PNG, 100, out);
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            try {
                                if (out != null) {
                                    out.close();
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    else{
                        isComplete = true;
                        drawOverView.setVisibility(View.VISIBLE);
                        drawLayout.setDrawingCacheEnabled(true);
                        Bitmap b = drawLayout.getDrawingCache();
                        FileOutputStream out = null;
                        File file=new File(Environment.getExternalStoragePublicDirectory(
                                Environment.DIRECTORY_PICTURES), "sampleimageoneside.png");
                        try {
                            out = new FileOutputStream(file);
                            b.compress(Bitmap.CompressFormat.PNG, 100, out);
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            try {
                                if (out != null) {
                                    out.close();
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        drawOverView.clear();
                        currentposition = 1;
                        instructionFrame.setVisibility(View.VISIBLE);
                        if(getIntent().getIntExtra("eyeside",R.string.righteyeside) == R.string.lefteyeside)
                            subinstructiontext.setText(getString(R.string.righteyeside)+questionslist[0]);
                        else
                            subinstructiontext.setText(getString(R.string.lefteyeside)+questionslist[0]);
                        question.setText(questionslist[currentposition]);
                        mainLayout.setAlpha(0.3f);
                    }
                }
                break;
            case R.id.yes:
                if(position == 1 && currentposition == 1){
                    if(currentposition+1<questionslist.length){
                        currentposition+=1;
                        question.setText(questionslist[currentposition]);
                    }
                }
                else{
                    question.setText(R.string.mark);
                    drawOverView.setVisibility(View.VISIBLE);
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
                    drawOverView.setVisibility(View.INVISIBLE);
                }else{
                    if(isComplete) {
                        drawOverView.setVisibility(View.VISIBLE);
                        drawLayout.setDrawingCacheEnabled(true);
                        Bitmap b = drawLayout.getDrawingCache();
                        FileOutputStream out = null;
                        File file=new File(Environment.getExternalStoragePublicDirectory(
                                Environment.DIRECTORY_PICTURES), "sampleimage.png");
                        try {
                            out = new FileOutputStream(file);
                            b.compress(Bitmap.CompressFormat.PNG, 100, out);
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            try {
                                if (out != null) {
                                    out.close();
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    else{
                        isComplete = true;
                        drawOverView.setVisibility(View.VISIBLE);
                        drawLayout.setDrawingCacheEnabled(true);
                        Bitmap b = drawLayout.getDrawingCache();
                        FileOutputStream out = null;
                        File file=new File(Environment.getExternalStoragePublicDirectory(
                                Environment.DIRECTORY_PICTURES), "sampleimageoneside.png");
                        try {
                            out = new FileOutputStream(file);
                            b.compress(Bitmap.CompressFormat.PNG, 100, out);
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            try {
                                if (out != null) {
                                    out.close();
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        drawOverView.clear();
                        currentposition = 1;
                        instructionFrame.setVisibility(View.VISIBLE);
                        if(getIntent().getIntExtra("eyeside",R.string.righteyeside) == R.string.lefteyeside)
                            subinstructiontext.setText(getString(R.string.righteyeside)+questionslist[0]);
                        else
                            subinstructiontext.setText(getString(R.string.lefteyeside)+questionslist[0]);
                        mainLayout.setAlpha(0.3f);
                        question.setText(questionslist[currentposition]);
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
