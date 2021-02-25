package com.example.tantan.ui.menu_setting;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import com.example.tantan.R;

public class ListViewAdapter2 extends BaseAdapter {

    private ArrayList<ListViewItem2> listViewItemList = new ArrayList<ListViewItem2>() ;

    public ListViewAdapter2() {
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
            convertView = inflater.inflate(R.layout.listview2_layout, parent, false);
        }

        TextView titleTextView = (TextView) convertView.findViewById(R.id.list_title2) ;
        ImageView pointImageView = (ImageView) convertView.findViewById(R.id.list_point2) ;

        ListViewItem2 listViewItem = listViewItemList.get(position);

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

    public void addItem(String title, Drawable point) {
        ListViewItem2 item = new ListViewItem2();

        item.setTitle(title);
        item.setPoint(point);

        listViewItemList.add(item);
    }
}
