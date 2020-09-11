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

public class SelectNationFragment extends Fragment implements SelectNationsAdapter.OnItemListener {

    private static final String TAG = "SelectNationFragment";

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private SelectNationsAdapter adapter;
    private SelectNationFragmentListener listener;

    private ArrayList<SelectNationItem> items;
    private ApiFootball_Api countiesApi;
    public static final String API_KEY = "6b55734152fb765975f842ba5a76a2dc62f86742eaf6fc8e22dc1494f315ca8c";

    public interface SelectNationFragmentListener {
        void onInputNationSent(String countryID);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)  {
        View v = inflater.inflate(R.layout.fragment_select_nation, container, false);

        recyclerView = v.findViewById(R.id.recycler_view_select_nation);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://apiv2.apifootball.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        countiesApi = retrofit.create(ApiFootball_Api.class);
        getNations();

        return v;
    }

    public void getNations() {
        Call<List<SelectNationItem>> call = countiesApi.getCountries("get_countries", API_KEY);
        call.enqueue(new Callback<List<SelectNationItem>>() {
            @Override
            public void onResponse(Call<List<SelectNationItem>> call, Response<List<SelectNationItem>> response) {
                if(!response.isSuccessful()) {
                    Log.d(TAG, "onResponse: ERROR CODE: " + response.code());
                    return;
                }

                items = new ArrayList<>();
                items.add(new SelectNationItem("", "All", "R.drawable.world"));

                List<SelectNationItem> obtainedItems = response.body();
                if(!obtainedItems.isEmpty()) {
                    for (SelectNationItem item : obtainedItems) {
                        items.add(item);
                    }
                    Log.d(TAG, "onResponse: items: " + items);
                    setRecyclerView();
                }
                else {
                    Log.d(TAG, "onResponse: empty items");
                }

            }

            @Override
            public void onFailure(Call<List<SelectNationItem>> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

    public void setRecyclerView() {
        adapter = new SelectNationsAdapter(items, this);
        layoutManager = new LinearLayoutManager(getContext());

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void onItemClick(int pos) {
        String countryId = items.get(pos).getCountryId();
        listener.onInputNationSent(countryId);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof SelectNationFragmentListener) {
            listener = (SelectNationFragmentListener) context;
        }
        else {
            throw new RuntimeException(context.toString() + " must implement SelectNationFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

}
