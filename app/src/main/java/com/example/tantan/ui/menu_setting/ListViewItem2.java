package com.example.tantan.ui.menu_setting;

import android.graphics.drawable.Drawable;

public class ListViewItem2 {

    private String titleStr ;
    private Drawable pointDrawable ;

    public void setTitle(String title) {
        titleStr = title;
    }
    public void setPoint(Drawable point) {
        pointDrawable = point;
    }

    public String getTitle() {
        return this.titleStr;
    }
    public Drawable getPoint() {
        return this.pointDrawable;
    }
}
