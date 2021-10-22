package com.example.tantan.data;

import com.google.gson.annotations.SerializedName;

public class MealModifyResponse {
    @SerializedName("code")
    private int code;

    @SerializedName("message")
    private String message;

    @SerializedName("mealID")
    private int mealID;

    @SerializedName("mealDate")
    String mealDate;

    @SerializedName("mealTime")
    String mealTime;

    @SerializedName("mealPicture")
    String mealPicture;

    @SerializedName("mealPicturePath")
    String mealPicturePath;

    @SerializedName("mealMemo")
    String mealMemo;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public int getMealID() {
        return mealID;
    }

    public String getMealDate() {
        return mealDate;
    }

    public String getMealTime() {
        return mealTime;
    }

    public String getMealPicture() {
        return mealPicture;
    }

    public String getMealPicturePath() {
        return mealPicturePath;
    }

    public String getMealMemo() {
        return mealMemo;
    }
}
