package com.example.tantan.data;

import com.google.gson.annotations.SerializedName;

public class UserEmailData {
    @SerializedName("userEmail")
    String userEmail;

    public UserEmailData(String userEmail) {
        this.userEmail = userEmail;
    }
}