package com.example.tantan.ui.menu_setting;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.tantan.R;

public class PrivatePage extends AppCompatActivity {

    final static int REQUEST_CODE = 0;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.private_layout);

        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("개인정보");

        /*
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("개인정보");
        actionBar.setDisplayHomeAsUpEnabled(true);

         */

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
                        startActivityForResult(nickname_intent, REQUEST_CODE);
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
                }
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CODE:
                if (resultCode == RESULT_OK) {
                    finish();
                } else if (resultCode == RESULT_CANCELED) {
                    Intent intent = new Intent();
                    setResult(RESULT_OK, intent);
                }
                break;
        }
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