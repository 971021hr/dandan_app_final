package com.example.tantan.ui.menu_setting;

import android.graphics.drawable.Drawable;

public class ConnectListViewItem {

    private Drawable imageDrawable ;
    private String smStr ;

    public void setImage(Drawable image) {
        imageDrawable = image;
    }
    public void setSm(String sm) {
        smStr = sm;
    }

    public Drawable getImage() {
        return this.imageDrawable;
    }
    public String getSm() {
        return this.smStr;
    }
}
