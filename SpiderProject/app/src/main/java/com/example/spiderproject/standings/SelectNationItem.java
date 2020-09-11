package com.example.spiderproject.standings;

import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.annotations.SerializedName;

public class SelectNationItem {
    private String country_id;
    private String country_logo;
    private String country_name;

    public SelectNationItem(String country_id, String country_name, String country_logo) {
        this.country_id = country_id;
        this.country_name = country_name;
        this.country_logo = country_logo;
    }

    public String getCountryId() {
        return country_id;
    }

    public String getCountryLogo() {
        return country_logo;
    }

    public String getCountryName() {
        return country_name;
    }
}
