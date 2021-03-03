package com.example.tantan.ui.menu_setting;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tantan.R;

public class Notice2Page extends AppCompatActivity {

    TextView n_number;
    ImageView n_image;
    TextView n_title;
    TextView n_date;
    TextView n_content;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notice2_layout);

        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("공지사항");

        n_number = (TextView) findViewById(R.id.notice_number);
        n_image = (ImageView) findViewById(R.id.notice_image);
        n_title = (TextView) findViewById(R.id.notice_title);
        n_date = (TextView) findViewById(R.id.notice_date);
        n_content = (TextView) findViewById(R.id.notice_content);

        Intent intent = getIntent();
        String n_n = intent.getStringExtra("공지 번호");
        if (n_n.equals("")) {
            n_number.setVisibility(View.GONE);
        } else {
            n_image.setVisibility(View.GONE);
        }
        String n_t = intent.getStringExtra("공지 제목");
        String n_d = intent.getStringExtra("공지 날짜");
        String n_c = intent.getStringExtra("공지 내용");

        n_number.setText(n_n);
        n_title.setText(n_t);
        n_date.setText(n_d);
        n_content.setText(n_c);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{ //toolbar의 back키 눌렀을 때 동작
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
