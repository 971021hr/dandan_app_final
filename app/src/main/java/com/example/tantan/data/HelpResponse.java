package com.example.tantan.data;

import com.google.gson.annotations.SerializedName;

public class HelpResponse {

    @SerializedName("code")
    private int code;

    @SerializedName("message")
    private String message;

    @SerializedName("helpTitle")
    private String helpTitle;

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

    public String getHelpTitle() {

        return helpTitle;
    }

    public int getArrLength() {
        return arrLength;
    }

    public int getUserAuto() {
        return userAuto;
    }
}