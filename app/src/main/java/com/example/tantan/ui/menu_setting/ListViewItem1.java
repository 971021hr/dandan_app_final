package com.example.tantan.ui.menu_setting;

import android.graphics.drawable.Drawable;

public class ListViewItem1 {

    private Drawable iconDrawable ;
    private String titleStr ;
    private Drawable pointDrawable ;

    public void setIcon(Drawable icon) {
        iconDrawable = icon;
    }
    public void setTitle(String title) {
        titleStr = title;
    }
    public void setPoint(Drawable point) {
        pointDrawable = point;
    }

    public Drawable getIcon() {
        return this.iconDrawable;
    }
    public String getTitle() {
        return this.titleStr;
    }
    public Drawable getPoint() {
        return this.pointDrawable;
    }
}
