package com.example.tantan.data;

import com.google.gson.annotations.SerializedName;

public class NoticeResponse {

    @SerializedName("code")
    private int code;

    @SerializedName("message")
    private String message;

    @SerializedName("noticeTitle")
    private String noticeTitle;

    @SerializedName("noticeDate")
    private String noticeDate;

    @SerializedName("noticePri")
    private String noticePri;

    @SerializedName("userAuto")
    private int userAuto;

    @SerializedName("arrLength")
    private int arrLength;

    public int getCode() {

        return code;
    }

    public String getMessage() {

        return message;
    }

    public String getNoticeTitle() {
        return noticeTitle;
    }

    public String getNoticeDate() {
        return noticeDate;
    }

    public String getNoticePri() {
        return noticePri;
    }

    public int getUserAuto() {
        return userAuto;
    }

    public int getArrLength() {
        return arrLength;
    }
}