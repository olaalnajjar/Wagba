package com.example.wagba.View;

import static com.example.wagba.View.Cart.total_int;
import static com.example.wagba.ViewModel.PaymentViewModel.history_item;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.wagba.Model.StoreModel;
import com.example.wagba.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.Date;

public class Payment extends AppCompatActivity {


    Button home_Btn;
    Intent homepage;
    public static int Order_NO=1001;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        homepage = new Intent(this, Homepage.class);
        RadioGroup radioGroup1 = findViewById(R.id.delivery_area);
        RadioGroup radioGroup2 = findViewById(R.id.payment_method);

        set_toolbar();

        home_Btn = findViewById(R.id.homepage);
        home_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (radioGroup1.getCheckedRadioButtonId() == -1)
                {
                    // no radio buttons are checked
                    Toast.makeText(getApplicationContext(),"Please select Delivery area",Toast.LENGTH_SHORT).show();

                }
                else if (radioGroup2.getCheckedRadioButtonId() == -1)
                {
                    // no radio buttons are checked
                    Toast.makeText(getApplicationContext(),"Please select Payment method",Toast.LENGTH_SHORT).show();
                }
                else
                {

                    Toast.makeText(getApplicationContext(),"Your Order has been placed!", Toast.LENGTH_LONG).show();

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        public void run() {
                            Date currentTime = Calendar.getInstance().getTime();
                            String gate;
                            if(radioGroup1.getCheckedRadioButtonId()==R.id.gate3){
                                gate= "Gate 3";
                            }else {gate = "Gate 4";}
                            history_item(String.valueOf(currentTime), String.valueOf(total_int),gate);
                            total_int=0;
                            Toast.makeText(getApplicationContext(),"Order has been placed",Toast.LENGTH_SHORT).show();
                            startActivity(homepage);
                            Payment.this.finish();
                        }
                    }, 1000);   //1 seconds

                }


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