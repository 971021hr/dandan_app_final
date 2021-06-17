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
import android.widget.Toast;

import com.example.tantan.R;
import com.example.tantan.data.ArrayData;
import com.example.tantan.data.NoticeResponse;
import com.example.tantan.data.RunResponse;
import com.example.tantan.data.UserEmailData;
import com.example.tantan.network.RetrofitClient;
import com.example.tantan.network.ServiceApi;
import com.example.tantan.network.SharedPreference;
import com.example.tantan.ui.menu_calendar.CalenderDetail;

import java.io.File;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NoticePage extends AppCompatActivity {
    private ServiceApi service;
    String userEmail = "";

    NoticeListViewAdapter1 adapter;
    ListView listview;

    ListView listview2;
    NoticeListViewAdapter2 adapter2;

    private int j = 1;
    private int intentNum = 1;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notice_layout);

        service = RetrofitClient.getClient().create(ServiceApi.class);

        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("공지사항");


        listview = (ListView) findViewById(R.id.notice_list1);
        listview2 = (ListView) findViewById(R.id.notice_list2);

        adapter = new NoticeListViewAdapter1() ;
        adapter2 = new NoticeListViewAdapter2() ;

        getNoticePri();

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                NoticeListViewItem1 item = (NoticeListViewItem1) adapter.getItem(position);

                Intent intent1 = new Intent(NoticePage.this, Notice2Page.class);
                intent1.putExtra("selectPosition",position);
                intent1.putExtra("j",j);
                startActivity(intent1);

            }
        });


        listview2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                NoticeListViewItem2 item = (NoticeListViewItem2) adapter2.getItem(position);

                Intent intent1 = new Intent(NoticePage.this, Notice2Page.class);
                intent1.putExtra("selectPosition",position+intentNum);
                intent1.putExtra("j",position + 1);
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

    private void getNoticePri(){
        userEmail = SharedPreference.getAttribute(this,"userEmail");

        if (new File("/data/data/com.example.tantan/shared_prefs/com.example.tantan_preferences.xml").exists()) {
            if (userEmail.equals("")) {
                userEmail = "joy@joy";
                Log.e("notice ID >> ", userEmail);
                startNoticePri(new UserEmailData(userEmail));

            } else {

                Log.e("notice ID >> ", userEmail);
                startNoticePri(new UserEmailData(userEmail));
            }
        } else {

        }

    }


    private void startNoticePri(UserEmailData data){
        service.userDataNotice(data).enqueue(new Callback<NoticeResponse>(){
            @Override
            public void onResponse(Call<NoticeResponse> call, Response<NoticeResponse> response){

                NoticeResponse result = response.body();
                int arrLength = result.getArrLength();

                Log.e("성공?",Integer.toString(arrLength));

                if (result.getCode() == 200) {
                    arrLength = result.getArrLength();

                    int db_userAuto = result.getUserAuto();

                    String db_noticeTitle = result.getNoticeTitle();
                    String db_noticeDate = result.getNoticeDate();
                    String db_noticePri = result.getNoticePri();

                    Log.e("db 받아온 title", db_noticeTitle);

                    db_noticeTitle = db_noticeTitle.replace("/$","");
                    db_noticeDate = db_noticeDate.replace("/$","");
                    db_noticePri = db_noticePri.replace("/$","");

                    String[] titleArr = db_noticeTitle.split("/");
                    String[] dateArr = db_noticeDate.split("/");
                    String[] priArr = db_noticePri.split("/");


                    for (int i = 0; i <arrLength; i++){
                        if (priArr[i].equals("1")){
                            adapter.addItem(ContextCompat.getDrawable(NoticePage.this, R.drawable.ic_baseline_report_problem_24), titleArr[i], dateArr[i]);
                            intentNum += i;
                        }
                        else {
                            adapter2.addItem(Integer.toString(j),titleArr[i],dateArr[i]);
                            j++;
                        }

                    }

                    listview.setAdapter(adapter);
                    listview2.setAdapter(adapter2);


                    Intent intent = new Intent();
                    setResult(RESULT_OK, intent);

                }else if (result.getCode() == 202){
                    //공지사항이 없습니다.
                    adapter.addItem(ContextCompat.getDrawable(NoticePage.this, R.drawable.ic_baseline_report_problem_24), "공지사항 없음", "-");
                    listview.setAdapter(adapter);
                }

            }

            @Override
            public void onFailure(Call<NoticeResponse> call, Throwable t) {
                Log.e("실행","onFailure");
                adapter.addItem(ContextCompat.getDrawable(NoticePage.this, R.drawable.ic_baseline_report_problem_24),"-","-");
                listview.setAdapter(adapter);

            }
        });
    }



}