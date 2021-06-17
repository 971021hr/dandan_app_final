package com.example.tantan.ui.menu_setting;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tantan.R;
import com.example.tantan.network.SharedPreference;

import java.io.File;

public class Menu5Fragment extends Fragment {

    TextView user_name;
    Button sign_btn;
    Button logout_btn;
    TextView user_hello;

    final static int RESULT_CODE = 0;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu5, container, false);
        user_name = (TextView) view.findViewById(R.id.user_name);
        sign_btn = (Button) view.findViewById(R.id.sign_btn);
        logout_btn = (Button) view.findViewById(R.id.logout_btn);
        user_hello = (TextView)view.findViewById(R.id.user_hello);

        logout_btn.setVisibility(View.GONE);
        user_name.setVisibility(View.GONE);

        // sign_btn.setOnClickListener(this);
        sign_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //if (SharedPreference.getAttribute(getActivity(), "userEmail").length() != 0) {//로그인 고유데이터(현재는 이메일) 길이 0 아닐시

                Intent intent = new Intent(getActivity(), LoginPage.class);
                startActivityForResult(intent, RESULT_CODE);

//                    Intent intent = new Intent(getActivity(), LoginPage.class);
//                    startActivityForResult(intent, RESULT_CODE);
//                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);//액티비티 스택제거
//                    Toast.makeText(getActivity(), "자동 로그인 되었습니다", Toast.LENGTH_SHORT).show();
//                    user_name.setText(SharedPreference.getAttribute(getContext(), "userEmail"));
                //}
            }
        });

        if (new File("/data/data/com.example.tantan/shared_prefs/com.example.tantan_preferences.xml").exists()) {
            if (SharedPreference.getAttribute(getActivity(), "userEmail").length() != 0) {
                Toast.makeText(getActivity(), "자동로그인", Toast.LENGTH_SHORT).show();
                user_name.setText(SharedPreference.getAttribute(getContext(), "userName"));

                user_hello.setVisibility(View.VISIBLE);
                sign_btn.setVisibility(View.GONE);
                logout_btn.setVisibility(View.VISIBLE);
                user_name.setVisibility(View.VISIBLE);
            } else {
                user_hello.setVisibility(View.GONE);
                sign_btn.setVisibility(View.VISIBLE);
                user_name.setVisibility(View.GONE);
                logout_btn.setVisibility(View.GONE);
            }
        } else {
            user_hello.setVisibility(View.GONE);
            sign_btn.setVisibility(View.VISIBLE);
            user_name.setVisibility(View.GONE);
            logout_btn.setVisibility(View.GONE);
        }

        logout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(getActivity(), Menu5Fragment.class);
//                startActivity(intent);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);//액티비티 스택제거
                Toast.makeText(getActivity(), "로그아웃 되었습니다.", Toast.LENGTH_SHORT).show();
                SharedPreference.setAttribute(getActivity(), "userEmail", "");

                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.detach(Menu5Fragment.this).attach(Menu5Fragment.this).commit();
            }
        });



        ListView listview1;
        ListViewAdapter1 adapter1;

        adapter1 = new ListViewAdapter1();

        listview1 = (ListView) view.findViewById(R.id.list_view1);
        listview1.setAdapter(adapter1);


        adapter1.addItem(ContextCompat.getDrawable(getActivity(), R.drawable.ic_baseline_person_24),
                "개인정보", ContextCompat.getDrawable(getActivity(), R.drawable.ic_baseline_navigate_next_24));
        adapter1.addItem(ContextCompat.getDrawable(getActivity(), R.drawable.ic_baseline_notifications_24),
                "공지사항", ContextCompat.getDrawable(getActivity(), R.drawable.ic_baseline_navigate_next_24));
        adapter1.addItem(ContextCompat.getDrawable(getActivity(), R.drawable.ic_baseline_question_answer_24),
                "도움말", ContextCompat.getDrawable(getActivity(), R.drawable.ic_baseline_navigate_next_24));
        adapter1.addItem(ContextCompat.getDrawable(getActivity(), R.drawable.ic_baseline_bluetooth_audio_24),
                "스마트미러 연결", ContextCompat.getDrawable(getActivity(), R.drawable.ic_baseline_navigate_next_24));

        listview1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ListViewItem1 item = (ListViewItem1) adapter1.getItem(position);
                switch (item.getTitle()) {
                    case "개인정보":
                        if (new File("/data/data/com.example.tantan/shared_prefs/com.example.tantan_preferences.xml").exists()) {
                            if (SharedPreference.getAttribute(getActivity(), "userEmail").length() != 0) {
                                Intent private_intent = new Intent(getActivity(), PrivatePage.class);
                                startActivityForResult(private_intent, RESULT_CODE);
                            } else {
                                Toast.makeText(getActivity(), "로그인이 필요합니다.", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getActivity(), "로그인이 필요합니다.", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case "공지사항":
                        Intent notice_intent = new Intent(getActivity(), NoticePage.class);
                        startActivity(notice_intent);
                        break;
                    case "도움말":
                        Intent help_intent = new Intent(getActivity(), HelpPage.class);
                        startActivity(help_intent);
                        break;
                    case "스마트미러 연결":
                        Intent connect_intent = new Intent(getActivity(), ConnectPage.class);
                        startActivity(connect_intent);
                        break;
                }
            }
        });

        ListView listview2;
        ListViewAdapter1 adapter2;

        adapter2 = new ListViewAdapter1();

        listview2 = (ListView) view.findViewById(R.id.list_view2);
        listview2.setAdapter(adapter2);

        //21.03.14 hr 스크롤뷰 높이 고정 때문에 추가
        setListViewHeightBasedOnChildren(listview1);
        setListViewHeightBasedOnChildren(listview2);

        adapter2.addItem(ContextCompat.getDrawable(getActivity(), R.drawable.ic_baseline_help_center_24),
                "개발자에게 피드백/문의", ContextCompat.getDrawable(getActivity(), R.drawable.ic_baseline_navigate_next_24));

        listview2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ListViewItem1 item = (ListViewItem1) adapter2.getItem(position);
                switch (item.getTitle()) {
                    case "개발자에게 피드백/문의":
                        Intent intent = new Intent(getActivity(), FeedBackPage.class);
                        startActivity(intent);
                        break;
                }
            }
        });

        return view;

    }

    /*
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_btn:
                Intent intent = new Intent(getActivity(), LoginPage.class);
                startActivityForResult(intent, RESULT_CODE);
                break;

            case R.id.logout_btn:
                user_name.setText("사용자");
                sign_btn.setVisibility(View.VISIBLE);
                logout_btn.setVisibility(View.GONE);
                break;
        }
    }
*/

    //21.03.14 hr 스크롤뷰 고정 함수 생성
    //한 페이지 안에 2개 이상 listview 사용 + 스크롤뷰 사용 시 문제 있어서 필요
    public void setListViewHeightBasedOnChildren (ListView listView){
        ListAdapter listAdapter = listView.getAdapter();

        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);

            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();

        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();

    }

    public void onActivityResult (int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case RESULT_CODE:
                if (resultCode == Activity.RESULT_OK) {
                    user_name.setText(SharedPreference.getAttribute(getContext(), "userName"));

                    user_hello.setVisibility(View.VISIBLE);
                    user_name.setVisibility(View.VISIBLE);
                    sign_btn.setVisibility(View.GONE);
                    logout_btn.setVisibility(View.VISIBLE);
                }
                break;
        }

    }



}