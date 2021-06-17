package com.example.tantan.ui.menu_setting;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.tantan.R;
import com.example.tantan.data.HelpResponse;
import com.example.tantan.data.UserEmailData;
import com.example.tantan.network.RetrofitClient;
import com.example.tantan.network.ServiceApi;
import com.example.tantan.network.SharedPreference;

import java.io.File;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HelpPage extends AppCompatActivity {
    private ServiceApi service;

    ListView listview;
    HelpListViewAdapter adapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.help_layout);

        service = RetrofitClient.getClient().create(ServiceApi.class);

        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("도움말");

        listview = (ListView) findViewById(R.id.help_list);

        adapter = new HelpListViewAdapter() ;

        getHelpTitle();

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                HelpListViewItem item = (HelpListViewItem) adapter.getItem(position);

                Intent intent1 = new Intent(HelpPage.this, HelpPage2.class);
                intent1.putExtra("helpNum", position);
                Log.e("helpNum",Integer.toString(position));

                startActivity(intent1);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{ //toolbar의 back키 눌렀을 때 동작
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void getHelpTitle(){
        String userEmail = SharedPreference.getAttribute(this,"userEmail");

        if (new File("/data/data/com.example.tantan/shared_prefs/com.example.tantan_preferences.xml").exists()) {
            if (userEmail.equals("")) {
                userEmail = "joy@joy";
                Log.e("notice ID >> ", userEmail);
                startHelpTitle(new UserEmailData(userEmail));

            } else {
                Log.e("notice ID >> ", userEmail);
                startHelpTitle(new UserEmailData(userEmail));
            }
        } else {

        }

    }

    private void startHelpTitle(UserEmailData data){
        service.userDataHelp(data).enqueue(new Callback<HelpResponse>(){

            @Override
            public void onResponse(Call<HelpResponse> call, Response<HelpResponse> response) {
                HelpResponse result = response.body();

                Log.e("성공?",result.getMessage());

                if (result.getCode() == 200){
                    int d_arr = result.getArrLength();

                    String d_title = result.getHelpTitle();
                    d_title = d_title.replace("/$","");

                    String[] titleArr = d_title.split("/");

                    for (int i = 0; i < d_arr; i++){

                        adapter.addItem(Integer.toString(i+1), titleArr[i], ContextCompat.getDrawable(HelpPage.this, R.drawable.ic_baseline_navigate_next_24));

                    }

                    listview.setAdapter(adapter);

                }

            }

            @Override
            public void onFailure(Call<HelpResponse> call, Throwable t) {
                Log.e("실패","-");

            }
        });
    }

}