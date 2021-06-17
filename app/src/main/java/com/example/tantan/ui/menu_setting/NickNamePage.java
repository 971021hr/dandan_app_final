package com.example.tantan.ui.menu_setting;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.example.tantan.R;
import com.example.tantan.data.NameData;
import com.example.tantan.data.NameResponse;
import com.example.tantan.network.RetrofitClient;
import com.example.tantan.network.ServiceApi;
import com.example.tantan.network.SharedPreference;

import java.io.File;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NickNamePage extends AppCompatActivity {
    String strEmail="";
    String strName = "";

    private ServiceApi service;
    private EditText editNickname;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nickname_layout);

        editNickname = (EditText) findViewById(R.id.join_name);
        service = RetrofitClient.getClient().create(ServiceApi.class);

        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setTitle("개인정보");

        //ActionBar actionBar = getSupportActionBar();
       // actionBar.setTitle("개인정보");

        strEmail = SharedPreference.getAttribute(this,"userEmail");


        strName = editNickname.getText().toString();

    }

    public void onBClick(View v) {
        switch (v.getId()) {
            case R.id.cancel_btn:
                finish();
                break;

            case R.id.finish_btn:
                Intent intent = new Intent();
                setResult(RESULT_CANCELED, intent);

                attemptNickname();

                finish();
                break;
        }
    }
    private void attemptNickname() {
        editNickname.setError(null);

        strName = editNickname.getText().toString();
        strEmail = SharedPreference.getAttribute(this,"userEmail");

        //RetrofitClient.setAttribute(mContext, "userEmail", email);
        boolean cancel = false;
        View focusView = null;


        // 패스워드의 유효성 검사
        if (strName.isEmpty()) {
            editNickname.setError("닉네임을 입력해주세요.");
            focusView = editNickname;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            startChangeNickname(new NameData(strEmail, strName));
        }
    }

    private void startChangeNickname(NameData data) {


        service.userName(data).enqueue(new Callback<NameResponse>() {
            @Override
            public void onResponse(Call<NameResponse> call, Response<NameResponse> response) {
                NameResponse result = response.body();

                if (result.getCode() == 200) {
                    SharedPreference.setAttribute(NickNamePage.this, "userName", editNickname.getText().toString());
                    Intent intent = new Intent();
                    setResult(RESULT_OK, intent);

                    finish();
                }


            }

            @Override
            public void onFailure(Call<NameResponse> call, Throwable t) {

                Log.e("닉네임 에러 발생", t.getMessage());

            }
        });
    }
}
