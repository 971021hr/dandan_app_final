package com.example.tantan.ui.menu_community;

public class VerticalData {
    private int img;
    private String text;
    private String text2;

    public VerticalData(int img, String text,String text2){
        this.img = img;
        this.text = text;
        this.text2 = text2;
    }

    public String getText2() {
        return text2;
    }

    public String getText() {
        return this.text;
    }

    public int getImg() {
        return this.img;
    }

}