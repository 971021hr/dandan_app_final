package com.example.tantan.ui.menu_community;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tantan.R;
import com.example.tantan.ui.menu_setting.HelpPage;

class VerticalViewHolder extends RecyclerView.ViewHolder {

    public ImageView icon;
    public TextView description;
    public TextView txtType;

    public VerticalViewHolder(View itemView) {
        super(itemView);

        icon = (ImageView) itemView.findViewById(R.id.vertical_icon);
        description = (TextView) itemView.findViewById(R.id.vertical_description);
        txtType = (TextView)itemView.findViewById(R.id.TxtTipType);

    }
}