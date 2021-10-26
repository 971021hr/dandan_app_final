package com.example.tantan.ui.menu_calendar;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tantan.R;
import com.example.tantan.data.DeleteData;
import com.example.tantan.data.RunModifyResponse;
import com.example.tantan.data.SimpleResponse;
import com.example.tantan.data.runModifyData;
import com.example.tantan.network.RetrofitClient;
import com.example.tantan.network.ServiceApi;
import com.example.tantan.network.SharedPreference;

import java.text.SimpleDateFormat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Modify_run extends AppCompatActivity {
    private ServiceApi service;

    private TextView run_date;

    private Spinner spinner_hour, spinner_min;

    private EditText mainRunText;
    private EditText subRunText;

    private Button run_cancel;
    private Button run_ok;

    Integer position;
    String select_date = "";

    String userEmail="";

    Integer runID;
    private String strRunDate= "";
    private Integer runHour, runMin;
    private String str_mainRun = "";
    private String str_subRun = "";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_modify_run);

        service = RetrofitClient.getClient().create(ServiceApi.class);

        run_date = (TextView)findViewById(R.id.run_date);

        spinner_hour = (Spinner) findViewById(R.id.spinner_hour);
        spinner_min = (Spinner) findViewById(R.id.spinner_min);

        mainRunText = (EditText)findViewById(R.id.editMainTxt);
        subRunText = (EditText)findViewById(R.id.text_input);

        run_cancel = (Button) findViewById(R.id.btn_runCancel);
        run_ok = (Button)findViewById(R.id.btn_runOk);

        Intent intent = getIntent();
        position = intent.getIntExtra("selectNum",0);
        select_date = intent.getStringExtra("selectDate");

        getRunData(position,select_date);

        run_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runModifyData();
            }
        });


        run_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    private void runModifyData() {
        int d_runID = runID;
        int d_runHour = Integer.parseInt(spinner_hour.getSelectedItem().toString());
        int d_runMin = Integer.parseInt(spinner_min.getSelectedItem().toString());
        String d_runMain = mainRunText.getText().toString();
        String d_runSub = subRunText.getText().toString();

        startRunModify(new runModifyData(d_runID,d_runHour,d_runMin,d_runMain,d_runSub));
    }

    private void startRunModify(runModifyData data) {
        service.userDataRunModifyUpdate(data).enqueue(new Callback<SimpleResponse>() {
            @Override
            public void onResponse(Call<SimpleResponse> call, Response<SimpleResponse> response) {
                SimpleResponse result = response.body();

                if (result.getCode() == 200){
                    Toast.makeText(Modify_run.this, "수정 성공", Toast.LENGTH_SHORT).show();
                    finish();
                }

            }

            @Override
            public void onFailure(Call<SimpleResponse> call, Throwable t) {
                Toast.makeText(Modify_run.this, "수정 실패", Toast.LENGTH_SHORT).show();
               finish();

            }
        });
    }

    private void getRunData(Integer position, String select_date) {
        userEmail = SharedPreference.getAttribute(this,"userEmail");
        getStartRunData(new DeleteData(userEmail,select_date,position));

    }

    private void getStartRunData(DeleteData data) {
        service.userDataRunModify(data).enqueue(new Callback<RunModifyResponse>() {
            @Override
            public void onResponse(Call<RunModifyResponse> call, Response<RunModifyResponse> response) {
                RunModifyResponse result = response.body();

                if (result.getCode() == 200) {
                    Toast.makeText(Modify_run.this, "수정 불러오기 성공", Toast.LENGTH_SHORT).show();

                    runID = result.getRunID();
                    strRunDate = result.getRunDate();
                    runHour = result.getRunTime_h();
                    runMin = result.getRunTime_m();
                    str_mainRun = result.getRunMain();
                    str_subRun = result.getRunSub();

                    run_date.setText(strRunDate);

                    spinner_hour.setSelection(runHour);
//                    if (runHour == 0){
//                        spinner_hour.setSelection(0);
//                    }else if(runHour == 60){
//                        spinner_hour.setSelection(1);
//                    }else if (runHour == 120){
//                        spinner_hour.setSelection(2);
//                    }else if (runHour == 180){
//                        spinner_hour.setSelection(3);
//                    }else{
//                        spinner_hour.setSelection(4);
//                    }

                    spinner_min.setSelection(runMin);
                    mainRunText.setText(str_mainRun);
                    subRunText.setText(str_subRun);

                }

            }

            @Override
            public void onFailure(Call<RunModifyResponse> call, Throwable t) {

            }
        });
    }
}
