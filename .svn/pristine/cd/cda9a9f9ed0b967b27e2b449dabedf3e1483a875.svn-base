package com.goldze.mvvmhabit.utils;

import android.arch.lifecycle.LifecycleOwner;

import io.reactivex.Observable;
import cn.ruicz.basecore.http.BaseHttpUtils;
import cn.ruicz.basecore.http.SimpleObserver;

import static cn.ruicz.basecore.http.BaseHttpUtils.toSubscribe;


/**
 * Created by CLW on 2017/4/17.
 */


public enum HttpManager {

    INSTANCE;
    public final String baseUrl = "http://";
    private HttpService httpService;

    HttpManager(){
        httpService = BaseHttpUtils.createApiService(HttpService.class, baseUrl);
    }



    /**
     * 获取人员信息的请求
     * @param sfzh  人员身份证号
     * @param
     */
    public SimpleObserver queryPersonArchivesDetailByID(LifecycleOwner lifecycleOwner, String sfzh, SimpleObserver subscriber) {
        Observable observable = httpService.queryPersonArchivesDetailByID("","a028");
        return toSubscribe(lifecycleOwner, observable, subscriber);
    }
}
