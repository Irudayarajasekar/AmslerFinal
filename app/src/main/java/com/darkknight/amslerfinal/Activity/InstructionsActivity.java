package com.darkknight.amslerfinal.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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

import me.relex.circleindicator.CircleIndicator;

/**
 * Created by iruda on 28-01-2018.
 */

public class InstructionsActivity extends AppCompatActivity {
    LayoutInflater inflater;
    ViewPager vp;
    Button gotit;
    int[] imageArray = {R.drawable.instructionzero,R.drawable.instructionone,R.drawable.instructiontwo,
                        R.drawable.instructionthree,R.drawable.instructionfour,R.drawable.instructionfive};
    String[] instructions;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructions);
        inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        vp=(ViewPager)findViewById(R.id.pager);
        gotit =(Button)findViewById(R.id.gotit);
        gotit.setVisibility(View.INVISIBLE);
        gotit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(getIntent().getBooleanExtra("finishScreen",false))
                    finish();
                else {
                    Intent intent = new Intent(InstructionsActivity.this, InitialActivity.class);
                    startActivity(intent);
                }
            }
        });
        CircleIndicator indicator = (CircleIndicator) findViewById(R.id.indicator);
        instructions=getResources().getStringArray(R.array.instructions);
        vp.setAdapter(new MyPagesAdapter());
        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(position == 5){
                    gotit.setVisibility(View.VISIBLE);
                }else{
                    gotit.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        indicator.setViewPager(vp);
    }

    class MyPagesAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            //Return total pages, here one for each data item
            return imageArray.length;
        }
        //Create the given page (indicated by position)
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            System.out.print(position);
            View page = inflater.inflate(R.layout.instructioncontent, null);
            ((TextView)page.findViewById(R.id.instructiontext)).setText(instructions[position]);
            ((ImageView)page.findViewById(R.id.imageview)).setImageResource(imageArray[position]);
            if(position > 0){
                ((TextView)page.findViewById(R.id.textview1)).setVisibility(View.INVISIBLE);
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
