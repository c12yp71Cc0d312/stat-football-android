package com.example.spiderproject.teaminfo;

import java.util.List;

public class TeamInfo {

    private String team_key;
    private String team_name;
    private String team_badge;

    private List<TeamInfoPlayerItem> players;
    private List<TeamInfoCoachItem> coaches;

    public String getTeam_key() {
        return team_key;
    }

    public String getTeam_name() {
        return team_name;
    }

    public String getTeam_badge() {
        return team_badge;
    }

    public List<TeamInfoPlayerItem> getPlayers() {
        return players;
    }

    public List<TeamInfoCoachItem> getCoaches() {
        return coaches;
    }
}
