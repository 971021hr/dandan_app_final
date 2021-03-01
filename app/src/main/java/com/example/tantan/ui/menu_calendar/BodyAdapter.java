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

public class BodyAdapter extends BaseAdapter {

    private ArrayList<BodyItem> bodyItemArrayList = new ArrayList<BodyItem>();

    public BodyAdapter(){}

    @Override
    public int getCount() {
        return bodyItemArrayList.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final int pos = position;
        final Context context = parent.getContext();

        if (convertView == null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.menu1_detail_body,parent,false);
        }

        ImageView bodyImg = (ImageView)convertView.findViewById(R.id.bodyImg);
        TextView bodyWeight = (TextView)convertView.findViewById(R.id.bodyWeight);
        TextView bodyMuscle = (TextView)convertView.findViewById(R.id.bodyMuscle);
        TextView bodyFat = (TextView)convertView.findViewById(R.id.bodyFat);

        BodyItem bodyItem = bodyItemArrayList.get(position);

        return convertView;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return bodyItemArrayList.get(position);
    }

    public void addItem(Drawable img, String weight, String muscle,String fat){
        BodyItem item = new BodyItem();

        item.setBodyImg(img);
        item.setBodyWeight(weight);
        item.setBodyMuscle(muscle);
        item.setBodyFat(fat);

        bodyItemArrayList.add(item);
    }
}
