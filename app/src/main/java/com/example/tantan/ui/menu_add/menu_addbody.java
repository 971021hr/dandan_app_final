package com.example.tantan.ui.menu_add;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tantan.R;
import com.example.tantan.data.BodyData;
import com.example.tantan.data.BodyResponse;
import com.example.tantan.network.RetrofitClient;
import com.example.tantan.network.ServiceApi;
import com.example.tantan.network.SharedPreference;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64.Encoder;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static java.util.Base64.getDecoder;
import static java.util.Base64.getEncoder;

public class menu_addbody extends AppCompatActivity {

    private ImageView body_image;
    private Uri imgUri, photoURI, albumURI;
    private String mCurrentPhotoPath;
    private TextView tv_body_date;
    private EditText et_weight, et_muscle, et_fat;

    private static final int FROM_CAMERA = 0;
    private static final int FROM_ALBUM = 1;

    private ServiceApi service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_menu3_3);

        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        Calendar maxDate = Calendar.getInstance();

        tv_body_date = (TextView) findViewById(R.id.tv_body_date);
        et_weight = (EditText) findViewById(R.id.et_weight);
        et_muscle = (EditText) findViewById(R.id.et_muscle);
        et_fat = (EditText) findViewById(R.id.et_fat);

        service = RetrofitClient.getClient().create(ServiceApi.class);

        tv_body_date.setText(mYear + "년 " + (mMonth+1) + "월 " + mDay + "일");
        tv_body_date.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                DatePickerDialog datePickerDialog = new DatePickerDialog(menu_addbody.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        tv_body_date.setText(year + "년 " + (month+1) + "월 " + dayOfMonth + "일");
                        c.set(year, month, dayOfMonth);
                    }
                },mYear, mMonth, mDay);
                datePickerDialog.getDatePicker().setMaxDate(maxDate.getTime().getTime());
                datePickerDialog.show();
            }
        });

        body_image = (ImageView) findViewById(R.id.body_image);
        body_image.setOnClickListener(new View.OnClickListener() {
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
                TedPermission.with(menu_addbody.this)
                        .setPermissionListener(permissionlistener)
                        .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                        .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
                        .check();

                makeDialog();
            }
        });

        Button btn_body_send = (Button) findViewById(R.id.btn_body_send);
        btn_body_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd");
                String bodyDate = sdf.format(c.getTime());
                Float bodyWeight = Float.valueOf(et_weight.getText().toString());
                Float bodyMuscle = Float.valueOf(et_muscle.getText().toString());
                Float bodyFat = Float.valueOf(et_fat.getText().toString());
                String bodyPhotoPath = mCurrentPhotoPath;

                String filename[] = mCurrentPhotoPath.split("/");
                BitmapDrawable drawable = (BitmapDrawable) body_image.getDrawable();
                Bitmap bitmap = drawable.getBitmap();
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                Resources res= getResources();

                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                byte[] bodyPhotoByte = stream.toByteArray();
                File bodyimage = new File("/data/data/com.example.tantan/files/" + filename[6]);

                Encoder encoder = getEncoder();

                byte[] encodedBytes = encoder.encode(bodyPhotoByte);
                String bodyPhoto = encoder.encodeToString(encodedBytes);

                try {
                    Log.v("알림", "사진 파일 저장");
                    FileOutputStream fos = new FileOutputStream(bodyimage);
                    //bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

                if (new File("/data/data/com.example.tantan/shared_prefs/com.example.tantan_preferences.xml").exists()) {
                    if (SharedPreference.getAttribute(getApplicationContext(), "userEmail").length() != 0) {
                        String email = SharedPreference.getAttribute(getBaseContext() ,"userEmail");
                        startBody(new BodyData(email, bodyDate, bodyWeight, bodyMuscle, bodyFat, bodyPhoto, bodyPhotoPath));
                    } else {
                    }
                } else {
                }
            }
        });

        Button run_cancel = (Button) findViewById(R.id.btn_bodycancel);
        run_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    private void startBody(BodyData data) {
        service.userBody(data).enqueue(new Callback<BodyResponse>() {
            @Override
            public void onResponse(Call<BodyResponse> call, Response<BodyResponse> response) {
                BodyResponse result = response.body();

                finish();
            }

            @Override
            public void onFailure(Call<BodyResponse> call, Throwable t) {

            }
        });
    }

    private void makeDialog(){
        AlertDialog.Builder alt_bld = new AlertDialog.Builder(menu_addbody.this);
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

    //사진 찍기 클릭
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
                    Uri providerURI = FileProvider.getUriForFile(this,getPackageName() + ".fileprovider",photoFile);
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

    //앨범 선택 클릭
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

    public void onBackPressed(){super.onBackPressed();}

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

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
                        body_image.setImageURI(photoURI);
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
                    body_image.setImageURI(imgUri);
                }catch (Exception e){
                    e.printStackTrace();
                }
                break;
            }
        }
    }
}