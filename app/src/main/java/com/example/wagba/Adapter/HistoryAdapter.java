package com.example.wagba.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import com.example.wagba.Model.HistoryModel;
import com.example.wagba.R;
import java.util.ArrayList;

import pl.droidsonroids.gif.GifImageView;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.RecyclerViewHolder>{


    private ArrayList<HistoryModel> ItemArrayList;
    private Context my_context;
    public HistoryAdapter(ArrayList<HistoryModel> recyclerDataArrayList, Context my_context) {
        this.ItemArrayList = recyclerDataArrayList;
        this.my_context = my_context;
    }


    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_item, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {

        HistoryModel recyclerData = ItemArrayList.get(position);
        holder.order_name.setText(recyclerData.getOrder_name());
        holder.order_date.setText(recyclerData.getOrder_date());
        holder.order_price.setText(recyclerData.getOrder_price());
        holder.order_status.setText(recyclerData.getOrder_status());
        holder.restaurant_img_id.setImageResource(recyclerData.getRestaurant_img_id());
        holder.food_type_img_id.setImageResource(recyclerData.getFood_type_img_id());
        holder.order_item_1.setText(recyclerData.getOrder_item_1());
        holder.order_item_2.setText(recyclerData.getOrder_item_2());
        holder.order_item_3.setText(recyclerData.getOrder_item_3());
        holder.order_item_1_price.setText(recyclerData.getOrder_item_1_price());
        holder.order_item_2_price.setText(recyclerData.getOrder_item_2_price());
        holder.order_item_3_price.setText(recyclerData.getOrder_item_3_price());
        holder.total_price_details.setText(recyclerData.getTotal_price_details());

        boolean isExpanded = ItemArrayList.get(position).isExpanded();
        holder.expandable.setVisibility(isExpanded ? View.VISIBLE : View.GONE);

        if(recyclerData.getOrder_status().equals("Delivered")){

            holder.order_status.setTextColor(Color.parseColor("#FA4A0C"));

        }else if(recyclerData.getOrder_status().equals("Processing")){

            holder.order_status.setTextColor(Color.parseColor("#FDBF50"));

        }else if(recyclerData.getOrder_status().equals("Canceled")){

            holder.order_status.setTextColor(Color.parseColor("#2A2C41"));

        }

    }

    @Override
    public int getItemCount() {
        return ItemArrayList.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {

        ConstraintLayout expandable;
        private TextView order_name;
        private TextView order_date;
        private TextView order_price;
        private TextView order_status;
        private GifImageView restaurant_img_id;
        private ImageView food_type_img_id;
        private TextView order_item_1;
        private TextView order_item_2;
        private TextView order_item_3;
        private TextView order_item_1_price;
        private TextView order_item_2_price;
        private TextView order_item_3_price;
        private TextView total_price_details;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            order_name = itemView.findViewById(R.id.order_name);
            order_date = itemView.findViewById(R.id.order_date);
            order_price = itemView.findViewById(R.id.order_price_main);
            order_status = itemView.findViewById(R.id.order_status);
            order_item_1 = itemView.findViewById(R.id.order_item1);
            order_item_2 = itemView.findViewById(R.id.order_item2);
            order_item_3 = itemView.findViewById(R.id.order_item3);
            order_item_1_price = itemView.findViewById(R.id.item1_price);
            order_item_2_price = itemView.findViewById(R.id.item2_price);
            order_item_3_price = itemView.findViewById(R.id.item3_price);
            total_price_details = itemView.findViewById(R.id.total_price_details);
            restaurant_img_id = itemView.findViewById(R.id.restaurant_image);
            food_type_img_id = itemView.findViewById(R.id.food_type);

            expandable= itemView.findViewById(R.id.expandable);

            TextView more_info = itemView.findViewById(R.id.more_info);
            ImageView arrow = itemView.findViewById(R.id.arrow_down);

            more_info.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    HistoryModel historyModel = ItemArrayList.get(getAdapterPosition());
                    historyModel.setExpanded(!historyModel.isExpanded());
                    notifyItemChanged(getAdapterPosition());
                }
            });

            arrow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    HistoryModel historyModel = ItemArrayList.get(getAdapterPosition());
                    historyModel.setExpanded(!historyModel.isExpanded());
                    notifyItemChanged(getAdapterPosition());
                }
            });

        }
    }
}
