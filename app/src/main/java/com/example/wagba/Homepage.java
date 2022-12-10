package com.example.wagba;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wagba.Adapter.CategoryAdapter;
import com.example.wagba.Adapter.OffersAdapter;
import com.example.wagba.Adapter.StoreAdapter;
import com.example.wagba.Model.CategoriesModel;
import com.example.wagba.Model.OffersModel;
import com.example.wagba.Model.StoreModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class Homepage extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {


    ImageView cart;
    Intent cart_intent;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.homepage_nav);


        cart_intent = new Intent(this,Cart.class);

        this.getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.toolbar_homepage);
        getSupportActionBar().setElevation(0);
        View view = getSupportActionBar().getCustomView();


        cart=findViewById(R.id.cart_img);

        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(cart_intent);
            }
        });

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.homepage_nav:
                getSupportFragmentManager().beginTransaction().replace(R.id.nav_frag, new HomepageFragment()).commit();
                return true;

            case R.id.track_order_nav:
                getSupportFragmentManager().beginTransaction().replace(R.id.nav_frag, new TrackOrderFragment()).commit();
                return true;

            case R.id.history_nav:
                getSupportFragmentManager().beginTransaction().replace(R.id.nav_frag, new HistoryFragment()).commit();
                return true;
        }
        return false;
    }




}