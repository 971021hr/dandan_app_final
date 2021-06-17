package com.example.tantan.ui.menu_setting;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.example.tantan.R;
import com.example.tantan.network.SendMail;
import com.example.tantan.data.PwdData;
import com.example.tantan.data.PwdResponse;
import com.example.tantan.network.GMailSender;
import com.example.tantan.network.RetrofitClient;
import com.example.tantan.network.SendMail;
import com.example.tantan.network.ServiceApi;

import java.util.Random;

import javax.mail.MessagingException;
import javax.mail.SendFailedException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PassWordFindPage extends AppCompatActivity {

    private ServiceApi service;
    private AutoCompleteTextView mUserEmail;
    private static final int REQUEST_CODE = 0;

    String strEmail = "";
    String strPassword = "";
    int minRandom = 100000;
    int maxRandom = 999999;
    int randomValue;

    Random random = new Random();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_menu5_4);

        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("비밀번호 찾기");

        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .permitDiskReads()
                .permitDiskWrites()
                .permitNetwork().build());

        mUserEmail = (AutoCompleteTextView) findViewById(R.id.user_email);

        service = RetrofitClient.getClient().create(ServiceApi.class);

        Button btn_email = (Button) findViewById(R.id.btn_email);
        btn_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptEmail();

                Intent pwd_find_intent = new Intent(PassWordFindPage.this, PopUpButton1.class);
                pwd_find_intent.putExtra("data", "비밀번호 재설정 메일이 발송되었습니다.");
                startActivityForResult(pwd_find_intent, REQUEST_CODE);
            }
        });
    }

    private void attemptEmail() {
        mUserEmail.setError(null);

        randomValue = random.nextInt(maxRandom - minRandom + 1) + minRandom;
        Log.e("랜덤값(100000~999999) : ", String.valueOf(randomValue));

        strEmail = mUserEmail.getText().toString();
        strPassword = String.valueOf(randomValue);

        boolean cancel = false;
        View focusView = null;


        // 패스워드의 유효성 검사
        if (strEmail.isEmpty()) {
            mUserEmail.setError("이메일을 입력해주세요.");
            focusView = mUserEmail;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {

            SendMail mail = new SendMail();
            mail.sendSecurityCode(PassWordFindPage.this,strEmail,strPassword);

            startChangePassword(new PwdData(strEmail, strPassword));
        }
    }

    private void startChangePassword(PwdData data) {

        service.userPwd(data).enqueue(new Callback<PwdResponse>() {
            @Override
            public void onResponse(Call<PwdResponse> call, Response<PwdResponse> response) {
                PwdResponse result = response.body();


                if (result.getCode() == 200) {
                    Intent intent = new Intent();
                    setResult(RESULT_OK, intent);

                    finish();
                }

            }

            @Override
            public void onFailure(Call<PwdResponse> call, Throwable t) {
                Toast.makeText(PassWordFindPage.this, "pw 변경 에러 발생 : onFailure", Toast.LENGTH_SHORT).show();

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

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CODE:
                if (resultCode == RESULT_OK)
                    finish();
                break;
        }
    }
}