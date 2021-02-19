package com.example.tantan.ui.menu_add;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.tantan.MainActivity;
import com.example.tantan.R;

public class menu_add extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_menu3);

        ImageButton btn1 = findViewById(R.id.btn_run);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(menu_add.this, menu_add1.class);
                intent.putExtra("message", "반갑습니다.");
                startActivity(intent);
            }
        });
    }
}
