package com.example.tantan.ui.menu_setting;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.tantan.R;

public class PassWordFindPage extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_menu5_4);

        Button btn_email = (Button) findViewById(R.id.btn_email);
        btn_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pwd_find_intent = new Intent(PassWordFindPage.this, PopUpButton1.class);
                pwd_find_intent.putExtra("data", "비밀번호 재설정 메일이 발송되었습니다.");
                startActivity(pwd_find_intent);
                finish();

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
}
