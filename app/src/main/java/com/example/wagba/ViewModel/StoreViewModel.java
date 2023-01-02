package com.example.wagba.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.wagba.Model.StoreModel;
import com.example.wagba.Model.Repository.StoreRepository;

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
