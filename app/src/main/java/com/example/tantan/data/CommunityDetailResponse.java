package com.example.tantan.data;

import com.google.gson.annotations.SerializedName;

public class CommunityDetailResponse {

    @SerializedName("code")
    private int code;

    @SerializedName("message")
    private String message;

    @SerializedName("tipTitle")
    private String tipTitle;

    @SerializedName("tipEdit")
    private String tipEdit;

    @SerializedName("tipDate")
    private String tipDate;

    @SerializedName("tipText")
    private String tipText;


    public int getCode() {

        return code;
    }

    public String getMessage() {

        return message;
    }

    public String getTipTitle() {
        return tipTitle;
    }

    public String getTipEdit() {
        return tipEdit;
    }

    public String getTipDate() {
        return tipDate;
    }

    public String getTipText() {
        return tipText;
    }
}