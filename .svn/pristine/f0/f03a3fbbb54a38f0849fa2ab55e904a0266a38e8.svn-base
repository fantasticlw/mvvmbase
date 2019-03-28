package com.goldze.mvvmhabit.utils;


import com.google.gson.JsonObject;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * Author: MBENBEN
 * Time: 2016/10/26 on 14:21
 */
public interface HttpService {

    //es服务总线人员详情
    @GET("services/archives/queryPersonInfo")
    Observable<String> queryPersonArchivesDetailByID(@Query("params") String params, @Query("key") String key);

}
