package com.example.tantan.data;

import com.google.gson.annotations.SerializedName;

public class WaterData {

    @SerializedName("userEmail")
    String userEmail;

    @SerializedName("waterDate")
    String waterDate;

    @SerializedName("waterMl")
    Integer waterMl;

    public WaterData(String userEmail, String waterDate, Integer waterMl) {
        this.userEmail = userEmail;
        this.waterDate = waterDate;
        this.waterMl = waterMl;
    }

}