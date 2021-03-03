package com.example.tantan.ui.menu_setting;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.example.tantan.R;

public class PassWordPage extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.password_layout);

        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setTitle("개인정보");

    //    ActionBar actionBar = getSupportActionBar();
   //     actionBar.setTitle("개인정보");

    }
    public void onBClick(View v) {
        switch (v.getId()) {
            case R.id.btn_pwdcancel:
                finish();
                break;

            case R.id.btn_pwdfinish:
                AlertDialog.Builder dlg = new AlertDialog.Builder(PassWordPage.this);
                dlg.setTitle("단단");
                dlg.setMessage("비밀번호가 변경되었습니다.");
                dlg.setIcon(R.drawable.ic_baseline_lock_24);
                dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                });
                dlg.show();

                break;
        }
    }
}
