package com.example.tantan.ui.menu_setting;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import com.example.tantan.R;

public class LoginPage extends AppCompatActivity implements View.OnClickListener {

    TextView join_text;
    TextView findpw_text;

    final static int REQUEST_CODE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("로그인");
        actionBar.setDisplayHomeAsUpEnabled(true);

        join_text = (TextView) findViewById(R.id.join_text);
        findpw_text = (TextView) findViewById(R.id.findpw_text);
        join_text.setOnClickListener(this);
        findpw_text.setOnClickListener(this);
    }

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
                Intent findpw_intent = new Intent(this, LoginPage.class);
                startActivity(findpw_intent);
                break;
        }
    }

}
