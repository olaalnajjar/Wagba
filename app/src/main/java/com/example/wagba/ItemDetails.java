package com.example.wagba;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ItemDetails extends AppCompatActivity {

    ImageView cart, add_to_cart;
    TextView add_to_cart_text;
    TextView title,desc,price,extra1,extra2;
    ImageView img;
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
        title=findViewById(R.id.dish_name_details);
        desc=findViewById(R.id.dish_description_details);
        price=findViewById(R.id.dish_price);
        extra1=findViewById(R.id.extra_text1);
        extra2=findViewById(R.id.extra_text2);
        img=findViewById(R.id.dish_img_details);


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


        String dish = getIntent().getStringExtra("dish");
        String name = getIntent().getStringExtra("name");
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Store/"+name+"/Dishes/"+dish);
        DatabaseReference extras_ref = database.getReference("Store/"+name+"/Extras");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                title.setText(snapshot.child("dish_name").getValue().toString());
                desc.setText(snapshot.child("Description").getValue().toString());
                price.setText(snapshot.child("Price").getValue().toString());
                Glide.with(getApplicationContext()).load(snapshot.child("Image").getValue()).into(img);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        extras_ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                extra1.setText(snapshot.child("1").getValue().toString());
                extra2.setText(snapshot.child("2").getValue().toString());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




    }
    void signOut (){
        googleSignInClient.signOut();
        auth.signOut();
        startActivity( new Intent(ItemDetails.this, MainActivity.class));
        finish();

    }






}