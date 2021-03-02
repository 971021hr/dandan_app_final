package com.example.tantan.ui.menu_setting;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.tantan.R;

public class NoticePage extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notice_layout);

       // ActionBar actionBar = getSupportActionBar();
      //  actionBar.setTitle("공지사항");
      //  actionBar.setDisplayHomeAsUpEnabled(true);

        ListView listview;
        NoticeListViewAdapter1 adapter;

        adapter = new NoticeListViewAdapter1() ;

        listview = (ListView) findViewById(R.id.notice_list1);
        listview.setAdapter(adapter);

        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.ic_baseline_report_problem_24),
                "긴급 알람 사항", "20.01.01");
        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.ic_baseline_report_problem_24),
                "비밀번호 변경 방법", "20.01.01");
        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.ic_baseline_report_problem_24),
                "운동 추가 방법", "20.01.01");

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                NoticeListViewItem1 item = (NoticeListViewItem1) adapter.getItem(position);
                switch (item.getTitle()) {
                    case "긴급 알람 사항":
                        Intent intent1 = new Intent(NoticePage.this, Notice2Page.class);
                        intent1.putExtra("공지 번호", "");
                        intent1.putExtra("공지 제목", "긴급 알람 사항");
                        intent1.putExtra("공지 날짜", "2020.01.01");
                        intent1.putExtra("공지 내용", "빠른 시일 내로 업데이트를 실행해주시길 바랍니다.");
                        startActivity(intent1);
                        break;
                    case "비밀번호 변경 방법":
                        Intent intent2 = new Intent(NoticePage.this, Notice2Page.class);
                        intent2.putExtra("공지 번호", "");
                        intent2.putExtra("공지 제목", "비밀번호 변경 방법");
                        intent2.putExtra("공지 날짜", "2020.01.01");
                        intent2.putExtra("공지 내용", "설정 -> 개인정보 -> 비밀번호 변경 -> 변경할 비밀번호 입력 -> 비밀번호 확인 -> 변경");
                        startActivity(intent2);
                        break;
                    case "운동 추가 방법":
                        Intent intent3 = new Intent(NoticePage.this, HelpPage.class);
                        startActivity(intent3);
                        break;
                }
            }
        });

        ListView listview2;
        NoticeListViewAdapter2 adapter2;

        adapter2 = new NoticeListViewAdapter2() ;

        listview2 = (ListView) findViewById(R.id.notice_list2);
        listview2.setAdapter(adapter2);

        adapter2.addItem("01",
                "어쩌고 공지", "20.01.01");
        adapter2.addItem("02",
                "저쩌고 공지", "20.01.01");
        adapter2.addItem("03",
                "공지 공지 공지", "20.01.01");

        listview2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                NoticeListViewItem2 item = (NoticeListViewItem2) adapter2.getItem(position);
                switch (item.getTitle()) {
                    case "어쩌고 공지":
                        Intent intent4 = new Intent(NoticePage.this, Notice2Page.class);
                        intent4.putExtra("공지 번호", "01");
                        intent4.putExtra("공지 제목", "어쩌고 공지");
                        intent4.putExtra("공지 날짜", "2020.01.01");
                        intent4.putExtra("공지 내용", "빠른 시일 내로 업데이트를 실행해주시길 바랍니다.");
                        startActivity(intent4);
                        break;
                    case "저쩌고 공지":
                        Intent intent5 = new Intent(NoticePage.this, Notice2Page.class);
                        intent5.putExtra("공지 번호", "02");
                        intent5.putExtra("공지 제목", "저쩌고 공지");
                        intent5.putExtra("공지 날짜", "2020.01.01");
                        intent5.putExtra("공지 내용", "설정 -> 개인정보 -> 비밀번호 변경 -> 변경할 비밀번호 입력 -> 비밀번호 확인 -> 변경");
                        startActivity(intent5);
                        break;
                    case "공지 공지 공지":
                        Intent intent3 = new Intent(NoticePage.this, HelpPage.class);
                        startActivity(intent3);
                        break;
                }
            }
        });
    }
}
