package com.example.spiderproject.standings;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.spiderproject.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SelectNationsAdapter extends RecyclerView.Adapter<SelectNationsAdapter.NationsViewHolder> {
    private ArrayList<SelectNationItem> items;
    public OnItemListener onItemListener;

    public static class NationsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView flag;
        TextView country;
        OnItemListener onItemListener;

        public NationsViewHolder(@NonNull View itemView, OnItemListener onItemListener) {
            super(itemView);
            this.onItemListener = onItemListener;

            flag = itemView.findViewById(R.id.imageView_flag);
            country = itemView.findViewById(R.id.country);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onItemListener.onItemClick(getAdapterPosition());
        }
    }

    public SelectNationsAdapter(ArrayList<SelectNationItem> items, OnItemListener listener) {
        this.items = items;
        this.onItemListener = listener;
    }

    @NonNull
    @Override
    public NationsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cards_select_nation, parent, false);
        NationsViewHolder viewHolder = new NationsViewHolder(v, onItemListener);
        return viewHolder;
    }

    private boolean world = true;
    @Override
    public void onBindViewHolder(@NonNull NationsViewHolder holder, int position) {
        SelectNationItem currentItem = items.get(position);
        holder.country.setText(currentItem.getCountryName());

        if(world) {
            Picasso.get()
                    .load(R.drawable.world)
                    .into(holder.flag);
            world = false;
        }
        else {
            Picasso.get()
                    .load(currentItem.getCountryLogo())
                    .placeholder(R.drawable.placeholder)
                    .into(holder.flag);
        }

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public interface OnItemListener {
        void onItemClick(int pos);
    }
}
