package com.example.wagba;

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

    private ArrayList<CartItemModel> cartModels =new ArrayList<>();

    public CartRepository() {

        set_gif();
        FirebaseAuth auth =  FirebaseAuth.getInstance();
        String id = auth.getUid();
        database = FirebaseDatabase.getInstance().getReference("cart/"+id);

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                cartModels.clear();
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    CartItemModel cart =dataSnapshot.getValue(CartItemModel.class);
                    cartModels.add(cart);
                }
                Cart_mutable_livedata.setValue(cartModels);
                set_gif();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    public void set_gif(){

        if(cartModels.isEmpty()){
            gif.setVisibility(View.VISIBLE);
            text.setVisibility(View.VISIBLE);
        }else{
            gif.setVisibility(View.GONE);
            text.setVisibility(View.GONE);
        }
    }

    public LiveData<ArrayList<CartItemModel>> getAllCartItems() {
        return Cart_mutable_livedata;
    }
}
