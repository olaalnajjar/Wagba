package com.example.wagba.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wagba.R;
import com.example.wagba.Model.StoreModel;

import java.util.ArrayList;

public class StoreAdapter extends RecyclerView.Adapter<StoreAdapter.RecyclerViewHolder>{

    private ArrayList<StoreModel> StoreArrayList;
    private Context my_context;
    public StoreAdapter(ArrayList<StoreModel> recyclerDataArrayList, Context my_context) {
        this.StoreArrayList = recyclerDataArrayList;
        this.my_context = my_context;
    }
    @NonNull
    @Override
    public StoreAdapter.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.store, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        StoreModel recyclerData = StoreArrayList.get(position);
        holder.store_title.setText(recyclerData.getTitle());
        holder.store_img.setImageResource(recyclerData.getImg_id());

    }


    @Override
    public int getItemCount() {
        return StoreArrayList.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {

        private TextView store_title;
        private ImageView store_img;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            store_title = itemView.findViewById(R.id.store_title);
            store_img = itemView.findViewById(R.id.store_image);
        }
    }
}
