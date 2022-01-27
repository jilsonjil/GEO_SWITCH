package com.example.geo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class splash extends AppCompatActivity {
    private  static int SPALSH_SCREEN=5000;
    Animation top,bottom;
    ImageView image;
    TextView logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.splash);
        top= AnimationUtils.loadAnimation(this,R.anim.top_animations);
        bottom= AnimationUtils.loadAnimation(this,R.anim.bottom_animations);
        image=findViewById(R.id.imageView4);
        logo=findViewById(R.id.textView5);
        image.setAnimation(top);
        logo.setAnimation(bottom);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
               // SharedPreferences pref = getSharedPreferences("mypref", Context.MODE_PRIVATE);
               // String uname=pref.getString("userId","");
               // if (uname.isEmpty()) {
                   Intent intent=new Intent(splash.this, login.class);
                   startActivity(intent);
                  //  finish();
                //} else {
                 //   Intent intent=new Intent(splash.this,dashboard.class);
                  //  startActivity(intent);
                   // finish();
                //}

            }
        },SPALSH_SCREEN);



    }
}