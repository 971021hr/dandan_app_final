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

public class ListViewAdapter1 extends BaseAdapter {

    private ArrayList<ListViewItem1> listViewItemList = new ArrayList<ListViewItem1>() ;

    public ListViewAdapter1() {
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
            convertView = inflater.inflate(R.layout.listview1_layout, parent, false);
        }

        ImageView iconImageView = (ImageView) convertView.findViewById(R.id.list_icon) ;
        TextView titleTextView = (TextView) convertView.findViewById(R.id.list_title1) ;
        ImageView pointImageView = (ImageView) convertView.findViewById(R.id.list_point1) ;

        ListViewItem1 listViewItem = listViewItemList.get(position);

        iconImageView.setImageDrawable(listViewItem.getIcon());
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

    public void addItem(Drawable icon, String title, Drawable point) {
        ListViewItem1 item = new ListViewItem1();

        item.setIcon(icon);
        item.setTitle(title);
        item.setPoint(point);

        listViewItemList.add(item);
    }
}
