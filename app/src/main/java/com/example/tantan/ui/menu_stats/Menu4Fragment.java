package com.example.tantan.ui.menu_stats;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

//MPAndroidChart import
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import com.example.tantan.R;

import java.util.ArrayList;

public class Menu4Fragment extends Fragment {

    Spinner s_spinner;
    String[] s_item;
    LineChart lineChart1;
    LineChart lineChart2;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu4, container, false);

        s_spinner = (Spinner) view.findViewById(R.id.s_spinner);
        s_item = new String[]{"일주일", "한 달", "일 년"};

        ArrayAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, s_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s_spinner.setAdapter(adapter);

        s_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        lineChart1 = view.findViewById(R.id.chart1);

        ArrayList<Entry> entry1 = new ArrayList<>();
        ArrayList<Entry> entry2 = new ArrayList<>();
        ArrayList<Entry> entry3 = new ArrayList<>();

        entry1.add(new Entry(1, 55));
        entry1.add(new Entry(2, 60));
        entry2.add(new Entry(1, 20));
        entry2.add(new Entry(2, 30));
        entry3.add(new Entry(1, 10));
        entry3.add(new Entry(2, 20));

        LineData chartData = new LineData();
        LineDataSet set1 = new LineDataSet(entry1, "몸무게");
        chartData.addDataSet(set1);

        LineDataSet set2 = new LineDataSet(entry2, "골격근");
        chartData.addDataSet(set2);

        LineDataSet set3 = new LineDataSet(entry3, "체지방");
        chartData.addDataSet(set3);

        lineChart1.setData(chartData);

        // lines and points
        set1.setColor(Color.BLUE);
        set1.setCircleColor(Color.BLUE);

        lineChart1.invalidate();

        //운동 그래프
        lineChart2 = view.findViewById(R.id.chart2);

        ArrayList<Entry> entry4 = new ArrayList<>();

        entry4.add(new Entry(1, 15));
        entry4.add(new Entry(2, 22));

        LineData chartData2 = new LineData();
        LineDataSet set4 = new LineDataSet(entry4, "운동");
        chartData2.addDataSet(set4);

        lineChart2.setData(chartData2);

        // lines and points
        //set1.setColor(Color.BLUE);
        //set1.setCircleColor(Color.BLUE);

        lineChart2.invalidate();

        return view;
    }
}