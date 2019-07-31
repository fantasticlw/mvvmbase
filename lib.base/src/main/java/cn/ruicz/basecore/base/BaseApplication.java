package cn.ruicz.basecore.base;

import android.app.Activity;
import android.app.Application;
import android.app.LauncherActivity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.multidex.MultiDex;

import cn.ruicz.basecore.BuildConfig;
import cn.ruicz.basecore.LibBase;
import cn.ruicz.basecore.utils.AppUtils;
import cn.ruicz.basecore.utils.Utils;
import cn.ruicz.basecore.zwwx.ZwwxInfoManager;
import cn.ruicz.basecore.zwwx.ZwwxUserInfo;

/**
 * Created by goldze on 2017/6/15.
 */

public class BaseApplication extends Application {
    private static Application sInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        MultiDex.install(this);
        setApplication(this);

        LibBase.builder(this).spname(AppUtils.getAppPackageName()).debug(BuildConfig.DEBUG).install();

        // 配置appid和secret
        //ZwwxInfoManager.initAppIdAndSecret("2000444", "Y2MzN2M0M2UtZWM2MC00ZGY2LTg2OGUtYWUyODAyYWZkNjdh");

        // 配置政务微信启动跳转activity
        //ZwwxInfoManager.initLaunchActivity(LauncherActivity.class);

        // 配置日志source
        //ZwwxInfoManager.initLogSource("946");

        // 配置睿策者接口地址
        //ZwwxInfoManager.initBaseUrl("http://xxxx");

        // 配置睿策者接口clientId
        //ZwwxInfoManager.initRczClientId("100000");

        // 获取政务微信用户信息
        //ZwwxInfoManager.getZwwxUserInfo();过时
        //ZwwxInfoManager.getUserData();   新版用户信息
        //ZwwxInfoManager.getUserInfo();   旧版用户信息


    }

    /**
     * 当主工程没有继承BaseApplication时，可以使用setApplication方法初始化BaseApplication
     *
     * @param application
     */
    public static synchronized void setApplication(@NonNull Application application) {
        sInstance = application;
        //初始化工具类
        Utils.init(application);
        //注册监听每个activity的生命周期,便于堆栈式管理
        application.registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {

            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                AppManager.getAppManager().addActivity(activity);
            }

            @Override
            public void onActivityStarted(Activity activity) {
            }

            @Override
            public void onActivityResumed(Activity activity) {
            }

            @Override
            public void onActivityPaused(Activity activity) {
            }

            @Override
            public void onActivityStopped(Activity activity) {
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                AppManager.getAppManager().removeActivity(activity);
            }
        });
    }

    /**
     * 获得当前app运行的Application
     */
    public static Application getInstance() {
        if (sInstance == null) {
            throw new NullPointerException("please inherit BaseApplication or call setApplication.");
        }
        return sInstance;
    }
}
