package cn.ruicz.basecore.http;


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
    @GET("http://api2.apaas.gd.ydxxw:8088/token")
    Observable<String> token(@Query("appId") String appId, @Query("secret") String secret);

    @GET("http://api2.apaas.gd.ydxxw:8088/auth/user/getUser")
    Observable<String> getUser(@Query("acode") String acode);

    @GET("http://api2.apaas.gd.ydxxw:8082/jwcgi/portal/api/jwsecurity!getToken.action")
    Observable<JsonObject> getToken(@Query("developerId") String developerId, @Query("developerKey") String developerKey);

    @GET("http://api2.apaas.gd.ydxxw:8082/jwcgi/api/user!getUser.action")
    Observable<JsonObject> getUser(@Query("token") String token, @Query("acode") String acode, @Query("appId") String appId);

    @GET("wechat/get_token")
    Observable<String> getRzcToken(@Query("acode") String acode, @Query("clientId") String clientld);
}
