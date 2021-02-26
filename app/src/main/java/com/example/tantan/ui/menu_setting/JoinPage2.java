package com.example.tantan.ui.menu_setting;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.tantan.MainActivity;
import com.example.tantan.R;

public class JoinPage2  extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_menu5_3);

        Button join_cancel = (Button) findViewById(R.id.btn_joincancel2);
        join_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Button join_finish = (Button)findViewById(R.id.btn_joinfinish);
        join_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dlg = new AlertDialog.Builder(JoinPage2.this);
                dlg.setTitle("단단");
                dlg.setMessage("축하합니다! 회원가입이 완료되었습니다.");
                dlg.setIcon(R.drawable.ic_baseline_mood_24);
                dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent join_intent = new Intent(JoinPage2.this, LoginPage.class);
                        startActivity(join_intent);
                        finish();
                    }
                });
                dlg.show();
            }
        });

    }
    public void onBackPressed(){super.onBackPressed();}

}
