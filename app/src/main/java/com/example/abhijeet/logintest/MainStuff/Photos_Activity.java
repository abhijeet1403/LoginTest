package com.example.abhijeet.logintest.MainStuff;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;


import com.example.abhijeet.logintest.Adapters.Customswipeadapter;
import com.example.abhijeet.logintest.R;


public class Photos_Activity extends AppCompatActivity {

    ViewPager viewPager;
    Customswipeadapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photos_);

        viewPager=(ViewPager)findViewById(R.id.view_pager);
        adapter=new Customswipeadapter(this);
        adapter.setPhotos(MainFragment.keyList);
        viewPager.setAdapter(adapter);
        Intent i = getIntent();
        int position = i.getIntExtra("position", 0);
        Log.d("position",""+position);
        viewPager.setCurrentItem(position);
        Log.d("viewpager",viewPager.toString());

    }



}
