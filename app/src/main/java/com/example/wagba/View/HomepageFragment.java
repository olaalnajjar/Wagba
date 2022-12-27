package com.example.wagba.View;

import static com.example.wagba.View.MainActivity.EMAIL;
import static com.example.wagba.ViewModel.HomeViewModel.on_category_selected;
import static com.example.wagba.ViewModel.HomeViewModel.setFilterValue;
import static com.example.wagba.ViewModel.HomeViewModel.set_category_data;
import static com.example.wagba.ViewModel.HomeViewModel.set_offers_adapter;
import static com.example.wagba.ViewModel.HomeViewModel.set_offers_data;
import static com.example.wagba.ViewModel.HomeViewModel.set_store_data;
import static com.example.wagba.ViewModel.HomeViewModel.set_username;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
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
import android.widget.SearchView;
import android.widget.TextView;

import com.example.wagba.View.Adapter.CategoryAdapter;
import com.example.wagba.View.Adapter.OffersAdapter;
import com.example.wagba.View.Adapter.StoreAdapter;
import com.example.wagba.Model.CategoriesModel;
import com.example.wagba.Model.OffersModel;
import com.example.wagba.Model.StoreModel;
import com.example.wagba.R;
import com.example.wagba.RoomDatabase.UserDatabase;
import com.example.wagba.RoomDatabase.UserEntity;
import com.example.wagba.ViewModel.HomeViewModel;
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


        on_store_selected(view.getContext());
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

        set_store_data( myRef,  storeAdapter,store_recyclerDataArrayList);

        categories_recyclerView.setLayoutManager((linearLayoutManager));
        categories_recyclerView.setAdapter(categoryAdapter);

        set_offers_adapter(offersAdapter,offers_recyclerView,view.getContext());
    }


    private void on_store_selected(Context context) {
        // on tap click listener for items inside the recyclerview
        Store_recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(context, Store_recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {

                        String name =store_recyclerDataArrayList.get(position).getTitle();
                        startActivity(details_page_intent.putExtra("name",name));
                    }

                    @Override public void onLongItemClick(View view, int position) {

                    }
                })
        );

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