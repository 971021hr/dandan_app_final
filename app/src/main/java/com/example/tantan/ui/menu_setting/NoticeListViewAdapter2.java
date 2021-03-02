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

import org.w3c.dom.Text;

import java.util.ArrayList;

public class NoticeListViewAdapter2 extends BaseAdapter {

    private ArrayList<NoticeListViewItem2> listViewItemList = new ArrayList<NoticeListViewItem2>() ;

    public NoticeListViewAdapter2() {
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
            convertView = inflater.inflate(R.layout.notice_list2_layout, parent, false);
        }

        TextView numberTextView = (TextView) convertView.findViewById(R.id.notice_list_number);
        TextView titleTextView = (TextView) convertView.findViewById(R.id.notice_list_title);
        TextView dateTextView = (TextView) convertView.findViewById(R.id.notice_list_date);

        NoticeListViewItem2 listViewItem = listViewItemList.get(position);

        numberTextView.setText(listViewItem.getNumber());
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

    public void addItem(String number, String title, String date) {
        NoticeListViewItem2 item = new NoticeListViewItem2();

        item.setNumber(number);
        item.setTitle(title);
        item.setDate(date);

        listViewItemList.add(item);
    }
}
