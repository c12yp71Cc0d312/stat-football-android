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

public class SelectCompetitionAdapter extends RecyclerView.Adapter<SelectCompetitionAdapter.SelectCompetitionViewHolder> {
    private ArrayList<SelectCompetitionItem> items;
    private OnItemListener onItemListener;

    public static class SelectCompetitionViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView logo;
        TextView competition;
        OnItemListener onItemListener;

        public SelectCompetitionViewHolder(@NonNull View itemView, OnItemListener onItemListener) {
            super(itemView);
            this.onItemListener = onItemListener;

            logo = itemView.findViewById(R.id.imageView_competition);
            competition = itemView.findViewById(R.id.textView_competition);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onItemListener.onItemClick(getAdapterPosition());
        }
    }

    public SelectCompetitionAdapter(ArrayList<SelectCompetitionItem> items, OnItemListener onItemListener) {
        this.items = items;
        this.onItemListener = onItemListener;
    }

    @NonNull
    @Override
    public SelectCompetitionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cards_select_competition, parent, false);
        SelectCompetitionViewHolder viewHolder = new SelectCompetitionViewHolder(v, onItemListener);
        return  viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SelectCompetitionViewHolder holder, int position) {
        SelectCompetitionItem currentItem = items.get(position);
        holder.competition.setText(currentItem.getLeague_name());
        Picasso.get()
                .load(currentItem.getLeague_logo())
                .placeholder(R.drawable.placeholder)
                .into(holder.logo);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public interface OnItemListener {
        void onItemClick(int pos);
    }

}
