package com.example.tantan.ui.menu_add;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.tantan.R;

public class menu_addbody extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_menu3_3);

        Button run_cancel = (Button) findViewById(R.id.btn_bodycancel);
        run_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
    public void onBackPressed(){super.onBackPressed();}
}
