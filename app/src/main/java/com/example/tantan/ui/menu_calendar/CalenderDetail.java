package com.example.tantan.ui.menu_calendar;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.tantan.DataEvent;
import com.example.tantan.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;
import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;

public class CalenderDetail extends AppCompatActivity{

    TextView txtSelect;
    Button btnMeal, btnBody;
    LinearLayout layoutMeal;
    LinearLayout layoutBody;
    TextView txtWater;
    SwipeMenuListView listMeal;
    SwipeMenuListView listBody;
    SwipeMenuListView listExercise;

    //AppAdapter mAdapter;
    List<ApplicationInfo> mAppList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu1_calender_detail);

        txtSelect = (TextView)findViewById(R.id.txt_select);

        Intent intent = getIntent();
        String select_date = intent.getStringExtra("날짜");
        txtSelect.setText(select_date);

        btnMeal = (Button)findViewById(R.id.btn_show_meal);
        btnBody = (Button)findViewById(R.id.btn_show_body);

        layoutMeal = (LinearLayout)findViewById(R.id.layout_meal);
        layoutBody = (LinearLayout)findViewById(R.id.layout_body);

        txtWater = (TextView)findViewById(R.id.txt_water);
        listMeal = (SwipeMenuListView)findViewById(R.id.list_meal);
        listBody = (SwipeMenuListView)findViewById(R.id.list_body);
        listExercise = (SwipeMenuListView)findViewById(R.id.list_exercise);

        //식단 list 추가 >> 나중에 DB 연결
        MealAdapter mealAdapter;
        mealAdapter = new MealAdapter();
        listMeal.setAdapter(mealAdapter);

        mAppList = getPackageManager().getInstalledApplications(0);

        SwipeMenuCreator creator = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {
                // create "open" item
                SwipeMenuItem openItem = new SwipeMenuItem(getApplicationContext());
                openItem.setBackground(new ColorDrawable(Color.rgb(0x9E,0x70,0xF2)));
                openItem.setWidth(dp2px(90));
                openItem.setTitle("수정");
                openItem.setTitleSize(18);
                openItem.setTitleColor(Color.WHITE);
                menu.addMenuItem(openItem);

                SwipeMenuItem deleteItem = new SwipeMenuItem(getApplicationContext());
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xC9,0xC9,0xCE)));
                deleteItem.setWidth(dp2px(90));
                deleteItem.setTitle("삭제");
                deleteItem.setTitleSize(18);
                deleteItem.setTitleColor(Color.WHITE);
                menu.addMenuItem(deleteItem);
            }
        };

        listMeal.setMenuCreator(creator);

        // step 2. listener item click event
        listMeal.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                ApplicationInfo item = mAppList.get(position);
                switch (index) {
                    case 0:
                        // open
                        open(item);
                        break;
                    case 1:
                        // delete
//                      delete(item);
                        mAppList.remove(position);
                        mealAdapter.notifyDataSetChanged();
                        break;
                }
                return false;
            }
        });

        // set SwipeListener
        listMeal.setOnSwipeListener(new SwipeMenuListView.OnSwipeListener() {

            @Override
            public void onSwipeStart(int position) {
                // swipe start
            }

            @Override
            public void onSwipeEnd(int position) {
                // swipe end
            }
        });

        mealAdapter.addItem(ContextCompat.getDrawable(this,R.drawable.ic_baseline_fastfood_24),"AM 10:00","오늘 아점은 이걸 먹었다고 한다.");
        mealAdapter.addItem(ContextCompat.getDrawable(this,R.drawable.ic_baseline_fastfood_24),"AM 14:00","오늘 간식은 이걸 먹었다고 한다.");
        mealAdapter.addItem(ContextCompat.getDrawable(this,R.drawable.ic_baseline_fastfood_24),"AM 18:00","오늘 저녁은 이걸 먹었다고 한다.");

        //신체 눈바디 등 정보 list 추가
        BodyAdapter bodyAdapter= new BodyAdapter();
        listBody.setAdapter(bodyAdapter);

        listBody.setMenuCreator(creator);

        // step 2. listener item click event
        listBody.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                ApplicationInfo item = mAppList.get(position);
                switch (index) {
                    case 0:
                        // open
                        open(item);
                        break;
                    case 1:
                        // delete
//                      delete(item);
                        mAppList.remove(position);
                        bodyAdapter.notifyDataSetChanged();
                        break;
                }
                return false;
            }
        });

        // set SwipeListener
        listBody.setOnSwipeListener(new SwipeMenuListView.OnSwipeListener() {

            @Override
            public void onSwipeStart(int position) {
                // swipe start
            }

            @Override
            public void onSwipeEnd(int position) {
                // swipe end
            }
        });

        bodyAdapter.addItem(ContextCompat.getDrawable(this,R.drawable.ic_baseline_person_24),"50.0 kg", "15.0 kg", "30.0 kg");

        //운동 정보 list 추가
        ExerciseAdapter exerciseAdapter = new ExerciseAdapter();
        listExercise.setAdapter(exerciseAdapter);

        exerciseAdapter.addItem("헬스","01:05:50","스쿼트 3set, 런지 5set, 풀업 3set");
        exerciseAdapter.addItem("요가","00:40:33","다운독, 스쿼트, 기타 등등");

        listExercise.setMenuCreator(creator);

        // step 2. listener item click event
        listExercise.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                ApplicationInfo item = mAppList.get(position);
                switch (index) {
                    case 0:
                        // open
                        open(item);
                        break;
                    case 1:
                        // delete
//                      delete(item);
                        mAppList.remove(position);
                        exerciseAdapter.notifyDataSetChanged();
                        break;
                }
                return false;
            }
        });

        // set SwipeListener
        listExercise.setOnSwipeListener(new SwipeMenuListView.OnSwipeListener() {

            @Override
            public void onSwipeStart(int position) {
                // swipe start
            }

            @Override
            public void onSwipeEnd(int position) {
                // swipe end
            }
        });

        btnMeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*화면전환*/
                layoutMeal.setVisibility(View.VISIBLE);
                layoutBody.setVisibility(View.INVISIBLE);

                btnMeal.setEnabled(false);
                btnBody.setEnabled(true);

            }
        });

        btnBody.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layoutMeal.setVisibility(View.INVISIBLE);
                layoutBody.setVisibility(View.VISIBLE);

                btnMeal.setEnabled(true);
                btnBody.setEnabled(false);

            }
        });
    }

    private void open(ApplicationInfo item) {
        // open app
        /*
        Intent resolveIntent = new Intent(Intent.ACTION_MAIN, null);
        resolveIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        resolveIntent.setPackage(item.packageName);
        List<ResolveInfo> resolveInfoList = getPackageManager()
                .queryIntentActivities(resolveIntent, 0);
        if (resolveInfoList != null && resolveInfoList.size() > 0) {
            ResolveInfo resolveInfo = resolveInfoList.get(0);
            String activityPackageName = resolveInfo.activityInfo.packageName;
            String className = resolveInfo.activityInfo.name;

            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);
            ComponentName componentName = new ComponentName(
                    activityPackageName, className);

            intent.setComponent(componentName);
            startActivity(intent);

        }

         */
    }

    /*
    class AppAdapter extends BaseSwipeListAdapter {

        @Override
        public int getCount() {
            return mAppList.size();
        }

        @Override
        public ApplicationInfo getItem(int position) {
            return mAppList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = View.inflate(getApplicationContext(),
                        R.layout.item_list_app, null);
                new ViewHolder(convertView);
            }
            ViewHolder holder = (ViewHolder) convertView.getTag();
            ApplicationInfo item = getItem(position);
            holder.iv_icon.setImageDrawable(item.loadIcon(getPackageManager()));
            holder.tv_name.setText(item.loadLabel(getPackageManager()));
            holder.iv_icon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(SimpleActivity.this, "iv_icon_click", Toast.LENGTH_SHORT).show();
                }
            });
            holder.tv_name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(SimpleActivity.this,"iv_icon_click",Toast.LENGTH_SHORT).show();
                }
            });
            return convertView;
        }

        class ViewHolder {
            ImageView iv_icon;
            TextView tv_name;

            public ViewHolder(View view) {
                iv_icon = (ImageView) view.findViewById(R.id.iv_icon);
                tv_name = (TextView) view.findViewById(R.id.tv_name);
                view.setTag(this);
            }
        }

        @Override
        public boolean getSwipeEnableByPosition(int position) {
            if(position % 2 == 0){
                return false;
            }
            return true;
        }
    }


     */
    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }

}
