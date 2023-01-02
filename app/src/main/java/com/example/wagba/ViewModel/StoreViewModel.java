package com.example.wagba.ViewModel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.wagba.Model.ItemModel;
import com.example.wagba.Model.StoreModel;
import com.example.wagba.StoreRepository;
import com.example.wagba.View.Adapter.ItemAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class StoreViewModel extends AndroidViewModel {

    private StoreRepository repository;

    private static LiveData<ArrayList<StoreModel>> StoreList;

    public StoreViewModel(@NonNull Application application) {
        super(application);
        repository =new StoreRepository();
        StoreList= repository.getAllStores();
    }

    public static LiveData<ArrayList<StoreModel>> getAllStores() {
        return StoreList;
    }


}
