package com.example.tantan.ui.menu_setting;

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

public class HelpListViewAdapter extends BaseAdapter {

    private ArrayList<HelpListViewItem> listViewItemList = new ArrayList<HelpListViewItem>() ;

    public HelpListViewAdapter() {
    }

    @Override
    public int getCount() {
        return listViewItemList.size() ;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.help_list_layout, parent, false);
        }

        TextView numberTextView = (TextView) convertView.findViewById(R.id.list_number) ;
        TextView titleTextView = (TextView) convertView.findViewById(R.id.list_title) ;
        ImageView pointImageView = (ImageView) convertView.findViewById(R.id.list_point) ;

        HelpListViewItem listViewItem = listViewItemList.get(position);

        numberTextView.setText(listViewItem.getNumber());
        titleTextView.setText(listViewItem.getTitle());
        pointImageView.setImageDrawable(listViewItem.getPoint());

        return convertView;
    }

    @Override
    public long getItemId(int position) {
        return position ;
    }

    @Override
    public Object getItem(int position) {
        return listViewItemList.get(position) ;
    }

    public void addItem(String number, String title, Drawable point) {
        HelpListViewItem item = new HelpListViewItem();

        item.setNumber(number);
        item.setTitle(title);
        item.setPoint(point);

        listViewItemList.add(item);
    }
}
