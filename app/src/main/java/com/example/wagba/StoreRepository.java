package com.example.wagba;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.wagba.Model.StoreModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class StoreRepository {

    DatabaseReference database;
    private MutableLiveData<ArrayList<StoreModel>> Store_mutable_livedata = new MutableLiveData<>();

    private ArrayList<StoreModel> StoreList =new ArrayList<>();

    public StoreRepository() {
        database = FirebaseDatabase.getInstance().getReference("Store");

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                StoreList.clear();
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    StoreModel store =dataSnapshot.getValue(StoreModel.class);
                    StoreList.add(store);
                }
                Store_mutable_livedata.setValue(StoreList);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }



    public LiveData<ArrayList<StoreModel>> getAllStores() {
        return Store_mutable_livedata;
    }
}
