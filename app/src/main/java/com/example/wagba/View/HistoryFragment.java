package com.example.wagba.View;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.wagba.Model.StoreModel;
import com.example.wagba.View.Adapter.HistoryAdapter;
import com.example.wagba.Model.HistoryModel;
import com.example.wagba.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import pl.droidsonroids.gif.GifImageView;


public class HistoryFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    public HistoryFragment() {
    }

    public static HistoryFragment newInstance(String param1, String param2) {
        HistoryFragment fragment = new HistoryFragment();
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

    RecyclerView History_recyclerView;
    ArrayList<HistoryModel> History_recyclerDataArrayList;
    GifImageView gifImageView;
    TextView text;
    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_history, container, false);



        gifImageView = view.findViewById(R.id.empty_history_gif);
        text = view.findViewById(R.id.empty_history_text);

        History_recyclerView=view.findViewById(R.id.history_recyclerview);

        // created new array list..
        History_recyclerDataArrayList=new ArrayList<>();

        set_gif();
        // added data from arraylist to adapter class.
        HistoryAdapter adapter=new HistoryAdapter(History_recyclerDataArrayList,view.getContext());

        // setting normal linear layout manger
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());

        // setting adapter to recycler view.
        History_recyclerView.setLayoutManager(linearLayoutManager);
        History_recyclerView.setAdapter(adapter);

        FirebaseAuth auth = FirebaseAuth.getInstance();
        String id = auth.getUid();
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference myRef = db.getReference("history_item/"+id);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                History_recyclerDataArrayList.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){


                    HistoryModel historyModel = dataSnapshot.getValue(HistoryModel.class);
                    if(dataSnapshot.hasChild("Status")) {
                        if ("Order Delivered".equals(snapshot.child(dataSnapshot.getKey().toString()).child("Status").getValue().toString())) {
                            myRef.child(dataSnapshot.getKey().toString()).child("order_status").setValue("Delivered");
                        }else if ("Order Cancelled".equals(snapshot.child(dataSnapshot.getKey().toString()).child("Status").getValue().toString())){
                            myRef.child(dataSnapshot.getKey().toString()).child("order_status").setValue("Cancelled");
                        }
                        else{
                            myRef.child(dataSnapshot.getKey().toString()).child("order_status").setValue("Processing");
                        }
                        History_recyclerDataArrayList.add(historyModel);
                        set_gif();
                    }

                }
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });





        return view;
    }
    void set_gif(){
        if(History_recyclerDataArrayList.isEmpty()){
            gifImageView.setVisibility(View.VISIBLE);
            text.setVisibility(View.VISIBLE);
        }else {
            gifImageView.setVisibility(View.GONE);
            text.setVisibility(View.GONE);
        }
    }
}