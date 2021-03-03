package com.example.tantan.ui.menu_setting;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.tantan.R;

public class PassWordFindPage extends AppCompatActivity {

    private static final int REQUEST_CODE = 0;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_menu5_4);

        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("비밀번호 찾기");

        Button btn_email = (Button) findViewById(R.id.btn_email);
        btn_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pwd_find_intent = new Intent(PassWordFindPage.this, PopUpButton1.class);
                pwd_find_intent.putExtra("data", "비밀번호 재설정 메일이 발송되었습니다.");
                startActivityForResult(pwd_find_intent, REQUEST_CODE);

//                AlertDialog.Builder dlg = new AlertDialog.Builder(PassWordFindPage.this);
//                dlg.setTitle("단단");
//                dlg.setMessage("비밀번호 재설정 메일이 발송되었습니다.");
//                dlg.setIcon(R.drawable.ic_baseline_lock_24);
//                dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        finish();
//                    }
//                });
//                dlg.show();
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

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CODE:
                if (resultCode == RESULT_OK)
                    finish();
                break;
        }
    }
}
