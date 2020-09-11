package com.example.spiderproject;

import com.example.spiderproject.fixtures.FixturesMatchInfo;
import com.example.spiderproject.standings.SelectCompetitionItem;
import com.example.spiderproject.standings.SelectNationItem;
import com.example.spiderproject.standings.StandingsTeam;
import com.example.spiderproject.teaminfo.TeamInfo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiFootball_Api {

    @GET("/")
    Call<List<SelectNationItem>> getCountries(@Query("action") String action,
                                              @Query("APIkey") String APIkey);

    @GET("/")
    Call<List<SelectCompetitionItem>> getCompetitions(@Query("action") String action,
                                                      @Query("country_id") String country_id,
                                                      @Query("APIkey") String APIkey);

    @GET("/")
    Call<List<StandingsTeam>> getStandingsTeams(@Query("action") String action,
                                                @Query("league_id") String league_id,
                                                @Query("APIkey") String APIkey);

    @GET("/")
    Call<List<TeamInfo>> getTeamInfo(@Query("action") String action,
                                     @Query("team_id") String team_id,
                                     @Query("APIkey") String APIkey);

    @GET("/")
    Call<List<FixturesMatchInfo>> getMatchInfos(@Query("action") String action,
                                                @Query("from") String from,
                                                @Query("to") String to,
                                                @Query("league_id") String league_id,
                                                @Query("APIkey") String APIkey,
                                                @Query("timezone") String timezone);

    @GET("/")
    Call<List<FixturesMatchInfo>> getMatchInfosByTeam(@Query("action") String action,
                                                @Query("from") String from,
                                                @Query("to") String to,
                                                @Query("team_id") String team_id,
                                                @Query("APIkey") String APIkey,
                                                @Query("timezone") String timezone);

}
