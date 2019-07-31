package cn.ruicz.basecore.zwwx;


/**
 * 政务微信信息管理器
 */
public class ZwwxInfoManager {

    private static ZwwxUserInfo zwwxUserInfo;
    private static RczUserInfo userInfo;
    private static String appId;
    private static String secret;
    private static String rczClientId;
    private static String logSource;
    private static Class cls;
    private static String baseUrl;
    private static boolean oldToken;

    private static ZwwxUserInfo.DataBean wxUserInfoData;
    private static RczUserInfo.DataBean userInfoData;

    /**
     * 初始化政务微信用户信息
     * @param o
     */
    public static void initZwwxUserInfo(ZwwxUserInfo o){
        zwwxUserInfo = o;
        wxUserInfoData = zwwxUserInfo.getData();
    }

    public static void initUserInfo(RczUserInfo o){
        userInfo = o;
        userInfoData = userInfo.getData();
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
     * 初始化LogSource
     */
    public static void initLogSource(String source){
        logSource = source;
    }

    /**
     * 初始化rczClientId
     */
    public static void initRczClientId(String rczClientId2){
        rczClientId = rczClientId2;
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

    public static String getBaseUrl(){
        return baseUrl;
    }

    public static String getLogSource(){
        return logSource;
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


    public static String getRczClientId(){
        return rczClientId;
    }

    public static void initOld(boolean isOld){
        oldToken = isOld;
    }

    public static boolean isOldToken(){
        return oldToken;
    }


    public static ZwwxUserInfo.DataBean getUserData(){
        if (wxUserInfoData == null){
            throw new RuntimeException("必须先调用初始化方法 initZwwxUserInfo()");
        }
        return wxUserInfoData;
    }

    public static RczUserInfo.DataBean getUserInfo(){
        if (userInfoData == null){
            throw new RuntimeException("必须先调用初始化方法 initZwwxUserInfo()");
        }
        return userInfoData;
    }


}
