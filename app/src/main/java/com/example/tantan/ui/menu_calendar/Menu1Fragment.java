package com.example.tantan.ui.menu_calendar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.tantan.DataEvent;
import com.example.tantan.R;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import org.greenrobot.eventbus.EventBus;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Menu1Fragment extends Fragment {

    //materialCalendarView에 이상있으면 import 잘 되어있는지 꼭 확인하기

    private static MaterialCalendarView materialCalendarView;
    Date selectDate;
    String selectFormatDate;
    LinearLayout calendarMain;
/*    LinearLayout calendarDetail;
    LinearLayout layoutMeal;
    LinearLayout layoutBody;
    TextView txtSelect;
    Button btnMeal, btnBody;

    LinearLayout layoutWater;
    ListView listMeal;
    ListView listBody;
    ListView listExercise;*/


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        ViewGroup calendarView = (ViewGroup)inflater.inflate(R.layout.fragment_menu1, container, false);

        materialCalendarView = (MaterialCalendarView)calendarView.findViewById(R.id.calendarView);
        calendarMain = (LinearLayout)calendarView.findViewById(R.id.calendar_main);
/*
        calendarDetail = (LinearLayout)calendarView.findViewById(R.id.calendar_detail);
        layoutMeal = (LinearLayout)calendarView.findViewById(R.id.layout_meal);
        layoutBody = (LinearLayout)calendarView.findViewById(R.id.layout_body);
        txtSelect = (TextView)calendarView.findViewById(R.id.txt_select);
        btnMeal = (Button)calendarView.findViewById(R.id.btn_show_meal);
        btnBody = (Button)calendarView.findViewById(R.id.btn_show_body);

        layoutWater = (LinearLayout)calendarView.findViewById(R.id.layout_water);
        listMeal = (ListView)calendarView.findViewById(R.id.list_meal);
*/

/*
        MealAdapter mealAdapter;
        mealAdapter = new MealAdapter();
        listMeal.setAdapter(mealAdapter);

        mealAdapter.addItem(ContextCompat.getDrawable(getActivity(),R.drawable.ic_baseline_fastfood_24),"AM 10:00","오늘 아점은 이걸 먹었다고 한다.");
        mealAdapter.addItem(ContextCompat.getDrawable(getActivity(),R.drawable.ic_baseline_fastfood_24),"AM 14:00","오늘 간식은 이걸 먹었다고 한다.");
        mealAdapter.addItem(ContextCompat.getDrawable(getActivity(),R.drawable.ic_baseline_fastfood_24),"AM 18:00","오늘 저녁은 이걸 먹었다고 한다.");

        */
/*21.03.01 운동, 신체버튼 부분 리스트뷰 추가*//*

        listBody = (ListView)calendarView.findViewById(R.id.list_body);
        listExercise = (ListView)calendarView.findViewById(R.id.list_exercise);

        BodyAdapter bodyAdapter= new BodyAdapter();
        listBody.setAdapter(bodyAdapter);

        bodyAdapter.addItem(ContextCompat.getDrawable(getActivity(),R.drawable.ic_baseline_person_24),"50.0 kg", "15.0 kg", "30.0 kg");

        ExerciseAdapter exerciseAdapter = new ExerciseAdapter();
        listExercise.setAdapter(exerciseAdapter);

        exerciseAdapter.addItem("헬스","01:05:50","스쿼트 3set, 런지 5set, 풀업 3set");
        exerciseAdapter.addItem("요가","00:40:33","다운독, 스쿼트, 기타 등등");
*/




        materialCalendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {

               /* System.out.println(date.getDate());
                System.out.println("날짜 받아오기 성공");*/

                selectDate = date.getDate();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd.EEE");
                String formatDate = sdf.format(selectDate);
                selectFormatDate = formatDate;

                EventBus.getDefault().post(new DataEvent(selectFormatDate));

/*                txtSelect.setText(formatDate);

                calendarMain.setVisibility(View.INVISIBLE);
                calendarDetail.setVisibility(View.VISIBLE);*/

                Intent intent = new Intent(getActivity(),CalenderDetail.class);
                intent.putExtra("날짜", selectFormatDate);
                startActivity(intent);


            }
        });

/*
        btnMeal.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {

                layoutMeal.setVisibility(View.VISIBLE);
                layoutBody.setVisibility(View.INVISIBLE);

                btnMeal.setEnabled(false);
                btnBody.setEnabled(true);

            }
        });

        btnBody.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                layoutMeal.setVisibility(View.INVISIBLE);
                layoutBody.setVisibility(View.VISIBLE);

                btnMeal.setEnabled(true);
                btnBody.setEnabled(false);

            }
        });
*/

        return calendarView;
    }


}
