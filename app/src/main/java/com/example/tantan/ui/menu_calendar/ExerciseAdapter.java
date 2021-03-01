package com.example.tantan.ui.menu_calendar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.tantan.R;

import java.util.ArrayList;

public class ExerciseAdapter extends BaseAdapter {
    private ArrayList<ExerciseItem> exerciseItemArrayList = new ArrayList<ExerciseItem>();

    public ExerciseAdapter(){}

    @Override
    public int getCount() {
        return exerciseItemArrayList.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final int pos = position;
        final Context context = parent.getContext();

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.menu1_detail_exercise,parent,false);
        }

        TextView exerciseTitle = (TextView)convertView.findViewById(R.id.exerciseTitle);
        TextView exerciseTime = (TextView)convertView.findViewById(R.id.exerciseTime);
        TextView exerciseMemo = (TextView)convertView.findViewById(R.id.exerciseMemo);

        ExerciseItem exerciseItem = exerciseItemArrayList.get(position);

        exerciseTitle.setText(exerciseItem.getExerciseTitle());
        exerciseTime.setText(exerciseItem.getExerciseTime());
        exerciseMemo.setText(exerciseItem.getExerciseMemo());


        return convertView;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return exerciseItemArrayList.get(position);
    }

    public void addItem(String exerciseTitle, String exerciseTime, String exerciseMemo){
        ExerciseItem item = new ExerciseItem();

        item.setExerciseTitle(exerciseTitle);
        item.setExerciseTime(exerciseTime);
        item.setExerciseMemo(exerciseMemo);

        exerciseItemArrayList.add(item);
    }
}
