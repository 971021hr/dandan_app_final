package com.example.tantan.ui.menu_setting;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tantan.R;
import com.example.tantan.data.JoinData;
import com.example.tantan.data.JoinResponse;
import com.example.tantan.network.RetrofitClient;
import com.example.tantan.network.ServiceApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JoinPage extends AppCompatActivity {

    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private EditText mNameView;
    private Button mJoinButton;
    private ServiceApi service;

    boolean cancel;
    View focusView;

    private static final int REQUEST_CODE = 0;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.join_layout);

        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setTitle("회원가입");

        //ActionBar ab = getSupportActionBar();
       // ab.setTitle("회원가입");
//
//        if(getIntent().getData() != null) {
//            Intent intent1 = getIntent();
//            String finish = intent1.getStringExtra("finish");
//            if(finish.equals("1"))
//                finish();
//        }

        mEmailView = (AutoCompleteTextView) findViewById(R.id.join_email);
        mPasswordView = (EditText) findViewById(R.id.join_password);
        mNameView = (EditText) findViewById(R.id.join_name);
        mJoinButton = (Button) findViewById(R.id.join_button);
        service = RetrofitClient.getClient().create(ServiceApi.class);

        mJoinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                attemptJoin();
            }
        });

        Button join_cancel = (Button) findViewById(R.id.btn_joincancel);
        join_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    private void attemptJoin() {
        mNameView.setError(null);
        mEmailView.setError(null);
        mPasswordView.setError(null);

        String name = mNameView.getText().toString();
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        cancel = false;
        focusView = null;

        // 패스워드의 유효성 검사
        if (password.isEmpty()) {
            mEmailView.setError("비밀번호를 입력해주세요.");
            focusView = mEmailView;
            cancel = true;
        } else if (!isPasswordValid(password)) {
            mPasswordView.setError("6자 이상의 비밀번호를 입력해주세요.");
            focusView = mPasswordView;
            cancel = true;
        }

        // 이메일의 유효성 검사
        if (email.isEmpty()) {
            mEmailView.setError("이메일을 입력해주세요.");
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError("@를 포함한 유효한 이메일을 입력해주세요.");
            focusView = mEmailView;
            cancel = true;
        }

        // 이름의 유효성 검사
        if (name.isEmpty()) {
            mNameView.setError("닉네임을 입력해주세요.");
            focusView = mNameView;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            startJoin(new JoinData(name, email, password));
        }
    }

    private void startJoin(JoinData data) {
        service.userJoin(data).enqueue(new Callback<JoinResponse>() {
            @Override
            public void onResponse(Call<JoinResponse> call, Response<JoinResponse> response) {
                JoinResponse result = response.body();
                Toast.makeText(JoinPage.this, result.getMessage(), Toast.LENGTH_SHORT).show();

                if (result.getCode() == 200) {
                    finish();
                }

                if (result.getCode() == 204) {
                    mEmailView.setError("존재하는 이메일입니다.");
                    focusView = mEmailView;
                    cancel = true;
                }

                /*
                Intent join_intent = new Intent(JoinPage.this, PopUpButton1.class);
                join_intent.putExtra("data", "축하합니다! 회원가입이 완료되었습니다.");
                startActivityForResult(join_intent, REQUEST_CODE);
                */
            }

            @Override
            public void onFailure(Call<JoinResponse> call, Throwable t) {
                Toast.makeText(JoinPage.this, "회원가입 에러 발생", Toast.LENGTH_SHORT).show();
                Log.e("회원가입 에러 발생", t.getMessage());
            }
        });
    }

    private boolean isEmailValid(String email) {
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        return password.length() >= 6;
    }

    public void onBackPressed(){super.onBackPressed();}

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CODE:
                if (resultCode == RESULT_OK)
                    finish();
                break;
        }
    }
}
