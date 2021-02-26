package com.example.tantan.ui.menu_setting;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.tantan.R;

public class JoinPage extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.join_layout);

        //ActionBar ab = getSupportActionBar();
       // ab.setTitle("회원가입");

        Button btn_joinnext = (Button)findViewById(R.id.btn_joinnext);
        btn_joinnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent join_intent = new Intent(JoinPage.this, JoinPage2.class);
                startActivity(join_intent);
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
    public void onBackPressed(){super.onBackPressed();}

}
