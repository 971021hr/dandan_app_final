package com.example.tantan.ui.menu_calendar;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;

import com.example.tantan.R;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Menu1Fragment extends Fragment {

    //materialCalendarView에 이상있으면 import 잘 되어있는지 꼭 확인하기

    private static MaterialCalendarView materialCalendarView;
    Date selectDate;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        ViewGroup calendarView = (ViewGroup)inflater.inflate(R.layout.fragment_menu1, container, false);
        materialCalendarView = (MaterialCalendarView)calendarView.findViewById(R.id.calendarView);

        materialCalendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {

               /* System.out.println(date.getDate());
                System.out.println("날짜 받아오기 성공");*/

                selectDate = date.getDate();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd, EEE");
                String formatDate = sdf.format(selectDate);
                System.out.println(formatDate + "잘 됨*******************************");

            }
        });

        return calendarView;
    }


}
