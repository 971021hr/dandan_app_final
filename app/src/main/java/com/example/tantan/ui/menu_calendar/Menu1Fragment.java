package com.example.tantan.ui.menu_calendar;

import android.annotation.SuppressLint;
import android.content.Context;
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



    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        ViewGroup calendarView = (ViewGroup)inflater.inflate(R.layout.fragment_menu1, container, false);

        materialCalendarView = (MaterialCalendarView)calendarView.findViewById(R.id.calendarView);
        calendarMain = (LinearLayout)calendarView.findViewById(R.id.calendar_main);


        materialCalendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {

                selectDate = date.getDate();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String formatDate = sdf.format(selectDate);
                selectFormatDate = formatDate;

                Intent intent = new Intent(getActivity(),CalenderDetail.class);
                intent.putExtra("날짜", selectFormatDate);
                startActivity(intent);

            }
        });

        return calendarView;
    }

}