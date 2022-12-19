package com.example.wagba;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.wagba.Adapter.CategoryAdapter;
import com.example.wagba.Adapter.OffersAdapter;
import com.example.wagba.Adapter.StoreAdapter;
import com.example.wagba.Model.CategoriesModel;
import com.example.wagba.Model.OffersModel;
import com.example.wagba.Model.StoreModel;
import com.example.wagba.RoomDatabase.UserDatabase;
import com.example.wagba.RoomDatabase.UserEntity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;


public class HomepageFragment extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomepageFragment() {
        // Required empty public constructor
    }


    public static HomepageFragment newInstance(String param1, String param2) {
        HomepageFragment fragment = new HomepageFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    StoreAdapter storeAdapter;

    EditText search_edittext;

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        RecyclerView Store_recyclerView;
        ArrayList<StoreModel> store_recyclerDataArrayList;
        RecyclerView categories_recyclerView;
        ArrayList<OffersModel> offers_recyclerDataArrayList;
        RecyclerView offers_recyclerView;
        ArrayList<CategoriesModel> categories_recyclerDataArrayList;
        Intent details_page_intent;
        TextView welcome_message;

        setHasOptionsMenu(true);

        View decorView = getActivity().getWindow().getDecorView();
        decorView.setSystemUiVisibility(0);

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_homepage, container, false);

        details_page_intent = new Intent(view.getContext(),StoreDetails.class);

        Store_recyclerView=view.findViewById(R.id.recycler_view);
        offers_recyclerView=view.findViewById(R.id.offers_recycler_view);
        categories_recyclerView=view.findViewById(R.id.icon_recycler_view);
        welcome_message = view.findViewById(R.id.welcome_message);

        Store_recyclerView.setHasFixedSize(true);


        String email = requireActivity().getIntent().getStringExtra("email");
        UserDatabase db = Room.databaseBuilder(view.getContext(),
                UserDatabase.class, "user").allowMainThreadQueries().build();
        UserEntity user_room = db.userDao().getCurrentUser(email);
        welcome_message.setText("Welcome Back "+user_room.getName()+"!");




        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Store");

        // created new array list..
        store_recyclerDataArrayList=new ArrayList<>();
        categories_recyclerDataArrayList=new ArrayList<>();
        offers_recyclerDataArrayList=new ArrayList<>();



        // added data to array list

        //store arraylist
       /* store_recyclerDataArrayList.add(new StoreModel("Pizza Hut ",R.drawable.pizza_hut));
        store_recyclerDataArrayList.add(new StoreModel("Sit & Sab Thai Chilli",R.drawable.sit_sab));
        store_recyclerDataArrayList.add(new StoreModel("McDonald's",R.drawable.mcdonalds_01));
        store_recyclerDataArrayList.add(new StoreModel("Papa John's",R.drawable.papa_johns_pizza_01));
        store_recyclerDataArrayList.add(new StoreModel("Food Corner",R.drawable.food_corner));
        store_recyclerDataArrayList.add(new StoreModel("El-Shabrawy",R.drawable.elshabrawy));
        store_recyclerDataArrayList.add(new StoreModel("City Crepe",R.drawable.city_crepe));
        store_recyclerDataArrayList.add(new StoreModel("KFC",R.drawable.kfc_4));
        store_recyclerDataArrayList.add(new StoreModel("Koshary Hend",R.drawable.koshary_hend));
        store_recyclerDataArrayList.add(new StoreModel("The Chinese Muslim",R.drawable.chinese_muslim_restrant));
        store_recyclerDataArrayList.add(new StoreModel("Chinese El Hawary",R.drawable.hawary));*/

        //category arraylist
        categories_recyclerDataArrayList.add(new CategoriesModel("Oriental",R.drawable.egypt));
        categories_recyclerDataArrayList.add(new CategoriesModel("Burgers",R.drawable.hamburger));
        categories_recyclerDataArrayList.add(new CategoriesModel("Fried Chicken",R.drawable.fried_chicken));
        categories_recyclerDataArrayList.add(new CategoriesModel("Pizza",R.drawable.pizza));
        categories_recyclerDataArrayList.add(new CategoriesModel("Crepe",R.drawable.crepe));
        categories_recyclerDataArrayList.add(new CategoriesModel("Asian",R.drawable.noodles));

        //offers arraylist
        offers_recyclerDataArrayList.add(new OffersModel(R.drawable.kfc_offer));
        offers_recyclerDataArrayList.add(new OffersModel(R.drawable.mcdonalds_offer));
        offers_recyclerDataArrayList.add(new OffersModel(R.drawable.papajohns_offer));
        offers_recyclerDataArrayList.add(new OffersModel(R.drawable.pizza_hut_offer));
        offers_recyclerDataArrayList.add(new OffersModel(R.drawable.papajohns_offer2));

        // added data from arraylist to adapter class.
        storeAdapter=new StoreAdapter(store_recyclerDataArrayList,view.getContext());
        CategoryAdapter categoryAdapter = new CategoryAdapter(categories_recyclerDataArrayList,view.getContext() );
        OffersAdapter offersAdapter = new OffersAdapter(offers_recyclerDataArrayList, view.getContext());

        // setting grid layout manager to implement grid view, and setting normal linear layout manger
        GridLayoutManager layoutManager=new GridLayoutManager(view.getContext(),2);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false);

        // setting adapter to recycler view.
        Store_recyclerView.setLayoutManager(layoutManager);
        Store_recyclerView.setAdapter(storeAdapter);

        categories_recyclerView.setLayoutManager((linearLayoutManager));
        categories_recyclerView.setAdapter(categoryAdapter);

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




        // on tap click listener for items inside the recyclerview
        Store_recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(view.getContext(), Store_recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {

                        String name =store_recyclerDataArrayList.get(position).getTitle();
                        startActivity(details_page_intent.putExtra("name",name));
                    }

                    @Override public void onLongItemClick(View view, int position) {

                    }
                })
        );


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
        offers_recyclerView.setLayoutManager(new CustomLinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false));
        offers_recyclerView.setAdapter(offersAdapter);
        return view;
    }



    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.search,menu);
        MenuItem menuItem = menu.findItem(R.id.search_view);
        SearchView searchView =(SearchView)menuItem.getActionView();
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                String search_str = s;
                storeAdapter.getFilter().filter(s);
                return false;
            }
        });

        super.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        if (id== R.id.search_view){

            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}