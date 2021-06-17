package com.example.tantan.data;

import com.google.gson.annotations.SerializedName;

public class BodyData {

    @SerializedName("userEmail")
    String userEmail;

    @SerializedName("bodyDate")
    String bodyDate;

    @SerializedName("bodyWeight")
    Float bodyWeight;

    @SerializedName("bodyMuscle")
    Float bodyMuscle;

    @SerializedName("bodyFat")
    Float bodyFat;

    @SerializedName("bodyPhoto")
    String bodyPhoto;

    @SerializedName("bodyPhotoPath")
    String bodyPhotoPath;

    public BodyData(String userEmail, String bodyDate, Float bodyWeight, Float bodyMuscle, Float bodyFat, String bodyPhoto, String bodyPhotoPath) {
        this.userEmail = userEmail;
        this.bodyDate = bodyDate;
        this.bodyWeight = bodyWeight;
        this.bodyMuscle = bodyMuscle;
        this.bodyFat = bodyFat;
        this.bodyPhoto = bodyPhoto;
        this.bodyPhotoPath = bodyPhotoPath;
    }
}