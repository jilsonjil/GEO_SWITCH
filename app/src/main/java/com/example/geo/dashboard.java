package com.example.geo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;


import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;


import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

public class dashboard extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager viewPager;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);
        drawerLayout=findViewById(R.id.drawer_layout);
        navigationView=findViewById(R.id.navigationview);
        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.nav_open,R.string.nav_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        tabLayout=findViewById(R.id.tab_layout);
        viewPager=findViewById(R.id.view_pager);
        tabLayout.addTab(tabLayout.newTab().setText("LOCATION BASED"));
        tabLayout.addTab(tabLayout.newTab().setText("TIME BASED"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        final locationadapter adapter=new locationadapter(getSupportFragmentManager(),this,tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.profile:
                        Intent intent=new Intent(getApplicationContext(),userprofile.class);
                        startActivity(intent);
                        break;

                    case R.id.history:
                        Toast.makeText(dashboard.this,"You have clicked history",Toast.LENGTH_SHORT).show();break;
                    case R.id.logout:
                        Intent i=new Intent(getApplicationContext(),login.class);
                        startActivity(i);
                        break;

                }
                return false;
            }
        });

    }


}