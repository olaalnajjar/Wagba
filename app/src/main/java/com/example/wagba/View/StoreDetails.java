package com.example.wagba.View;

import static com.example.wagba.ViewModel.StoreViewModel.set_extras_data;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.wagba.R;
import com.example.wagba.View.Adapter.ItemAdapter;
import com.example.wagba.Model.ItemModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import pl.droidsonroids.gif.GifImageView;

public class
StoreDetails extends AppCompatActivity {

    String NOT_AVAILABLE = "https://firebasestorage.googleapis.com/v0/b/wagba-6d791.appspot.com/o/delete.png?alt=media&token=f9b95472-f875-48b3-a4ff-ffdd45b4ca5a";
    Intent details_intent;
    CardView item_card;
    GifImageView gif1, gif2;
    TextView tag1,tag2, store_name;
    RecyclerView recyclerView;
    ArrayList<ItemModel> recyclerDataArrayList;
    DatabaseReference page_ref;
    FirebaseDatabase database;
    ItemAdapter adapter;
    LinearLayoutManager layoutManager;
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

        getSupportActionBar().hide();

        String name = getIntent().getStringExtra("name");

        database = FirebaseDatabase.getInstance();
        page_ref = database.getReference("Store/"+name);
        set_tags();
        recyclerView_init();
        set_extras_data(name,recyclerDataArrayList,adapter);


        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(this, recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        if (recyclerDataArrayList.get(position).getStatus().toString().equals(NOT_AVAILABLE)){
                            Toast.makeText(getApplicationContext(), "Item Not Available",Toast.LENGTH_SHORT).show();

                        }else{
                            String dish =recyclerDataArrayList.get(position).getDish_name();
                            startActivity(details_intent.putExtra("dish",dish).putExtra("name",name));
                            finish();
                        }
                    }

                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );

    }

    private void recyclerView_init() {
        // added data from arraylist to adapter class.
        recyclerDataArrayList=new ArrayList<>();
        adapter=new ItemAdapter(recyclerDataArrayList,this);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    private void set_tags() {

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

    }


}