package com.example.tantan.ui.menu_setting;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tantan.R;
import com.example.tantan.data.ConnectData;
import com.example.tantan.data.NameData;
import com.example.tantan.data.NameResponse;
import com.example.tantan.network.RetrofitClient;
import com.example.tantan.network.ServiceApi;
import com.example.tantan.network.SharedPreference;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConnectPage extends AppCompatActivity {
    String strEmail = "";
    String strRandom = "";

    private ServiceApi service;
    private EditText editRandom;
    private Button btnOK;

    private static final int REQUEST_CODE = 0;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mirror_connect_layout);

        editRandom = (EditText) findViewById(R.id.connect_code);
        btnOK = (Button)findViewById(R.id.connect_btn);
        service = RetrofitClient.getClient().create(ServiceApi.class);

        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("스마트 미러 연결");

        strEmail = SharedPreference.getAttribute(this,"userEmail");
        strRandom = editRandom.getText().toString();

        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptConnect();
                finish();
            }
        });

    }

    private void attemptConnect(){
        editRandom.setError(null);

        strEmail = SharedPreference.getAttribute(this,"userEmail");
        strRandom = editRandom.getText().toString();

        View focusView = null;

        if (strRandom.isEmpty()) {
            editRandom.setError("랜덤값을 입력해주세요.");
            focusView = editRandom;
        }else {
            startConnect(new ConnectData(strEmail, strRandom));
        }
    }

    private void startConnect(ConnectData data){
        service.userDataConnect(data).enqueue(new Callback<NameResponse>() {
            @Override
            public void onResponse(Call<NameResponse> call, Response<NameResponse> response) {
                NameResponse result = response.body();

                if (result.getCode() == 200) {
                    Toast.makeText(ConnectPage.this, "스마트미러 연결 성공", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent();
                    setResult(RESULT_OK, intent);

                    finish();
                }else if(result.getCode() == 204){
                    Toast.makeText(ConnectPage.this, "랜덤값 잘 못 입력", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent();
                    setResult(RESULT_OK, intent);

                    finish();
                }


            }

            @Override
            public void onFailure(Call<NameResponse> call, Throwable t) {

                Log.e("연결 에러 발생", t.getMessage());

            }
        });
    }
}
