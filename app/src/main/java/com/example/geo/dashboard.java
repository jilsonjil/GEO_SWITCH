package com.example.geo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.tabs.TabLayout;

public class dashboard extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager viewPager;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);

        tabLayout=findViewById(R.id.tab_layout);
        viewPager=findViewById(R.id.view_pager);
        imageView=findViewById(R.id.imageView2);
        tabLayout.addTab(tabLayout.newTab().setText("LOCATION BASED"));
        tabLayout.addTab(tabLayout.newTab().setText("TIME BASED"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        final locationadapter adapter=new locationadapter(getSupportFragmentManager(),this,tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),userprofile.class);
                startActivity(intent);

            }
        });

    }

}