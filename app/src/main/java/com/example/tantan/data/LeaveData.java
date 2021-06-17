package com.example.tantan.data;

import com.google.gson.annotations.SerializedName;

public class LeaveData {
    @SerializedName("userEmail")
    String userEmail;

    public LeaveData(String userEmail) {
        this.userEmail = userEmail;
    }
}
