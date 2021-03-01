package com.example.tantan.ui.menu_calendar;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tantan.R;

import java.util.ArrayList;

public class MealAdapter extends BaseAdapter {

    private ArrayList<MealItem> mealItemArrayList = new ArrayList<MealItem>();

    public MealAdapter(){

    }

    @Override
    public int getCount() {
        return mealItemArrayList.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final int pos = position;
        final Context context = parent.getContext();

        if (convertView == null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.menu1_detail_meal,parent,false);
        }


        ImageView mealImg = (ImageView)convertView.findViewById(R.id.list_img);
        TextView mealTime = (TextView)convertView.findViewById(R.id.list_time);
        TextView mealMemo = (TextView)convertView.findViewById(R.id.list_memo);

        MealItem MealItem = mealItemArrayList.get(position);


        mealImg.setImageDrawable(MealItem.getMealImg());
        mealTime.setText(MealItem.getMealTime());
        mealMemo.setText(MealItem.getMealMemo());

        return convertView;
    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    @Override
    public Object getItem(int position) {

        return mealItemArrayList.get(position);
    }

    public void addItem(Drawable mealIcon, String mealTime, String mealMemo){
        MealItem item = new MealItem();

        item.setMealImg(mealIcon);
        item.setMealTime(mealTime);
        item.setMealMemo(mealMemo);

        mealItemArrayList.add(item);
    }




}
