package com.example.tantan;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import android.os.Bundle;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    // FrameLayout에 각 메뉴의 Fragment를 바꿔 줌
    private FragmentManager fragmentManager = getSupportFragmentManager();
    // 4개의 메뉴에 들어갈 Fragment들
    private Menu1Fragment menu1Fragment = new Menu1Fragment();
    private Menu2Fragment menu2Fragment = new Menu2Fragment();
    private Menu3Fragment menu3Fragment = new Menu3Fragment();
    private Menu4Fragment menu4Fragment = new Menu4Fragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        BottomNavigationHelper.disableShiftMode(navView);

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.menu_community, R.id.menu_add)
                .build();

       // NavController navController = Navigation.findNavController(this, R.id.frame_layout);
       // NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
       // NavigationUI.setupWithNavController(navView, navController);

        //FragmentTransaction transaction = fragmentManager.beginTransaction();
        //transaction.replace(R.id.frame_layout, menu1Fragment).commitAllowingStateLoss();

        navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                switch (item.getItemId()) {
                    case R.id.menu_calendar: {
                        transaction.replace(R.id.frame_layout, menu1Fragment).commitAllowingStateLoss();
                        break;
                    }
                    case R.id.menu_community: {
                        transaction.replace(R.id.frame_layout, menu2Fragment).commitAllowingStateLoss();
                        break;
                    }
                    case R.id.menu_add: {
                        transaction.replace(R.id.frame_layout, menu3Fragment).commitAllowingStateLoss();
                        break;
                    }
                    case R.id.menu_stats: {
                        transaction.replace(R.id.frame_layout, menu4Fragment).commitAllowingStateLoss();
                        break;
                    }
                    //case R.id.menu_setting: {
                  //      transaction.replace(R.id.frame_layout, menu5Fragment).commitAllowingStateLoss();
                  //      break;
                   // }
                }

                return true;
            }
        });
    }

}