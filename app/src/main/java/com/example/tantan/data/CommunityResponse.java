package com.example.tantan.data;

import com.google.gson.annotations.SerializedName;

public class CommunityResponse {

    @SerializedName("code")
    private int code;

    @SerializedName("message")
    private String message;

    @SerializedName("tipType")
    private String tipType;

    @SerializedName("tipTitle")
    private String tipTitle;

    @SerializedName("arrLength")
    private int arrLength;

    @SerializedName("userAuto")
    private int userAuto;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getTipType() {
        return tipType;
    }

    public String getTipTitle() {
        return tipTitle;
    }

    public int getArrLength() {
        return arrLength;
    }

    public int getUserAuto() {
        return userAuto;
    }
}