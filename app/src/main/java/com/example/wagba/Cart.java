package com.example.wagba;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.wagba.Adapter.CartItemAdapter;
import com.example.wagba.Model.CartItemModel;

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

        this.getSupportActionBar().setDisplayOptions(androidx.appcompat.app.ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.toolbar);
        getSupportActionBar().setElevation(0);
        View view = getSupportActionBar().getCustomView();

        recyclerView=findViewById(R.id.recycler_view_cart);
        payment= findViewById(R.id.payment_btn);
        payment_activity = new Intent(this, Payment.class);

        payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(payment_activity);
            }
        });


        // created new array list..
        recyclerDataArrayList=new ArrayList<>();

        // added data to array list
        recyclerDataArrayList.add(new CartItemModel("Margherita",getResources().getString(R.string.margherita),"50 EGP",R.drawable.marg,"1"));
        recyclerDataArrayList.add(new CartItemModel("Pepperoni",getResources().getString(R.string.pepperoni),"80 EGP",R.drawable.pep,"2"));
        recyclerDataArrayList.add(new CartItemModel("Cheesey",getResources().getString(R.string.cheeseLovers),"80 EGP",R.drawable.cheese,"1"));
        recyclerDataArrayList.add(new CartItemModel("Vegetarian",getResources().getString(R.string.vegetarian),"70 EGP",R.drawable.veg,"1"));


        // added data from arraylist to adapter class.
        CartItemAdapter adapter=new CartItemAdapter(recyclerDataArrayList,this);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);

        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(adapter);


    }
}