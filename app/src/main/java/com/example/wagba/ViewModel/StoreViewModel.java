package com.example.wagba.ViewModel;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.wagba.Model.ItemModel;
import com.example.wagba.View.Adapter.ItemAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class StoreViewModel {


    public static void set_extras_data(String name, ArrayList<ItemModel> recyclerDataArrayList, ItemAdapter adapter) {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Store/"+name+"/Dishes");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Log.d("extra",dataSnapshot.getKey().toString());
                    if (!(dataSnapshot.getKey().toString() =="Extras")){

                        ItemModel item =dataSnapshot.getValue(ItemModel.class);
                        recyclerDataArrayList.add(item);
                    }
                }
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

    }
}
