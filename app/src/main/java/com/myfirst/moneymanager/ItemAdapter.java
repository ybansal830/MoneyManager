package com.myfirst.moneymanager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemViewHolder> {

    private List<ItemList> itemLists = new ArrayList<>();
    private OnItemClickListener onItemClickListener;

    public ItemAdapter(List<ItemList> itemLists,OnItemClickListener onItemClickListener) {
        this.itemLists = itemLists;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_itemview,parent,false);
        return new ItemViewHolder(view,onItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        holder.setData(itemLists.get(position));
    }

    @Override
    public int getItemCount() {
        return itemLists.size();
    }
}
