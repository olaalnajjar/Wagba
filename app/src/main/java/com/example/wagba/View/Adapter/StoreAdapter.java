package com.example.wagba.View.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.wagba.R;
import com.example.wagba.Model.StoreModel;
import com.example.wagba.View.StoreDetails;

import java.util.ArrayList;
import java.util.List;

public class StoreAdapter extends RecyclerView.Adapter<StoreAdapter.RecyclerViewHolder> implements Filterable {

    private ArrayList<StoreModel> StoreArrayList;
    private ArrayList<StoreModel> getStoreArrayListFilter = new ArrayList<>();
    private Context my_context;
    public StoreAdapter(ArrayList<StoreModel> recyclerDataArrayList, Context my_context) {
        this.StoreArrayList = recyclerDataArrayList;
        this.my_context = my_context;
        this.getStoreArrayListFilter = recyclerDataArrayList;
    }
    @NonNull
    @Override
    public StoreAdapter.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.store, parent, false);

        RecyclerViewHolder view_holder=new RecyclerViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view1) {
                Intent intent = new Intent(view1.getContext(), StoreDetails.class);
                Bundle bundle = new Bundle();
                bundle.putString("name", view_holder.store_title.getText().toString());
                intent.putExtras(bundle);
                view1.getContext().startActivity(intent);
            }
        });


        return view_holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        StoreModel recyclerData = StoreArrayList.get(position);
        holder.store_title.setText(recyclerData.getTitle());
        Glide.with(my_context).load(recyclerData.getImg_id()).into(holder.store_img);
    }


    @Override
    public int getItemCount() {
        return StoreArrayList.size();
    }



    public void setStoreList(ArrayList<StoreModel> stores){
        StoreArrayList=stores;
        notifyDataSetChanged();
    }



    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                FilterResults filterResults = new FilterResults();
                if(charSequence == null || charSequence.length()==0){
                    filterResults.values= getStoreArrayListFilter;
                    filterResults.count=getStoreArrayListFilter.size();

                }else{

                    String searchStr = charSequence.toString().toLowerCase();
                    List<StoreModel> storeModels= new ArrayList<>();
                    for(StoreModel storeModel: getStoreArrayListFilter){
                        if(storeModel.getTitle().toLowerCase().contains(searchStr)){
                            storeModels.add(storeModel);
                        }
                    }

                    filterResults.values=storeModels;
                    filterResults.count=storeModels.size();
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

                StoreArrayList = (ArrayList<StoreModel>) filterResults.values;
                notifyDataSetChanged();
            }
        };
        return filter;
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
