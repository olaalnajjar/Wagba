package com.example.wagba.ViewModel;

import static com.example.wagba.View.MainActivity.EMAIL;

import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.wagba.Model.CategoriesModel;
import com.example.wagba.Model.OffersModel;
import com.example.wagba.Model.StoreModel;
import com.example.wagba.R;
import com.example.wagba.RoomDatabase.UserDatabase;
import com.example.wagba.RoomDatabase.UserEntity;
import com.example.wagba.StoreRepository;
import com.example.wagba.View.Adapter.OffersAdapter;
import com.example.wagba.View.Adapter.StoreAdapter;
import com.example.wagba.View.CustomLinearLayoutManager;
import com.example.wagba.View.RecyclerItemClickListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class HomeViewModel extends AndroidViewModel {

    private StoreRepository repository;

    private static LiveData<ArrayList<StoreModel>> StoreList;

    public HomeViewModel(@NonNull Application application) {
        super(application);
        repository =new StoreRepository();
        StoreList= repository.getAllStores();
    }

    public static LiveData<ArrayList<StoreModel>> getAllStores() {
        return StoreList;
    }


}
