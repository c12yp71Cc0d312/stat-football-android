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

public class StandingsTableAdapter extends RecyclerView.Adapter<StandingsTableAdapter.StandingsTableViewHolder> {
    private ArrayList<StandingsTeam> teams;
    public OnItemListener onItemListener;

    public static class StandingsTableViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView logo;
        TextView pos, team, played, won, draw, lost, points;
        OnItemListener onItemListener;

        public StandingsTableViewHolder(@NonNull View itemView, OnItemListener onItemListener) {
            super(itemView);

            this.onItemListener = onItemListener;

            logo = itemView.findViewById(R.id.imageView_standings_team_logo);
            pos = itemView.findViewById(R.id.textView_position);
            team = itemView.findViewById(R.id.textView_standings_team_name);
            played = itemView.findViewById(R.id.textView_standings_played);
            won = itemView.findViewById(R.id.textView_standings_won);
            draw = itemView.findViewById(R.id.textView_standings_draw);
            lost = itemView.findViewById(R.id.textView_standings_lost);
            points = itemView.findViewById(R.id.textView_standings_pts);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onItemListener.OnItemClick(getAdapterPosition());
        }

    }

    public StandingsTableAdapter(ArrayList<StandingsTeam> teams, OnItemListener onItemListener) {
        this.teams = teams;
        this.onItemListener = onItemListener;
    }

    @NonNull
    @Override
    public StandingsTableViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cards_standings, parent, false);
        StandingsTableViewHolder viewHolder = new StandingsTableViewHolder(v, onItemListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull StandingsTableViewHolder holder, int position) {
        StandingsTeam currentTeam = teams.get(position);
        holder.pos.setText(currentTeam.getPosition());
        holder.team.setText(currentTeam.getTeam_name());
        holder.played.setText(currentTeam.getPlayed());
        holder.won.setText(currentTeam.getWins());
        holder.draw.setText(currentTeam.getDraws());
        holder.lost.setText(currentTeam.getLosses());
        holder.points.setText(currentTeam.getPoints());

        Picasso.get()
                .load(currentTeam.getTeam_badge())
                .placeholder(R.drawable.placeholder)
                .into(holder.logo);
    }

    @Override
    public int getItemCount() {
        return teams.size();
    }

    public interface OnItemListener {
        void OnItemClick(int pos);
    }

}
