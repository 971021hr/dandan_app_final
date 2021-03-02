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

public class NoticeListViewAdapter1 extends BaseAdapter {

    private ArrayList<NoticeListViewItem1> listViewItemList = new ArrayList<NoticeListViewItem1>() ;

    public NoticeListViewAdapter1() {
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
            convertView = inflater.inflate(R.layout.notice_list1_layout, parent, false);
        }

        ImageView yellowImageView = (ImageView) convertView.findViewById(R.id.notice_list_icon);
        TextView titleTextView = (TextView) convertView.findViewById(R.id.notice_list_title);
        TextView dateTextView = (TextView) convertView.findViewById(R.id.notice_list_date);

        NoticeListViewItem1 listViewItem = listViewItemList.get(position);

        yellowImageView.setImageDrawable(listViewItem.getYellow());
        titleTextView.setText(listViewItem.getTitle());
        dateTextView.setText(listViewItem.getDate());

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

    public void addItem(Drawable yellow, String title, String date) {
        NoticeListViewItem1 item = new NoticeListViewItem1();

        item.setYellow(yellow);
        item.setTitle(title);
        item.setDate(date);

        listViewItemList.add(item);
    }
}
