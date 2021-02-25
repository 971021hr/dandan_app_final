package com.example.tantan.ui.menu_calendar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.tantan.R;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Menu1Fragment extends Fragment {

    //materialCalendarView에 이상있으면 import 잘 되어있는지 꼭 확인하기

    private static MaterialCalendarView materialCalendarView;
    Date selectDate;
    LinearLayout calendarMain;
    LinearLayout calendarDetail;
    LinearLayout layoutMeal;
    LinearLayout layoutBody;
    TextView txtSelect;
    Button btnMeal, btnBody;

    LinearLayout layoutWater;
    ListView listMeal;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        ViewGroup calendarView = (ViewGroup)inflater.inflate(R.layout.fragment_menu1, container, false);

        materialCalendarView = (MaterialCalendarView)calendarView.findViewById(R.id.calendarView);
        calendarMain = (LinearLayout)calendarView.findViewById(R.id.calendar_main);
        calendarDetail = (LinearLayout)calendarView.findViewById(R.id.calendar_detail);
        layoutMeal = (LinearLayout)calendarView.findViewById(R.id.layout_meal);
        layoutBody = (LinearLayout)calendarView.findViewById(R.id.layout_body);
        txtSelect = (TextView)calendarView.findViewById(R.id.txt_select);
        btnMeal = (Button)calendarView.findViewById(R.id.btn_show_meal);
        btnBody = (Button)calendarView.findViewById(R.id.btn_show_body);

        layoutWater = (LinearLayout)calendarView.findViewById(R.id.layout_water);
        listMeal = (ListView)calendarView.findViewById(R.id.list_meal);

        menuAdapter adapter1;
        adapter1 = new menuAdapter();
        listMeal.setAdapter(adapter1);

        adapter1.addItem(ContextCompat.getDrawable(getActivity(),R.drawable.ic_baseline_fastfood_24),"AM 10:00","오늘 아점은 이걸 먹었다고 한다.");
        adapter1.addItem(ContextCompat.getDrawable(getActivity(),R.drawable.ic_baseline_fastfood_24),"AM 14:00","오늘 간식은 이걸 먹었다고 한다.");
        adapter1.addItem(ContextCompat.getDrawable(getActivity(),R.drawable.ic_baseline_fastfood_24),"AM 18:00","오늘 저녁은 이걸 먹었다고 한다.");

        materialCalendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {

               /* System.out.println(date.getDate());
                System.out.println("날짜 받아오기 성공");*/

                selectDate = date.getDate();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd.EEE");
                String formatDate = sdf.format(selectDate);

                txtSelect.setText(formatDate);

                calendarMain.setVisibility(View.INVISIBLE);
                calendarDetail.setVisibility(View.VISIBLE);


            }
        });

        btnMeal.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                /*화면전환*/
                layoutMeal.setVisibility(View.VISIBLE);
                layoutBody.setVisibility(View.INVISIBLE);

            }
        });

        btnBody.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                layoutMeal.setVisibility(View.INVISIBLE);
                layoutBody.setVisibility(View.VISIBLE);

            }
        });

        return calendarView;
    }


}
