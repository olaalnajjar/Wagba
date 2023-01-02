package com.example.wagba.ViewModel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.wagba.CartRepository;
import com.example.wagba.Model.CartItemModel;
import java.util.ArrayList;

public class CartViewModel extends AndroidViewModel {
    private CartRepository repository;

    private static LiveData<ArrayList<CartItemModel>>  CartItemList;
    public CartViewModel(@NonNull Application application) {
        super(application);
        repository= new CartRepository();
        CartItemList= repository.getAllCartItems();
    }

    public static LiveData<ArrayList<CartItemModel>> getAllCartItems() {
        return CartItemList;
    }


}
