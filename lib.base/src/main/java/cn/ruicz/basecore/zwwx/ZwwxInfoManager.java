package cn.ruicz.basecore.zwwx;

import android.text.TextUtils;

/**
 * 政务微信信息管理器
 */
public class ZwwxInfoManager {

    private static ZwwxUserInfo zwwxUserInfo;
    private static String appId;
    private static String secret;

    /**
     * 初始化政务微信用户信息
     * @param o
     */
    public static void initZwwxUserInfo(ZwwxUserInfo o){
        if (zwwxUserInfo == null){
            throw new RuntimeException("此初始化方法只能调用一次");
        }
        zwwxUserInfo = o;
    }

    /**
     * 初始化政务微信appId和secret
     */
    public static void initAppIdAndSecret(String aappid, String asecret){
        if (!TextUtils.isEmpty(appId) && !TextUtils.isEmpty(secret)){
            throw new RuntimeException("此初始化方法只能调用一次");
        }
        appId = aappid;
        secret = asecret;
    }

    /**
     * 获取政务微信用户信息
     * @return
     */
    public static ZwwxUserInfo getZwwxUserInfo(){
        if (zwwxUserInfo == null){
            throw new RuntimeException("必须先调用初始化方法 initZwwxUserInfo()");
        }
        return zwwxUserInfo;
    }

    public static String getAppid(){
        if (appId == null){
            throw new RuntimeException("必须先调用初始化方法 initAppIdAndSecret()");
        }
        return appId;
    }

    public static String getSecret(){
        if (secret == null){
            throw new RuntimeException("必须先调用初始化方法 initAppIdAndSecret()");
        }
        return secret;
    }
}
