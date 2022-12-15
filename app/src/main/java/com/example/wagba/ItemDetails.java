package com.example.wagba;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;

public class ItemDetails extends AppCompatActivity {

    ImageView cart, add_to_cart;
    TextView add_to_cart_text;
    Intent cart_intent;
    GoogleSignInClient googleSignInClient;
    GoogleSignInOptions googleSignInOptions;
    FirebaseAuth auth;
    ImageView logout;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);
        this.getSupportActionBar().setDisplayOptions(androidx.appcompat.app.ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.toolbar_homepage);
        getSupportActionBar().setElevation(0);
        View view = getSupportActionBar().getCustomView();

        cart_intent = new Intent(this,Cart.class);
        cart=findViewById(R.id.cart_img);
        add_to_cart=findViewById(R.id.add_to_cart_img);
        add_to_cart_text=findViewById(R.id.add_to_cart_text);

        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(cart_intent);
            }
        });
        add_to_cart_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast toast = Toast.makeText(getApplicationContext(), "Item added to Cart", Toast.LENGTH_SHORT);
                toast.show();
            }
        });
        add_to_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast toast = Toast.makeText(getApplicationContext(), "Item added to Cart", Toast.LENGTH_SHORT);
                toast.show();
            }
        });


        logout = findViewById(R.id.logout_btn);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                signOut();
            }
        });


        googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();


        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions);

        auth = FirebaseAuth.getInstance();
    }
    void signOut (){
        googleSignInClient.signOut();
        auth.signOut();
        startActivity( new Intent(ItemDetails.this, MainActivity.class));
        finish();

    }

}