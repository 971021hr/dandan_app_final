package com.example.tantan.ui.menu_add;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.text.method.KeyListener;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.tantan.BottomNavigationHelper;
import com.example.tantan.R;
import com.example.tantan.data.AddRunData;
import com.example.tantan.data.JoinData;
import com.example.tantan.data.JoinResponse;
import com.example.tantan.data.SimpleResponse;
import com.example.tantan.network.RetrofitClient;
import com.example.tantan.network.ServiceApi;
import com.example.tantan.network.SharedPreference;
import com.example.tantan.ui.menu_setting.JoinPage;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class menu_addrun extends AppCompatActivity {
    private FragmentManager fragmentManager = getSupportFragmentManager();

    private ServiceApi service;

    private TextView run_time, run_date;

    private Spinner spinner_mainRun;

    //스피너
    private Spinner spinner_hour, spinner_min;
    Integer S_hours, S_minutes, hour_change_min;

    private EditText mainRunText;
    private EditText subRunText;

    private TextView subTxt;

    private Button run_cancel;
    private Button run_ok;
    private Button btn_sub;

    private String str_mainRun = "";
    private String db_str_mainRun = "";

    private String str_subRun = "";
    private String db_str_subRun = "";

    boolean cancel;
    View focusView;

    private String strRunDate= "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_menu3_1);

        service = RetrofitClient.getClient().create(ServiceApi.class);

        spinner_mainRun = (Spinner)findViewById(R.id.ex_sort);

        mainRunText = (EditText)findViewById(R.id.editMainTxt);
        subRunText = (EditText)findViewById(R.id.text_input);

        subTxt = (TextView)findViewById(R.id.text_output);

        run_cancel = (Button) findViewById(R.id.btn_runcancel);
        run_ok = (Button)findViewById(R.id.btn_runOk);
        btn_sub = (Button)findViewById(R.id.btn_ex);
        spinner_hour = (Spinner) findViewById(R.id.spinner_hour);
        spinner_min = (Spinner) findViewById(R.id.spinner_min);

        str_mainRun = spinner_mainRun.getSelectedItem().toString();

        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        //int mHour = c.get(Calendar.HOUR);
        //int mMinute = c.get(Calendar.MINUTE);
        Calendar maxDate = Calendar.getInstance();

        SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("HH:MM");


        run_date = (TextView)findViewById(R.id.run_date);
        run_date.setText(mYear + "년 " + (mMonth+1) + "월 " + mDay + "일");
        run_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(menu_addrun.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        run_date.setText(year + "년 " + (month+1) + "월 " + dayOfMonth + "일");
                        c.set(year, month, dayOfMonth);
                    }
                },mYear, mMonth, mDay);
                datePickerDialog.getDatePicker().setMaxDate(maxDate.getTime().getTime());
                datePickerDialog.show();
            }
        });

        spinner_mainRun.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l){
                str_mainRun = spinner_mainRun.getSelectedItem().toString();

                if(str_mainRun.equals("기타")){
                    mainRunText.setVisibility(View.VISIBLE);
                }else {
                    mainRunText.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        run_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        run_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strRunDate = sdf.format(c.getTime());
                attemptRun();

            }
        });

        btn_sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str_subRun += "· " + subRunText.getText().toString() + "\n";
                db_str_subRun += subRunText.getText().toString() + ",";
                subTxt.setText(str_subRun);
                subRunText.setText("");
            }
        });
    }

    private void attemptRun() {
        mainRunText.setError(null);

        String userEmail = SharedPreference.getAttribute(getBaseContext() ,"userEmail");

        //runTime
        S_hours = Integer.parseInt(spinner_hour.getSelectedItem().toString());
        S_minutes = Integer.parseInt(spinner_min.getSelectedItem().toString());
        hour_change_min = S_hours * 60;

        String runDate = strRunDate;
        //String waterDate = sdf.format(c.getTime());

       // String runTime = run_time.getText().toString();

        str_mainRun = spinner_mainRun.getSelectedItem().toString();

        if(str_mainRun.equals("기타")){
            db_str_mainRun = mainRunText.getText().toString();
        }else {
            db_str_mainRun = spinner_mainRun.getSelectedItem().toString();
        }

        String runMain = db_str_mainRun;

        //runSub = runSub.replace("/$", "");

        String runSub = db_str_subRun.replace(",$","");

       // Log.e("확인 : ", userEmail + "," + runDate + "," + runTime + "," + runMain + "," + runSub );

        cancel = false;
        focusView = null;

        // 이름의 유효성 검사
        if (runMain.isEmpty()) {
            mainRunText.setError("운동을 입력해주세요.");
            focusView = mainRunText;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            addRun(new AddRunData(userEmail, runDate, hour_change_min, S_minutes,runMain,runSub));
        }
    }

    private void addRun(AddRunData data) {
        service.userAddRun(data).enqueue(new Callback<SimpleResponse>() {
            @Override
            public void onResponse(Call<SimpleResponse> call, Response<SimpleResponse> response) {
                SimpleResponse result = response.body();


                if (result.getCode() == 200) {
                    finish();
                }else if (result.getCode() == 202){
                    finish();
                }
            }
            @Override
            public void onFailure(Call<SimpleResponse> call, Throwable t) {

                Log.e("운동 추가 에러 발생", t.getMessage());

            }

        });
    }

    public void onBackPressed(){
        super.onBackPressed();
    }
}



