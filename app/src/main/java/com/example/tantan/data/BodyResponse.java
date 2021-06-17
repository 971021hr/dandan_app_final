package com.example.tantan.data;

import com.google.gson.annotations.SerializedName;

public class BodyResponse {

    @SerializedName("code")
    private int code;

    @SerializedName("message")
    private String message;

    @SerializedName("bodyweight")
    private String bodyweight;

    @SerializedName("bodymuscle")
    private String bodymuscle;

    @SerializedName("bodyfat")
    private String bodyfat;

    @SerializedName("bodyphoto")
    private String bodyphoto;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getBodyweight() {
        return bodyweight;
    }

    public String getBodymuscle() {
        return bodymuscle;
    }

    public String getBodyfat() {
        return bodyfat;
    }

    public String getBodyphoto() {
        return bodyphoto;
    }
}