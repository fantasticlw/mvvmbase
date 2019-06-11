package cn.ruicz.basecore.http;

import android.arch.lifecycle.Lifecycle;

import io.reactivex.Observable;

import static cn.ruicz.basecore.http.BaseHttpUtils.toSubscribe;

/**
 * cn.meeting.ruicz.http
 *
 * @author xyq
 * @time 2019-1-22 17:18
 * Remark ------------------------------------------------
 */
public class BaseHttpManager {
    private static BaseHttpManager httpManger;

    private static String userUrl = "";
    private BaseHttpService wxService;


    private BaseHttpManager() {
    }


    public static BaseHttpManager newInstance() {
        if(httpManger == null){
            synchronized (BaseHttpManager.class) {
                httpManger = new BaseHttpManager();
                httpManger.wxService = BaseHttpUtils.createApiService(BaseHttpService.class, userUrl);
            }
        }
        return httpManger;
    }


    /**
     * 获取睿策者token
     */
    public SimpleObserver getRzcToken(Lifecycle lifecycle,String acode,SimpleObserver dialogObserver) {
        Observable observable = wxService.getRzcToken( acode, "10002");
        return toSubscribe(lifecycle,observable, dialogObserver);
    }

    /**
     * 获取新版本token
     */
    public SimpleObserver token(Lifecycle lifecycle, String appid, String secret, SimpleObserver dialogObserver) {
        Observable observable = wxService.token(appid, secret);
        return toSubscribe(lifecycle,observable, dialogObserver);
    }

    /**
     * 获取新版本用户信息
     */
    public SimpleObserver getUser(Lifecycle lifecycle, String acode, SimpleObserver dialogObserver) {
        Observable observable = wxService.getUser(acode);
        return toSubscribe(lifecycle,observable, dialogObserver);
    }


    /**
     * 获取token
     */
    public SimpleObserver getToken(Lifecycle lifecycle, SimpleObserver dialogObserver) {
        Observable observable = wxService.getToken("qw4c7b93430004400fa574fa5e8e4567d6","ODZhNWJjNmMtNzdiMS00ZjhmLWE3MDAtYjY3Y2RkNTAzNTY4");
        return toSubscribe(lifecycle,observable, dialogObserver);
    }

    /**
     * 获取用户信息
     * @param token
     * @param acode
     * @param appId
     * @param dialogObserver
     * @return
     */
    public SimpleObserver getUser(Lifecycle lifecycle, String token, String acode, String appId, SimpleObserver dialogObserver) {
        Observable observable = wxService.getUser(token, acode, appId);
        return toSubscribe(lifecycle,observable,dialogObserver);
    }

}
