package com.example.wagba;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.wagba.Adapter.ItemAdapter;
import com.example.wagba.Model.ItemModel;
import com.example.wagba.Model.StoreModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import pl.droidsonroids.gif.GifImageView;

public class
StoreDetails extends AppCompatActivity {

    Intent details_intent;
    CardView item_card;
    GifImageView gif1, gif2;
    TextView tag1,tag2, store_name;

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
        gif1=findViewById(R.id.gifImageView);
        gif2=findViewById(R.id.gifImageView2);
        tag1=findViewById(R.id.tag1);
        tag2=findViewById(R.id.tag2);
        store_name=findViewById(R.id.Store_name);




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



        String name = getIntent().getStringExtra("name");

        // created new array list..
        recyclerDataArrayList=new ArrayList<>();
        recyclerView.setHasFixedSize(true);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Store/"+name+"/Dishes");
        DatabaseReference page_ref = database.getReference("Store/"+name);
        page_ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                tag1.setText(snapshot.child("Tags/1").getValue().toString());
                tag2.setText(snapshot.child("Tags/2").getValue().toString());
                if(snapshot.child("Icon").getValue()!= null){
                    Glide.with(getApplicationContext()).load(snapshot.child("Icon").getValue()).into(gif1);
                }
                Glide.with(getApplicationContext()).load(snapshot.child("Cover Image").getValue()).into(gif2);
                store_name.setText(snapshot.child("title").getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        // added data to array list
       /* recyclerDataArrayList.add(new ItemModel("Margherita",getResources().getString(R.string.margherita),"50 EGP",R.drawable.marg,R.drawable.ic_baseline_check_circle_outline_24));
        recyclerDataArrayList.add(new ItemModel("Pepperoni",getResources().getString(R.string.pepperoni),"80 EGP",R.drawable.pep,R.drawable.ic_baseline_check_circle_outline_24));
        recyclerDataArrayList.add(new ItemModel("Cheesey",getResources().getString(R.string.cheeseLovers),"80 EGP",R.drawable.cheese,R.drawable.ic_baseline_check_circle_outline_24));
        recyclerDataArrayList.add(new ItemModel("Vegetarian",getResources().getString(R.string.vegetarian),"70 EGP",R.drawable.veg,R.drawable.ic_baseline_check_circle_outline_24));
*/

        // added data from arraylist to adapter class.
        ItemAdapter adapter=new ItemAdapter(recyclerDataArrayList,this);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);

        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(adapter);



        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){

                    ItemModel item =dataSnapshot.getValue(ItemModel.class);
                    recyclerDataArrayList.add(item);
                }

                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

}