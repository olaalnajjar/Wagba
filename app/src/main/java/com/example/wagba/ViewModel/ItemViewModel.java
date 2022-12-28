package com.example.wagba.ViewModel;

import android.content.Context;
import android.widget.Toast;
import androidx.annotation.NonNull;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class ItemViewModel {

    public static void add_item_to_cart( DatabaseReference myRef, DatabaseReference cartRef,String item_num) {

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String dish_name = snapshot.child("dish_name").getValue().toString();
                cartRef.child("cart").child(dish_name).child("dish_name").setValue(snapshot.child("dish_name").getValue().toString());
                cartRef.child("cart").child(dish_name).child("dish_description").setValue(snapshot.child("Description").getValue().toString());
                cartRef.child("cart").child(dish_name).child("dish_price").setValue(snapshot.child("Price").getValue().toString());
                cartRef.child("cart").child(dish_name).child("img_id").setValue(snapshot.child("Image").getValue());
                cartRef.child("cart").child(dish_name).child("number").setValue(item_num);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
             }
        });
    }

 }


