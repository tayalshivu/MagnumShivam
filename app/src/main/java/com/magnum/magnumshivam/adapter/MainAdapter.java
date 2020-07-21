package com.magnum.magnumshivam.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.GenericLifecycleObserver;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.magnum.magnumshivam.R;
import com.magnum.magnumshivam.data.MainData;
import com.magnum.magnumshivam.data.MainItem;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> implements Filterable {

    private ArrayList<MainItem> dataArrayList;
    private Activity activity;
    LinearLayoutManager linearLayoutManager;

    private ArrayList<MainItem> AllData;

    public MainAdapter( Activity activity,ArrayList<MainItem> dataArrayList) {
        this.dataArrayList = dataArrayList;
        this.activity = activity;

        AllData=new ArrayList<>();
        AllData.addAll(dataArrayList);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        MainItem mainData = dataArrayList.get(position);

        if (mainData.equals(null)){
            Toast.makeText(activity, "This is Last", Toast.LENGTH_SHORT).show();
        }

        Glide.with(activity).load(mainData.getAvatarUrl())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.imageView);

        holder.textView.setText(mainData.getLogin());
    }


    @Override
    public int getItemCount() {
        return dataArrayList.size();
    }

    @Override
    public Filter getFilter() {
        return myfilter;
    }

     Filter myfilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<MainItem> filterList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0)
                filterList.addAll(AllData);
            else {
                for (MainItem user : AllData) {
                    if (user.getLogin().toLowerCase().contains(constraint.toString().toLowerCase())) {
                        filterList.add(user);
                    }
                }
            }

            FilterResults filterResults = new FilterResults();
            filterResults.values = filterList;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            dataArrayList.clear();
            dataArrayList.addAll((Collection<? extends MainItem>) results.values);
            notifyDataSetChanged();
        }
    };

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView=itemView.findViewById(R.id.image_view);
            textView=itemView.findViewById(R.id.text_view);
        }
    }
}
