package com.example.mydemo.http;


import com.example.mydemo.dingdingcontact.entity.ResultVo;

import io.reactivex.Flowable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;


public interface ApiService {

    String baseURL = "http://101.201.108.172:8280/masCustomer/service/";

    @FormUrlEncoded
    @POST("UserInfoService/getEmployeeOrg")
    Flowable<ResultVo> getOrgContacts(
            @Field("servId") String servId,
            @Field("orgCode") String code,
            @Field("loginId") String loginId,
            @Field("token") String token
    );

    @FormUrlEncoded
    @POST("UserFriendService/userFriendList")
    Flowable<ResultVo> getFriendList(
            @Field("uid") String uid,
            @Field("currentPageNO") String currentPageNO,
            @Field("pageSize") String pageSize,
            @Field("token") String token
    );

}