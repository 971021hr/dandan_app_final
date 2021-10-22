package com.example.tantan.data;

import com.google.gson.annotations.SerializedName;

public class runModifyData {

    @SerializedName("selectNum")
    int selectNum;

    @SerializedName("runTime_h")
    int runTime_h;

    @SerializedName("runTime_m")
    int runTime_m;

    @SerializedName("runMain")
    String runMain;

    @SerializedName("runSub")
    String runSub;

    public runModifyData(Integer selectNum, Integer runTime_h, Integer runTime_m, String runMain, String runSub) {
        this.selectNum = selectNum;
        this.runTime_h = runTime_h;
        this.runTime_m = runTime_m;
        this.runMain = runMain;
        this.runSub = runSub;

    }
}
