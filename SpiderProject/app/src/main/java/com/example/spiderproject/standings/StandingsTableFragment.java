package com.example.spiderproject.standings;

import android.content.Intent;
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
import com.example.spiderproject.teaminfo.TeamInfoActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class StandingsTableFragment extends Fragment implements StandingsTableAdapter.OnItemListener {
    private static final String TAG = "StandingsTableFragment";

    private RecyclerView recyclerView;
    private StandingsTableAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    private static String leagueId;
    private ArrayList<StandingsTeam> teams;
    private ApiFootball_Api standingsApi;
    public static final String API_KEY = "6b55734152fb765975f842ba5a76a2dc62f86742eaf6fc8e22dc1494f315ca8c";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_standings, container, false);

        recyclerView = v.findViewById(R.id.recycler_view_standings);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://apiv2.apifootball.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        standingsApi = retrofit.create(ApiFootball_Api.class);

        getStandings();

        return v;
    }

    public void getStandings() {
        Call<List<StandingsTeam>> call = standingsApi.getStandingsTeams("get_standings", leagueId, API_KEY);
        call.enqueue(new Callback<List<StandingsTeam>>() {
            @Override
            public void onResponse(Call<List<StandingsTeam>> call, Response<List<StandingsTeam>> response) {
                if(!response.isSuccessful()) {
                    Log.d(TAG, "onResponse: Error code: " + response.code());
                    return;
                }

                teams = new ArrayList<>();

                List<StandingsTeam> returnedTeams = response.body();
                for(StandingsTeam team : returnedTeams) {
                    teams.add(team);
                }

                setUpRecyclerView();

            }

            @Override
            public void onFailure(Call<List<StandingsTeam>> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

    public void setUpRecyclerView() {
        adapter = new StandingsTableAdapter(teams, this);
        layoutManager = new LinearLayoutManager(getContext());

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    public void getLeagueId(String leagueId) {
        this.leagueId = leagueId;
    }

    @Override
    public void OnItemClick(int pos) {
        String teamId = teams.get(pos).getTeam_id();
        Intent toTeamInfo = new Intent(getActivity(), TeamInfoActivity.class);
        toTeamInfo.putExtra("teamId", teamId);
        startActivity(toTeamInfo);
    }

}
