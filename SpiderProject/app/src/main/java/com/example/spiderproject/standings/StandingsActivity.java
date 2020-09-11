package com.example.spiderproject.standings;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.spiderproject.R;

public class StandingsActivity extends AppCompatActivity implements SelectNationFragment.SelectNationFragmentListener, SelectCompetitionFragment.SelectCompetitionFragmentListener {
    private static final String TAG = "StandingsActivity";

    private SelectNationFragment selectNation;
    private SelectCompetitionFragment selectCompetition;
    private StandingsTableFragment standingsTable;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_standings);
        getSupportActionBar().setTitle("Standings");

        selectNation = new SelectNationFragment();
        selectCompetition = new SelectCompetitionFragment();
        standingsTable = new StandingsTableFragment();

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.standings_frame_layout, selectNation, "nation select")
                .commit();
    }

    @Override
    public void onInputNationSent(String countryID) {
        selectCompetition.getCountryId(countryID);
        getSupportFragmentManager().beginTransaction()
                .addToBackStack("competition select")
                .replace(R.id.standings_frame_layout, selectCompetition, "competition select")
                .commit();
        Log.d(TAG, "onInputNationSent: fragment changed");
    }

    @Override
    public void onInputLeagueSent(String leagueId) {
        standingsTable.getLeagueId(leagueId);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.standings_frame_layout, standingsTable, "standings")
                .addToBackStack("standings")
                .commit();
    }
}
