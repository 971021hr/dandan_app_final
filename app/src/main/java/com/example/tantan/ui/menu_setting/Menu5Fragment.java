package com.example.tantan.ui.menu_setting;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.tantan.MainActivity;
import com.example.tantan.R;

public class Menu5Fragment extends Fragment implements OnClickListener {

    TextView user_name;
    Button sign_btn;
    Button logout_btn;

    final static int RESULT_CODE = 0;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu5, container, false);

        sign_btn = (Button) view.findViewById(R.id.sign_btn);
        sign_btn.setOnClickListener(this);

        user_name = (TextView) view.findViewById(R.id.user_name);

        ListView listview1;
        ListViewAdapter1 adapter1;

        adapter1 = new ListViewAdapter1() ;

        listview1 = (ListView) view.findViewById(R.id.list_view1);
        listview1.setAdapter(adapter1);

        adapter1.addItem(ContextCompat.getDrawable(getActivity(), R.drawable.ic_baseline_person_24),
                "개인정보", ContextCompat.getDrawable(getActivity(), R.drawable.ic_baseline_navigate_next_24));
        adapter1.addItem(ContextCompat.getDrawable(getActivity(), R.drawable.ic_baseline_notifications_24),
                "공지사항", ContextCompat.getDrawable(getActivity(), R.drawable.ic_baseline_navigate_next_24));
        adapter1.addItem(ContextCompat.getDrawable(getActivity(), R.drawable.ic_baseline_question_answer_24),
                "도움말", ContextCompat.getDrawable(getActivity(), R.drawable.ic_baseline_navigate_next_24));
        adapter1.addItem(ContextCompat.getDrawable(getActivity(), R.drawable.ic_baseline_bluetooth_audio_24),
                "스마트 미러 연결", ContextCompat.getDrawable(getActivity(), R.drawable.ic_baseline_navigate_next_24));

        listview1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ListViewItem1 item = (ListViewItem1) adapter1.getItem(position);
                switch (item.getTitle()) {
                    case "개인정보":
                        Intent private_intent = new Intent(getActivity(), PrivatePage.class);
                        startActivity(private_intent);
                        break;
                    case "공지사항":
                        Intent notice_intent = new Intent(getActivity(), NoticePage.class);
                        startActivity(notice_intent);
                        break;
                    case "도움말":
                        Intent help_intent = new Intent(getActivity(), HelpPage.class);
                        startActivity(help_intent);
                        break;
                    case "스마트 미러 연결":
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

        adapter2.addItem(ContextCompat.getDrawable(getActivity(), R.drawable.ic_baseline_help_center_24),
                "개발자에게 피드백/문의", ContextCompat.getDrawable(getActivity(), R.drawable.ic_baseline_navigate_next_24));

        listview2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ListViewItem1 item = (ListViewItem1) adapter2.getItem(position);
                switch (item.getTitle()) {
                    case "개발자에게 피드백/문의":
                        Intent intent = new Intent(getActivity(), PrivatePage.class);
                        startActivity(intent);
                        break;
                }
            }
        });

        logout_btn = (Button) view.findViewById(R.id.logout_btn);
        logout_btn.setVisibility(View.GONE);

        return view;
    }

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

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case RESULT_CODE:
                if (resultCode == Activity.RESULT_OK) {
                    user_name.setText(data.getStringExtra("이름"));
                    sign_btn.setVisibility(View.GONE);
                    logout_btn.setVisibility(View.VISIBLE);
                }
                break;
        }
    }
}