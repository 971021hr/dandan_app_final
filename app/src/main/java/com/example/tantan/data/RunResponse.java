package com.example.tantan.data;

import com.google.gson.annotations.SerializedName;

public class RunResponse {
    @SerializedName("code")
    private int code;

    @SerializedName("message")
    private String message;

    @SerializedName("arrLength")
    private int arrLength;

    @SerializedName("runTime")
    private int[] runTime;

    @SerializedName("runMain")
    private String runMain;

    @SerializedName("runSub")
    private String runSub;

    @SerializedName("runID")
    private String runID;

    public int getCode() {

        return code;
    }

    public String getMessage() {

        return message;
    }
    public int getArrLength() {

        return arrLength;
    }

    public int[] getRunTime() {
        return runTime;
    }

    public String getRunMain() {
        return runMain;
    }

    public String getRunSub() {
        return runSub;
    }

    public String getRunID() {
        return runID;
    }

}