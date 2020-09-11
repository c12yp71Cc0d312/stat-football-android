package com.example.spiderproject.fixtures;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.spiderproject.ApiFootball_Api;
import com.example.spiderproject.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FixturesActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    private static final String TAG = "FixturesActivity";

    private String leagueId;
    ApiFootball_Api fixturesApi;
    public static final String API_KEY = "6b55734152fb765975f842ba5a76a2dc62f86742eaf6fc8e22dc1494f315ca8c";

    TextView startDate;
    TextView endDate;

    RecyclerView recyclerView;
    FixturesAdapter adapter;
    RecyclerView.LayoutManager layoutManager;

    private ArrayList<FixturesMatchInfo> matchInfos;

    Calendar calendar;
    int startYear, startMonth, startDay;
    int endYear, endMonth, endDay;

    private static boolean startDateChosen = false;
    private static boolean endDateChosen = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fixtures);
        getSupportActionBar().setTitle("Fixtures and Results");

        leagueId = getIntent().getStringExtra("leagueId");

        startDate = findViewById(R.id.textView_fixtures_start);
        endDate = findViewById(R.id.textView_fixture_end);

        recyclerView = findViewById(R.id.recycler_view_fixtures);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://apiv2.apifootball.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        setDates();

        startDate.setOnClickListener(v -> {
            showDatePickerDialog(startYear, startMonth, startDay);
            startDateChosen = true;
            endDateChosen = false;
        });

        endDate.setOnClickListener(v -> {
            showDatePickerDialog(endYear, endMonth, endDay);
            endDateChosen = true;
            startDateChosen = false;
        });

        fixturesApi = retrofit.create(ApiFootball_Api.class);

        getFixtures();

    }

    public void setDates() {

        calendar = Calendar.getInstance();
        int currentYear = calendar.get(Calendar.YEAR);
        int currentMonth = calendar.get(Calendar.MONTH);
        int currentDay = calendar.get(Calendar.DAY_OF_MONTH);

        endYear = currentYear;
        if(currentMonth == 11) {
            endMonth = 0;
            endYear = currentYear + 1;
        }
        else {
            endMonth = currentMonth + 1;
        }
        if(currentDay == 31 && currentMonth == 0) {
            endDay = 28;
        }
        else if(currentDay == 31) {
            endDay = 30;
        }
        else {
            endDay = currentDay;
        }

        startYear = currentYear;
        if(endMonth == 0) {
            startMonth = 11;
            startYear = currentYear - 1;
        }
        else {
            startMonth = currentMonth - 1;
        }
        if(currentDay == 31 && currentMonth == 1) {
            startDay = 28;
        }
        else if(currentDay == 31) {
            startDay = 30;
        }
        else {
            startDay = currentDay;
        }

        startDate.setText(startDay + "/" + (startMonth + 1) + "/" + startYear);
        endDate.setText(endDay + "/" + (endMonth + 1) + "/" + endYear);

    }

    public void showDatePickerDialog(int year, int month, int day) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(FixturesActivity.this, this, year, month, day);
        datePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        if(startDateChosen) {
            startYear = year;
            startMonth = month;
            startDay = dayOfMonth;

            startDate.setText(startDay + "/" + (startMonth + 1) + "/" + startYear);

            startDateChosen = false;
        }
        else if(endDateChosen) {
            endYear = year;
            endMonth = month;
            endDay = dayOfMonth;

            endDate.setText(endDay + "/" + (endMonth + 1) + "/" + endYear);

            endDateChosen = false;
        }

        getFixtures();
    }

    public void getFixtures() {
        String fromDate = startYear + "-" + (startMonth + 1) + "-" + startDay;
        String toDate = endYear + "-" + (endMonth + 1) + "-" + endDay;
        Call<List<FixturesMatchInfo>> call = fixturesApi.getMatchInfos("get_events", fromDate, toDate, leagueId, API_KEY, "Asia/Kolkata");
        call.enqueue(new Callback<List<FixturesMatchInfo>>() {
            @Override
            public void onResponse(Call<List<FixturesMatchInfo>> call, Response<List<FixturesMatchInfo>> response) {
                if(!response.isSuccessful()) {
                    Log.d(TAG, "onResponse: error code: " + response.code());
                    return;
                }

                matchInfos = new ArrayList<>();

                List<FixturesMatchInfo> matches = response.body();
                for(FixturesMatchInfo match : matches) {
                    matchInfos.add(match);
                }

                setRecyclerView();

            }

            @Override
            public void onFailure(Call<List<FixturesMatchInfo>> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

    public void setRecyclerView() {
        layoutManager = new LinearLayoutManager(this);
        adapter = new FixturesAdapter(matchInfos);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);

        int scrollTo = 0;
        for(int i = matchInfos.size() - 1; i >=0; i-=2) {
            if(matchInfos.get(i).getMatch_status().length() != 0) {
                scrollTo = i;
                break;
            }
        }
        recyclerView.smoothScrollToPosition(scrollTo);
    }

}
