package cn.ruicz.basecore.http;

import android.arch.lifecycle.Lifecycle;
import android.text.TextUtils;

import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import cn.ruicz.basecore.http.factory.StringConverterFactory;
import cn.ruicz.basecore.utils.NetworkUtils;
import cn.ruicz.basecore.utils.Utils;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by CLW on 2017/3/19.
 * 接口请求工具类
 */

public class BaseHttpUtils {

    public static String rczToken;  // 睿策者Token
    public static String wxToken;    // 政务微信Token

    private static final ObservableTransformer schedulersTransformer = new ObservableTransformer() {
        @Override
        public ObservableSource apply(Observable upstream) {
            return upstream
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .unsubscribeOn(Schedulers.io());
        }
    };

    private static <T> ObservableTransformer<T, T> applySchedulers() {
        return (ObservableTransformer<T, T>) schedulersTransformer;
    }

    public static <T> SimpleObserver<T> toSubscribe(Observable<T> o, SimpleObserver<T> s) {
        o.compose(applySchedulers()).subscribe(s);
        return s;
    }

    public static <T> SimpleObserver<T> toSubscribe(Lifecycle lifecycle, Observable<T> o, SimpleObserver<T> s) {
        o.compose(applySchedulers())
                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(lifecycle)))
                .subscribe(s);
        return s;
    }


    public static <T> T createApiService(Class<T> cls, String baseUrl, Interceptor... interceptors){
        return createApiService(cls, baseUrl, false, 0, interceptors);
    }

    public static <T> T createApiService(Class<T> cls, String baseUrl, boolean isCache, int secend, Interceptor... interceptors){
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY); // 设置日志级别
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.addInterceptor(httpLoggingInterceptor) // 日志拦截器，打印请求日志
                .addInterceptor(new Interceptor() {
                    @Override public Response intercept(Chain chain) throws IOException {
                        Request.Builder builder = chain.request().newBuilder();
                        // 睿策者Token
                        if (!TextUtils.isEmpty(rczToken)) {
                            builder.header("Authorization", "bearer "+ rczToken);
                        }
                        // 政务微信Token
                        if (!TextUtils.isEmpty(wxToken)) {
                            builder.header("X-Auth0-Token", wxToken);
                        }
                        Request authorised = builder.build();
                        return chain.proceed(authorised);
                    }
                })
                .connectTimeout(120, TimeUnit.SECONDS)   // 连接超时时间
                .writeTimeout(180, TimeUnit.SECONDS)    // 写入服务器超时时间
                .readTimeout(180, TimeUnit.SECONDS);     // 读取数据超时时间

        if (isCache) {
            File cacheFile = new File(Utils.getApp().getExternalCacheDir(), "netcache");
            Cache cache = new Cache(cacheFile, 1024 * 1024 * 10);
            builder.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request request = chain.request();
                    if (!NetworkUtils.isConnected()) {
                        request = request.newBuilder()
                                .cacheControl(CacheControl.FORCE_CACHE)
                                .build();
                    }
                    Response response = chain.proceed(request);
                    if (NetworkUtils.isConnected()) {
                        // 有网络时 设置缓存超时时间0个小时
                        response.newBuilder()
                                .header("Cache-Control", "public, max-age=" + secend)
                                .removeHeader("Pragma")// 清除头信息，因为服务器如果不支持，会返回一些干扰信息，不清除下面无法生效
                                .build();
                    }
                    return response;
                }
            }).cache(cache);
        }

        // 添加拦截器
        for(Interceptor interceptor : interceptors){
            builder.addInterceptor(interceptor);
        }

        OkHttpClient okHttpClient = builder.build();

        /**
         * 注：addConverterFactory是有先后顺序的，如果有多个ConverterFactory都支持同一种类型，那么就是只有第一个才会被使用，
         * 而GsonConverterFactory是不判断是否支持的，所以这里交换了顺序还会有一个异常抛出，原因是类型不匹配。
         */
        Retrofit retrofit = new Retrofit
                .Builder()
                .client(okHttpClient)
                .baseUrl(baseUrl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())  // RxJava回调适配器
                .addConverterFactory(new StringConverterFactory())      // String转换器
                .addConverterFactory(GsonConverterFactory.create())     // Gson转换器
                .build();
        return retrofit.create(cls);
    }
}
