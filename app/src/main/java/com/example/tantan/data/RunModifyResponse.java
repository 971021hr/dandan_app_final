package com.example.tantan.data;

import com.google.gson.annotations.SerializedName;

public class RunModifyResponse {

    @SerializedName("code")
    private int code;

    @SerializedName("message")
    private String message;

    @SerializedName("runID")
    private int runID;

    @SerializedName("runDate")
    private String runDate;

    @SerializedName("runTime_h")
    private int runTime_h;

    @SerializedName("runTime_m")
    private int runTime_m;

    @SerializedName("runMain")
    private String runMain;

    @SerializedName("runSub")
    private String runSub;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public int getRunID() {
        return runID;
    }

    public String getRunDate() {
        return runDate;
    }

    public int getRunTime_h() {
        return runTime_h;
    }

    public int getRunTime_m() {
        return runTime_m;
    }

    public String getRunMain() {
        return runMain;
    }

    public String getRunSub() {
        return runSub;
    }
}
