package com.example.tantan.ui.menu_calendar;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.tantan.R;

public class ListView_Inflater extends LinearLayout {
    
    private ImageView imageView;
    private TextView mealTime;
    private TextView mealMemo;
    
    public ListView_Inflater(Context context) {
        super(context);
        
        init(context);
    }
    
    private void init(Context context){
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.menu1_detail,this,true);
        
        imageView = (ImageView)findViewById(R.id.list_img);
        mealTime = (TextView)findViewById(R.id.list_time);
        mealMemo = (TextView)findViewById(R.id.list_memo);
        
    }
    
    public void setImageView(int resId){
        imageView.setImageResource(resId);
    }

    
}
