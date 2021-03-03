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

public class ConnectListViewAdapter extends BaseAdapter {

    private ArrayList<ConnectListViewItem> listViewItemList = new ArrayList<ConnectListViewItem>() ;

    public ConnectListViewAdapter() {
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
            convertView = inflater.inflate(R.layout.connect_list_layout, parent, false);
        }

        ImageView imageImageView = (ImageView) convertView.findViewById(R.id.c_list_image) ;
        TextView smTextView = (TextView) convertView.findViewById(R.id.c_list_sm) ;

        ConnectListViewItem listViewItem = listViewItemList.get(position);

        smTextView.setText(listViewItem.getSm());
        imageImageView.setImageDrawable(listViewItem.getImage());

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

    public void addItem(Drawable image, String sm) {
        ConnectListViewItem item = new ConnectListViewItem();

        item.setImage(image);
        item.setSm(sm);

        listViewItemList.add(item);
    }
}
