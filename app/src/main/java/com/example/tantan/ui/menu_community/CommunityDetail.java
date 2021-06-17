package com.example.tantan.ui.menu_community;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.tantan.R;
import com.example.tantan.data.CommunityDetailResponse;
import com.example.tantan.data.NumData;
import com.example.tantan.network.RetrofitClient;
import com.example.tantan.network.ServiceApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommunityDetail extends AppCompatActivity {
    private ServiceApi service;

    TextView tipTitle;
    TextView tipWriter;
    TextView tipDate;
    TextView tipText;

    int pos;
    String type;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu2_detail);

        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        service = RetrofitClient.getClient().create(ServiceApi.class);

        tipTitle = (TextView)findViewById(R.id.tip_detail_title);
        tipWriter = (TextView)findViewById(R.id.tip_detail_writer);
        tipDate = (TextView)findViewById(R.id.tip_detail_date);
        tipText = (TextView)findViewById(R.id.tip_detail_text);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("커뮤니티");

        Intent intent1 = getIntent();
        pos = intent1.getIntExtra("position",0);
        type = intent1.getStringExtra("type");

        if(type.equals("식단")){
            getCommunityDetailMeal();
        }else {
            getCommunityDetailRun();
        }


    }


    private void getCommunityDetailMeal() {
        startMealDetail(new NumData(pos));
    }

    private void getCommunityDetailRun() {
        startRunDetail(new NumData(pos));
    }

    private void startMealDetail(NumData data) {
        service.userDataCommunityMealDetail(data).enqueue(new Callback<CommunityDetailResponse>() {
            @Override
            public void onResponse(Call<CommunityDetailResponse> call, Response<CommunityDetailResponse> response) {
                CommunityDetailResponse result = response.body();

                String title = result.getTipTitle();
                String writer = result.getTipEdit();
                String date = result.getTipDate();
                String text = result.getTipText();

                tipTitle.setText(title);
                tipWriter.setText(writer);
                tipDate.setText(date);
                tipText.setText(text);

            }

            @Override
            public void onFailure(Call<CommunityDetailResponse> call, Throwable t) {

            }
        });
    }

    private void startRunDetail(NumData data) {
        service.userDataCommunityRunDetail(data).enqueue(new Callback<CommunityDetailResponse>() {
            @Override
            public void onResponse(Call<CommunityDetailResponse> call, Response<CommunityDetailResponse> response) {
                CommunityDetailResponse result = response.body();

                String title = result.getTipTitle();
                String writer = result.getTipEdit();
                String date = result.getTipDate();
                String text = result.getTipText();

                tipTitle.setText(title);
                tipWriter.setText(writer);
                tipDate.setText(date);
                tipText.setText(text);

            }

            @Override
            public void onFailure(Call<CommunityDetailResponse> call, Throwable t) {

            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{ //toolbar의 back키 눌렀을 때 동작
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}