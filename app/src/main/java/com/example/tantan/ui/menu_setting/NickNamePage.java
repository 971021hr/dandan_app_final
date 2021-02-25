package com.example.tantan.ui.menu_setting;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;
import com.example.tantan.R;

public class NickNamePage extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nickname_layout);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("개인정보");
    }

    public void onBClick(View v) {
        switch (v.getId()) {
            case R.id.cancel_btn:
                finish();
                break;

            case R.id.finish_btn:
                Toast.makeText(getApplicationContext(), "비밀번호가 변경되었습니다.", Toast.LENGTH_LONG).show();
                finish();
                break;
        }
    }
}
