package com.example.tantan.network;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import java.util.concurrent.TimeUnit;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;


public class RetrofitClient {

    private final static String BASE_URL = "";       // ""에 http://192.xxx.xxx.xxx:3000 형식으로 작성
    private static Retrofit retrofit = null;

    private RetrofitClient() {
    }

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}