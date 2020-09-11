package com.example.spiderproject.teaminfo;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.spiderproject.ApiFootball_Api;
import com.example.spiderproject.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TeamInfoSquadTabFragment extends Fragment {
    private static final String TAG = "TeamInfoSquadTabFragmen";

    private RecyclerView recyclerView_gk, recyclerView_def, recyclerView_mid, recyclerView_fw, recyclerView_coach;
    private AdapterPlayer adapterGk, adapterDef, adapterMid, adapterFw;
    private AdapterCoach adapterCoach;

    private ArrayList<TeamInfoPlayerItem> goalkeepers, defenders, midfielders, forwards;
    private ArrayList<TeamInfoCoachItem> coaches;

    ApiFootball_Api playersApi;
    public static final String API_KEY = "6b55734152fb765975f842ba5a76a2dc62f86742eaf6fc8e22dc1494f315ca8c";

    private String team_id;
    private TeamInfoActivity teamInfoActivity;

    TeamInfoSquadFragmentListener listener;

    public interface TeamInfoSquadFragmentListener {
        void onInputLogoAndTeamNameSent(String logo, String teamName);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_team_players, container, false);

        recyclerView_coach = v.findViewById(R.id.recycler_view_teaminfo_coaches);
        recyclerView_def = v.findViewById(R.id.recyclerView_teaminfo_defenders);
        recyclerView_fw = v.findViewById(R.id.recyclerView_teaminfo_forwards);
        recyclerView_mid = v.findViewById(R.id.recyclerView_teaminfo_midfielders);
        recyclerView_gk = v.findViewById(R.id.recyclerView_teaminfo_goalkeepers);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://apiv2.apifootball.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        playersApi = retrofit.create(ApiFootball_Api.class);

        teamInfoActivity = new TeamInfoActivity();
        team_id = teamInfoActivity.getTeamId();

        getPlayers();

        return v;
    }

    public void getPlayers() {
        Call<List<TeamInfo>> call = playersApi.getTeamInfo("get_teams", team_id, API_KEY);
        call.enqueue(new Callback<List<TeamInfo>>() {
            @Override
            public void onResponse(Call<List<TeamInfo>> call, Response<List<TeamInfo>> response) {
                if(!response.isSuccessful()) {
                    Log.d(TAG, "onResponse: Error code: " + response.code());
                    return;
                }


                goalkeepers = new ArrayList<>();
                defenders = new ArrayList<>();
                midfielders = new ArrayList<>();
                forwards = new ArrayList<>();
                coaches = new ArrayList<>();

                List<TeamInfo> teamInfoList = response.body();
                for(TeamInfo teamInfo : teamInfoList) {
                    List<TeamInfoPlayerItem> players = teamInfo.getPlayers();
                    for(TeamInfoPlayerItem player : players) {
                        switch (player.getPlayer_type()) {
                            case "Goalkeepers":
                                goalkeepers.add(player);
                                break;
                            case "Defenders":
                                defenders.add(player);
                                break;
                            case "Midfielders":
                                midfielders.add(player);
                                break;
                            case "Forwards":
                                forwards.add(player);
                                break;
                        }
                    }

                    List<TeamInfoCoachItem> coachItems = teamInfo.getCoaches();
                    for(TeamInfoCoachItem coach : coachItems) {
                        coaches.add(coach);
                    }

                    listener.onInputLogoAndTeamNameSent(teamInfo.getTeam_badge(), teamInfo.getTeam_name());

                }

                setRecyclerViews();

            }

            @Override
            public void onFailure(Call<List<TeamInfo>> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });

    }

    public void setRecyclerViews() {
        adapterGk = new AdapterPlayer(goalkeepers);
        adapterDef = new AdapterPlayer(defenders);
        adapterMid = new AdapterPlayer(midfielders);
        adapterFw = new AdapterPlayer(forwards);

        recyclerView_gk.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView_mid.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView_def.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView_fw.setLayoutManager(new LinearLayoutManager(getContext()));

        recyclerView_gk.setAdapter(adapterGk);
        recyclerView_mid.setAdapter(adapterMid);
        recyclerView_def.setAdapter(adapterDef);
        recyclerView_fw.setAdapter(adapterFw);

        adapterCoach = new AdapterCoach(coaches);
        recyclerView_coach.setLayoutManager(new LinearLayoutManager(getContext()));

        recyclerView_coach.setAdapter(adapterCoach);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof TeamInfoSquadFragmentListener) {
            listener = (TeamInfoSquadFragmentListener) context;
        }
        else {
            throw new RuntimeException(context.toString() + " should implement TeanInfoSquadFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }
}
