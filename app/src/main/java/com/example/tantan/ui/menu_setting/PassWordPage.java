package com.example.tantan.ui.menu_setting;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tantan.R;
import com.example.tantan.data.PwdData;
import com.example.tantan.data.PwdResponse;
import com.example.tantan.network.RetrofitClient;
import com.example.tantan.network.ServiceApi;
import com.example.tantan.network.SharedPreference;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PassWordPage extends AppCompatActivity {

    private EditText mUserPwd_p;
    private EditText mUserPwd_n;
    private EditText mUserPwd_n_c;
    private Button mUserPwdChange;
    private Button mUserPwdNotChange;
    private ServiceApi service;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.password_layout);

        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setTitle("개인정보");

        //    ActionBar actionBar = getSupportActionBar();
        //     actionBar.setTitle("개인정보");

        mUserPwd_p = (EditText) findViewById(R.id.userPwd_p);
        mUserPwd_n = (EditText) findViewById(R.id.userPwd_n);
        mUserPwd_n_c = (EditText) findViewById(R.id.userPwd_n_c);
        mUserPwdChange = (Button) findViewById(R.id.btn_pwdfinish);
        mUserPwdNotChange = (Button) findViewById(R.id.btn_pwdcancel);

        service = RetrofitClient.getClient().create(ServiceApi.class);

        mUserPwdChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptChange();
            }
        });

        mUserPwdNotChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void attemptChange() {
        mUserPwd_p.setError(null);
        mUserPwd_n.setError(null);
        mUserPwd_n_c.setError(null);

        String password = SharedPreference.getAttribute(getBaseContext(), "userPwd");
        String email = SharedPreference.getAttribute(getBaseContext() ,"userEmail");
        String password_n_c = mUserPwd_n_c.getText().toString();
        String password_n = mUserPwd_n.getText().toString();
        String password_p = mUserPwd_p.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (password_p.isEmpty()) {
            mUserPwd_p.setError("현재 비밀번호를 입력해주세요.");
            focusView = mUserPwd_p;
            cancel = true;
        } else if (!password_p.equals(password)) {
            mUserPwd_p.setError("비밀번호가 일치하지 않습니다.");
            focusView = mUserPwd_p;
            cancel = true;
        }

        if (password_n.isEmpty()) {
            mUserPwd_n.setError("새로운 비밀번호를 입력해주세요.");
            focusView = mUserPwd_n;
            cancel = true;
        } else if (!isPasswordValid(password_n)) {
            mUserPwd_n.setError("6자 이상의 비밀번호를 입력해주세요.");
            focusView = mUserPwd_n;
            cancel = true;
        } else if (password_n.equals(password)) {
            mUserPwd_n.setError("현재 비밀번호와 동일한 비밀번호입니다.");
            focusView = mUserPwd_n;
            cancel = true;
        }

        if (password_n_c.isEmpty()) {
            mUserPwd_n_c.setError("새로운 비밀번호를 다시 입력해주세요.");
            focusView = mUserPwd_n_c;
            cancel = true;
        } else if (!password_n_c.equals(password_n)) {
            mUserPwd_n_c.setError("새로운 비밀번호가 일치하지 않습니다.");
            focusView = mUserPwd_n_c;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            startChange(new PwdData(email, password_n_c));
        }
    }

    private void startChange(PwdData data) {
        service.userPwd(data).enqueue(new Callback<PwdResponse>() {

            @Override
            public void onResponse(Call<PwdResponse> call, Response<PwdResponse> response) {
                PwdResponse result = response.body();


                if (result.getCode() == 200) {
                    SharedPreference.setAttribute(PassWordPage.this, "userPwd", mUserPwd_n_c.getText().toString());
                    finish();
                }
            }

            @Override
            public void onFailure(Call<PwdResponse> call, Throwable t) {

            }
        });
    }

    private boolean isPasswordValid(String password) {
        return password.length() >= 6;
    }


    /*
    public void onBClick(View v) {
        switch (v.getId()) {
            case R.id.btn_pwdcancel:
                finish();
                break;

            case R.id.btn_pwdfinish:
                AlertDialog.Builder dlg = new AlertDialog.Builder(PassWordPage.this);
                dlg.setTitle("단단");
                dlg.setMessage("비밀번호가 변경되었습니다.");
                dlg.setIcon(R.drawable.ic_baseline_lock_24);
                dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                });
                dlg.show();

                break;
        }
    }

     */
}