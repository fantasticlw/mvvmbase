package cn.ruicz.demo.utils;

import cn.ruicz.basecore.http.BaseHttpUtils;


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
