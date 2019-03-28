package com.goldze.mvvmhabit.app;

import android.app.Activity;

import com.goldze.mvvmhabit.BuildConfig;
import com.goldze.mvvmhabit.R;
import cn.ruicz.basecore.base.BaseApplication;
import cn.ruicz.basecore.crash.CaocConfig;
import cn.ruicz.basecore.util.ActivityUtils;
import cn.ruicz.basecore.utils.KLog;

/**
 * Created by goldze on 2017/7/16.
 */

public class AppApplication extends BaseApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        //是否开启打印日志
        KLog.init(BuildConfig.DEBUG);
        //初始化全局异常崩溃
        initCrash();
        //内存泄漏检测
//        if (!LeakCanary.isInAnalyzerProcess(this)) {
//            LeakCanary.install(this);
//        }
    }

    private void initCrash() {
        try {
            CaocConfig.Builder.create()
                    .backgroundMode(CaocConfig.BACKGROUND_MODE_SILENT) //背景模式,开启沉浸式
                    .enabled(true) //是否启动全局异常捕获
                    .showErrorDetails(true) //是否显示错误详细信息
                    .showRestartButton(true) //是否显示重启按钮
                    .trackActivities(true) //是否跟踪Activity
                    .minTimeBetweenCrashesMs(2000) //崩溃的间隔时间(毫秒)
                    .errorDrawable(R.mipmap.ic_launcher) //错误图标
                    .restartActivity((Class<? extends Activity>) Class.forName(ActivityUtils.getLauncherActivity())) //重新启动后的activity
    //                .errorActivity(YourCustomErrorActivity.class) //崩溃后的错误activity
    //                .eventListener(new YourCustomEventListener()) //崩溃后的错误监听
                    .apply();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
