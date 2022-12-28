package com.example.wagba.View.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.wagba.Model.CartItemModel;
import com.example.wagba.R;

import java.util.ArrayList;

public class CartItemAdapter extends RecyclerView.Adapter<CartItemAdapter.RecyclerViewHolder> {

    private ArrayList<CartItemModel> CartItemArrayList;
    private Context my_context;
    public CartItemAdapter(ArrayList<CartItemModel> recyclerDataArrayList, Context my_context) {
        this.CartItemArrayList = recyclerDataArrayList;
        this.my_context = my_context;
    }

    @NonNull
    @Override
    public CartItemAdapter.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartItemAdapter.RecyclerViewHolder holder, int position) {

        CartItemModel recyclerData = CartItemArrayList.get(position);
        holder.dish_name.setText(recyclerData.getDish_name());
        holder.dish_description.setText(recyclerData.getDish_description());
        holder.dish_price.setText(recyclerData.getDish_price());
        Glide.with(my_context).load(recyclerData.getImg_id()).into(holder.dish_img);
        holder.number.setText(recyclerData.getNumber());
    }

    @Override
    public int getItemCount() {
        return CartItemArrayList.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {

        private TextView dish_name;
        private TextView dish_description;
        private TextView dish_price;
        private ImageView dish_img;
        private TextView number;


        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            dish_name = itemView.findViewById(R.id.dish_name);
            dish_description = itemView.findViewById(R.id.dish_description);
            dish_price = itemView.findViewById(R.id.dish_price);
            dish_img = itemView.findViewById(R.id.dish_img);
            number = itemView.findViewById(R.id.dish_number);


        }
    }
}
