package com.example.tantan.ui.menu_add;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.example.tantan.R;

public class Menu3Fragment extends Fragment {

    ImageButton btn_run, btn_meal, btn_body, btn_water;

    LinearLayout menu_layout;
    LinearLayout menu_layout_run;
    LinearLayout menu_layout_meal;
    LinearLayout menu_layout_body;
    LinearLayout menu_layout_water;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.fragment_menu3,container,false);

        btn_run = (ImageButton) rootView.findViewById(R.id.btn_run);
        btn_meal = (ImageButton) rootView.findViewById(R.id.btn_meal);
        btn_body = (ImageButton) rootView.findViewById(R.id.btn_body);
        btn_water = (ImageButton) rootView.findViewById(R.id.btn_water);

        menu_layout = (LinearLayout) rootView.findViewById(R.id.layout_menuadd);
        menu_layout_run = (LinearLayout) rootView.findViewById(R.id.layout_menuadd1);
        menu_layout_meal = (LinearLayout) rootView.findViewById(R.id.layout_menuadd2);
        menu_layout_body = (LinearLayout) rootView.findViewById(R.id.layout_menuadd3);
        menu_layout_water = (LinearLayout) rootView.findViewById(R.id.layout_menuadd4);

        btn_run.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                menu_layout.setVisibility(View.INVISIBLE);
                menu_layout_run.setVisibility(View.VISIBLE);
                menu_layout_meal.setVisibility(View.INVISIBLE);
                menu_layout_body.setVisibility(View.INVISIBLE);
                menu_layout_water.setVisibility(View.INVISIBLE);
            }
        });
        btn_meal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menu_layout.setVisibility(View.INVISIBLE);
                menu_layout_run.setVisibility(View.INVISIBLE);
                menu_layout_meal.setVisibility(View.VISIBLE);
                menu_layout_body.setVisibility(View.INVISIBLE);
                menu_layout_water.setVisibility(View.INVISIBLE);
            }
        });
        btn_body.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menu_layout.setVisibility(View.INVISIBLE);
                menu_layout_run.setVisibility(View.INVISIBLE);
                menu_layout_meal.setVisibility(View.INVISIBLE);
                menu_layout_body.setVisibility(View.VISIBLE);
                menu_layout_water.setVisibility(View.INVISIBLE);
            }
        });
        btn_water.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menu_layout.setVisibility(View.INVISIBLE);
                menu_layout_run.setVisibility(View.INVISIBLE);
                menu_layout_meal.setVisibility(View.INVISIBLE);
                menu_layout_body.setVisibility(View.INVISIBLE);
                menu_layout_water.setVisibility(View.VISIBLE);
            }
        });
        return rootView;
    }
}