package com.example.wagba.ViewModel;

import static com.example.wagba.View.MainActivity.EMAIL;

import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.wagba.Model.CategoriesModel;
import com.example.wagba.Model.OffersModel;
import com.example.wagba.Model.StoreModel;
import com.example.wagba.R;
import com.example.wagba.RoomDatabase.UserDatabase;
import com.example.wagba.RoomDatabase.UserEntity;
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
import java.util.Objects;

public class HomeViewModel extends ViewModel {

    public static void set_username(View view) {
        String name;
        TextView welcome_message = view.findViewById(R.id.welcome_message);
        UserDatabase db = Room.databaseBuilder(view.getContext(),
                UserDatabase.class, "user").allowMainThreadQueries().build();
        UserEntity user_room = db.userDao().getCurrentUser(EMAIL);

        FirebaseAuth auth;
        auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();


        if (user.getDisplayName() == null){
            name = user_room.getName();
            welcome_message.setText("Welcome Back "+name+"!");
        }else{
            name =  user.getDisplayName().toString();
            welcome_message.setText("Welcome Back "+name+"!");

        }
    }

    public static void set_offers_adapter(OffersAdapter offersAdapter, RecyclerView offers_recyclerView, Context context) {
        // setting the automatically scrolling recyclerview for offers
        final int speedScroll = 3200;
        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            int count = 0;
            boolean flag = true;
            @Override
            public void run() {
                if(count < offersAdapter.getItemCount()){
                    if(count==offersAdapter.getItemCount()-1){
                        flag = false;
                    }else if(count == 0){
                        flag = true;
                    }
                    if(flag) count++;
                    else count--;

                    offers_recyclerView.smoothScrollToPosition(count);
                    handler.postDelayed(this,speedScroll);
                }
            }
        };

        handler.postDelayed(runnable,speedScroll);

        // setting adapter to recycler view.
        offers_recyclerView.setLayoutManager(new CustomLinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        offers_recyclerView.setAdapter(offersAdapter);
    }

    public static void set_store_data(DatabaseReference myRef, StoreAdapter storeAdapter,ArrayList<StoreModel> store_recyclerDataArrayList ) {
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){

                    StoreModel store =dataSnapshot.getValue(StoreModel.class);
                    store_recyclerDataArrayList.add(store);
                }
                storeAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public static void set_offers_data(ArrayList<OffersModel> offers_recyclerDataArrayList) {
        //offers arraylist
        offers_recyclerDataArrayList.add(new OffersModel(R.drawable.kfc_offer));
        offers_recyclerDataArrayList.add(new OffersModel(R.drawable.mcdonalds_offer));
        offers_recyclerDataArrayList.add(new OffersModel(R.drawable.papajohns_offer));
        offers_recyclerDataArrayList.add(new OffersModel(R.drawable.pizza_hut_offer));
        offers_recyclerDataArrayList.add(new OffersModel(R.drawable.papajohns_offer2));
    }

    public static void set_category_data(ArrayList<CategoriesModel> categories_recyclerDataArrayList) {
        //category arraylist
        categories_recyclerDataArrayList.add(new CategoriesModel("Oriental",R.drawable.egypt));
        categories_recyclerDataArrayList.add(new CategoriesModel("Burgers",R.drawable.hamburger));
        categories_recyclerDataArrayList.add(new CategoriesModel("Fried Chicken",R.drawable.fried_chicken));
        categories_recyclerDataArrayList.add(new CategoriesModel("Pizza",R.drawable.pizza));
        categories_recyclerDataArrayList.add(new CategoriesModel("Crepe",R.drawable.crepe));
        categories_recyclerDataArrayList.add(new CategoriesModel("Asian",R.drawable.noodles));
    }

    public static void on_category_selected(RecyclerView categories_recyclerView, Context context,
                                      ArrayList<CategoriesModel> categories_recyclerDataArrayList,
                                      ArrayList<StoreModel> store_recyclerDataArrayList,
                                      StoreAdapter storeAdapter,
                                      DatabaseReference myRef) {
        categories_recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(context, categories_recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {

                        String types = categories_recyclerDataArrayList.get(position).getTitle();
                        CategoriesModel category = categories_recyclerDataArrayList.get(position);

                        if (Objects.equals(types, "Oriental"))
                        {setFilterValue("Oriental",category,categories_recyclerDataArrayList,store_recyclerDataArrayList,storeAdapter,myRef);}
                        else if(Objects.equals(types, "Burgers"))
                        {setFilterValue("American",category,categories_recyclerDataArrayList,store_recyclerDataArrayList,storeAdapter,myRef);}
                        else if (Objects.equals(types, "Fried Chicken"))
                        {setFilterValue("Fried Chicken",category,categories_recyclerDataArrayList,store_recyclerDataArrayList,storeAdapter,myRef);}
                        else if(Objects.equals(types, "Pizza"))
                        {setFilterValue("Italian",category,categories_recyclerDataArrayList,store_recyclerDataArrayList,storeAdapter,myRef);}
                        else if(Objects.equals(types, "Crepe"))
                        {setFilterValue("Crepes",category,categories_recyclerDataArrayList,store_recyclerDataArrayList,storeAdapter,myRef);}
                        else if(Objects.equals(types, "Asian"))
                        {setFilterValue("Asian",category,categories_recyclerDataArrayList,store_recyclerDataArrayList,storeAdapter,myRef);}

                    }

                    @Override public void onLongItemClick(View view, int position) {

                    }
                })
        );


    }


    public static void setFilterValue(String types,
                                CategoriesModel category,
                                ArrayList<CategoriesModel> categories_recyclerDataArrayList,
                                ArrayList<StoreModel> store_recyclerDataArrayList,
                                StoreAdapter storeAdapter,
                                DatabaseReference myRef){


        if(!category.isSelected()) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                categories_recyclerDataArrayList.forEach(categoriesModel -> {
                    categoriesModel.setSelected(false);
                });
            }
            category.setSelected(true);

            store_recyclerDataArrayList.clear();
            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                        StoreModel store;
                        String type = dataSnapshot.child("Tags/1").getValue().toString();
                        if (type.equals(types)) {
                            store = dataSnapshot.getValue(StoreModel.class);
                            store_recyclerDataArrayList.add(store);
                        }
                    }

                    storeAdapter.notifyDataSetChanged();

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }else{
            category.setSelected(false);

            store_recyclerDataArrayList.clear();
            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                        StoreModel store = dataSnapshot.getValue(StoreModel.class);
                        store_recyclerDataArrayList.add(store);

                    }

                    storeAdapter.notifyDataSetChanged();

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });


        }
    }
}
