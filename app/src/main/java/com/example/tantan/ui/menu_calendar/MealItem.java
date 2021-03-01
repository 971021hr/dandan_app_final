package com.example.tantan.ui.menu_calendar;

import android.graphics.drawable.Drawable;

public class MealItem {
    private Drawable mealImg;
    private String mealTime;
    private String mealMemo;

    public Drawable getMealImg(){
        return mealImg;
    }
    public String getMealTime(){
        return mealTime;
    }
    public String getMealMemo(){
        return mealMemo;
    }

    public void setMealImg(Drawable icon){
        mealImg = icon;
    }

    public  void setMealTime(String time){
        mealTime = time;
    }

    public void setMealMemo(String memo){
        mealMemo = memo;
    }




}
