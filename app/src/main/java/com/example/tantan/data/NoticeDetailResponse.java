package com.example.tantan.data;

import com.google.gson.annotations.SerializedName;

public class NoticeDetailResponse {
    @SerializedName("code")
    private int code;

    @SerializedName("message")
    private String message;

    @SerializedName("noticeTitle")
    private String noticeTitle;

    @SerializedName("noticeDate")
    private String noticeDate;

    @SerializedName("noticeText")
    private String noticeText;

    @SerializedName("noticePri")
    private int noticePri;


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

    public String getNoticeText() {
        return noticeText;
    }

    public int getNoticePri() {
        return noticePri;
    }
}