package com.darkknight.amslerfinal.Activity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.darkknight.amslerfinal.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;

import me.relex.circleindicator.CircleIndicator;

/**
 * Created by iruda on 17-02-2018.
 */

public class ReportActivity extends AppCompatActivity {
    LayoutInflater inflater;
    ViewPager vp;
    Button generate;
    Bitmap[] imageArray;
    int[] titleArray= {R.string.lefteyereport,R.string.righteyereport};
    String[] imageNameArray = {"lefteyereport","righteyereport"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        imageArray = new Bitmap[2];
        Bitmap image = BitmapFactory.decodeByteArray(getIntent().getByteArrayExtra("lefteyereport"),0,
                getIntent().getByteArrayExtra("lefteyereport").length);
        imageArray[0] = image;
        image = BitmapFactory.decodeByteArray(getIntent().getByteArrayExtra("righteyereport"),0,
                getIntent().getByteArrayExtra("righteyereport").length);
        imageArray[1] = image;
        inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        vp=(ViewPager)findViewById(R.id.pager);
        generate =(Button)findViewById(R.id.gotit);
        CircleIndicator indicator = (CircleIndicator) findViewById(R.id.indicator);
        vp.setAdapter(new MyPagesAdapter());
        indicator.setViewPager(vp);
        generate = (Button)findViewById(R.id.generate);
        generate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i=0;i<2;i++){
                    String filename = imageNameArray[i];
                    filename+= DateFormat.getTimeInstance().format(new Date())+".png";
                    generateReport(filename,imageArray[i]);
                }
            }
        });
    }

    public void generateReport(String filename,Bitmap bitmap){
        FileOutputStream out = null;
        File file=new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), filename);
        try {
            out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
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

    class MyPagesAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            //Return total pages, here one for each data item
            return 2;
        }
        //Create the given page (indicated by position)
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            System.out.print(position);
            View page = inflater.inflate(R.layout.reportcontent, null);
            ((TextView)page.findViewById(R.id.textview1)).setText(titleArray[position]);
            ((ImageView)page.findViewById(R.id.imageview)).setImageBitmap(imageArray[position]);
            if(getIntent().getBooleanExtra("isdefect",false)){
                ((TextView)page.findViewById(R.id.instructiontext)).setText(R.string.defect);
            }else {
                ((TextView)page.findViewById(R.id.instructiontext)).setText(R.string.nodefect);
            }
            ((ViewPager) container).addView(page, 0);
            return page;
        }
        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0==(View)arg1;
        }
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ((ViewPager) container).removeView((View) object);
            object=null;
        }
    }
}