package cn.ruicz.basecore;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;

import cn.ruicz.basecore.crash.CaocConfig;
import cn.ruicz.basecore.manager.SPManager;
import cn.ruicz.utilcode.util.ActivityUtils;
import cn.ruicz.utilcode.util.AppUtils;
import cn.ruicz.utilcode.util.Utils;
import me.yokeyword.fragmentation.BuildConfig;
import me.yokeyword.fragmentation.Fragmentation;
import me.yokeyword.fragmentation.helper.ExceptionHandler;

/**
 * Created by CLW on 2017/3/20.
 */

public class LibBase {

    public static LibBase INSTANCE;
    private Handler mHander;

    public LibBase(LibBaseBuilder builder){
        init(builder);
    }

    public static LibBaseBuilder builder(Context context){
        return new LibBaseBuilder(context);
    }

    public LibBase init(LibBaseBuilder builder){
        mHander = new Handler();

        // 初始化工具类框架
        Utils.init(builder.context);
        try {
            CaocConfig.Builder.create()
                    .backgroundMode(CaocConfig.BACKGROUND_MODE_SILENT) //背景模式,开启沉浸式
                    .enabled(true) //是否启动全局异常捕获
                    .showErrorDetails(true) //是否显示错误详细信息
                    .showRestartButton(true) //是否显示重启按钮
                    .trackActivities(true) //是否跟踪Activity
                    .minTimeBetweenCrashesMs(2000) //崩溃的间隔时间(毫秒)
                    .errorDrawable(R.mipmap.error) //错误图标
                    .restartActivity((Class<? extends Activity>) Class.forName(ActivityUtils.getLauncherActivity())) //重新启动后的activity
    //                .errorActivity(YourCustomErrorActivity.class) //崩溃后的错误activity
    //                .eventListener(new YourCustomEventListener()) //崩溃后的错误监听
                    .apply();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        // 初始化SharedPreference
        SPManager.INSTANCE.init(builder.spname);
        // 相册选择
//        IBoxingMediaLoader loader = new BoxingGlideLoader();
//        BoxingMediaLoader.getInstance().init(loader);

        if (builder.debug) {
            Fragmentation.builder()
                    // 设置 栈视图 模式为 悬浮球模式   SHAKE: 摇一摇唤出   NONE：隐藏
                    .stackViewMode(Fragmentation.BUBBLE)
                    // ture时，遇到异常："Can not perform this action after onSaveInstanceState!"时，会抛出
                    // false时，不会抛出，会捕获，可以在handleException()里监听到
                    .debug(BuildConfig.DEBUG)
                    // 线上环境时，可能会遇到上述异常，此时debug=false，不会抛出该异常（避免crash），会捕获
                    // 建议在回调处上传至我们的Crash检测服务器
                    .handleException(new ExceptionHandler() {
                        @Override
                        public void onException(Exception e) {
                            // 以Bugtags为例子: 手动把捕获到的 Exception 传到 Bugtags 后台。
                            // Bugtags.sendException(e);
                        }
                    })
                    .install();

        }
        return this;
    }

    public Handler getHandler() {
        return mHander;
    }

    public void postDelayed(Runnable runnable, long delayMillis) {
        if (runnable != null) {
            mHander.postDelayed(runnable, delayMillis);
        }
    }

    public void post(Runnable runnable) {
        if (runnable != null) {
            mHander.post(runnable);
        }
    }

    public static class LibBaseBuilder {

        private Context context;
        private String spname;
        private String logtag = "APP_TAG";
        private boolean debug = false;

        public LibBaseBuilder(Context context){
            this.context = context;
            this.spname = context.getPackageName();
        }

        public LibBaseBuilder spname(String spname){
            this.spname = spname;
            return this;
        }

        public LibBaseBuilder logtag(String logtag){
            this.logtag = logtag;
            return this;
        }

        public LibBaseBuilder debug(boolean debug){
            this.debug = debug;
            return this;
        }

        public LibBase install() {
            synchronized (LibBase.class) {
                if (LibBase.INSTANCE != null) {
                    throw new RuntimeException("Default instance already exists." +
                            " It may be only set once before it's used the first time to ensure consistent behavior.");
                }
                LibBase.INSTANCE = new LibBase(this);
                return LibBase.INSTANCE;
            }
        }
    }
}
