package com.example.wagba.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.wagba.Model.OffersModel;
import com.example.wagba.R;

import java.util.ArrayList;

public class OffersAdapter extends RecyclerView.Adapter<OffersAdapter.RecyclerViewHolder>{

    private ArrayList<OffersModel> OfferArrayList;
    private Context my_context;
    public OffersAdapter(ArrayList<OffersModel> recyclerDataArrayList, Context my_context) {
        this.OfferArrayList = recyclerDataArrayList;
        this.my_context = my_context;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.offer, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {

        OffersModel recyclerData = OfferArrayList.get(position);
        holder.img.setImageResource(recyclerData.getImg_id());
    }

    @Override
    public int getItemCount() {
        return OfferArrayList.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {


        private ImageView img;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.offer_image);
        }
    }
}
