package com.example.tantan.ui.menu_setting;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tantan.R;
import com.example.tantan.data.NoticeDetailResponse;
import com.example.tantan.data.NoticeResponse;
import com.example.tantan.data.NumData;
import com.example.tantan.data.UserEmailData;
import com.example.tantan.network.RetrofitClient;
import com.example.tantan.network.ServiceApi;
import com.example.tantan.network.SharedPreference;

import java.io.File;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Notice2Page extends AppCompatActivity {
    private ServiceApi service;

    TextView n_number;
    ImageView n_image;
    TextView n_title;
    TextView n_date;
    TextView n_content;

    private int noticeID;
    private int j;

    private  String n_n = "";
    private String n_t = "공지 제목";
    private String n_d = "공지 날짜";
    private String n_c = "공지 내용";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notice2_layout);

        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        service = RetrofitClient.getClient().create(ServiceApi.class);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("공지사항");

        n_number = (TextView) findViewById(R.id.notice_number);
        n_image = (ImageView) findViewById(R.id.notice_image);
        n_title = (TextView) findViewById(R.id.notice_title);
        n_date = (TextView) findViewById(R.id.notice_date);
        n_content = (TextView) findViewById(R.id.notice_content);

        Intent intent = getIntent();
        noticeID = intent.getIntExtra("selectPosition",0);
        j = intent.getIntExtra("j",0);
        Log.e("selectPosition", Integer.toString(noticeID));
        Log.e("j", Integer.toString(j));

        getNoticeDetail();

        if (n_n.equals("")) {
            n_number.setVisibility(View.GONE);
            n_image.setVisibility(View.VISIBLE);
        } else {
            n_image.setVisibility(View.GONE);
            n_number.setVisibility(View.VISIBLE);
        }


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


    private void getNoticeDetail() {

        startNoticeDetail(new NumData(noticeID));

    }

    private void startNoticeDetail(NumData data) {
        service.userDataNoticeDetail(data).enqueue(new Callback<NoticeDetailResponse>() {
            @Override
            public void onResponse(Call<NoticeDetailResponse> call, Response<NoticeDetailResponse> response) {
                NoticeDetailResponse result = response.body();
                Log.e("성공?", "ㅇㅇㅇ");

                if (result.getCode() == 200) {
                    Log.e("성공?", result.getMessage());

                    int noPri =  result.getNoticePri();
                    String noTitle = result.getNoticeTitle();
                    String noDate = result.getNoticeDate();
                    String noText = result.getNoticeText();

                    if(noPri == 1){
                        n_n = "";

                    }else {
                        n_n = Integer.toString(j);

                    }
                    n_t = noTitle;
                    n_d = noDate;
                    n_c = noText;

                }
                n_number.setText(n_n);
                n_title.setText(n_t);
                n_date.setText(n_d);
                n_content.setText(n_c);


            }

            @Override
            public void onFailure(Call<NoticeDetailResponse> call, Throwable t) {
                Log.e("실행","onFailure");

                n_t = "null 값_제목";
                n_d = "null 값_날짜";
                n_c = "null 값_본문";

                n_number.setText(n_n);
                n_title.setText(n_t);
                n_date.setText(n_d);
                n_content.setText(n_c);


            }
        });
    }



}