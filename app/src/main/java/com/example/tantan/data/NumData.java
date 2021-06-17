package com.example.tantan.data;

import com.google.gson.annotations.SerializedName;

public class NumData {
    @SerializedName("selectNum")
    int selectNum;

    public NumData(int selectNum) {
        this.selectNum = selectNum;
    }

}