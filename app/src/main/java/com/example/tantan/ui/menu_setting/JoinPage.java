package com.example.tantan.ui.menu_setting;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
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
    private EditText mPasswordView2;
    private Button mJoinButton;
    private ServiceApi service;
    private TextView mTextView;
    private CheckBox mCheckBox;

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

        mEmailView = (AutoCompleteTextView) findViewById(R.id.join_email);
        mPasswordView = (EditText) findViewById(R.id.join_password);
        mPasswordView2 = (EditText) findViewById(R.id.editTextTextPassword4);
        mNameView = (EditText) findViewById(R.id.join_name);
        mCheckBox = (CheckBox) findViewById(R.id.checkbox1);
        mJoinButton = (Button) findViewById(R.id.join_button);
        mTextView = (TextView) findViewById(R.id.join_ok);
        service = RetrofitClient.getClient().create(ServiceApi.class);

        mTextView.setVisibility(View.GONE);

        mPasswordView2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (mPasswordView.getText().toString().equals(mPasswordView2.getText().toString())) {
                    mTextView.setVisibility(View.VISIBLE);
                    mTextView.setText("비밀번호가 일치합니다.");
                } else {
                    mTextView.setVisibility(View.VISIBLE);
                    mTextView.setText("비밀번호가 불일치합니다.");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

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
        String password2 = mPasswordView2.getText().toString();

        cancel = false;
        focusView = null;

        // 이름의 유효성 검사
        if (name.isEmpty()) {
            mNameView.setError("닉네임을 입력해주세요.");
            focusView = mNameView;
            cancel = true;
        }

        // 패스워드의 유효성 검사
        if (password.isEmpty()) {
            mPasswordView.setError("비밀번호를 입력해주세요.");
            focusView = mPasswordView;
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

        // 패스워드의 유효성 검사
        if (password2.isEmpty()) {
            mPasswordView2.setError("비밀번호를 입력해주세요.");
            focusView = mPasswordView2;
            cancel = true;
        }

        if(mCheckBox.isChecked()) {
            if (password.equals(password2)) {
                Toast.makeText(JoinPage.this, "개인정보 처리에 동의하셨습니다.", Toast.LENGTH_SHORT).show();
                cancel = false;
            } else {
                Toast.makeText(JoinPage.this, "비밀번호가 불일치합니다.", Toast.LENGTH_SHORT).show();
                focusView = mPasswordView2;
                cancel = true;
            }
        }
        if (!mCheckBox.isChecked()){
            Toast.makeText(JoinPage.this, "동의하지 않으면 회원가입하지 못합니다.", Toast.LENGTH_SHORT).show();
            focusView = mCheckBox;
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

                if (result.getCode() == 200) {
                    finish();
                }

                if (result.getCode() == 204) {
                    mEmailView.setError("존재하는 이메일입니다.");
                    focusView = mEmailView;
                    cancel = true;
                }

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