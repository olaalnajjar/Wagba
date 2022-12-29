package com.example.wagba.ViewModel;

import static com.example.wagba.View.Payment.Order_NO;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import com.example.wagba.View.Payment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PaymentViewModel extends ViewModel {

    public static void history_item(String currentTime, String payment,String delivery, int order){

        FirebaseDatabase db =FirebaseDatabase.getInstance();
        DatabaseReference history_ref = db.getReference("history_item/"+String.valueOf(order));

        history_ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot0) {


                String order_name = snapshot0.getKey().toString();
                history_ref.child("order_name").setValue("#" + order_name);
                history_ref.child("order_date").setValue("Date: "+currentTime);
                history_ref.child("order_price").setValue("Price: "+payment);
                history_ref.child("total_price_details").setValue("Total: " + payment + " LE");
                history_ref.child("delivery_area").setValue(delivery);
                history_ref.child("order_status").setValue("Processing");
                history_ref.child("Status").setValue("Order Placed");
                Log.d("debug","location 10");

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        return;
    }
}
