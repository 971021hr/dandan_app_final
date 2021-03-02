package com.example.tantan.ui.menu_setting;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.tantan.R;

public class Help2Page extends AppCompatActivity {

    TextView q_number;
    TextView q_title;
    TextView q_answer;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.help2_layout);

        q_number = (TextView) findViewById(R.id.q_number);
        q_title = (TextView) findViewById(R.id.q_title);
        q_answer = (TextView) findViewById(R.id.q_answer);

        Intent intent = getIntent();
        String q_n = intent.getStringExtra("질문 번호");
        String q_t = intent.getStringExtra("질문 제목");
        String q_a = intent.getStringExtra("질문 답변");

        q_number.setText(q_n);
        q_title.setText(q_t);
        q_answer.setText(q_a);
    }
}
