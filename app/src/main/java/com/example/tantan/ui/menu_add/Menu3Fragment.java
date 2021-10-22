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

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.fragment_menu3,container,false);

        btn_run = (ImageButton) rootView.findViewById(R.id.btn_run);
        btn_meal = (ImageButton) rootView.findViewById(R.id.btn_meal);
        btn_body = (ImageButton) rootView.findViewById(R.id.btn_body);
        btn_water = (ImageButton) rootView.findViewById(R.id.btn_water);

        btn_run.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            Intent intent = new Intent(getActivity(), menu_addrun.class);
            startActivity(intent);

            }
        });
        btn_meal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), menu_addmeal.class);
                startActivity(intent);

            }
        });
        btn_body.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), menu_addbody.class);
                startActivity(intent);

            }
        });
        btn_water.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), menu_addwater.class);
                startActivity(intent);

            }
        });
        return rootView;
    }
}