package com.example.spiderproject.fixtures;

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

public class FixturesAdapter extends RecyclerView.Adapter<FixturesAdapter.FixturesViewHolder> {
    private ArrayList<FixturesMatchInfo> matchInfos;

    public static class FixturesViewHolder extends RecyclerView.ViewHolder {

        ImageView homeLogo, awayLogo;
        TextView homeTeam, awayTeam, score;
        TextView matchStatus, matchDate, matchDay;

        public FixturesViewHolder(@NonNull View itemView) {
            super(itemView);

            homeLogo = itemView.findViewById(R.id.imageView_fixture_home_logo);
            awayLogo = itemView.findViewById(R.id.imageView_fixture_away_logo);
            homeTeam = itemView.findViewById(R.id.textView_fixture_home_name);
            awayTeam = itemView.findViewById(R.id.textView_fixture_away_name);
            score = itemView.findViewById(R.id.textView_fixtures_score);
            matchStatus = itemView.findViewById(R.id.textView_fixture_status);
            matchDate = itemView.findViewById(R.id.textView_fixture_date);
            matchDay = itemView.findViewById(R.id.textView_fixture_matchday);

        }
    }

    public FixturesAdapter(ArrayList<FixturesMatchInfo> matchInfos) {
        this.matchInfos = matchInfos;
    }

    @NonNull
    @Override
    public FixturesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_fixtures, parent, false);
        FixturesViewHolder viewHolder = new FixturesViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FixturesViewHolder holder, int position) {
        FixturesMatchInfo matchInfo = matchInfos.get(position);
        holder.homeTeam.setText(matchInfo.getMatch_hometeam_name());
        holder.awayTeam.setText(matchInfo.getMatch_awayteam_name());
        holder.matchStatus.setText(matchInfo.getMatch_status());
        holder.matchDay.setText(matchInfo.getMatch_round());
        holder.score.setText(matchInfo.getMatch_hometeam_score() + " : " + matchInfo.getMatch_awayteam_score());
        holder.matchDate.setText(matchInfo.getMatch_date() + " | " + matchInfo.getMatch_time());

        Picasso.get()
                .load(matchInfo.getTeam_home_badge())
                .placeholder(R.drawable.placeholder)
                .into(holder.homeLogo);
        Picasso.get()
                .load(matchInfo.getTeam_away_badge())
                .placeholder(R.drawable.placeholder)
                .into(holder.awayLogo);

    }

    @Override
    public int getItemCount() {
        return matchInfos.size();
    }
}
