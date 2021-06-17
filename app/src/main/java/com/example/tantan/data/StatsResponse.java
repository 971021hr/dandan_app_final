package com.example.tantan.data;

import com.google.gson.annotations.SerializedName;

public class StatsResponse {
    @SerializedName("code")
    private int code;

    @SerializedName("message")
    private String message;

    @SerializedName("userId")
    private int userId;

    @SerializedName("result")
    private int result;

    @SerializedName("bodydate")
    private String[] bodydate;

    @SerializedName("bodyweight")
    private Float[] bodyweight;

    @SerializedName("bodymuscle")
    private Float[] bodymuscle;

    @SerializedName("bodyfat")
    private Float[] bodyfat;

    @SerializedName("runTime")
    private int[] runTime;

    @SerializedName("runDate")
    private String[] runDate;

    public String[] getRunDate() {
        return runDate;
    }

    public int[] getRunTime() {
        return runTime;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public int getUserId() {
        return userId;
    }

    public int getResult() { return result; };

    public String[] getBodydate() {
        return bodydate;
    }

    public Float[] getBodyweight() {
        return bodyweight;
    }

    public Float[] getBodymuscle() {
        return bodymuscle;
    }

    public Float[] getBodyfat() {
        return bodyfat;
    }

}