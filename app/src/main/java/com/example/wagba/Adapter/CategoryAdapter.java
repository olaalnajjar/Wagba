package com.example.wagba.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wagba.Model.CategoriesModel;
import com.example.wagba.Model.StoreModel;
import com.example.wagba.R;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.RecyclerViewHolder>{

    private ArrayList<CategoriesModel> CategoryArrayList;
    private Context my_context;
    public CategoryAdapter(ArrayList<CategoriesModel> recyclerDataArrayList, Context my_context) {
        this.CategoryArrayList = recyclerDataArrayList;
        this.my_context = my_context;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {

        CategoriesModel recyclerData = CategoryArrayList.get(position);
        holder.title.setText(recyclerData.getTitle());
        holder.img.setImageResource(recyclerData.getImg_id());
    }

    @Override
    public int getItemCount() {
        return CategoryArrayList.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {

        private TextView title;
        private ImageView img;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.category_text);
            img = itemView.findViewById(R.id.category_icon);
        }
    }
}
