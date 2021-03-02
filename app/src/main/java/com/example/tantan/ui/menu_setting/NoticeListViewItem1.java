package com.example.tantan.ui.menu_setting;

import android.graphics.drawable.Drawable;

public class NoticeListViewItem1 {

    private Drawable yellowDrawable;
    private String titleStr;
    private String dateStr;

    public void setYellow(Drawable yellow) {
        yellowDrawable = yellow;
    }
    public void setTitle(String title) {
        titleStr = title;
    }
    public void setDate(String date) {
        dateStr = date;
    }

    public Drawable getYellow() {
        return this.yellowDrawable;
    }
    public String getTitle() {
        return this.titleStr;
    }
    public String getDate() {
        return this.dateStr;
    }
}
