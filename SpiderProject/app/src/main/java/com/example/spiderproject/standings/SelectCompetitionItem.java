package com.example.spiderproject.standings;

public class SelectCompetitionItem {

    private String country_id;
    private String country_name;
    private String league_id;
    private String league_name;
    private String league_logo;

    public SelectCompetitionItem(String country_id, String country_name, String league_id, String league_name, String league_logo) {
        this.country_id = country_id;
        this.country_name = country_name;
        this.league_id = league_id;
        this.league_name = league_name;
        this.league_logo = league_logo;
    }

    public String getCountry_id() {
        return country_id;
    }

    public String getCountry_name() {
        return country_name;
    }

    public String getLeague_id() {
        return league_id;
    }

    public String getLeague_name() {
        return league_name;
    }

    public String getLeague_logo() {
        return league_logo;
    }
}
