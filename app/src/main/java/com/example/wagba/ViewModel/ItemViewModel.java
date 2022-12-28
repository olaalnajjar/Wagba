package com.example.wagba.ViewModel;

import android.content.Context;
import android.widget.Toast;
import androidx.annotation.NonNull;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ItemViewModel {

    public static void add_item_to_cart( DatabaseReference myRef,String item_num) {

        FirebaseDatabase database =FirebaseDatabase.getInstance();
        DatabaseReference cartRef = database.getReference();
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String dish_name = snapshot.child("dish_name").getValue().toString();
                cartRef.child("cart").child(dish_name).child("dish_name").setValue(snapshot.child("dish_name").getValue().toString());
                cartRef.child("cart").child(dish_name).child("dish_description").setValue(snapshot.child("Description").getValue().toString());
                cartRef.child("cart").child(dish_name).child("dish_price").setValue(snapshot.child("Price").getValue().toString());
                cartRef.child("cart").child(dish_name).child("img_id").setValue(snapshot.child("Image").getValue());
                cartRef.child("cart").child(dish_name).child("number").setValue("X"+item_num);
                String[] price=snapshot.child("Price").getValue().toString().split(" ");
                int dish_price = Integer.parseInt(price[0]);
                cartRef.child("payment").child(dish_name).child("price").setValue((Integer.parseInt(item_num)*dish_price));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
             }
        });
    }

 }


