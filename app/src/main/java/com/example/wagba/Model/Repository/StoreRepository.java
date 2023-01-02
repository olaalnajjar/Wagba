package com.example.wagba.Model.Repository;

import static com.example.wagba.View.HomepageFragment.store_recyclerDataArrayList;

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


    public StoreRepository() {
        database = FirebaseDatabase.getInstance().getReference("Store");

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                store_recyclerDataArrayList.clear();
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    StoreModel store =dataSnapshot.getValue(StoreModel.class);
                    store_recyclerDataArrayList.add(store);
                }
                Store_mutable_livedata.setValue(store_recyclerDataArrayList);

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
