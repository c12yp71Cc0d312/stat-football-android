package com.example.spiderproject.fixtures;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.spiderproject.R;
import com.example.spiderproject.standings.SelectCompetitionFragment;
import com.example.spiderproject.standings.SelectNationFragment;

public class FixturesActivitySelection extends AppCompatActivity implements SelectNationFragment.SelectNationFragmentListener, SelectCompetitionFragment.SelectCompetitionFragmentListener {
    private static final String TAG = "FixturesActivity";

    SelectNationFragment selectNationFragment;
    SelectCompetitionFragment selectCompetitionFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_standings);

        selectNationFragment = new SelectNationFragment();
        selectCompetitionFragment = new SelectCompetitionFragment();

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.standings_frame_layout, selectNationFragment, "nation select")
                .commit();

    }

    @Override
    public void onInputNationSent(String countryID) {
        selectCompetitionFragment.getCountryId(countryID);
        getSupportFragmentManager().beginTransaction()
                .addToBackStack("competition select")
                .replace(R.id.standings_frame_layout, selectCompetitionFragment, "competition select")
                .commit();
        Log.d(TAG, "onInputNationSent: fragment changed");
    }

    @Override
    public void onInputLeagueSent(String leagueId) {
        Intent toFixturesActivity = new Intent(FixturesActivitySelection.this, FixturesActivity.class);
        toFixturesActivity.putExtra("leagueId", leagueId);
        startActivity(toFixturesActivity);
    }

}
