package cn.ruicz.demo.utils;

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
}
