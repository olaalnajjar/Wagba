package com.example.wagba.View;

import static com.example.wagba.View.MainActivity.EMAIL;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
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
import android.widget.SearchView;
import android.widget.TextView;

import com.example.wagba.RoomDatabase.UserDatabase;
import com.example.wagba.RoomDatabase.UserEntity;
import com.example.wagba.View.Adapter.CategoryAdapter;
import com.example.wagba.View.Adapter.OffersAdapter;
import com.example.wagba.View.Adapter.StoreAdapter;
import com.example.wagba.Model.CategoriesModel;
import com.example.wagba.Model.OffersModel;
import com.example.wagba.Model.StoreModel;
import com.example.wagba.R;
import com.example.wagba.ViewModel.StoreViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class HomepageFragment extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public HomepageFragment() {
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
    RecyclerView Store_recyclerView;
    ArrayList<StoreModel> store_recyclerDataArrayList;
    RecyclerView categories_recyclerView;
    ArrayList<OffersModel> offers_recyclerDataArrayList;
    RecyclerView offers_recyclerView;
    ArrayList<CategoriesModel> categories_recyclerDataArrayList;
    Intent details_page_intent;
    CategoryAdapter categoryAdapter;
    OffersAdapter offersAdapter;
    DatabaseReference myRef;
    StoreViewModel storeViewModel;

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        setHasOptionsMenu(true);

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_homepage, container, false);

        details_page_intent = new Intent(view.getContext(), StoreDetails.class);

        //finding components
        Store_recyclerView=view.findViewById(R.id.recycler_view);
        offers_recyclerView=view.findViewById(R.id.offers_recycler_view);
        categories_recyclerView=view.findViewById(R.id.icon_recycler_view);

        set_username(view);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Store");

        recyclerview_init(view);


        //on_store_selected(view.getContext());
        on_category_selected(categories_recyclerView,view.getContext(),categories_recyclerDataArrayList,store_recyclerDataArrayList,storeAdapter,myRef);


        return view;
    }

    private void recyclerview_init(View view) {

        Store_recyclerView.setHasFixedSize(true);
        // created new array list..
        store_recyclerDataArrayList=new ArrayList<>();
        categories_recyclerDataArrayList=new ArrayList<>();
        offers_recyclerDataArrayList=new ArrayList<>();

        set_category_data( categories_recyclerDataArrayList);
        set_offers_data(offers_recyclerDataArrayList);

        storeViewModel  = new ViewModelProvider(this).get(StoreViewModel.class);


        // added data from arraylist to adapter class.
        storeAdapter=new StoreAdapter(store_recyclerDataArrayList,view.getContext());
        categoryAdapter = new CategoryAdapter(categories_recyclerDataArrayList,view.getContext() );
        offersAdapter = new OffersAdapter(offers_recyclerDataArrayList, view.getContext());

        // setting grid layout manager to implement grid view, and setting normal linear layout manger
        GridLayoutManager layoutManager=new GridLayoutManager(view.getContext(),2);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false);

        // setting adapter to recycler view.
        Store_recyclerView.setLayoutManager(layoutManager);
        Store_recyclerView.setAdapter(storeAdapter);

        storeViewModel.getAllStores().observe(getViewLifecycleOwner(), new Observer<List<StoreModel>>() {
            @Override
            public void onChanged(List<StoreModel> storeModels) {
                storeAdapter.setStoreList((ArrayList<StoreModel>) storeModels);

            }
        });


        categories_recyclerView.setLayoutManager((linearLayoutManager));
        categories_recyclerView.setAdapter(categoryAdapter);

        set_offers_adapter(offersAdapter,offers_recyclerView,view.getContext());
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

    public static void set_offers_adapter(OffersAdapter offersAdapter,
                                          RecyclerView offers_recyclerView, Context context) {
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