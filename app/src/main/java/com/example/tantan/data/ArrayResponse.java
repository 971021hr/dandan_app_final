package com.example.tantan.data;

import com.google.gson.annotations.SerializedName;

public class ArrayResponse {
    @SerializedName("code")
    private int code;

    @SerializedName("message")
    private String message;

    @SerializedName("arrLength")
    private int arrLength;

    @SerializedName("result")
    private String[] result;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public int getArrLength() {
        return arrLength;
    }

    public String[] getResult() {
        return result;
    }
}
