package com.example.spiderproject.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.spiderproject.R;

import java.util.ArrayList;

public class HomeMenuAdapter extends RecyclerView.Adapter<HomeMenuAdapter.MenuViewHolder> {
    private ArrayList<HomeMenuItem> items;
    private OnItemListener onItemListener;

    public static class MenuViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView text;
        OnItemListener onItemListener;

        public MenuViewHolder(@NonNull View itemView, OnItemListener onItemListener) {
            super(itemView);
            this.onItemListener = onItemListener;

            text = itemView.findViewById(R.id.text_view_menu);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onItemListener.onItemClick(getAdapterPosition());
        }
    }

    public HomeMenuAdapter(ArrayList<HomeMenuItem> items, OnItemListener onItemListener) {
        this.items = items;
        this.onItemListener = onItemListener;
    }

    @NonNull
    @Override
    public MenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_menu_cards, parent, false);
        MenuViewHolder viewHolder = new MenuViewHolder(v, onItemListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MenuViewHolder holder, int position) {
        HomeMenuItem currentItem = items.get(position);
        holder.text.setText(currentItem.getTitle());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public interface OnItemListener {
        void onItemClick(int pos);
    }

}
