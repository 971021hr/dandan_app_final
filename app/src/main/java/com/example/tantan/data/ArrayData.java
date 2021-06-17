package com.example.tantan.data;

import com.google.gson.annotations.SerializedName;

public class ArrayData {
    @SerializedName("userEmail")
    String userEmail;

    @SerializedName("selectDate")
    String selectDate;

    public ArrayData(String userEmail, String selectDate){
        this.userEmail = userEmail;
        this.selectDate = selectDate;
    }
}