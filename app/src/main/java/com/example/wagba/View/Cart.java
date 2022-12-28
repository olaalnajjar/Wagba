package com.example.wagba.View;

import static com.example.wagba.ViewModel.CartViewModel.set_cart_data;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Database;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.wagba.Model.StoreModel;
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
    TextView subtotal ,total;

    int total_int=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        set_toolbar();

        subtotal= findViewById(R.id.subtotal);
        total= findViewById(R.id.total);
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

        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference myRef = db.getReference("payment");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Log.d("tag",dataSnapshot.child("price").getValue().toString());
                    total_int= total_int+Integer.parseInt(dataSnapshot.child("price").getValue().toString());
                    subtotal.setText(String.valueOf(total_int));
                    total.setText(String.valueOf(total_int+10));
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




    }


    private void set_toolbar() {
        this.getSupportActionBar().setDisplayOptions(androidx.appcompat.app.ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.toolbar);
        getSupportActionBar().setElevation(0);
    }
}