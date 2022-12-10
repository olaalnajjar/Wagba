package com.example.wagba;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.wagba.Adapter.ItemAdapter;
import com.example.wagba.Model.ItemModel;

import java.util.ArrayList;

public class
StoreDetails extends AppCompatActivity {

    Intent details_intent;
    CardView item_card;
    private RecyclerView recyclerView;
    private ArrayList<ItemModel> recyclerDataArrayList;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_details);

        details_intent= new Intent(this, ItemDetails.class);
        item_card=findViewById(R.id.item_card);
        recyclerView=findViewById(R.id.store_detail_recyclerview);



        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(this, recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        startActivity(details_intent);
                    }

                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );



        getSupportActionBar().hide();




        // created new array list..
        recyclerDataArrayList=new ArrayList<>();

        // added data to array list
        recyclerDataArrayList.add(new ItemModel("Margherita",getResources().getString(R.string.margherita),"50 EGP",R.drawable.marg,R.drawable.ic_baseline_check_circle_outline_24));
        recyclerDataArrayList.add(new ItemModel("Pepperoni",getResources().getString(R.string.pepperoni),"80 EGP",R.drawable.pep,R.drawable.ic_baseline_check_circle_outline_24));
        recyclerDataArrayList.add(new ItemModel("Cheesey",getResources().getString(R.string.cheeseLovers),"80 EGP",R.drawable.cheese,R.drawable.ic_baseline_check_circle_outline_24));
        recyclerDataArrayList.add(new ItemModel("Vegetarian",getResources().getString(R.string.vegetarian),"70 EGP",R.drawable.veg,R.drawable.ic_baseline_check_circle_outline_24));


        // added data from arraylist to adapter class.
        ItemAdapter adapter=new ItemAdapter(recyclerDataArrayList,this);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);

        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(adapter);



    }
}