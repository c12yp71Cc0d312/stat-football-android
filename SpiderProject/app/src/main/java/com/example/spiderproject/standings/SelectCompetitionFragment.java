package com.example.spiderproject.standings;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
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

public class SelectCompetitionFragment extends Fragment implements SelectCompetitionAdapter.OnItemListener {

    private static final String TAG = "SelectCompetitionFragme";

    private RecyclerView recyclerView;
    private SelectCompetitionAdapter adapter;
    private GridLayoutManager layoutManager;

    private SelectCompetitionFragmentListener listener;

    private ArrayList<SelectCompetitionItem> items;
    public static final String API_KEY = "6b55734152fb765975f842ba5a76a2dc62f86742eaf6fc8e22dc1494f315ca8c";
    private static String countryId;

    private ApiFootball_Api competitionsApi;

    public interface SelectCompetitionFragmentListener {
        void onInputLeagueSent(String leagueId);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_select_competition, container, false);

        recyclerView = v.findViewById(R.id.recycler_view_select_competition);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://apiv2.apifootball.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        competitionsApi = retrofit.create(ApiFootball_Api.class);

        getCompetitions();

        return v;
    }

    public void getCompetitions() {
        Log.d(TAG, "getCompetitions: country id: " + countryId);
        Call<List<SelectCompetitionItem>> call = competitionsApi.getCompetitions("get_leagues", countryId, API_KEY);
        call.enqueue(new Callback<List<SelectCompetitionItem>>() {
            @Override
            public void onResponse(Call<List<SelectCompetitionItem>> call, Response<List<SelectCompetitionItem>> response) {
                if(!response.isSuccessful()) {
                    Log.d(TAG, "onResponse: Error code: " + response.code());
                    return;
                }

                items = new ArrayList<>();

                List<SelectCompetitionItem> returnedItems = response.body();
                for(SelectCompetitionItem item : returnedItems) {
                    items.add(item);
                }

                setRecyclerView();

            }

            @Override
            public void onFailure(Call<List<SelectCompetitionItem>> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

    public void setRecyclerView() {
        layoutManager = new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false);
        adapter = new SelectCompetitionAdapter(items, this);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

    }

    public void getCountryId(String countryId) {
        this.countryId = countryId;
        Log.d(TAG, "getCountryId: " + this.countryId);
    }

    @Override
    public void onItemClick(int pos) {
        String leagueId = items.get(pos).getLeague_id();
        listener.onInputLeagueSent(leagueId);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof SelectCompetitionFragmentListener) {
            listener = (SelectCompetitionFragmentListener) context;
        }
        else {
            throw new RuntimeException(context.toString() + " should implement SelectCompetitionFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }
}
