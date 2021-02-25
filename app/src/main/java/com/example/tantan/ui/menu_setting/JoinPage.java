package com.example.tantan.ui.menu_setting;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import com.example.tantan.R;

public class JoinPage extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.join_layout);

        ActionBar ab = getSupportActionBar();
        ab.setTitle("회원가입");
    }

    public void onBClick(View v) {
        switch (v.getId()) {
            case R.id.cancel_btn:
                finish();
                break;
        }
    }
}
