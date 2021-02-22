package com.example.tantan.ui.menu_community;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.tantan.R;

public class Menu2Fragment extends Fragment {

    Button btn_detail;
    LinearLayout community_layout_main;
    LinearLayout community_layout_detail;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.fragment_menu2,container,false);

        btn_detail = (Button) rootView.findViewById(R.id.btn_community_detail);
        community_layout_main = (LinearLayout) rootView.findViewById(R.id.menu_community_main);
        community_layout_detail = (LinearLayout) rootView.findViewById(R.id.menu_community_detail);

        btn_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                community_layout_main.setVisibility(View.INVISIBLE);
                community_layout_detail.setVisibility(View.VISIBLE);
            }
        });

        return rootView;
    }

    public void onBackPressed(){

    }
}
