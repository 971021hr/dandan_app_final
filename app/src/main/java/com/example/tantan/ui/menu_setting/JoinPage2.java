package com.example.tantan.ui.menu_setting;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.tantan.MainActivity;
import com.example.tantan.R;

public class JoinPage2  extends AppCompatActivity {

    private static final int REQUEST_CODE = 0;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_menu5_3);

        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setTitle("회원가입");

        Button join_cancel = (Button) findViewById(R.id.btn_joincancel2);
        join_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Button join_finish = (Button)findViewById(R.id.btn_joinfinish);
        join_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent join_intent = new Intent(JoinPage2.this, PopUpButton1.class);
                join_intent.putExtra("data", "축하합니다! 회원가입이 완료되었습니다.");
                startActivityForResult(join_intent, REQUEST_CODE);

            }
        });

    }
    public void onBackPressed(){super.onBackPressed();}

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Intent intent = new Intent();

        switch (requestCode) {
            case REQUEST_CODE:
                if (resultCode == RESULT_OK)
                    setResult(RESULT_OK, intent);
                    finish();
                break;
        }
    }

}
