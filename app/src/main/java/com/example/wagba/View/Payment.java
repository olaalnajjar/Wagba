package com.example.wagba.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.wagba.R;

public class Payment extends AppCompatActivity {

    Button home_Btn;
    Intent homepage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        homepage = new Intent(this, Homepage.class);

        set_toolbar();

        home_Btn = findViewById(R.id.homepage);
        home_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Your Order has been placed!", Toast.LENGTH_LONG).show();

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        startActivity(homepage);
                        Payment.this.finish();
                    }
                }, 1000);   //1 seconds


            }
        });
    }

    private void set_toolbar() {
        this.getSupportActionBar().setDisplayOptions(androidx.appcompat.app.ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.toolbar);
        getSupportActionBar().setElevation(0);
    }
}