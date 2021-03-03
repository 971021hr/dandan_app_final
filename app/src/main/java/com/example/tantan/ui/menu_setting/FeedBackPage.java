package com.example.tantan.ui.menu_setting;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tantan.R;

public class FeedBackPage extends AppCompatActivity {

    private static final int REQUEST_CODE = 0;

    Spinner fd_spinner;
    String[] fd_item;
    //TextView txtResult;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feedback_layout);

        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setTitle("개발자에게 피드백/문의");

        fd_spinner = (Spinner) findViewById(R.id.fd_spinner);
        fd_item = new String[]{"문의 종류를 선택해주세요.", "개발자에게 피드백", "오류 문의", "스마트 미러 문의", "운동 추가 요청", "기타 문의"};

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, fd_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fd_spinner.setAdapter(adapter);

        fd_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    public void mOnClick(View v){
        switch (v.getId()) {
            case R.id.fd_cancel:
                finish();
                break;

            case R.id.fd_send:
                //데이터 담아서 팝업(액티비티) 호출
                Intent intent2 = new Intent(this, PopUpButton1.class);
                intent2.putExtra("data", "문의가 접수되었습니다.");
                startActivityForResult(intent2, REQUEST_CODE);
                break;
        }
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CODE:
                if (resultCode == RESULT_OK)
                    finish();
                break;
        }
    }
}
