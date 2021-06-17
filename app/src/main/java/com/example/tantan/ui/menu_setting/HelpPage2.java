package com.example.tantan.ui.menu_setting;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.tantan.R;
import com.example.tantan.data.HelpDetailResponse;
import com.example.tantan.data.NumData;
import com.example.tantan.network.RetrofitClient;
import com.example.tantan.network.ServiceApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HelpPage2 extends AppCompatActivity {
    private ServiceApi service;

    TextView q_number;
    TextView q_title;
    TextView q_answer;

    private int helpNum;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.help2_layout);

        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        service = RetrofitClient.getClient().create(ServiceApi.class);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("도움말");

        q_number = (TextView) findViewById(R.id.q_number);
        q_title = (TextView) findViewById(R.id.q_title);
        q_answer = (TextView) findViewById(R.id.q_answer);

        Intent intent = getIntent();
        helpNum = intent.getIntExtra("helpNum",0);

        getHelpDetail();

        /*String q_n = intent.getStringExtra("질문 번호");
        String q_t = intent.getStringExtra("질문 제목");
        String q_a = intent.getStringExtra("질문 답변");

        q_number.setText(q_n);
        q_title.setText(q_t);
        q_answer.setText(q_a);*/
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


    public void getHelpDetail(){
        startHelpDetail(new NumData(helpNum));
    };

    private void startHelpDetail(NumData data){
        service.userDataHelpDetail(data).enqueue(new Callback<HelpDetailResponse>(){

            @Override
            public void onResponse(Call<HelpDetailResponse> call, Response<HelpDetailResponse> response) {
                HelpDetailResponse result = response.body();

                String q_n = Integer.toString(helpNum + 1);
                String q_t = result.getFaqTitle();
                String q_a = result.getFaqText();

                q_number.setText(q_n);
                q_title.setText(q_t);
                q_answer.setText(q_a);

                Log.e("success : ", result.getMessage());
            }

            @Override
            public void onFailure(Call<HelpDetailResponse> call, Throwable t) {
                Log.e("success : ", "실패");

            }
        });
    }
}