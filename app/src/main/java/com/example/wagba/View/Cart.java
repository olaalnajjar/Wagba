package com.example.wagba.View;

import static com.example.wagba.ViewModel.CartViewModel.set_cart_data;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.wagba.View.Adapter.CartItemAdapter;
import com.example.wagba.Model.CartItemModel;
import com.example.wagba.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Cart extends AppCompatActivity {

    Intent payment_activity;
    private RecyclerView recyclerView;
    private ArrayList<CartItemModel> recyclerDataArrayList;
    Button payment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        set_toolbar();

        recyclerView=findViewById(R.id.recycler_view_cart);
        payment= findViewById(R.id.payment_btn);
        payment_activity = new Intent(this, Payment.class);

        payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(payment_activity);
                Cart.this.finish();
            }
        });

        // created new array list..
        recyclerDataArrayList=new ArrayList<>();

        // added data from arraylist to adapter class.
        CartItemAdapter adapter=new CartItemAdapter(recyclerDataArrayList,this);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        set_cart_data( recyclerDataArrayList,adapter);

    }


    private void set_toolbar() {
        this.getSupportActionBar().setDisplayOptions(androidx.appcompat.app.ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.toolbar);
        getSupportActionBar().setElevation(0);
    }
}