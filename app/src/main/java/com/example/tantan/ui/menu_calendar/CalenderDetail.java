package com.example.tantan.ui.menu_calendar;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.example.tantan.R;
import com.example.tantan.data.ArrayData;
import com.example.tantan.data.BodyResponse;
import com.example.tantan.data.MealResponse;
import com.example.tantan.data.RunResponse;
import com.example.tantan.data.WaterGetResponse;
import com.example.tantan.network.RetrofitClient;
import com.example.tantan.network.ServiceApi;
import com.example.tantan.network.SharedPreference;
import com.example.tantan.ui.menu_add.menu_addbody;
import com.example.tantan.ui.menu_add.menu_addmeal;
import com.example.tantan.ui.menu_add.menu_addrun;

import java.io.File;
import java.lang.reflect.Array;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static java.util.Base64.getDecoder;

public class CalenderDetail extends AppCompatActivity{

    TextView txtSelect;
    Button btnMeal, btnBody;
    LinearLayout layoutMeal;
    LinearLayout layoutBody;
    TextView txtWater;
    TextView txtNotData;
    SwipeMenuListView listMeal;
    SwipeMenuListView listBody;
    SwipeMenuListView listExercise;


    //AppAdapter mAdapter;
    List<ApplicationInfo> mAppList;

    String select_date = "";
    String userEmail="";
    String selectDate = "";

    private ServiceApi service;
    ProgressBar progressbar;

    byte[] meal_img;
    byte[] meal_img2;
    Bitmap bitmap;


    ExerciseAdapter exerciseAdapter = new ExerciseAdapter();
    BodyAdapter bodyAdapter= new BodyAdapter();
    MealAdapter mealAdapter= new MealAdapter();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu1_calender_detail);

        service = RetrofitClient.getClient().create(ServiceApi.class);

        txtSelect = (TextView)findViewById(R.id.txt_select);
        txtNotData = (TextView)findViewById(R.id.txt_notData);

        Intent intent = getIntent();
        select_date = intent.getStringExtra("날짜");
        txtSelect.setText(select_date);

        selectDate = select_date;

        btnMeal = (Button)findViewById(R.id.btn_show_meal);
        btnBody = (Button)findViewById(R.id.btn_show_body);

        layoutMeal = (LinearLayout)findViewById(R.id.layout_meal);
        layoutBody = (LinearLayout)findViewById(R.id.layout_body);

        txtWater = (TextView)findViewById(R.id.txt_water);
        listMeal = (SwipeMenuListView)findViewById(R.id.list_meal);
        listBody = (SwipeMenuListView)findViewById(R.id.list_body);
        listExercise = (SwipeMenuListView)findViewById(R.id.list_exercise);

        progressbar = (ProgressBar) findViewById(R.id.progressBar);

        //이거 사용

        //운동 정보 list 추가




        getWaterData();
        getRunData();


        /*exerciseAdapter.addItem("헬스","01:05:50"," ·스쿼트 3set \n ·런지 5set \n ·풀업 3set");
        exerciseAdapter.addItem("요가","00:40:33"," ·다운독 \n ·스쿼트 \n ·기타 등등");*/

        //listExercise.setAdapter(exerciseAdapter);


        //식단 list 추가 >> 나중에 DB 연결
        MealAdapter mealAdapter;
        mealAdapter = new MealAdapter();
        listMeal.setAdapter(mealAdapter);



        mAppList = getPackageManager().getInstalledApplications(0);



        SwipeMenuCreator creator = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {
                // create "open" item
                SwipeMenuItem openItem = new SwipeMenuItem(getApplicationContext());
                openItem.setBackground(new ColorDrawable(Color.rgb(0x9E,0x70,0xF2)));
                openItem.setWidth(dp2px(90));
                openItem.setTitle("수정");
                openItem.setTitleSize(18);
                openItem.setTitleColor(Color.WHITE);
                menu.addMenuItem(openItem);

                SwipeMenuItem deleteItem = new SwipeMenuItem(getApplicationContext());
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xC9,0xC9,0xCE)));
                deleteItem.setWidth(dp2px(90));
                deleteItem.setTitle("삭제");
                deleteItem.setTitleSize(18);
                deleteItem.setTitleColor(Color.WHITE);
                menu.addMenuItem(deleteItem);
            }
        };

        listMeal.setMenuCreator(creator);

        // step 2. listener item click event
        listMeal.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                ApplicationInfo item = mAppList.get(position);
                switch (index) {
                    case 0:
                        // open
                        open(item);
                        break;
                    case 1:
                        // delete
//                      delete(item);
                        mAppList.remove(position);
                        mealAdapter.notifyDataSetChanged();
                        break;
                }
                return false;
            }
        });

        // set SwipeListener
        listMeal.setOnSwipeListener(new SwipeMenuListView.OnSwipeListener() {

            @Override
            public void onSwipeStart(int position) {
                // swipe start
            }

            @Override
            public void onSwipeEnd(int position) {
                // swipe end
            }
        });

        mealAdapter.addItem(ContextCompat.getDrawable(this,R.drawable.ic_baseline_fastfood_24)," "," ");

        //신체 눈바디 등 정보 list 추가
        listBody.setAdapter(bodyAdapter);

        listBody.setMenuCreator(creator);

        // step 2. listener item click event
        listBody.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                ApplicationInfo item = mAppList.get(position);
                switch (index) {
                    case 0:
                        // open
                        open2(item);
                        break;
                    case 1:
                        // delete
//                      delete(item);
                        mAppList.remove(position);
                        bodyAdapter.notifyDataSetChanged();
                        break;
                }
                return false;
            }
        });

        // set SwipeListener
        listBody.setOnSwipeListener(new SwipeMenuListView.OnSwipeListener() {

            @Override
            public void onSwipeStart(int position) {
                // swipe start
            }

            @Override
            public void onSwipeEnd(int position) {
                // swipe end
            }
        });

        listExercise.setMenuCreator(creator);

        // step 2. listener item click event
        listExercise.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                ApplicationInfo item = mAppList.get(position);
                switch (index) {
                    case 0:
                        // open
                        open3(item);
                        break;
                    case 1:
                        // delete
//                      delete(item);
                        mAppList.remove(position);
                        exerciseAdapter.notifyDataSetChanged();
                        break;
                }
                return false;
            }
        });

        // set SwipeListener
        listExercise.setOnSwipeListener(new SwipeMenuListView.OnSwipeListener() {

            @Override
            public void onSwipeStart(int position) {
                // swipe start
            }

            @Override
            public void onSwipeEnd(int position) {
                // swipe end
            }
        });

        btnMeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*화면전환*/
                layoutMeal.setVisibility(View.VISIBLE);
                layoutBody.setVisibility(View.INVISIBLE);

                btnMeal.setEnabled(false);
                btnBody.setEnabled(true);

            }
        });

        btnBody.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layoutMeal.setVisibility(View.INVISIBLE);
                layoutBody.setVisibility(View.VISIBLE);

                btnMeal.setEnabled(true);
                btnBody.setEnabled(false);

            }
        });

        //선택한 날짜 txt 누르면 캘린더뷰로 이동
        txtSelect.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    public void onBackPressed(){

        super.onBackPressed();
    }

    //식단 수정
    private void open(ApplicationInfo item) {
        Intent update_intent = new Intent(CalenderDetail.this, menu_addmeal.class);
        startActivity(update_intent);
        finish();
    }

    //신체 수정
    private void open2(ApplicationInfo item) {
        Intent update_intent = new Intent(CalenderDetail.this, menu_addbody.class);
        startActivity(update_intent);
        finish();
    }

    //운동 수정
    private void open3(ApplicationInfo item) {
        Intent update_intent = new Intent(CalenderDetail.this, menu_addrun.class);
        startActivity(update_intent);
        finish();
    }

    //list 여백주기
    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }

    private void getWaterData(){
        userEmail = SharedPreference.getAttribute(this,"userEmail");
        selectDate = select_date;

        if (new File("/data/data/com.example.tantan/shared_prefs/com.example.tantan_preferences.xml").exists()) {
            if (userEmail.equals("")) {


            } else {
                startGetWaterDate(new ArrayData(userEmail, selectDate));
                startGetMealDate(new ArrayData(userEmail, selectDate));
            }
        } else {

        }

    }

    private void getRunData(){
        userEmail = SharedPreference.getAttribute(this,"userEmail");
        selectDate = select_date;

        if (new File("/data/data/com.example.tantan/shared_prefs/com.example.tantan_preferences.xml").exists()) {
            if (userEmail.equals("")) {
                txtNotData.setVisibility(View.GONE);

                exerciseAdapter.addItem("로그인 안한 사람","0","-");
                listExercise.setAdapter(exerciseAdapter);


            } else {
                startGetRunDate(new ArrayData(userEmail, selectDate));
                startGetBodyDate(new ArrayData(userEmail, selectDate));
            }
        } else {

        }

    }

    private void startGetMealDate(ArrayData data) {
        service.userDataMeal(data).enqueue(new Callback<MealResponse>() {
            @Override
            public void onResponse(Call<MealResponse> call, Response<MealResponse> response) {
                MealResponse result = response.body();

                String Meal_Hour = "";
                String Meal_Min = "";

                if (result.getCode() == 200) {
                    String[] meal_time = result.getMealTime();
                    String[] meal_picture = result.getMealPicture();
                    String[] meal_memo = result.getMealMemo();
                    java.util.Base64.Decoder decoder = getDecoder();
                    int length = result.getResult();
                    for(int i = 0 ; i < length ; i ++) {
                        String[] MealArray = meal_time[i].split(":");
                        Integer meal_hour = Integer.parseInt(MealArray[0]);
                        Integer meal_minute = Integer.parseInt(MealArray[1]);

                        //시간
                        if(meal_hour > 21) {
                            meal_hour -= 12;
                            Meal_Hour = String.valueOf(meal_hour);
                            if(meal_minute < 10) {
                                Meal_Min = "0" + String.valueOf(meal_minute) + " PM";
                            } else {
                                Meal_Min =String.valueOf(meal_minute) + " PM";
                            }
                        } else if (meal_hour > 12) {
                            meal_hour -= 12;
                            Meal_Hour = "0" + String.valueOf(meal_hour);
                            Log.e("오후 10시 출력", String.valueOf(Meal_Hour));
                            if(meal_minute < 10) {
                                Meal_Min = "0" + String.valueOf(meal_minute) + " PM";
                            } else {
                                Meal_Min =String.valueOf(meal_minute) + " PM";
                            }
                        } else {
                            if(meal_hour < 10) {
                                Meal_Hour = "0" + String.valueOf(meal_hour);
                                Log.e("오전 10시 출력", String.valueOf(Meal_Hour));
                            } else {
                                Meal_Hour = String.valueOf(meal_hour);
                            }
                            if(meal_minute < 10) {
                                Meal_Min = "0" + String.valueOf(meal_minute) + " AM";
                            } else {
                                Meal_Min = String.valueOf(meal_minute) + " AM";
                            }
                        }
                        String Meal_Time = Meal_Hour + ":" + Meal_Min;

                        //이미지
                        meal_img = decoder.decode(meal_picture[i]);
                        meal_img2 = Base64.decode(meal_img, Base64.DEFAULT | Base64.NO_WRAP);
                        Bitmap bitmap = BitmapFactory.decodeByteArray(meal_img2, 0, meal_img2.length);
                        Log.e("사진", String.valueOf(bitmap));
                        Drawable meal_drawable_image = new BitmapDrawable(bitmap);

                        //메모
                        String memo = meal_memo[i];
                        
                        //어뎁터 보내기
                        mealAdapter.addItem(meal_drawable_image, Meal_Time, memo);
                        mealAdapter.notifyDataSetChanged();
                        listMeal.setAdapter(mealAdapter);
                    }

                    Intent intent = new Intent();
                    setResult(RESULT_OK, intent);
                } else {
                    mealAdapter.addItem(ContextCompat.getDrawable(CalenderDetail.this,R.drawable.ic_baseline_person_24),"식사 시간", "식단 메모");
                    mealAdapter.notifyDataSetChanged();
                    listMeal.setAdapter(mealAdapter);
                }
            }

            @Override
            public void onFailure(Call<MealResponse> call, Throwable t) {

            }
        });
    }


    private void startGetBodyDate(ArrayData data) {
        service.userDataBody(data).enqueue(new Callback<BodyResponse>(){
            @Override
            public void onResponse(Call<BodyResponse> call, Response<BodyResponse> response){
                BodyResponse result = response.body();

                if (result.getCode() == 200) {
                    String body_weight = result.getBodyweight();
                    String body_muscle = result.getBodymuscle();
                    String body_fat = result.getBodyfat();
                    String body_photo = result.getBodyphoto();

                    java.util.Base64.Decoder decoder = getDecoder();

                    byte[] body_img = decoder.decode(body_photo);
                    byte[] body_img2 = Base64.decode(body_img, Base64.DEFAULT | Base64.NO_WRAP);

                    Bitmap bitmap = BitmapFactory.decodeByteArray(body_img2, 0, body_img2.length);
                    Log.e("사진", String.valueOf(bitmap));
                    Drawable body_drawable_image = new BitmapDrawable(bitmap);

                    bodyAdapter.addItem(body_drawable_image,"몸무게 : " + body_weight + " kg", "골격근 : " + body_muscle + " kg", "체지방 : " + body_fat + " kg");
                    bodyAdapter.notifyDataSetChanged();
                    listBody.setAdapter(bodyAdapter);

                    Intent intent = new Intent();
                    setResult(RESULT_OK, intent);
                } else {
                    bodyAdapter.addItem(ContextCompat.getDrawable(CalenderDetail.this,R.drawable.ic_baseline_person_24),"몸무게 : 00.0 kg", "골격근 : 00.0 kg", "체지방 : 00.0 kg");
                    bodyAdapter.notifyDataSetChanged();
                    listBody.setAdapter(bodyAdapter);
                }

            }

            @Override
            public void onFailure(Call<BodyResponse> call, Throwable t) {

            }
        });
    }


    private void startGetWaterDate(ArrayData data){
        service.userDataWater(data).enqueue(new Callback<WaterGetResponse>(){
            @Override
            public void onResponse(Call<WaterGetResponse> call, Response<WaterGetResponse> response){
                WaterGetResponse result = response.body();

                if (result.getCode() == 200) {
                    String waterData = result.getResult();

                    float r = Float.valueOf(waterData) / 20;
                    progressbar.setProgress((int)r);

                    txtWater.setText(waterData + " ml");

                    Intent intent = new Intent();
                    setResult(RESULT_OK, intent);
                }
            }

            @Override
            public void onFailure(Call<WaterGetResponse> call, Throwable t) {

            }
        });
    }

    private void startGetRunDate(ArrayData data){
        service.userDataRun(data).enqueue(new Callback<RunResponse>(){
            @Override
            public void onResponse(Call<RunResponse> call, Response<RunResponse> response){
                RunResponse result = response.body();
                Log.e("되나? : ", result.getMessage());

                if (result.getCode() == 200) {
                    int arrLen = result.getArrLength();

                    String runMain = result.getRunMain();
                    int[] runTime = result.getRunTime();
                    String runSub = result.getRunSub();

                    runMain = runMain.replace("/$", "");
                    runSub = runSub.replace("/$", "");
                    runSub = runSub.replace(",","\n");

                    String[] mainArray = runMain.split("/");
                    String[] subArray = runSub.split("/");

                    for (int i = 0; i <arrLen; i++){
                        txtNotData.setVisibility(View.GONE);
                        String run_time_total = String.valueOf(runTime[i]);
                        exerciseAdapter.addItem(mainArray[i], run_time_total+"분",subArray[i]);
                    }
                    listExercise.setAdapter(exerciseAdapter);

                    Intent intent = new Intent();
                    setResult(RESULT_OK, intent);

                }else if (result.getCode() == 202){
                    txtNotData.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<RunResponse> call, Throwable t) {
                txtNotData.setVisibility(View.VISIBLE);
                exerciseAdapter.addItem("디비가져옴 실패","0","-");
                listExercise.setAdapter(exerciseAdapter);

            }
        });
    }

}