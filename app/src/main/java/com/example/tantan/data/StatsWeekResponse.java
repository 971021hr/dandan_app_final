package com.example.tantan.data;

import com.google.gson.annotations.SerializedName;

public class StatsWeekResponse {
    @SerializedName("code")
    private int code;

    @SerializedName("message")
    private String message;

    @SerializedName("arrLen")
    private int arrLen;

    @SerializedName("bodyDate")
    private String[] bodyDate;

    @SerializedName("bodyWeight")
    private Float[] bodyWeight;

    @SerializedName("bodyMuscle")
    private Float[] bodyMuscle;

    @SerializedName("bodyFat")
    private Float[] bodyFat;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public int getArrLen() {
        return arrLen;
    }

    public String[] getBodyDate() {
        return bodyDate;
    }

    public Float[] getBodyWeight() {
        return bodyWeight;
    }

    public Float[] getBodyFat() {
        return bodyFat;
    }

    public Float[] getBodyMuscle() {
        return bodyMuscle;
    }
}