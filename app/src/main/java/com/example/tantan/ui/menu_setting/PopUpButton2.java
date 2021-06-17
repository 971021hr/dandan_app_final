package com.example.tantan.ui.menu_setting;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tantan.MainActivity;
import com.example.tantan.R;
import com.example.tantan.data.LeaveData;
import com.example.tantan.data.LoginData;
import com.example.tantan.data.LoginResponse;
import com.example.tantan.network.RetrofitClient;
import com.example.tantan.network.ServiceApi;
import com.example.tantan.network.SharedPreference;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PopUpButton2 extends Activity {

    TextView txtText;
    private static final String TAG = "TAG";
    private ServiceApi service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //타이틀바 없애기
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.popup_button2_layout);

        //UI 객체생성
        txtText = (TextView)findViewById(R.id.txtText);

        //데이터 가져오기
        Intent intent = getIntent();
        String data = intent.getStringExtra("data");
        txtText.setText(data);

        service = RetrofitClient.getClient().create(ServiceApi.class);

    }

    //확인 버튼 클릭
    public void mOnClose(View v){
        switch (v.getId()) {
            case R.id.leave_no:
                finish();
                break;

            case R.id.leave_yes:
                String str = SharedPreference.getAttribute(getApplicationContext(), "userEmail");
                LeaveCrew(new LeaveData(str));
                break;
        }
    }

    private void LeaveCrew(LeaveData str) {
        service.userLeave(str).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                LoginResponse result = response.body();
                Toast.makeText(PopUpButton2.this, result.getMessage(), Toast.LENGTH_SHORT).show();
                SharedPreference.setAttribute(PopUpButton2.this, "userEmail", "");
                startActivityForResult(new Intent(PopUpButton2.this, MainActivity.class), RESULT_OK);
                //Intent intent = new Intent();
                //setResult(RESULT_OK, intent);
                //finish();
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(PopUpButton2.this, "탈퇴 에러", Toast.LENGTH_SHORT).show();
                Log.e("탈퇴 에러 발생", t.getMessage());
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //바깥레이어 클릭시 안닫히게
        if(event.getAction()==MotionEvent.ACTION_OUTSIDE){
            return false;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        //안드로이드 백버튼 막기
        return;
    }

}
