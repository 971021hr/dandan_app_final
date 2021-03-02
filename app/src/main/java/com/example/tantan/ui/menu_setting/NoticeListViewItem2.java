package com.example.tantan.ui.menu_setting;

import android.graphics.drawable.Drawable;

public class NoticeListViewItem2 {

    private String numberStr;
    private String titleStr;
    private String dateStr;

    public void setNumber(String number) {
        numberStr = number;
    }
    public void setTitle(String title) {
        titleStr = title;
    }
    public void setDate(String date) {
        dateStr = date;
    }

    public String getNumber() {
        return this.numberStr;
    }
    public String getTitle() {
        return this.titleStr;
    }
    public String getDate() {
        return this.dateStr;
    }
}
