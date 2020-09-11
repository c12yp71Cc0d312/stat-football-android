package com.example.spiderproject.teaminfo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.spiderproject.R;

import java.util.ArrayList;

public class AdapterPlayer extends RecyclerView.Adapter<AdapterPlayer.AdapterPlayerViewHolder> {
    ArrayList<TeamInfoPlayerItem> players;

    public static class AdapterPlayerViewHolder extends RecyclerView.ViewHolder {

        TextView playerName, country, number;

        public AdapterPlayerViewHolder(@NonNull View itemView) {
            super(itemView);

            playerName = itemView.findViewById(R.id.textView_teaminfo_player_name);
            country = itemView.findViewById(R.id.textView_teaminfo_player_country);
            number = itemView.findViewById(R.id.textView1_teaminfo_player_number);
        }
    }

    public AdapterPlayer(ArrayList<TeamInfoPlayerItem> players) {
        this.players = players;
    }

    @NonNull
    @Override
    public AdapterPlayerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_team_info_player, parent, false);
        AdapterPlayerViewHolder viewHolder = new AdapterPlayerViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterPlayerViewHolder holder, int position) {
        TeamInfoPlayerItem playerItem = players.get(position);
        holder.number.setText(playerItem.getPlayer_number());
        holder.country.setText(playerItem.getPlayer_country());

        String[] names = playerItem.getPlayer_name().split(" ");
        String player = names[names.length-1];
        for(int i = 0; i < names.length - 1; i++) {
            player += " ";
            player += names[i];
        }
        holder.playerName.setText(player);

    }

    @Override
    public int getItemCount() {
        return players.size();
    }
}
