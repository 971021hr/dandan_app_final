package com.example.tantan.network;

import com.example.tantan.data.AddRunData;
import com.example.tantan.data.ArrayData;
import com.example.tantan.data.BodyData;
import com.example.tantan.data.BodyResponse;
import com.example.tantan.data.CommunityDetailResponse;
import com.example.tantan.data.CommunityResponse;
import com.example.tantan.data.HelpDetailResponse;
import com.example.tantan.data.HelpResponse;
import com.example.tantan.data.JoinData;
import com.example.tantan.data.JoinResponse;
import com.example.tantan.data.LeaveData;
import com.example.tantan.data.LoginData;
import com.example.tantan.data.LoginResponse;
import com.example.tantan.data.MealData;
import com.example.tantan.data.MealResponse;
import com.example.tantan.data.NameData;
import com.example.tantan.data.NameResponse;
import com.example.tantan.data.NoticeDetailResponse;
import com.example.tantan.data.NoticeResponse;
import com.example.tantan.data.NumData;
import com.example.tantan.data.PwdData;
import com.example.tantan.data.PwdResponse;
import com.example.tantan.data.RunResponse;
import com.example.tantan.data.SimpleResponse;
import com.example.tantan.data.StatsData;
import com.example.tantan.data.StatsDataWeek;
import com.example.tantan.data.StatsResponse;
import com.example.tantan.data.StatsRunMonthData;
import com.example.tantan.data.StatsRunWeekData;
import com.example.tantan.data.StatsWeekResponse;
import com.example.tantan.data.UserEmailData;
import com.example.tantan.data.WaterData;
import com.example.tantan.data.WaterGetResponse;
import com.example.tantan.data.WaterResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ServiceApi {
    @POST("/user/login")
    Call<LoginResponse> userLogin(@Body LoginData data);

    @POST("/user/join")
    Call<JoinResponse> userJoin(@Body JoinData data);

    @POST("/user/leave")
    Call<LoginResponse> userLeave(@Body LeaveData data);

    @POST("/user/pwd")
    Call<PwdResponse> userPwd(@Body PwdData data);

    @POST("/user/name")
    Call<NameResponse> userName(@Body NameData data);

    @POST("/user/water")
    Call<WaterResponse> userWater(@Body WaterData data);

    @POST("/user/meal")
    Call<MealResponse> userMeal(@Body MealData data);

   // @POST("/user/stats")
    //Call<StatsResponse> userStats(@Body StatsData data);

    @POST("/user/stats/time")
    Call<StatsResponse> userStatsRunTime(@Body StatsRunWeekData data);

    @POST("/user/stats/time")
    Call<StatsResponse> userStatsRunTimeMonth(@Body StatsRunMonthData data);

    @POST("/user/stats/week")
    Call<StatsWeekResponse> userStatsWeek(@Body StatsDataWeek data);

    @POST("/user/body")
    Call<BodyResponse> userBody(@Body BodyData data);

    @POST("/user/add/run")
    Call<SimpleResponse> userAddRun(@Body AddRunData data);

    @POST("/user/data/run")
    Call<RunResponse> userDataRun(@Body ArrayData data);

    @POST("/user/data/water")
    Call<WaterGetResponse> userDataWater(@Body ArrayData data);

    @POST("/user/data/body")
    Call<BodyResponse> userDataBody(@Body ArrayData data);

    @POST("/user/data/meal")
    Call<MealResponse> userDataMeal(@Body ArrayData data);

    @POST("/user/data/community/run")
    Call<CommunityResponse> userDataCommunityRun (@Body UserEmailData data);

    @POST("/user/data/community/meal")
    Call<CommunityResponse> userDataCommunityMeal (@Body UserEmailData data);

    @POST("/user/data/community/meal/detail")
    Call<CommunityDetailResponse> userDataCommunityMealDetail (@Body NumData data);

    @POST("/user/data/community/run/detail")
    Call<CommunityDetailResponse> userDataCommunityRunDetail (@Body NumData data);

    @POST("/user/stats/month")
    Call<StatsWeekResponse> userStatsMonth(@Body StatsDataWeek data);

    @POST("/user/stats/year")
    Call<StatsWeekResponse> userStatsYear(@Body StatsDataWeek data);

    @POST("/user/data/notice")
    Call<NoticeResponse> userDataNotice(@Body UserEmailData data);

    @POST("/user/data/notice/detail")
    Call<NoticeDetailResponse> userDataNoticeDetail(@Body NumData data);

    @POST("/user/data/help")
    Call<HelpResponse> userDataHelp (@Body UserEmailData data);

    @POST("/user/data/help/detail")
    Call<HelpDetailResponse> userDataHelpDetail (@Body NumData data);

    @POST("/user/stats/time/year")
    Call<StatsResponse> userStatsRunTimeYear(@Body StatsRunWeekData data);

}