package com.example.tantan.ui.menu_setting;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.example.tantan.R;

public class LoginPage extends AppCompatActivity {

    TextView join_text;
    TextView pwd_email_text;
    Button login_btn;

    final static int REQUEST_CODE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);

        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("로그인/회원가입");

       // ActionBar actionBar = getSupportActionBar();
        //actionBar.setTitle("로그인");
       //actionBar.setDisplayHomeAsUpEnabled(true);

        login_btn = (Button) findViewById(R.id.login_btn);
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("이름", "차현경");
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        join_text = (TextView) findViewById(R.id.join_text);
        pwd_email_text = (TextView) findViewById(R.id.findpw_text);

        join_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent join_intent = new Intent(LoginPage.this, JoinPage.class);
                startActivity(join_intent);
            }
        });
        pwd_email_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pwd_email_intent = new Intent(LoginPage.this, PassWordFindPage.class);
                startActivity(pwd_email_intent);
            }
        });

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CODE:
                if (resultCode == RESULT_OK)
                    finish();
                break;
        }
    }

/*
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login_btn:
                Intent intent = new Intent();
                intent.putExtra("이름", "차현경");
                setResult(RESULT_OK, intent);
                finish();
                break;

            case R.id.join_text:
                Intent join_intent = new Intent(this, JoinPage.class);
                startActivity(join_intent);
                break;

            case R.id.findpw_text:
                Intent findpw_intent = new Intent(this, PassWordPage.class);
                startActivity(findpw_intent);
                break;
        }
    }*/

}
