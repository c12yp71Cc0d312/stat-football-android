package com.example.spiderproject.standings;

import com.google.gson.annotations.SerializedName;

public class StandingsTeam {

    private String team_id;
    private String team_name;
    private String team_badge;
    @SerializedName("overall_league_payed")
    private String played;
    @SerializedName("overall_league_position")
    private String position;
    @SerializedName("overall_league_W")
    private String wins;
    @SerializedName("overall_league_D")
    private String draws;
    @SerializedName("overall_league_L")
    private String losses;
    @SerializedName("overall_league_PTS")
    private String points;

    public String getTeam_id() {
        return team_id;
    }

    public String getTeam_name() {
        return team_name;
    }

    public String getTeam_badge() {
        return team_badge;
    }

    public String getPlayed() {
        return played;
    }

    public String getPosition() {
        return position;
    }

    public String getWins() {
        return wins;
    }

    public String getDraws() {
        return draws;
    }

    public String getLosses() {
        return losses;
    }

    public String getPoints() {
        return points;
    }

}
