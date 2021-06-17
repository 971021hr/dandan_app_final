package com.example.tantan.data;

import com.google.gson.annotations.SerializedName;

public class NameData {

    @SerializedName("userEmail")
    String userEmail;

    @SerializedName("userName")
    String userName;

    public NameData(String userEmail, String userName) {
        this.userEmail = userEmail;
        this.userName = userName;
    }
}