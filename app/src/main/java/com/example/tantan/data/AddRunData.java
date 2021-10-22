package com.example.tantan.data;

import android.content.Intent;

import com.google.gson.annotations.SerializedName;

public class AddRunData {
    @SerializedName("userEmail")
    String userEmail;

    @SerializedName("runDate")
    String runDate;

    @SerializedName("runTime_h")
    int runTime_h;

    @SerializedName("runTime_m")
    int runTime_m;

    @SerializedName("runMain")
    String runMain;

    @SerializedName("runSub")
    String runSub;

    public AddRunData(String userEmail, String runDate, int runTime_h, int runTime_m, String runMain, String runSub){
        this.userEmail = userEmail;
        this.runDate = runDate;
        this.runTime_h = runTime_h;
        this.runTime_m = runTime_m;
        this.runMain = runMain;
        this.runSub = runSub;

    }


}
