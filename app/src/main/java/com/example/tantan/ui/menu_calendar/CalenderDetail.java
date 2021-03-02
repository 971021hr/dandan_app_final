package com.example.tantan.ui.menu_calendar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.tantan.DataEvent;
import com.example.tantan.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class CalenderDetail extends AppCompatActivity{

    TextView txtSelect;
    Button btnMeal, btnBody;
    LinearLayout layoutMeal;
    LinearLayout layoutBody;
    TextView txtWater;
    ListView listMeal;
    ListView listBody;
    ListView listExercise;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu1_calender_detail);

        txtSelect = (TextView)findViewById(R.id.txt_select);

        Intent intent = getIntent();
        String select_date = intent.getStringExtra("날짜");
        txtSelect.setText(select_date);

        btnMeal = (Button)findViewById(R.id.btn_show_meal);
        btnBody = (Button)findViewById(R.id.btn_show_body);

        layoutMeal = (LinearLayout)findViewById(R.id.layout_meal);
        layoutBody = (LinearLayout)findViewById(R.id.layout_body);

        txtWater = (TextView)findViewById(R.id.txt_water);
        listMeal = (ListView)findViewById(R.id.list_meal);
        listBody = (ListView)findViewById(R.id.list_body);
        listExercise = (ListView)findViewById(R.id.list_exercise);

        //식단 list 추가 >> 나중에 DB 연결
        MealAdapter mealAdapter;
        mealAdapter = new MealAdapter();
        listMeal.setAdapter(mealAdapter);

        mealAdapter.addItem(ContextCompat.getDrawable(this,R.drawable.ic_baseline_fastfood_24),"AM 10:00","오늘 아점은 이걸 먹었다고 한다.");
        mealAdapter.addItem(ContextCompat.getDrawable(this,R.drawable.ic_baseline_fastfood_24),"AM 14:00","오늘 간식은 이걸 먹었다고 한다.");
        mealAdapter.addItem(ContextCompat.getDrawable(this,R.drawable.ic_baseline_fastfood_24),"AM 18:00","오늘 저녁은 이걸 먹었다고 한다.");

        //신체 눈바디 등 정보 list 추가
        BodyAdapter bodyAdapter= new BodyAdapter();
        listBody.setAdapter(bodyAdapter);

        bodyAdapter.addItem(ContextCompat.getDrawable(this,R.drawable.ic_baseline_person_24),"50.0 kg", "15.0 kg", "30.0 kg");

        //운동 정보 list 추가
        ExerciseAdapter exerciseAdapter = new ExerciseAdapter();
        listExercise.setAdapter(exerciseAdapter);

        exerciseAdapter.addItem("헬스","01:05:50","스쿼트 3set, 런지 5set, 풀업 3set");
        exerciseAdapter.addItem("요가","00:40:33","다운독, 스쿼트, 기타 등등");

        btnMeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*화면전환*/
                layoutMeal.setVisibility(View.VISIBLE);
                layoutBody.setVisibility(View.INVISIBLE);

                btnMeal.setEnabled(false);
                btnBody.setEnabled(true);

            }
        });

        btnBody.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layoutMeal.setVisibility(View.INVISIBLE);
                layoutBody.setVisibility(View.VISIBLE);

                btnMeal.setEnabled(true);
                btnBody.setEnabled(false);

            }
        });
    }

}
