package com.example.tantan.ui.menu_stats;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.tantan.R;
import com.example.tantan.data.StatsDataWeek;
import com.example.tantan.data.StatsResponse;
import com.example.tantan.data.StatsRunMonthData;
import com.example.tantan.data.StatsRunWeekData;
import com.example.tantan.data.StatsWeekResponse;
import com.example.tantan.network.RetrofitClient;
import com.example.tantan.network.ServiceApi;
import com.example.tantan.network.SharedPreference;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//MPAndroidChart import

public class Menu4Fragment extends Fragment {

    Spinner s_spinner;
    String[] s_item;
    LineChart lineChart1;
    LineChart lineChart2;

    String email="";

    ServiceApi service;

    ArrayList<Entry> entries_w = new ArrayList<>();
    ArrayList<Entry> entries_m = new ArrayList<>();
    ArrayList<Entry> entries_f = new ArrayList<>();
    ArrayList<Entry> entries_run = new ArrayList<>();

    //일주일 시작 날짜와 끝날짜 전송
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    Date todayDate = new Date();
    String strEndDate = format.format(todayDate);

    Date weekDate = new Date(todayDate.getTime() + 86400000*-6);
    String strStartDateWeek = format.format(weekDate);

    //운동 한 달 -> 수정해야됨
    Date monthDate = new Date(todayDate.getTime() + 86400000*+30);
    String strStartDate_month = format.format(monthDate);

    Calendar c = Calendar.getInstance();
    String strStartDateMonth ="";

    Date yearDate = new Date();
    Calendar tDate = Calendar.getInstance();
    String strStartDateYear = "";

    int xAxisLen;
    String xAxisFormat;

    int intRun = 0;

/*
    Date monthDate = new Date(todayDate.getTime() + 86400000*-4*7);
    String strStartDateMonth = format.format(monthDate);
*/

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        c.setTime(todayDate);
        c.add(c.MONTH,-1);
        strStartDateMonth = format.format(c.getTime());

        tDate.setTime(yearDate);
        tDate.add(tDate.YEAR,-1);
        strStartDateYear = "";

        View view = inflater.inflate(R.layout.fragment_menu4, container, false);

        service = RetrofitClient.getClient().create(ServiceApi.class);
        email = SharedPreference.getAttribute(getContext() ,"userEmail");

        s_spinner = (Spinner) view.findViewById(R.id.s_spinner);
        s_item = new String[]{"일주일", "한 달", "일 년"};

        lineChart1 = view.findViewById(R.id.chart1);

        //운동 그래프
        lineChart2 = view.findViewById(R.id.chart2);

        ArrayAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, s_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s_spinner.setAdapter(adapter);

        s_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0:     //일주일
                        getWeekData();
                        getWeekRunData();
                        xAxisFormat = "일주일";
                        break;

                    case 1:     //한달
                        getMonthData();
                        getMonthRunData();
                        xAxisFormat = "한달";
                        break;

                    case 2:     //일년
                        getYearData();
                        getYearRunData();
                        xAxisFormat = "일년";
                        break;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                getWeekData();

            }
        });

        return view;
    }

    //수정
    private void getWeekRunData() {
        StartRunTimeWeek(new StatsRunWeekData(email, strStartDateWeek, strEndDate));
    }

    private void getMonthRunData() {
        StartRunTimeMonth(new StatsRunMonthData(email, strStartDateMonth, strEndDate));
    }

    private void getYearRunData() {
        StartRunTimeYear(new StatsRunWeekData(email, strStartDateYear, strEndDate));
    }

    private void StartRunTimeWeek(StatsRunWeekData data) {
        service.userStatsRunTime(data).enqueue(new Callback<StatsResponse>() {
            @Override
            public void onResponse(Call<StatsResponse> call, Response<StatsResponse> response) {
                StatsResponse result = response.body();
                int arrLen = result.getResult();

                int[] run_time = result.getRunTime();

                entries_run.clear();

                long[] run_long = new long[arrLen];
                String[] run_date = result.getRunDate();

                for (int i = 0; i<arrLen; i++){

                    Log.e("날짜를 string으로 : ", run_date[i]);
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    try {
                        Date date =  dateFormat.parse(run_date[i]);
                        long long_body = date.getTime();
                        Log.e("날짜를 long으로 : ", String.valueOf(long_body));
                        run_long[i] = long_body;
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }

                for(int i=0; i<arrLen; i++) {
                    entries_run.add(new Entry(run_long[i], run_time[i]));
                }

                long run_sub = run_long[arrLen-1] - run_long[0];
                Date date = new Date((long) (run_sub));

                //Specify the format you'd like
                SimpleDateFormat sdf = new SimpleDateFormat("dd", Locale.ENGLISH);
                Log.e("며칠? " , sdf.format(date));

                xAxisLen = Integer.parseInt(sdf.format(date));
                addChart2();
            }

            @Override
            public void onFailure(Call<StatsResponse> call, Throwable t) {

            }
        });
    }

    private void StartRunTimeMonth(StatsRunMonthData data) {
        service.userStatsRunTimeMonth(data).enqueue(new Callback<StatsResponse>() {
            @Override
            public void onResponse(Call<StatsResponse> call, Response<StatsResponse> response) {
                StatsResponse result = response.body();
                int arrLen = result.getResult();

                int[] run_time = result.getRunTime();

                entries_run.clear();

                long[] run_long = new long[arrLen];
                String[] run_date = result.getRunDate();

                for (int i = 0; i<arrLen; i++){

                    Log.e("날짜를 string으로 : ", run_date[i]);
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    try {
                        Date date =  dateFormat.parse(run_date[i]);
                        long long_body = date.getTime();
                        Log.e("날짜를 long으로 : ", String.valueOf(long_body));
                        run_long[i] = long_body;
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }

                for(int i=0; i<arrLen; i++) {
                    entries_run.add(new Entry(run_long[i], run_time[i]));
                }

                addChart2();

            }

            @Override
            public void onFailure(Call<StatsResponse> call, Throwable t) {

            }
        });
    }

    private void StartRunTimeYear(StatsRunWeekData data) {
        service.userStatsRunTimeYear(data).enqueue(new Callback<StatsResponse>() {
            @Override
            public void onResponse(Call<StatsResponse> call, Response<StatsResponse> response) {
                StatsResponse result = response.body();
                int arrLen = result.getResult();
                intRun = arrLen;

                int[] run_long = new int[arrLen];
                String[] run_date = result.getRunDate();

                for (int i = 0; i<arrLen; i++){

                    String run_month[] = run_date[i].split("-");
                    run_long[i] = Integer.parseInt(run_month[1]);

                }

                int[] run_time = result.getRunTime();

                entries_run.clear();

                for(int i=0; i<arrLen; i++) {
                    entries_run.add(new Entry(run_long[i], run_time[i]));
                }

                xAxisLen = run_long[arrLen-1] - run_long[0] + 1;
                addChart2();
            }

            @Override
            public void onFailure(Call<StatsResponse> call, Throwable t) {

            }
        });
    }

    public void getWeekData(){
        StartStatsWeek(new StatsDataWeek(email,strStartDateWeek,strEndDate));
    }

    public void getMonthData(){
        StartStatsMonth(new StatsDataWeek(email,strStartDateMonth,strEndDate));
    }

    public void getYearData(){
        StartStatsYear(new StatsDataWeek(email,strStartDateYear,strEndDate));
    }

    public void StartStatsWeek(StatsDataWeek data){
        service.userStatsWeek(data).enqueue(new Callback<StatsWeekResponse>() {
            @Override
            public void onResponse(Call<StatsWeekResponse> call, Response<StatsWeekResponse> response) {
                StatsWeekResponse result = response.body();
                int arrLen = result.getArrLen();

                long[] body_long = new long[arrLen];
                String[] body_date = result.getBodyDate();

                for (int i = 0; i<arrLen; i++){

                    Log.e("날짜를 string으로 : ", body_date[i]);
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    try {
                        Date date =  dateFormat.parse(body_date[i]);
                        long long_body = date.getTime();
                        Log.e("날짜를 long으로 : ", String.valueOf(long_body));
                        body_long[i] = long_body;
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                }

                Float[] body_weight =  result.getBodyWeight();
                Float[] body_muscle = result.getBodyMuscle();
                Float[] body_fat = result.getBodyFat();

                entries_w.clear();
                entries_m.clear();
                entries_f.clear();

                for(int i=0; i<arrLen; i++) {
                    entries_w.add(new Entry(body_long[i], body_weight[i]));
                    entries_m.add(new Entry(body_long[i], body_muscle[i]));
                    entries_f.add(new Entry(body_long[i], body_fat[i]));
                }

                long body_sub = body_long[arrLen-1] - body_long[0];
                Date date = new Date((long) (body_sub));

                //Specify the format you'd like
                SimpleDateFormat sdf = new SimpleDateFormat("dd", Locale.ENGLISH);
                Log.e("며칠? " , sdf.format(date));

                xAxisLen = Integer.parseInt(sdf.format(date));
                addChart();

            }

            @Override
            public void onFailure(Call<StatsWeekResponse> call, Throwable t) {

            }
        });
    }

    private void StartStatsMonth(StatsDataWeek data) {
        service.userStatsMonth(data).enqueue(new Callback<StatsWeekResponse>() {
            @Override
            public void onResponse(Call<StatsWeekResponse> call, Response<StatsWeekResponse> response) {
                StatsWeekResponse result = response.body();
                int arrLen = result.getArrLen();

                long[] body_long = new long[arrLen];
                String[] body_date = result.getBodyDate();

                for (int i = 0; i<arrLen; i++){

                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    try {
                        Date date =  dateFormat.parse(body_date[i]);
                        long long_body = date.getTime();
                        Log.e("날짜를 long으로 : ", String.valueOf(long_body));
                        body_long[i] = long_body;
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                }

                Float[] body_weight =  result.getBodyWeight();
                Float[] body_muscle = result.getBodyMuscle();
                Float[] body_fat = result.getBodyFat();

                entries_w.clear();
                entries_m.clear();
                entries_f.clear();

                for(int i=0; i<arrLen; i++) {
                    entries_w.add(new Entry(body_long[i], body_weight[i]));
                    entries_m.add(new Entry(body_long[i], body_muscle[i]));
                    entries_f.add(new Entry(body_long[i], body_fat[i]));
                }

                addChart();
            }

            @Override
            public void onFailure(Call<StatsWeekResponse> call, Throwable t) {

            }
        });
    }

    private void StartStatsYear(StatsDataWeek data) {
        service.userStatsYear(data).enqueue(new Callback<StatsWeekResponse>() {
            @Override
            public void onResponse(Call<StatsWeekResponse> call, Response<StatsWeekResponse> response) {
                StatsWeekResponse result = response.body();
                int arrLen = result.getArrLen();

                int[] body_long = new int[arrLen];
                String[] body_date = result.getBodyDate();

                for (int i = 0; i<arrLen; i++){

                    String body_month[] = body_date[i].split("-");
                    body_long[i] = Integer.parseInt(body_month[1]);

                }

                Float[] body_weight =  result.getBodyWeight();
                Float[] body_muscle = result.getBodyMuscle();
                Float[] body_fat = result.getBodyFat();

                entries_w.clear();
                entries_m.clear();
                entries_f.clear();

                for(int i=0; i<arrLen; i++) {
                    entries_w.add(new Entry(body_long[i], body_weight[i]));
                    entries_m.add(new Entry(body_long[i], body_muscle[i]));
                    entries_f.add(new Entry(body_long[i], body_fat[i]));
                }

                Log.e("몇달? " , String.valueOf(body_long[arrLen-1] - body_long[0] + 1));

                xAxisLen = body_long[arrLen-1] - body_long[0] + 1;
                addChart();

            }

            @Override
            public void onFailure(Call<StatsWeekResponse> call, Throwable t) {

            }
        });
    }

    private void addChart(){
        LineData chartData = new LineData();
        LineDataSet set1 = new LineDataSet(entries_w, "몸무게");
        LineDataSet set2 = new LineDataSet(entries_m, "근육");
        LineDataSet set3 = new LineDataSet(entries_f, "지방");
        chartData.addDataSet(set1);
        chartData.addDataSet(set2);
        chartData.addDataSet(set3);

        set1.setColor(Color.GREEN);
        set1.setCircleColor(Color.GREEN);
        set2.setColor(Color.RED);
        set2.setCircleColor(Color.RED);
        set3.setColor(Color.YELLOW);
        set3.setCircleColor(Color.YELLOW);

        XAxis xAxis = lineChart1.getXAxis();
        if (xAxisFormat.equals("일주일") | xAxisFormat.equals("한달")) {
            xAxis.setValueFormatter(new IndexAxisValueFormatter() {
                @Override
                public String getFormattedValue(float value) {
                    Double value2 = Double.parseDouble(String.valueOf(value));
                    long value3 = (long) (Math.ceil(value2 / 1000000) * 1000000);
                    Date date = new Date((long) (value3));

                    //Specify the format you'd like
                    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd", Locale.ENGLISH);
                    return sdf.format(date);
                }
            });
        } else if (xAxisFormat.equals("일년")) {
            xAxis.setValueFormatter(new IndexAxisValueFormatter() {
                @Override
                public String getFormattedValue(float value) {
                    String month_str = (int) value + "월";

                    return month_str;
                }
            });
        }
        xAxis.setLabelCount(xAxisLen, true);

        lineChart1.setData(chartData);
        lineChart1.invalidate();
    }

    private void addChart2() {
        LineData chartData = new LineData();
        LineDataSet set4 = new LineDataSet(entries_run, "운동 시간 (분 단위)");
        chartData.addDataSet(set4);
        set4.setColor(Color.BLACK);
        set4.setCircleColor(Color.BLACK);

        XAxis xAxis = lineChart2.getXAxis();
        if (xAxisFormat.equals("일주일") | xAxisFormat.equals("한달")) {
            xAxis.setValueFormatter(new IndexAxisValueFormatter() {
                @Override
                public String getFormattedValue(float value) {
                    Double value2 = Double.parseDouble(String.valueOf(value));
                    long value3 = (long) (Math.ceil(value2 / 1000000) * 1000000);
                    Date date = new Date((long) (value3));

                    //Specify the format you'd like
                    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd", Locale.ENGLISH);
                    return sdf.format(date);
                }
            });
        } else if (xAxisFormat.equals("일년")) {
            xAxis.setValueFormatter(new IndexAxisValueFormatter() {
                @Override
                public String getFormattedValue(float value) {
                    String month_str = (int) value + "월";

                    return month_str;
                }
            });
        }
        xAxis.setLabelCount(xAxisLen, true);

        lineChart2.setData(chartData);
        lineChart2.invalidate();
    }

    /*
    private void addChartRunYear() {
        LineData chartData = new LineData();
        LineDataSet set4 = new LineDataSet(entries_run, "운동 시간 (분 단위)");
        chartData.addDataSet(set4);
        set4.setColor(Color.BLACK);
        set4.setCircleColor(Color.BLACK);

        XAxis xAxisYear = lineChart2.getXAxis();
        xAxisYear.setValueFormatter(new IndexAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                Double value2 = Double.parseDouble(String.valueOf(value));
                long value3 = (long) (Math.ceil(value2 / 1000000) * 1000000);
                Date date = new Date((long) (value3));

                //Specify the format you'd like
                SimpleDateFormat sdf = new SimpleDateFormat("MM", Locale.ENGLISH);
                return sdf.format(date);
            }
        });
        xAxisYear.setLabelCount(intRun, true);

        lineChart2.setData(chartData);
        lineChart2.invalidate();
    }
     */

}