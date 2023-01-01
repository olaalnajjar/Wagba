package com.example.wagba.View;

import static com.example.wagba.View.Cart.total_int;
import static com.example.wagba.ViewModel.PaymentViewModel.compareDates;
import static com.example.wagba.ViewModel.PaymentViewModel.history_item;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.wagba.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.concurrent.ThreadLocalRandom;

public class Payment extends AppCompatActivity {


    Button place_order_btn;
    Intent homepage;



    private String compareStringOne = "12:00";
    private String compareStringTwo = "01:00";


    public static int Order_NO= ThreadLocalRandom.current().nextInt(10000); ;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        homepage = new Intent(this, Homepage.class);
        RadioGroup radioGroup1 = findViewById(R.id.delivery_area);
        RadioGroup radioGroup2 = findViewById(R.id.delivery_time);

        set_toolbar();

        FirebaseDatabase db =FirebaseDatabase.getInstance();
        DatabaseReference reference = db.getReference();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        String id = auth.getUid();
        place_order_btn = findViewById(R.id.place_order);
        place_order_btn.setOnClickListener(new View.OnClickListener() {
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

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        public void run() {
                            Date currentTime = Calendar.getInstance().getTime();
                            String gate;
                            if(radioGroup1.getCheckedRadioButtonId()==R.id.gate3){
                                gate= "Gate 3";
                            }else {gate = "Gate 4";}
                            if(radioGroup2.getCheckedRadioButtonId()==R.id.noon){
                                if(compareDates(compareStringOne)){

                                    history_item(String.valueOf(currentTime), String.valueOf(total_int),gate,Order_NO,"noon");
                                    reference.child("payment").removeValue();
                                    reference.child("cart").removeValue();

                                   // reference.child("order_number").child(id).setValue(ee);
                                    Order_NO=ThreadLocalRandom.current().nextInt(10000);
                                    total_int=0;
                                    ItemDetails.COUNT=1;
                                    Toast.makeText(getApplicationContext(),"Order has been placed",Toast.LENGTH_SHORT).show();
                                    startActivity(homepage);
                                    finish();
                                }else{
                                    Toast.makeText(getApplicationContext(),"Cannot make order for noon when its past 10",Toast.LENGTH_SHORT).show();
                                }

                            }else {
                                if(compareDates(compareStringTwo)){

                                    history_item(String.valueOf(currentTime), String.valueOf(total_int),gate,Order_NO,"three");
                                    reference.child("payment").removeValue();
                                    reference.child("cart").removeValue();
                                  //  reference.child("order_number").child(id).setValue(ee);
                                    total_int=0;
                                    ItemDetails.COUNT=1;
                                    Order_NO= ThreadLocalRandom.current().nextInt(10000);
                                    Toast.makeText(getApplicationContext(),"Order has been placed",Toast.LENGTH_SHORT).show();
                                    startActivity(homepage);
                                    finish();
                                }else{
                                    Toast.makeText(getApplicationContext(),"Cannot make order for 3:00 when its past 1:00",Toast.LENGTH_SHORT).show();
                                }
                                }

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

  /*  public int get_current_order_num(){

        FirebaseDatabase db =FirebaseDatabase.getInstance();
        DatabaseReference reference = db.getReference();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        String id = auth.getUid();
        final String[] order = new String[1];
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                order[0] = snapshot.child("order_number").child(id).getValue().toString();
                Log.d("order1",snapshot.getKey().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        Log.d("order",id);

        return Integer.parseInt(order[0]);
    }*/
}