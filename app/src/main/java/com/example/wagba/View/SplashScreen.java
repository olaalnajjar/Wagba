package com.example.wagba.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wagba.R;

public class SplashScreen extends AppCompatActivity {

    Animation left_anim, right_anim;
    ImageView logo;
    TextView app_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);


        getSupportActionBar().hide();

        //configuring window to full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splash_screen);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(SplashScreen.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        }, 2000);

        left_anim= AnimationUtils.loadAnimation(this,R.anim.side_slide_left);
        right_anim= AnimationUtils.loadAnimation(this,R.anim.side_slide_right);

        logo= findViewById(R.id.logo);
        app_name= findViewById(R.id.app_namee);

        logo.setAnimation(left_anim);
        app_name.setAnimation(right_anim);

    }
}