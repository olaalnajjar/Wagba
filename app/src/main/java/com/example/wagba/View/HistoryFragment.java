package com.example.wagba.View;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.wagba.View.Adapter.HistoryAdapter;
import com.example.wagba.Model.HistoryModel;
import com.example.wagba.R;

import java.util.ArrayList;


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

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_history, container, false);


        History_recyclerView=view.findViewById(R.id.history_recyclerview);

        // created new array list..
        History_recyclerDataArrayList=new ArrayList<>();

        // added data to array list

        History_recyclerDataArrayList.add(new HistoryModel("Order #18403"
                , "Sunday, 11-Dec-2022, 10:00 AM "
                ,"410 LE"
                , "Processing"
                ,R.drawable.pizza_hut_cover
                ,R.drawable.pizza
                ,"1X Large Pepperoni Pizza"
                ,"2X Medium Cheesy Lovers Pizza"
                ,"1X Small Vegetarian Pizza"
                ,"Price: 140 LE"
                ,"Price: 200 LE"
                ,"Price: 60 LE"
                ,"Total: 410 LE"));
        History_recyclerDataArrayList.add(new HistoryModel("Order #07829"
                , "Friday, 9-Dec-2022, 2:00 PM "
                ,"510 LE"
                , "Delivered"
                ,R.drawable.pizza_hut_cover
                ,R.drawable.pizza
                ,"1X Large Pepperoni Pizza"
                ,"3X Medium Cheesy Lovers Pizza"
                ,"1X Small Vegetarian Pizza"
                ,"Price: 140 LE"
                ,"Price: 300 LE"
                ,"Price: 60 LE"
                ,"Total: 510 LE"));
        History_recyclerDataArrayList.add(new HistoryModel("Order #22543"
                , "Thursday, 8-Dec-2022, 09:00 AM "
                ,"310 LE"
                , "Canceled"
                ,R.drawable.pizza_hut_cover
                ,R.drawable.pizza
                ,"1X Large Pepperoni Pizza"
                ,"1X Medium Cheesy Lovers Pizza"
                ,"1X Small Vegetarian Pizza"
                ,"Price: 140 LE"
                ,"Price: 100 LE"
                ,"Price: 60 LE"
                ,"Total: 310 LE"));


        // added data from arraylist to adapter class.
        HistoryAdapter adapter=new HistoryAdapter(History_recyclerDataArrayList,view.getContext());

        // setting normal linear layout manger
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());

        // setting adapter to recycler view.
        History_recyclerView.setLayoutManager(linearLayoutManager);
        History_recyclerView.setAdapter(adapter);


        return view;
    }
}