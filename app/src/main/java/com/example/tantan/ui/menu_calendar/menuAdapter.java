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

public class menuAdapter extends BaseAdapter {

    private ArrayList<menu1Item> menu1ItemArrayList = new ArrayList<menu1Item>();

    public menuAdapter(){

    }

    @Override
    public int getCount() {
        return menu1ItemArrayList.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final int pos = position;
        final Context context = parent.getContext();

        if (convertView == null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.menu1_detail,parent,false);
        }


        ImageView mealImg = (ImageView)convertView.findViewById(R.id.list_img);
        TextView mealTime = (TextView)convertView.findViewById(R.id.list_time);
        TextView mealMemo = (TextView)convertView.findViewById(R.id.list_memo);

        menu1Item menu1Item = menu1ItemArrayList.get(position);


        mealImg.setImageDrawable(menu1Item.getMealImg());
        mealTime.setText(menu1Item.getMealTime());
        mealMemo.setText(menu1Item.getMealMemo());

        return convertView;
    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    @Override
    public Object getItem(int position) {

        return menu1ItemArrayList.get(position);
    }

    public void addItem(Drawable mealIcon, String mealTime, String mealMemo){
        menu1Item item = new menu1Item();

        item.setMealImg(mealIcon);
        item.setMealTime(mealTime);
        item.setMealMemo(mealMemo);

        menu1ItemArrayList.add(item);
    }




}
