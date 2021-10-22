package com.example.tantan.ui.menu_calendar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tantan.DataEvent;
import com.example.tantan.R;
import com.example.tantan.data.ArrayData;
import com.example.tantan.data.ArrayResponse;
import com.example.tantan.data.CommunityResponse;
import com.example.tantan.data.UserEmailData;
import com.example.tantan.network.RetrofitClient;
import com.example.tantan.network.ServiceApi;
import com.example.tantan.network.SharedPreference;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Month;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Menu1Fragment extends Fragment {

    //materialCalendarView에 이상있으면 import 잘 되어있는지 꼭 확인하기
    private ServiceApi service;

    private static MaterialCalendarView materialCalendarView;

    String userEmail="";
    String currentMonth = "";

    Date selectDate;
    String selectFormatDate;
    LinearLayout calendarMain;
    Calendar calendar;

    int lenArr;
    String[] dotDate;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        service = RetrofitClient.getClient().create(ServiceApi.class);

        ViewGroup calendarView = (ViewGroup)inflater.inflate(R.layout.fragment_menu1, container, false);

        materialCalendarView = (MaterialCalendarView)calendarView.findViewById(R.id.calendarView);
        calendarMain = (LinearLayout)calendarView.findViewById(R.id.calendar_main);

        calendar = Calendar.getInstance();
        currentMonth = Integer.toString(calendar.get(Calendar.MONTH) + 1);
        Log.i("currentMonth >> ",currentMonth);
        getDotDate();

        materialCalendarView.setOnMonthChangedListener(new OnMonthChangedListener() {
            @Override
            public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {

                currentMonth = Integer.toString(date.getMonth() + 1);
                Log.i("currentMonth >> ",currentMonth);
                getDotDate();
            }
        });

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

    private void getDotDate() {
        userEmail = SharedPreference.getAttribute(getContext(),"userEmail");

        if (new File("/data/data/com.example.tantan/shared_prefs/com.example.tantan_preferences.xml").exists()) {
            if (userEmail.equals("")) {
                userEmail = "joy@joy";

                startTotalData(new ArrayData(userEmail,currentMonth));

            } else {
                startTotalData(new ArrayData(userEmail,currentMonth));
            }
        } else {

        }
    }

    private void startTotalData(ArrayData data) {
        service.userDataTotal(data).enqueue(new Callback<ArrayResponse>() {
            @Override
            public void onResponse(Call<ArrayResponse> call, Response<ArrayResponse> response) {
                ArrayResponse result = response.body();
                lenArr = result.getArrLength();

                if (result.getCode() == 200){
                    dotDate = result.getResult();
                    DateFormat d_dateFormat = new SimpleDateFormat("yyyy-MM-dd");

                    //dotDateArray
                    for (int i = 0; i<lenArr; i++){
                        String strDate = dotDate[i];
                        try {
                            Date f_date = d_dateFormat.parse(strDate);
                            CalendarDay day = CalendarDay.from(f_date);

                            int dot_color = Color.rgb(255,170,56);

                            materialCalendarView.addDecorator(new EventDecorator(dot_color, Collections.singleton(day)));

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }

                }

            }

            @Override
            public void onFailure(Call<ArrayResponse> call, Throwable t) {
                Log.i("msg >> ","해당 월에는 데이터가 없습니다.");
            }
        });
    }

}