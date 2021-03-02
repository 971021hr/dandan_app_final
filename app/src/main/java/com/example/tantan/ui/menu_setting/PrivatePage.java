package com.example.tantan.ui.menu_setting;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.tantan.R;

public class PrivatePage extends AppCompatActivity {

    final static int REQUEST_CODE = 0;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.private_layout);

     //   ActionBar actionBar = getSupportActionBar();
     //   actionBar.setTitle("개인정보");
     //   actionBar.setDisplayHomeAsUpEnabled(true);

        ListView listview;
        ListViewAdapter2 adapter;

        adapter = new ListViewAdapter2() ;

        listview = (ListView) findViewById(R.id.list_view3);
        listview.setAdapter(adapter);

        adapter.addItem("닉네임 변경", ContextCompat.getDrawable(this, R.drawable.ic_baseline_navigate_next_24));
        adapter.addItem("비밀번호 변경", ContextCompat.getDrawable(this, R.drawable.ic_baseline_navigate_next_24));
        adapter.addItem("탈퇴하기", ContextCompat.getDrawable(this, R.drawable.ic_baseline_navigate_next_24));

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ListViewItem2 item = (ListViewItem2) adapter.getItem(position);
                switch (item.getTitle()) {
                    case "닉네임 변경":
                        Intent nickname_intent = new Intent(PrivatePage.this, NickNamePage.class);
                        startActivity(nickname_intent);
                        break;
                    case "비밀번호 변경":
                        Intent password_intent = new Intent(PrivatePage.this, PassWordPage.class);
                        startActivity(password_intent);
                        break;
                    case "탈퇴하기":
                        Intent intent2 = new Intent(PrivatePage.this, PopUpButton2.class);
                        intent2.putExtra("data", "정말로 탈퇴하시겠습니까? 힝구");
                        startActivityForResult(intent2, REQUEST_CODE);
                        break;
                        //Toast.makeText(getApplicationContext(), "탈퇴되었습니다.", Toast.LENGTH_LONG).show();
//                        if (REQUEST_CODE == RESULT_OK) {
//                            Toast.makeText(getApplicationContext(), "탈퇴되었습니다.", Toast.LENGTH_LONG).show();
//                            finish();
//                        } else {
//
//                        }
//                        break;
                }
            }
        });

        /*
        if (this.getIntent().getData() != null) {
            Intent intent = getIntent();
            String finish = intent.getStringExtra("finish");
            if (finish.equals("1"))
                finish();
        }

         */
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CODE:
                if (requestCode != RESULT_OK) {
                    Toast.makeText(getApplicationContext(), "탈퇴되었습니다.", Toast.LENGTH_LONG).show();
                    finish();
                }
                Toast.makeText(getApplicationContext(), Integer.toString(requestCode), Toast.LENGTH_LONG).show();
                break;
        }
    }
}