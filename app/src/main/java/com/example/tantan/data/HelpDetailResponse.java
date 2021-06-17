package com.example.tantan.data;

import com.google.gson.annotations.SerializedName;

public class HelpDetailResponse {
/*                    'code': resultCode,
            'message': message,
            'faqTitle' : faqTitle,
            'faqText' : faqText*/

    @SerializedName("code")
    private int code;

    @SerializedName("message")
    private String message;

    @SerializedName("faqTitle")
    private String faqTitle;

    @SerializedName("faqText")
    private String faqText;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getFaqTitle() {
        return faqTitle;
    }

    public String getFaqText() {
        return faqText;
    }
}