package com.example.wagba.ViewModel;

import static com.example.wagba.View.Payment.Order_NO;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import com.example.wagba.View.Payment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PaymentViewModel extends ViewModel {
   public static void history_item(String currentTime, String payment,String delivery){


       Order_NO= Order_NO+1;

       FirebaseDatabase db =FirebaseDatabase.getInstance();
       DatabaseReference history_ref = db.getReference("history_item/"+String.valueOf(Order_NO));
       DatabaseReference cart = db.getReference("cart");



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
                final int[] i = {1};
                cart.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot1) {

                        for (DataSnapshot dataSnapshot : snapshot1.getChildren()) {

                            if (i[0]<4) {
                                String[] num = dataSnapshot.child("number").getValue().toString().split("X");
                                String dish = dataSnapshot.child("dish_name").getValue().toString();
                                history_ref.child("order_item_" + String.valueOf(i[0])).setValue("X" + num[1] + " " + dish);
                                String[] price = dataSnapshot.child("dish_price").getValue().toString().split(" ");
                                int dish_price = Integer.parseInt(price[0]);
                                history_ref.child("order_item_" + String.valueOf(i[0]) + "_price").setValue("Price: " + String.valueOf(dish_price * Integer.parseInt(num[1])));
                                i[0]++;
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                DatabaseReference reference = db.getReference();
                reference.child("payment").removeValue();
                cart.removeValue();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

    }
}
