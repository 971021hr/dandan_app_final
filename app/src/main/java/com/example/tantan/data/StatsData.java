package com.example.tantan.data;

import com.google.gson.annotations.SerializedName;

public class StatsData {
    @SerializedName("userEmail")
    String userEmail;

    public StatsData(String userEmail) {
            this.userEmail = userEmail;
        }
}
