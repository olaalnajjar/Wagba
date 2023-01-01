package com.example.wagba.ViewModel;

import static com.example.wagba.View.ItemDetails.COUNT;
import static com.example.wagba.View.Payment.Order_NO;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.ThreadLocalRandom;

public class ItemViewModel {

    //public static long order_number= ThreadLocalRandom.current().nextInt(); ;

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
                /*Ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        if(snapshot.hasChild("order_number/"+id)){
                            order_number = (long) snapshot.child("order_number").child(id).getValue();
                        }else{
                            Ref.child("order_number").child(id).setValue(order_number);
                        }
                           //assert  snapshot.child("order_number").child(id).getValue() != null;


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
*/
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


