package com.example.wagba.View;

import static com.example.wagba.View.Payment.Order_NO;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.wagba.R;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ItemDetails extends AppCompatActivity {

    ImageView cart;
    CardView add_to_cart;
    TextView title,desc,price,extra1,extra2,item_num;
    ImageView img, add, remove;
    Intent cart_intent;
    GoogleSignInClient googleSignInClient;
    FirebaseAuth auth;
    ImageView logout;
    FirebaseDatabase database;
    DatabaseReference myRef, extras_ref, cartRef;
    static public int COUNT=1;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);

        set_toolbar();

        cart_intent = new Intent(this, Cart.class);
        cart=findViewById(R.id.cart_img);
        add_to_cart = findViewById(R.id.add_to_cart);
        title=findViewById(R.id.dish_name_details);
        desc=findViewById(R.id.dish_description_details);
        price=findViewById(R.id.dish_price);
        extra1=findViewById(R.id.extra_text1);
        extra2=findViewById(R.id.extra_text2);
        img=findViewById(R.id.dish_img_details);
        logout = findViewById(R.id.logout_btn);
        add = findViewById(R.id.add_number);
        remove = findViewById(R.id.remove_number);
        item_num = findViewById(R.id.item_number);

        item_num.setText("0");
        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(cart_intent);
                finish();
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
            }
        });

        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(item_num.getText().toString().equals("0")){
                    Toast.makeText(getApplicationContext(),"Cannot remove item",Toast.LENGTH_SHORT).show();
                }else {
                    item_num.setText(String.valueOf(Integer.parseInt(item_num.getText().toString())-1));
                }
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                item_num.setText(String.valueOf(Integer.parseInt(item_num.getText().toString())+1));
            }
        });



        String dish = getIntent().getStringExtra("dish");
        String name = getIntent().getStringExtra("name");

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Store/"+name+"/Dishes/"+dish);
        extras_ref = database.getReference("Store/"+name+"/Extras");


        set_item_data();


        add_to_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if( item_num.getText().toString().equals("0")) {
                    Toast.makeText(getApplicationContext(),"Cannot add 0 items", Toast.LENGTH_SHORT).show();

                }else{
                    add_item_to_cart(name,dish, item_num.getText().toString());
                    Toast toast = Toast.makeText(getApplicationContext(), "Item added to Cart", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });


    }

    private void set_item_data() {
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

    private void set_toolbar() {
        this.getSupportActionBar().setDisplayOptions(androidx.appcompat.app.ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.toolbar_homepage);
        getSupportActionBar().setElevation(0);
    }

    void signOut (){
        googleSignInClient.signOut();
        auth.signOut();
        startActivity( new Intent(ItemDetails.this, MainActivity.class));
        finish();

    }


    public static void add_item_to_cart( String name,String dish,String item_num) {

        FirebaseDatabase database =FirebaseDatabase.getInstance();
        DatabaseReference Ref = database.getReference();
        FirebaseAuth auth =  FirebaseAuth.getInstance();
        String id = auth.getUid();
        DatabaseReference myRef = database.getReference("Store/"+name+"/Dishes/"+dish);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                String dish_name = snapshot.child("dish_name").getValue().toString();
                Ref.child("cart").child(id).child(dish_name).child("dish_name").setValue(snapshot.child("dish_name").getValue().toString());
                Ref.child("cart").child(id).child(dish_name).child("dish_description").setValue(snapshot.child("Description").getValue().toString());
                Ref.child("cart").child(id).child(dish_name).child("dish_price").setValue(snapshot.child("Price").getValue().toString());
                Ref.child("cart").child(id).child(dish_name).child("img_id").setValue(snapshot.child("Image").getValue());
                Ref.child("cart").child(id).child(dish_name).child("number").setValue("X"+item_num);
                String[] price=snapshot.child("Price").getValue().toString().split(" ");
                int dish_price = Integer.parseInt(price[0]);
                Ref.child("payment").child(id).child(dish_name).child("price").setValue((Integer.parseInt(item_num)*dish_price));
                DatabaseReference history_ref = database.getReference("history_item/"+id+"/"+String.valueOf(Order_NO));
                history_ref.child("order_item_" + String.valueOf(COUNT)).setValue("X" + item_num + " " + dish);
                history_ref.child("order_item_" + String.valueOf(COUNT) + "_price").setValue("Price: " + String.valueOf(Integer.parseInt(item_num)*dish_price));
                COUNT++;
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        return;
    }




}