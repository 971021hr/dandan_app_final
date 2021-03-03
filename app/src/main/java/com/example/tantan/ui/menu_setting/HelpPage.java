package com.example.tantan.ui.menu_setting;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.tantan.R;


public class HelpPage extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.help_layout);

        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("도움말");

        //ActionBar actionBar = getSupportActionBar();
       // actionBar.setTitle("도움말");
        //actionBar.setDisplayHomeAsUpEnabled(true);

        ListView listview;
        HelpListViewAdapter adapter;

        adapter = new HelpListViewAdapter() ;

        listview = (ListView) findViewById(R.id.help_list);
        listview.setAdapter(adapter);

        adapter.addItem("1",
                "닉네임 변경 방법", ContextCompat.getDrawable(this, R.drawable.ic_baseline_navigate_next_24));
        adapter.addItem("2",
                "비밀번호 변경 방법", ContextCompat.getDrawable(this, R.drawable.ic_baseline_navigate_next_24));
        adapter.addItem("3",
                "운동 추가 방법", ContextCompat.getDrawable(this, R.drawable.ic_baseline_navigate_next_24));
        adapter.addItem("4",
                "식단 추가 방법", ContextCompat.getDrawable(this, R.drawable.ic_baseline_navigate_next_24));
        adapter.addItem("5",
                "물 추가 방법", ContextCompat.getDrawable(this, R.drawable.ic_baseline_navigate_next_24));

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                HelpListViewItem item = (HelpListViewItem) adapter.getItem(position);
                switch (item.getTitle()) {
                    case "닉네임 변경 방법":
                        Intent intent1 = new Intent(HelpPage.this, Help2Page.class);
                        intent1.putExtra("질문 번호", "1");
                        intent1.putExtra("질문 제목", "닉네임 변경 방법");
                        intent1.putExtra("질문 답변", "설정 -> 개인정보 -> 닉네임 변경 -> 변경할 닉네임 입력 -> 중복 확인 -> 변경");
                        startActivity(intent1);
                        break;
                    case "비밀번호 변경 방법":
                        Intent intent2 = new Intent(HelpPage.this, Help2Page.class);
                        intent2.putExtra("질문 번호", "2");
                        intent2.putExtra("질문 제목", "비밀번호 변경 방법");
                        intent2.putExtra("질문 답변", "설정 -> 개인정보 -> 비밀번호 변경 -> 변경할 비밀번호 입력 -> 비밀번호 확인 -> 변경");
                        startActivity(intent2);
                        break;
                    case "운동 추가 방법":
                        Intent intent3 = new Intent(HelpPage.this, HelpPage.class);
                        startActivity(intent3);
                        break;
                    case "식단 추가 방법":
                        Intent intent4 = new Intent(HelpPage.this, ConnectPage.class);
                        startActivity(intent4);
                        break;
                    case "물 추가 방법":
                        Intent intent5 = new Intent(HelpPage.this, ConnectPage.class);
                        startActivity(intent5);
                        break;
                }
            }
        });
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
