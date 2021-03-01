package com.example.tantan.ui.menu_calendar;

import android.graphics.drawable.Drawable;

public class BodyItem {

    private Drawable bodyImg;
    private String bodyWeight;
    private String bodyMuscle;
    private String bodyFat;

    public Drawable getBodyImg() {
        return bodyImg;
    }

    public String getBodyWeight() {
        return bodyWeight;
    }

    public String getBodyMuscle() {
        return bodyMuscle;
    }

    public String getBodyFat() {
        return bodyFat;
    }

    public void setBodyImg(Drawable bodyImg) {
        this.bodyImg = bodyImg;
    }

    public void setBodyWeight(String bodyWeight) {
        this.bodyWeight = bodyWeight;
    }

    public void setBodyMuscle(String bodyMuscle) {
        this.bodyMuscle = bodyMuscle;
    }

    public void setBodyFat(String bodyFat) {
        this.bodyFat = bodyFat;
    }
}
