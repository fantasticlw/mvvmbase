package cn.ruicz.demo.app;

import cn.ruicz.basecore.base.BaseApplication;
import cn.ruicz.basecore.manager.SwipeBackManager;
import cn.ruicz.basecore.utils.KLog;
import cn.ruicz.basecore.zwwx.ZwwxInfoManager;
import cn.ruicz.demo.BuildConfig;

/**
 * Created by goldze on 2017/7/16.
 */

public class App extends BaseApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        //是否开启打印日志
        KLog.init(BuildConfig.DEBUG);
        //内存泄漏检测
//        if (!LeakCanary.isInAnalyzerProcess(this)) {
//            LeakCanary.install(this);
//        }

        // 初始化政务微信接口AppId和Secret
        ZwwxInfoManager.initAppIdAndSecret(BuildConfig.AppId, BuildConfig.Secret);
        SwipeBackManager.getInstance().setEnable(true);
    }
}
