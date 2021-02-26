package com.example.tantan.ui.menu_add;

import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.tantan.BottomNavigationHelper;
import com.example.tantan.R;

public class menu_addrun extends AppCompatActivity {
    private FragmentManager fragmentManager = getSupportFragmentManager();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_menu3_1);

//
//        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,menu1Fragment).commit();
//
//        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
//                R.id.calendar, R.id.community, R.id.add, R.id.stats, R.id.setting)
//                .build();
//         NavController navController = Navigation.findNavController(this, R.id.frame_layout);
//         NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
//         NavigationUI.setupWithNavController(navView, navController);

        Button run_cancel = (Button) findViewById(R.id.btn_runcancel);
        run_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
    public void onBackPressed(){super.onBackPressed();}
}




