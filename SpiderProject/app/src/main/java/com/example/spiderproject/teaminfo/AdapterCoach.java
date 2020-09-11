package com.example.spiderproject.teaminfo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.spiderproject.R;

import java.util.ArrayList;

public class AdapterCoach extends RecyclerView.Adapter<AdapterCoach.CoachViewHolder> {
    ArrayList<TeamInfoCoachItem> coachItems;

    public static class CoachViewHolder extends RecyclerView.ViewHolder {

        TextView coachName, country;

        public CoachViewHolder(@NonNull View itemView) {
            super(itemView);

            coachName = itemView.findViewById(R.id.textView_teaminfo_coach_name);
            country = itemView.findViewById(R.id.textView_teaminfo_coach_country);
        }
    }

    public AdapterCoach(ArrayList<TeamInfoCoachItem> coaches) {
        this.coachItems = coaches;
    }

    @NonNull
    @Override
    public CoachViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_team_info_coach, parent, false);
        CoachViewHolder viewHolder = new CoachViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CoachViewHolder holder, int position) {
        TeamInfoCoachItem coachItem = coachItems.get(position);
        holder.country.setText(coachItem.getCoach_country());

        String[] names = coachItem.getCoach_name().split(" ");
        String coach = names[names.length-1];
        for(int i = 0; i < names.length - 1; i++) {
            coach += " ";
            coach += names[i];
        }
        holder.coachName.setText(coach);

    }

    @Override
    public int getItemCount() {
        return coachItems.size();
    }
}
