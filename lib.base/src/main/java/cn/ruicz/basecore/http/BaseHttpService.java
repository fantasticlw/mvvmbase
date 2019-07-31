package cn.ruicz.basecore.http;


import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * cn.meeting.ruicz.http
 *
 * @author xyq
 * @time 2019-1-22 17:19
 * Remark ------------------------------------------------
 */
public interface BaseHttpService {
    // 新版政务微信接口
    @GET("http://api2.apaas.gd.ydxxw:8088/token")
    Observable<JSONObject> token(@Query("appId") String appId, @Query("secret") String secret);

    // 新版政务微信接口
    @GET("http://api2.apaas.gd.ydxxw:8088/auth/user/getUser")
    Observable<String> getUser(@Query("acode") String acode);

    // 旧版政务微信接口
    @GET("http://api2.apaas.gd.ydxxw:8082/jwcgi/portal/api/jwsecurity!getToken.action")
    Observable<JsonObject> getToken(@Query("developerId") String developerId, @Query("developerKey") String developerKey);

    // 旧版政务微信接口
    @GET("http://api2.apaas.gd.ydxxw:8082/jwcgi/api/user!getUser.action")
    Observable<JSONObject> getUser(@Query("token") String token, @Query("acode") String acode, @Query("appId") String appId);

    // 睿策者获取token接口
    @GET("wechat/get_token")
    Observable<String> getRzcToken(@Query("acode") String acode, @Query("clientId") String clientld);
}
