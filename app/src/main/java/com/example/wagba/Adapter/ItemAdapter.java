package com.example.wagba.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wagba.Model.ItemModel;
import com.example.wagba.R;

import java.util.ArrayList;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.RecyclerViewHolder>{

    private ArrayList<ItemModel> ItemArrayList;
    private Context my_context;
    public ItemAdapter(ArrayList<ItemModel> recyclerDataArrayList, Context my_context) {
        this.ItemArrayList = recyclerDataArrayList;
        this.my_context = my_context;
    }
    @NonNull
    @Override
    public ItemAdapter.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        ItemModel recyclerData = ItemArrayList.get(position);
        holder.dish_name.setText(recyclerData.getDishName());
        holder.dish_description.setText(recyclerData.getDishDescription());
        holder.dish_price.setText(recyclerData.getDishPrice());
        holder.dish_img.setImageResource(recyclerData.getImg_id());
        holder.availability.setImageResource(recyclerData.getAvailability_id());


    }



    @Override
    public int getItemCount() {
        return ItemArrayList.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {

        private TextView dish_name;
        private TextView dish_description;
        private TextView dish_price;
        private ImageView dish_img;
        private ImageView availability;
        private ImageView add_btn;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            dish_name = itemView.findViewById(R.id.dish_name);
            dish_description = itemView.findViewById(R.id.dish_description);
            dish_price = itemView.findViewById(R.id.dish_price);
            dish_img = itemView.findViewById(R.id.dish_img);
            availability = itemView.findViewById(R.id.availability);

        }
    }
}

