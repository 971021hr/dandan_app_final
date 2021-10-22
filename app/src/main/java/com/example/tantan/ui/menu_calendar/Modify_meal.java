package com.example.tantan.ui.menu_calendar;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.tantan.R;
import com.example.tantan.data.DeleteData;
import com.example.tantan.data.MealData;
import com.example.tantan.data.MealModifyData;
import com.example.tantan.data.MealModifyResponse;
import com.example.tantan.data.MealResponse;
import com.example.tantan.data.RunModifyResponse;
import com.example.tantan.data.SimpleResponse;
import com.example.tantan.network.RetrofitClient;
import com.example.tantan.network.ServiceApi;
import com.example.tantan.network.SharedPreference;
import com.example.tantan.ui.menu_add.menu_addmeal;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static java.util.Base64.getDecoder;
import static java.util.Base64.getEncoder;

public class Modify_meal extends AppCompatActivity {
    private ServiceApi service;

    String userEmail="";

    private TextView tv_meal_date;
    private TextView meal_time, meal_ampm;
    private ImageView imgView;
    private EditText meal_memo;
    private Button btnCancel, btnOk;

    private String mCurrentPhotoPath;

    private static final int FROM_CAMERA = 0;
    private static final int FROM_ALBUM = 1;

    private Uri imgUri, photoURI, albumURI;

    Integer position;
    String select_date = "";

    Integer get_mealID;
    String get_mealDate = "";
    String get_mealTime = "";
    String get_mealPicture = "";
    String get_mealPicturePath = "";
    String get_mealMemo = "";

    byte[] meal_img;
    byte[] meal_img2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_modify_meal);
        service = RetrofitClient.getClient().create(ServiceApi.class);

        SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("HH:MM");
        SimpleDateFormat sdf3 = new SimpleDateFormat("HH:MM a");

        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        int mHour = c.get(Calendar.HOUR);
        int mMinute = c.get(Calendar.MINUTE);
        Calendar maxDate = Calendar.getInstance();

        tv_meal_date = (TextView) findViewById(R.id.tv_meal_date);
        meal_time = (TextView) findViewById(R.id.meal_time);
        meal_ampm = (TextView) findViewById(R.id.meal_ampm);
        meal_ampm.setVisibility(View.GONE);

        meal_memo = (EditText) findViewById(R.id.meal_memo);

        imgView = (ImageView)findViewById(R.id.meal_img);

        btnCancel = (Button)findViewById(R.id.btn_mealCancel);
        btnOk = (Button)findViewById(R.id.btn_meal);

        Intent intent = getIntent();
        position = intent.getIntExtra("selectNum",0);
        select_date = intent.getStringExtra("selectDate");

        getMealData(position,select_date);

        tv_meal_date.setText(mYear + "년 " + (mMonth+1) + "월 " + mDay + "일");
        tv_meal_date.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //showDialog(DIALOG_DATE); // 날짜 설정 다이얼로그 띄우기

                DatePickerDialog datePickerDialog = new DatePickerDialog(Modify_meal.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        tv_meal_date.setText(year + "년 " + (month+1) + "월 " + dayOfMonth + "일");
                        c.set(year, month, dayOfMonth);
                    }
                },mYear, mMonth, mDay);
                datePickerDialog.getDatePicker().setMaxDate(maxDate.getTime().getTime());
                datePickerDialog.show();
            }
        });

        meal_time.setText(sdf3.format(c.getTime()));
        meal_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(Modify_meal.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                        c.set(Calendar.HOUR_OF_DAY , hourOfDay);
                        c.set(Calendar.MINUTE , minute);
                        String am_pm = (hourOfDay < 12) ? "AM" : "PM";
                        if (am_pm == "PM") {
                            hourOfDay = hourOfDay - 12;
                            meal_ampm.setVisibility(View.VISIBLE);
                            meal_ampm.setText(am_pm);
                        } else {
                            meal_ampm.setVisibility(View.VISIBLE);
                            meal_ampm.setText(am_pm);
                        }
                        meal_time.setText(String.format("%02d:%02d", hourOfDay, minute));

                    }
                },mHour, mMinute, false);
                timePickerDialog.show();
            }
        });

        imgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PermissionListener permissionlistener = new PermissionListener() {

                    @Override
                    public void onPermissionGranted() {

                    }

                    @Override
                    public void onPermissionDenied(List<String> deniedPermissions) {

                    }
                };

                TedPermission.with(Modify_meal.this)
                        .setPermissionListener(permissionlistener)
                        .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                        .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
                        .check();

                makeDialog();
            }
        });

        btnOk.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                String mealDate = sdf.format(c.getTime());
                String mealTime = sdf2.format(c.getTime());

                String email = SharedPreference.getAttribute(getBaseContext(), "userEmail");
                String mealPhotoPath = mCurrentPhotoPath;
                String mealMemo = meal_memo.getText().toString();

                if (new File("/data/data/com.example.tantan/shared_prefs/com.example.tantan_preferences.xml").exists()) {
                    if (SharedPreference.getAttribute(getApplicationContext(), "userEmail").length() != 0) {
                        String filename[] = mCurrentPhotoPath.split("/");
                        BitmapDrawable drawable = (BitmapDrawable) imgView.getDrawable();
                        Bitmap bitmap = drawable.getBitmap();
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();

                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                        byte[] bodyPhotoByte = stream.toByteArray();
                        File bodyimage = new File("/data/data/com.example.tantan/files/" + filename[6]);

                        Base64.Encoder encoder = getEncoder();

                        byte[] encodedBytes = encoder.encode(bodyPhotoByte);
                        String bodyPhoto = encoder.encodeToString(encodedBytes);

                        try {
                            Log.v("알림", "사진 파일 저장");
                            FileOutputStream fos = new FileOutputStream(bodyimage);
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                        //해야됨
                        startMeal(new MealModifyData(get_mealID, mealDate, mealTime, bodyPhoto, mealPhotoPath, mealMemo));

                    } else {

                    }
                } else {

                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    onBackPressed();
            }
        });


    }

    private void getMealData(Integer position, String select_date) {
        userEmail = SharedPreference.getAttribute(this,"userEmail");
        getStartRunData(new DeleteData(userEmail,select_date,position));
    }

    private void startMeal(MealModifyData data) {
        service.userDataMealModifyUpdate(data).enqueue(new Callback<SimpleResponse>() {

            @Override
            public void onResponse(Call<SimpleResponse> call, Response<SimpleResponse> response) {
                SimpleResponse result = response.body();

                finish();
            }

            @Override
            public void onFailure(Call<SimpleResponse> call, Throwable t) {

            }
        });
    }

    private void getStartRunData(DeleteData data) {
        service.userDataMealModify(data).enqueue(new Callback<MealModifyResponse>() {
            @Override
            public void onResponse(Call<MealModifyResponse> call, Response<MealModifyResponse> response) {
                MealModifyResponse result = response.body();

                String Meal_Hour = "";
                String Meal_Min = "";

                if (result.getCode() == 200) {
                    Toast.makeText(Modify_meal.this, "수정 불러오기 성공", Toast.LENGTH_SHORT).show();

                    get_mealID = result.getMealID();
                    get_mealDate = result.getMealDate();
                    get_mealTime = result.getMealTime();
                    get_mealPicture = result.getMealPicture();
                    get_mealPicturePath = result.getMealPicturePath();
                    get_mealMemo = result.getMealMemo();

                    java.util.Base64.Decoder decoder = getDecoder();

                    String[] MealArray = get_mealTime.split(":");
                    Integer meal_hour = Integer.parseInt(MealArray[0]);
                    Integer meal_minute = Integer.parseInt(MealArray[1]);

                    mCurrentPhotoPath = get_mealPicturePath;

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

                    meal_img = decoder.decode(get_mealPicture);
                    meal_img2 = android.util.Base64.decode(meal_img, android.util.Base64.DEFAULT | android.util.Base64.NO_WRAP);
                    Bitmap bitmap = BitmapFactory.decodeByteArray(meal_img2, 0, meal_img2.length);
                    Drawable meal_drawable_image = new BitmapDrawable(bitmap);

                    tv_meal_date.setText(get_mealDate);
                    meal_time.setText(Meal_Time);
                    imgView.setImageBitmap(bitmap);
                    meal_memo.setText(get_mealMemo);








                }

            }

            @Override
            public void onFailure(Call<MealModifyResponse> call, Throwable t) {

            }
        });
    }

    private void makeDialog(){
        AlertDialog.Builder alt_bld = new AlertDialog.Builder(Modify_meal.this);
        alt_bld.setTitle("사진 업로드").setIcon(R.drawable.ic_baseline_add_circle_24).setCancelable(false)
                .setPositiveButton("사진촬영",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                // 사진 촬영 클릭
                                Log.v("알림", "다이얼로그 > 사진촬영 선택");
                                takePhoto();
                            }
                        }).setNeutralButton("앨범선택",

                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int id) {
                        Log.v("알림", "다이얼로그 > 앨범선택 선택");

                        //앨범에서 선택
                        selectAlbum();
                    }
                }).setNegativeButton("취소   ",

                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Log.v("알림", "다이얼로그 > 취소 선택");

                        // 취소 클릭. dialog 닫기.
                        dialog.cancel();
                    }
                });

        AlertDialog alert = alt_bld.create();
        alert.show();
    }

    public void takePhoto(){

        // 촬영 후 이미지 가져옴
        String state = Environment.getExternalStorageState();

        if(Environment.MEDIA_MOUNTED.equals(state)){

            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if(intent.resolveActivity(getPackageManager())!=null){
                File photoFile = null;
                try{
                    photoFile = createImageFile();
                }catch (IOException e){
                    e.printStackTrace();
                }

                if(photoFile!=null){
                    Uri providerURI = FileProvider.getUriForFile(this,"com.example.tantan.fileprovider",photoFile);
                    imgUri = providerURI;
                    intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, providerURI);
                    startActivityForResult(intent, FROM_CAMERA);
                }
            }

        }else{
            Log.v("알림", "저장공간에 접근 불가능");
            return;

        }
    }

    public File createImageFile() throws IOException{

        String imgFileName = System.currentTimeMillis() + ".jpg";
        File imageFile= null;
        File storageDir = new File(Environment.getExternalStorageDirectory() + "/Pictures", "tantan");

        if(!storageDir.exists()){
            Log.v("알림","storageDir 존재 x " + storageDir.toString());
            storageDir.mkdirs();
        }

        Log.v("알림","storageDir 존재함 " + storageDir.toString());
        imageFile = new File(storageDir,imgFileName);
        mCurrentPhotoPath = imageFile.getAbsolutePath();

        return imageFile;
    }

    public void selectAlbum(){

        //앨범에서 이미지 가져옴
        //앨범 열기
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);

        intent.setType("image/*");
        startActivityForResult(intent, FROM_ALBUM);
    }

    public void galleryAddPic(){
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(mCurrentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        sendBroadcast(mediaScanIntent);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap bm;
        if(resultCode != RESULT_OK){
            return;
        }
        switch (requestCode){
            case FROM_ALBUM : {
                //앨범에서 가져오기
                if(data.getData()!=null){
                    try{
                        File albumFile = null;
                        albumFile = createImageFile();

                        photoURI = data.getData();
                        albumURI = Uri.fromFile(albumFile);

                        galleryAddPic();
                        imgView.setImageURI(photoURI);
                        //cropImage();
                    }catch (Exception e){
                        e.printStackTrace();
                        Log.v("알림","앨범에서 가져오기 에러");
                    }
                }
                break;
            }
            case FROM_CAMERA : {
                //카메라 촬영
                try{
                    Log.v("알림", "FROM_CAMERA 처리");
                    galleryAddPic();
                    imgView.setImageURI(imgUri);
                }catch (Exception e){
                    e.printStackTrace();
                }
                break;
            }
        }
    }
}
