package com.example.tantan.data;

import com.google.gson.annotations.SerializedName;

public class MealResponse {
    @SerializedName("code")
    private int code;

    @SerializedName("message")
    private String message;

    @SerializedName("mealTime")
    private String[] mealTime;

    @SerializedName("mealMemo")
    private String[] mealMemo;

    @SerializedName("mealPicture")
    private String[] mealPicture;

    @SerializedName("result")
    private int result;

    public int getCode() { return code; }

    public String getMessage() {
        return message;
    }

    public String[] getMealTime() { return mealTime; }

    public String[] getMealMemo() { return mealMemo; }

    public String[] getMealPicture() { return mealPicture; }

    public int getResult() { return result; }
}
