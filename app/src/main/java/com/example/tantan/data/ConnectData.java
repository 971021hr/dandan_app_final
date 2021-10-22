package com.example.tantan.data;

import com.google.gson.annotations.SerializedName;

public class ConnectData {
    @SerializedName("userEmail")
    String userEmail;

    @SerializedName("randomNum")
    String randomNum;

    public ConnectData(String userEmail, String randomNum) {
        this.userEmail = userEmail;
        this.randomNum = randomNum;
    }
}
