package com.example.tantan.ui.menu_community;

import android.app.Person;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.tantan.R;
import com.example.tantan.data.ArrayData;
import com.example.tantan.data.CommunityResponse;
import com.example.tantan.data.UserEmailData;
import com.example.tantan.data.WaterGetResponse;
import com.example.tantan.network.RetrofitClient;
import com.example.tantan.network.ServiceApi;
import com.example.tantan.network.SharedPreference;
import com.example.tantan.ui.menu_calendar.CalenderDetail;
import com.example.tantan.ui.menu_setting.HelpPage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Menu2Fragment extends Fragment {

    private ServiceApi service;

    String userEmail="";

    private RecyclerView mVerticalView;
    private VerticalAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;

    int lenArr = 0;
    List<String> runList = new ArrayList<String>();

    ArrayList<VerticalData> data = new ArrayList<>();

    private RecyclerView mVerticalView01;
    private VerticalAdapter mAdapter01;
    private LinearLayoutManager mLayoutManager01;

    int lenArr01 = 0;
    List<String> mealList = new ArrayList<String>();

    ArrayList<VerticalData> data01 = new ArrayList<>();

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.fragment_menu2,container,false);

        service = RetrofitClient.getClient().create(ServiceApi.class);


        mVerticalView = (RecyclerView)rootView.findViewById(R.id.vertical_list);
        mVerticalView01 = (RecyclerView)rootView.findViewById(R.id.vertical_list01);

        mAdapter = new VerticalAdapter();
        mAdapter01 = new VerticalAdapter();

        //배열 초기화 필수
        data.clear();
        data01.clear();

        runList.clear();
        mealList.clear();

        getTip();

        mAdapter.setItemClick(new VerticalAdapter.ItemClick() {
            @Override
            public void onClick(View view, int position) {
                int pos = position;
                String type = "운동";

                Intent intent1 = new Intent(getActivity(), CommunityDetail.class);
                intent1.putExtra("position", pos);
                intent1.putExtra("type", type);

                startActivity(intent1);
            }
        });

        mAdapter01.setItemClick(new VerticalAdapter.ItemClick() {
            @Override
            public void onClick(View view, int position) {
                int pos = position;
                String type = "식단";

                Intent intent1 = new Intent(getActivity(), CommunityDetail.class);
                intent1.putExtra("position", pos);
                intent1.putExtra("type", type);

                startActivity(intent1);
            }
        });


        return rootView;
    }

    private void getTip() {
        userEmail = SharedPreference.getAttribute(getContext(),"userEmail");

        if (new File("/data/data/com.example.tantan/shared_prefs/com.example.tantan_preferences.xml").exists()) {
            if (userEmail.equals("")) {
                userEmail = "joy@joy";
                startRunTip(new UserEmailData(userEmail));
                startMealTip(new UserEmailData(userEmail));


            } else {
                startRunTip(new UserEmailData(userEmail));
                startMealTip(new UserEmailData(userEmail));
            }
        } else {

        }

    }



    private void startRunTip(UserEmailData data){

        service.userDataCommunityRun(data).enqueue(new Callback<CommunityResponse>() {
            @Override
            public void onResponse(Call<CommunityResponse> call, Response<CommunityResponse> response) {
                CommunityResponse result = response.body();
                lenArr = result.getArrLength();


                if (result.getCode()==200){
                    String runTitle = result.getTipTitle();
                    runTitle = runTitle.replace("/$","");

                    String[] runTitleArr = runTitle.split("/");



                    for (int i = 0; i<lenArr ; i++){
                        runList.add(runTitleArr[i]);

                    }
                    addArr();

                }

            }

            @Override
            public void onFailure(Call<CommunityResponse> call, Throwable t) {

            }
        });


    }

    private void startMealTip(UserEmailData data){

        service.userDataCommunityMeal(data).enqueue(new Callback<CommunityResponse>() {
            @Override
            public void onResponse(Call<CommunityResponse> call, Response<CommunityResponse> response) {
                CommunityResponse result = response.body();
                lenArr01 = result.getArrLength();


                if (result.getCode()==200){
                    String mealTitle = result.getTipTitle();
                    mealTitle = mealTitle.replace("/$","");

                    String[] mealTitleArr = mealTitle.split("/");



                    for (int i = 0; i<lenArr01 ; i++){
                        mealList.add(mealTitleArr[i]);

                    }
                    addArr01();

                }

            }

            @Override
            public void onFailure(Call<CommunityResponse> call, Throwable t) {

            }
        });


    }

    private void addArr() {

        int i = 0;
        while (i<lenArr){
            String str_tip = runList.get(i);
            this.data.add(new VerticalData(R.mipmap.ic_launcher,str_tip,"운동"));
            i++;
        }

        mLayoutManager = new LinearLayoutManager(getContext());
        mLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        // setLayoutManager
        mVerticalView.setLayoutManager(mLayoutManager);




        mAdapter.setData(this.data);
        mVerticalView.setAdapter(mAdapter);
    }

    private void addArr01() {

        int i = 0;
        while (i<lenArr01){
            String str_tip = mealList.get(i);
            this.data01.add(new VerticalData(R.mipmap.ic_launcher,str_tip,"식단"));
            i++;
        }


        mLayoutManager01 = new LinearLayoutManager(getContext());
        mLayoutManager01.setOrientation(LinearLayoutManager.HORIZONTAL);

        // setLayoutManager
        mVerticalView01.setLayoutManager(mLayoutManager01);




        mAdapter01.setData(data01);
        mVerticalView01.setAdapter(mAdapter01);
    }


}