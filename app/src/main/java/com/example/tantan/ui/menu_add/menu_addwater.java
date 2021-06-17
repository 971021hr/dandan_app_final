package com.example.tantan.ui.menu_add;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tantan.R;
import com.example.tantan.data.PwdResponse;
import com.example.tantan.data.WaterData;
import com.example.tantan.data.WaterResponse;
import com.example.tantan.network.RetrofitClient;
import com.example.tantan.network.ServiceApi;
import com.example.tantan.network.SharedPreference;
import com.example.tantan.ui.menu_setting.PassWordPage;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class menu_addwater extends AppCompatActivity {

    private Spinner spinner_w;
    private TextView tv_water_date;

    String waterDate;
    Integer waterMl;

    private ServiceApi service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_menu3_4);


        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        Calendar maxDate = Calendar.getInstance();

        SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd");

        spinner_w = (Spinner) findViewById(R.id.spinner_w);
        tv_water_date = (TextView) findViewById(R.id.tv_water_date);

        tv_water_date.setText(mYear + "년 " + (mMonth+1) + "월 " + mDay + "일");
        tv_water_date.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //showDialog(DIALOG_DATE); // 날짜 설정 다이얼로그 띄우기

                DatePickerDialog datePickerDialog = new DatePickerDialog(menu_addwater.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        tv_water_date.setText(year + "년 " + (month+1) + "월 " + dayOfMonth + "일");
                        c.set(year, month, dayOfMonth);
                    }
                },mYear, mMonth, mDay);
                datePickerDialog.getDatePicker().setMaxDate(maxDate.getTime().getTime());
                datePickerDialog.show();
            }
        });

        service = RetrofitClient.getClient().create(ServiceApi.class);

        Button run_cancel = (Button) findViewById(R.id.btn_watercancel);
        run_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Button btn_water = (Button) findViewById(R.id.btn_water);
        btn_water.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = SharedPreference.getAttribute(getBaseContext() ,"userEmail");
                String waterDate = sdf.format(c.getTime());
                waterMl = Integer.parseInt(spinner_w.getSelectedItem().toString());
                Log.e("날짜", waterDate);

                if (new File("/data/data/com.example.tantan/shared_prefs/com.example.tantan_preferences.xml").exists()) {
                    if (SharedPreference.getAttribute(getApplicationContext(), "userEmail").length() != 0) {
                        startWater(new WaterData(email, waterDate, waterMl));

                    } else {

                    }
                } else {

                }

            }
        });
    }

    private void startWater(WaterData data) {
        service.userWater(data).enqueue(new Callback<WaterResponse>() {

            @Override
            public void onResponse(Call<WaterResponse> call, Response<WaterResponse> response) {
                WaterResponse result = response.body();
                Toast.makeText(menu_addwater.this, result.getMessage(), Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onFailure(Call<WaterResponse> call, Throwable t) {

            }
        });
    }


    public void onBackPressed(){super.onBackPressed();}
}