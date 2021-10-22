package com.example.tantan.data;

import com.google.gson.annotations.SerializedName;

public class DeleteData {

    @SerializedName("userEmail")
    private String userEmail;

    @SerializedName("selectDate")
    private String selectDate;

    @SerializedName("selectNum")
    private Integer selectNum;

    public DeleteData(String userEmail, String selectDate, Integer selectNum) {

        this.userEmail = userEmail;
        this.selectDate = selectDate;
        this.selectNum = selectNum;
    }
}
