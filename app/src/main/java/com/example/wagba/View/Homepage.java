package com.example.wagba.View;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.wagba.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class Homepage extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {


    ImageView cart;
    GoogleSignInClient googleSignInClient;
    GoogleSignInOptions googleSignInOptions;
    FirebaseAuth auth;
    ImageView logout;
    Fragment fragment;
    BottomNavigationView bottomNavigationView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);


        set_actionbar();
        google_init();

        logout = findViewById(R.id.logout_btn);
        cart=findViewById(R.id.cart_img);
        fragment= getSupportFragmentManager().findFragmentById(R.id.nav_frag);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.homepage_nav);


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
            }
        });

        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Homepage.this,Cart.class));
            }
        });

    }



    private void google_init() {
        googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions);

        auth = FirebaseAuth.getInstance();
    }

    private void set_actionbar() {
        this.getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.toolbar_homepage);
        getSupportActionBar().setElevation(0);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.homepage_nav:
                getSupportFragmentManager().beginTransaction().replace(R.id.nav_frag, new HomepageFragment(),"Home").commit();
                return true;

            case R.id.track_order_nav:
                getSupportFragmentManager().beginTransaction().replace(R.id.nav_frag, new TrackOrderFragment()).commit();
                return true;

            case R.id.history_nav:
                getSupportFragmentManager().beginTransaction().replace(R.id.nav_frag, new HistoryFragment()).commit();
                return true;

            case R.id.profile_page_nav:
                getSupportFragmentManager().beginTransaction().replace(R.id.nav_frag, new ProfileFragment()).commit();
                return true;
        }
        return false;
    }


    void signOut (){
        googleSignInClient.signOut();
        auth.signOut();
        startActivity( new Intent(Homepage.this, MainActivity.class));
        finish();

    }

    @Override
    public void onBackPressed() {

        HomepageFragment myFragment = (HomepageFragment) getSupportFragmentManager().findFragmentByTag("Home");
        if (myFragment != null && myFragment.isVisible()) {
            super.onBackPressed();
            getSupportFragmentManager().clearBackStack(null);
            signOut();
            finish();
        }else {
            getSupportFragmentManager().beginTransaction().replace(R.id.nav_frag, new HomepageFragment(),"Home").commit();
            bottomNavigationView.setSelectedItemId(R.id.homepage_nav);
        }

    }


}