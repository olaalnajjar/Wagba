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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

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
/*


    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {


                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("Store");

                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot dataSnapshot : snapshot.getChildren()){

                            StoreModel store =dataSnapshot.getValue(StoreModel.class);
                            //store_recyclerDataArrayList.add(store);
                        }

                        // storeAdapter.notifyDataSetChanged();

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                FilterResults filterResults = new FilterResults();
                if(charSequence == null || charSequence.length()==0){
                    filterResults.values= getCategoryArrayListFilter;
                    filterResults.count=getCategoryArrayListFilter.size();

                }else{

                    String searchStr = charSequence.toString().toLowerCase();
                    List<CategoriesModel> categoriesModels= new ArrayList<>();
                    for(CategoriesModel categoriesModel: getCategoryArrayListFilter){
                        if(categoriesModel.getTitle().toLowerCase().contains(searchStr)){
                            categoriesModels.add(categoriesModel);
                        }
                    }

                    filterResults.values=categoriesModels;
                    filterResults.count=categoriesModels.size();
                }
                return filterResults;
          }
*/
