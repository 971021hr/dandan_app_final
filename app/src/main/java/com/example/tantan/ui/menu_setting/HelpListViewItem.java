package com.example.tantan.ui.menu_setting;

import android.graphics.drawable.Drawable;

public class HelpListViewItem {

    private String numberStr;
    private String titleStr;
    private Drawable pointDrawable;

    public void setNumber(String number) {
        numberStr = number;
    }
    public void setTitle(String title) {
        titleStr = title;
    }
    public void setPoint(Drawable point) {
        pointDrawable = point;
    }

    public String getNumber() {
        return this.numberStr;
    }
    public String getTitle() {
        return this.titleStr;
    }
    public Drawable getPoint() {
        return this.pointDrawable;
    }
}
