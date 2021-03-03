package com.example.tantan.ui.menu_setting;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.tantan.R;

public class ConnectPage extends AppCompatActivity {

    private static final int REQUEST_CODE = 0;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.connect_layout);

        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("스마트 미러 연결");

      //  ActionBar actionBar = getSupportActionBar();
      //  actionBar.setTitle("스마트 미러 연결");
      //  actionBar.setDisplayHomeAsUpEnabled(true);

        ListView listview;
        ConnectListViewAdapter adapter;

        adapter = new ConnectListViewAdapter() ;

        listview = (ListView) findViewById(R.id.connect_sm_list);
        listview.setAdapter(adapter);

        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.ic_baseline_phonelink_ring_24),
                "우리집 스마트미러");
        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.ic_baseline_phonelink_ring_24),
                "옆집 스마트미러");
        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.ic_baseline_phonelink_ring_24),
                "윗집 스마트미러");

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ConnectListViewItem item = (ConnectListViewItem) adapter.getItem(position);
                switch (item.getSm()) {
                    case "우리집 스마트미러":
                        Intent intent = new Intent(ConnectPage.this, PopUpButton1.class);
                        intent.putExtra("data", "연결이 완료되었습니다.");
                        startActivityForResult(intent, REQUEST_CODE);
                        break;
                    case "옆집 스마트미러":
                        break;
                    case "윗집 스마트미러":
                        break;
                }
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CODE:
                if (resultCode == RESULT_OK)
                    finish();
                break;
        }
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
}
