package com.example.tantan.data;

import com.google.gson.annotations.SerializedName;

public class MealModifyData {

    @SerializedName("selectNum")
    Integer selectNum;

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

    public MealModifyData(Integer selectNum, String mealDate, String mealTime, String mealPicture, String mealPicturePath, String mealMemo) {
        this.selectNum = selectNum;
        this.mealDate = mealDate;
        this.mealTime = mealTime;
        this.mealPicture = mealPicture;
        this.mealPicturePath = mealPicturePath;
        this.mealMemo = mealMemo;
    }

}
