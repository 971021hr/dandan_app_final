package com.example.tantan.data;

import com.google.gson.annotations.SerializedName;

public class MealData {

    @SerializedName("userEmail")
    String userEmail;

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

    public MealData(String userEmail, String mealDate, String mealTime, String mealPicture, String mealPicturePath, String mealMemo) {
        this.userEmail = userEmail;
        this.mealDate = mealDate;
        this.mealTime = mealTime;
        this.mealPicture = mealPicture;
        this.mealPicturePath = mealPicturePath;
        this.mealMemo = mealMemo;
    }

}
