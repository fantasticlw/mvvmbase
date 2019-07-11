package cn.ruicz.basecore.zwwx;


/**
 * 政务微信信息管理器
 */
public class ZwwxInfoManager {

    private static ZwwxUserInfo zwwxUserInfo;
    private static String appId;
    private static String secret;
    private static Class cls;
    private static String baseUrl;

    /**
     * 初始化政务微信用户信息
     * @param o
     */
    public static void initZwwxUserInfo(ZwwxUserInfo o){
        zwwxUserInfo = o;
    }

    /**
     * 初始化跳转界面
     * @param cls2
     */
    public static void initLaunchActivity(Class cls2){
        cls = cls2;
    }

    /**
     * 初始化政务微信appId和secret
     */
    public static void initAppIdAndSecret(String aappid, String asecret){
        appId = aappid;
        secret = asecret;
    }

    /**
     * 初始化BaseUrl
     */
    public static void initBaseUrl(String abaseUrl){
        baseUrl = abaseUrl;
    }

    /**
     * 获取政务微信用户信息
     * @return
     */
    public static ZwwxUserInfo getZwwxUserInfo(){
        if (zwwxUserInfo == null){
            throw new RuntimeException("必须先调用初始化方法 initBaseUrl()");
        }
        return zwwxUserInfo;
    }


    public static String getBaseUrl(){
        if (baseUrl == null){
            throw new RuntimeException("必须先调用初始化方法 initLaunchActivity()");
        }
        return baseUrl;
    }

    public static Class getLaunchActivity(){
        if (cls == null){
            throw new RuntimeException("必须先调用初始化方法 initLaunchActivity()");
        }
        return cls;
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
