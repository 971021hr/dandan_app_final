package com.example.tantan.ui.menu_add;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.tantan.MainActivity;
import com.example.tantan.R;
import com.example.tantan.data.MealData;
import com.example.tantan.data.MealResponse;
import com.example.tantan.data.WaterData;
import com.example.tantan.data.WaterResponse;
import com.example.tantan.network.RetrofitClient;
import com.example.tantan.network.ServiceApi;
import com.example.tantan.network.SharedPreference;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.tedpark.tedpermission.rx1.TedRxPermission;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.Base64.Encoder;
import static java.util.Base64.getDecoder;
import static java.util.Base64.getEncoder;

public class menu_addmeal extends AppCompatActivity {

    private EditText meal_memo;
    private ImageView img1;
    private Uri imgUri, photoURI, albumURI;
    private String mCurrentPhotoPath;
    private TextView tv_meal_date, meal_time, meal_ampm;

    private static final int FROM_CAMERA = 0;
    private static final int FROM_ALBUM = 1;


    private ServiceApi service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_menu3_2);

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

        meal_memo = (EditText) findViewById(R.id.meal_memo);
        tv_meal_date = (TextView) findViewById(R.id.tv_meal_date);
        meal_time = (TextView) findViewById(R.id.meal_time);
        meal_ampm = (TextView) findViewById(R.id.meal_ampm);
        meal_ampm.setVisibility(View.GONE);

        service = RetrofitClient.getClient().create(ServiceApi.class);

        Button run_cancel = (Button) findViewById(R.id.btn_mealcancel);
        run_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        tv_meal_date.setText(mYear + "??? " + (mMonth+1) + "??? " + mDay + "???");
        tv_meal_date.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //showDialog(DIALOG_DATE); // ?????? ?????? ??????????????? ?????????

                DatePickerDialog datePickerDialog = new DatePickerDialog(menu_addmeal.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        tv_meal_date.setText(year + "??? " + (month+1) + "??? " + dayOfMonth + "???");
                        c.set(year, month, dayOfMonth);
                    }
                },mYear, mMonth, mDay);
                datePickerDialog.getDatePicker().setMaxDate(maxDate.getTime().getTime());
                datePickerDialog.show();
            }
        });

        //meal_time.setText(mHour + ":" + mMinute);
        meal_time.setText(sdf3.format(c.getTime()));
        meal_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(menu_addmeal.this, new TimePickerDialog.OnTimeSetListener() {
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

        img1 = (ImageView) findViewById(R.id.meal_img);
        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PermissionListener permissionlistener = new PermissionListener() {

                    @Override
                    public void onPermissionGranted() {

                    }

                    @Override
                    public void onPermissionDenied(List<String> deniedPermissions) {

                    }
                };

                TedPermission.with(menu_addmeal.this)
                        .setPermissionListener(permissionlistener)
                        .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                        .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
                        .check();

                makeDialog();
            }
        });

        Button btn_meal = (Button) findViewById(R.id.btn_meal);
        btn_meal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mealDate = sdf.format(c.getTime());
                String mealTime = sdf2.format(c.getTime());
                //String mealTime = meal_time.getText().toString();

                String email = SharedPreference.getAttribute(getBaseContext(), "userEmail");
                String mealPhotoPath = mCurrentPhotoPath;
                String mealMemo = meal_memo.getText().toString();

                if (new File("/data/data/com.example.tantan/shared_prefs/com.example.tantan_preferences.xml").exists()) {
                    if (SharedPreference.getAttribute(getApplicationContext(), "userEmail").length() != 0) {
                        String filename[] = mCurrentPhotoPath.split("/");
                        BitmapDrawable drawable = (BitmapDrawable) img1.getDrawable();
                        Bitmap bitmap = drawable.getBitmap();
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();

                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                        byte[] bodyPhotoByte = stream.toByteArray();
                        File bodyimage = new File("/data/data/com.example.tantan/files/" + filename[6]);

                        Encoder encoder = getEncoder();

                        byte[] encodedBytes = encoder.encode(bodyPhotoByte);
                        String bodyPhoto = encoder.encodeToString(encodedBytes);

                        try {
                            Log.v("??????", "?????? ?????? ??????");
                            FileOutputStream fos = new FileOutputStream(bodyimage);
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                        startMeal(new MealData(email, mealDate, mealTime, bodyPhoto, mealPhotoPath, mealMemo));

                    } else {

                    }
                } else {

                }

            }
        });
    }

    public void BitMapToString(Bitmap bitmap){
        ByteArrayOutputStream baos=new  ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100, baos);    //bitmap compress
        byte [] arr=baos.toByteArray();
        String image= Base64.encodeToString(arr, Base64.DEFAULT);
        String temp="";
        try{
            temp="&imagedevice="+ URLEncoder.encode(image,"utf-8");
        }catch (Exception e){
            Log.e("exception",e.toString());
        }
//        controlMysql addinfo=new controlMysql(temp);
//        controlMysql.active=true;
//        addinfo.start();
    }

        private void makeDialog(){
            AlertDialog.Builder alt_bld = new AlertDialog.Builder(menu_addmeal.this);
            alt_bld.setTitle("?????? ?????????").setIcon(R.drawable.ic_baseline_add_circle_24).setCancelable(false)
                    .setPositiveButton("????????????",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                    // ?????? ?????? ??????
                                    Log.v("??????", "??????????????? > ???????????? ??????");
                                    takePhoto();
                                }
                            }).setNeutralButton("????????????",

                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialogInterface, int id) {
                            Log.v("??????", "??????????????? > ???????????? ??????");

                            //???????????? ??????
                            selectAlbum();
                        }
                    }).setNegativeButton("??????   ",

                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Log.v("??????", "??????????????? > ?????? ??????");

                            // ?????? ??????. dialog ??????.
                            dialog.cancel();
                        }
                    });

            AlertDialog alert = alt_bld.create();
            alert.show();
        }

        //?????? ?????? ??????
        public void takePhoto(){

            // ?????? ??? ????????? ?????????
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
                Log.v("??????", "??????????????? ?????? ?????????");
                return;

            }
        }

        public File createImageFile() throws IOException{

                String imgFileName = System.currentTimeMillis() + ".jpg";
                File imageFile= null;
                File storageDir = new File(Environment.getExternalStorageDirectory() + "/Pictures", "tantan");

                if(!storageDir.exists()){
                    Log.v("??????","storageDir ?????? x " + storageDir.toString());
                    storageDir.mkdirs();
                }

                Log.v("??????","storageDir ????????? " + storageDir.toString());
                imageFile = new File(storageDir,imgFileName);
            mCurrentPhotoPath = imageFile.getAbsolutePath();

            return imageFile;
        }

        //?????? ?????? ??????
        public void selectAlbum(){

            //???????????? ????????? ?????????
            //?????? ??????
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
                    //???????????? ????????????
                    if(data.getData()!=null){
                        try{
                            File albumFile = null;
                            albumFile = createImageFile();

                            photoURI = data.getData();
                            albumURI = Uri.fromFile(albumFile);

                            galleryAddPic();
                            img1.setImageURI(photoURI);
                            //cropImage();
                        }catch (Exception e){
                            e.printStackTrace();
                            Log.v("??????","???????????? ???????????? ??????");
                        }
                    }
                    break;
                }
                case FROM_CAMERA : {
                    //????????? ??????
                    try{
                        Log.v("??????", "FROM_CAMERA ??????");
                        galleryAddPic();
                        img1.setImageURI(imgUri);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    break;
                }
            }
        }

    private void startMeal(MealData data) {
        service.userMeal(data).enqueue(new Callback<MealResponse>() {

            @Override
            public void onResponse(Call<MealResponse> call, Response<MealResponse> response) {
                MealResponse result = response.body();

                finish();
            }

            @Override
            public void onFailure(Call<MealResponse> call, Throwable t) {

            }
        });
    }

    public void onBackPressed(){super.onBackPressed();}
}