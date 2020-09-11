package com.example.spiderproject.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.spiderproject.fixtures.FixturesActivitySelection;
import com.example.spiderproject.R;
import com.example.spiderproject.standings.StandingsActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements HomeMenuAdapter.OnItemListener {

    private ArrayList<HomeMenuItem> items = new ArrayList<>();
    private RecyclerView recyclerView;
    private HomeMenuAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler_view_home);

        items.add(new HomeMenuItem("Standings"));
        items.add(new HomeMenuItem("Fixtures and Results"));

        buildRecyclerView();

    }

    public void buildRecyclerView() {
        adapter = new HomeMenuAdapter(items, this);
        recyclerView.setHasFixedSize(true);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onItemClick(int pos) {
        HomeMenuItem item = items.get(pos);
        switch(item.getTitle()) {
            case "Standings":
                Intent toStandingsActivity = new Intent(MainActivity.this, StandingsActivity.class);
                startActivity(toStandingsActivity);
                break;
            case "Fixtures and Results":
                Intent toFixturesActivitySelection = new Intent(MainActivity.this, FixturesActivitySelection.class);
                startActivity(toFixturesActivitySelection);
                break;
        }

    }



}
