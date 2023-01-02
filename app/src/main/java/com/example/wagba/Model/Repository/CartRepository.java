package com.example.wagba.Model.Repository;

import static com.example.wagba.View.Cart.Cart_recyclerDataArrayList;
import static com.example.wagba.View.Cart.gif;
import static com.example.wagba.View.Cart.text;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.wagba.Model.CartItemModel;
import com.example.wagba.Model.StoreModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import pl.droidsonroids.gif.GifImageView;

public class CartRepository {

    DatabaseReference database;
    private  MutableLiveData<ArrayList<CartItemModel>> Cart_mutable_livedata = new MutableLiveData<>();

    public CartRepository() {

        FirebaseAuth auth =  FirebaseAuth.getInstance();
        String id = auth.getUid();
        database = FirebaseDatabase.getInstance().getReference("cart/"+id);

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Cart_recyclerDataArrayList.clear();
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    CartItemModel cart =dataSnapshot.getValue(CartItemModel.class);
                    Cart_recyclerDataArrayList.add(cart);
                }
                Cart_mutable_livedata.setValue(Cart_recyclerDataArrayList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


    public LiveData<ArrayList<CartItemModel>> getAllCartItems() {
        return Cart_mutable_livedata;
    }
}
