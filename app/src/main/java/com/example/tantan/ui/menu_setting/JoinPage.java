package com.example.tantan.ui.menu_setting;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.tantan.R;

public class JoinPage extends AppCompatActivity {

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

        Button btn_joinnext = (Button)findViewById(R.id.btn_joinnext);
        btn_joinnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent join_intent = new Intent(JoinPage.this, JoinPage2.class);
                startActivityForResult(join_intent, REQUEST_CODE);
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

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CODE:
                if (resultCode == RESULT_OK)
                    finish();
                break;
        }
    }

}
