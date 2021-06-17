package com.example.tantan.data;

import com.google.gson.annotations.SerializedName;

public class StatsRunMonthData {
    @SerializedName("userEmail")
    String userEmail;

    @SerializedName("dateStart")
    String dateStart;

    @SerializedName("dateEnd")
    String dateEnd;

    public StatsRunMonthData(String userEmail, String dateStart, String dateEnd) {
        this.userEmail = userEmail;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
    }
}
