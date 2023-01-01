package com.example.wagba.ViewModel;


import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import com.example.wagba.View.Payment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class PaymentViewModel extends ViewModel {

    public static void history_item(String currentTime, String payment,String delivery, int order,String time){

        FirebaseAuth auth = FirebaseAuth.getInstance();
        String id = auth.getUid();
        FirebaseDatabase db =FirebaseDatabase.getInstance();
        DatabaseReference history_ref = db.getReference("history_item/"+id+"/"+String.valueOf(order));
        Log.d("order",String.valueOf(order));

        history_ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot0) {


                if(!snapshot0.hasChild("order_name")) {
                    String order_name = snapshot0.getKey().toString();
                    history_ref.child("order_name").setValue("#" + order_name);
                    history_ref.child("order_date").setValue("Date: " + currentTime);
                    history_ref.child("order_price").setValue("Price: " + payment);
                    history_ref.child("total_price_details").setValue("Total: " + payment + " LE");
                    history_ref.child("delivery_area").setValue(delivery);
                    history_ref.child("order_status").setValue("Processing");
                    history_ref.child("Status").setValue("Order Placed");
                    history_ref.child("order_time").setValue(time);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        return;
    }



    public static boolean compareDates(String date_string){
        Calendar now = Calendar.getInstance();
        Date date;
        Date dateCompareOne;
        int hour = now.get(Calendar.HOUR);
        int minute = now.get(Calendar.MINUTE);
        Log.d("time",String.valueOf(hour));
        Log.d("time",String.valueOf(minute));

        date = parseDate(hour + ":" + minute);
        dateCompareOne = parseDate(date_string);
        if ( date.before( dateCompareOne ) ) {
            return true;
        }
        return false;

    }
    public static final String inputFormat = "HH:mm";
    public static Date parseDate(String date) {

        SimpleDateFormat inputParser = new SimpleDateFormat(inputFormat, Locale.US);

        try {
            return inputParser.parse(date);
        } catch (java.text.ParseException e) {
            return new Date(0);
        }
    }

}
