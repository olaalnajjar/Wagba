package com.example.wagba.ViewModel;

import androidx.annotation.NonNull;

import com.example.wagba.Model.CartItemModel;
import com.example.wagba.View.Adapter.CartItemAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CartViewModel {

    public static void set_cart_data(ArrayList<CartItemModel> recyclerDataArrayList, CartItemAdapter adapter) {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("cart");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){

                    CartItemModel cartItemModel =dataSnapshot.getValue(CartItemModel.class);
                    recyclerDataArrayList.add(cartItemModel);
                }

                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }
}
