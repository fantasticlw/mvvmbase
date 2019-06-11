package cn.ruicz.basecore.utils;

import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Field;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.ruicz.basecore.BuildConfig;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by ydjw on 2018/5/30.
 */

public class LogHelper {
    private LogST logST;
    private LogHelper(){
        logST = new LogST();
    }

    public static LogBuilder build(){
        return new LogBuilder();
    }

    public void uploadLog(){

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        final OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(loggingInterceptor).build();
//        FormBody formBody = new FormBody.Builder()
//                .add("grant_type", "client_credentials")
//                .add("client_id", "10000")
//                .build();
//        Request request = new Request.Builder().url
//                (String.format("http://%s:%s/platform/oauth/token", AreaBaseSetting.getInstance().getServiceIP(), AreaBaseSetting.PortSetting.getAppServerPort())).post(formBody).build();
//        okHttpClient.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                e.printStackTrace();
//            }
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                String ss = response.body().string();
//                ss.toString();
//                JsonObject jsonObject = new JsonParser().parse(ss).getAsJsonObject();
//                String token = jsonObject.get("access_token").getAsString();

                logST.requestid = UUID.randomUUID().toString();
                logST.sessionid = UUID.randomUUID().toString();
                logST.terminalip = getIPAddress(true);
                logST.sourceip = getIPAddress(true);
                logST.time = System.currentTimeMillis()+"";

                RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), new Gson().toJson(logST));
                Request request = new Request.Builder().url("http://127.0.0.1:8181/platform/api/mp/stlog/upLoadStLog").post(requestBody).build();
                okHttpClient.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String ss = response.body().string();
                        Log.d(LogHelper.class.getSimpleName(), ss);
                    }
                });
//            }
//        });


    }



    public static class LogBuilder{
        LogHelper logHelper;
        Map<String,String> formatMap;
        Map<String,String> responseMap;
        private LogBuilder(){
            logHelper = new LogHelper();
        }

//        public LogBuilder setResultSuccess(){
//            logHelper.logST.result = "成功";
//            return this;
//        }
//
//        public LogBuilder setResultFail(){
//            logHelper.logST.result = "失败";
//            return this;
//        }

        public LogBuilder setCardno(String cardno){
            logHelper.logST.cardno = cardno;
            return this;
        }

        public LogBuilder setLogType(LogType logType){
            logHelper.logST.logtype = logType.value;
            return this;
        }

        public LogBuilder setModule(String module){
            logHelper.logST.module = module;
            return this;
        }

        public LogBuilder setFormatParam(Map formatParam){
            logHelper.logST.formatparam = new JSONObject(formatParam).toString();
            return this;
        }

        public LogBuilder setFormatParamEntity(Object object){
            logHelper.logST.formatparam = getKeyValue(object).toString();
            return this;
        }

        public LogBuilder addFormatParam(String key, String value){
            if (formatMap == null){
                formatMap = new HashMap<>();
            }
            formatMap.put(key, value);
            return this;
        }

        public LogBuilder addResponseParam(String key, String value){
            if (responseMap == null){
                responseMap = new HashMap<>();
            }
            responseMap.put(key, value);
            return this;
        }

        public LogBuilder setUrl(String url){
            if (!TextUtils.isEmpty(url)) {
                logHelper.logST.url = url;
                logHelper.logST.setContent(url);
                logHelper.logST.setSourceIp(url);
                logHelper.logST.setSourcePost(url);
            }
            return this;
        }

        public LogBuilder setResponse(Map response){
            logHelper.logST.response = new JSONObject(response).toString();
            return this;
        }

        public LogBuilder setResponseEntity(Object object){
            JSONArray jsonArray = new JSONArray();
            if (object instanceof List){
                List list = (List) object;
                for (Object item : list){
                    jsonArray.put(getKeyValue(item));
                }
                logHelper.logST.response = jsonArray.toString();
            }else {
                jsonArray.put(getKeyValue(object));
                logHelper.logST.response = jsonArray.toString();
            }
            return this;
        }

        public LogBuilder setResponse(List<Map> response){
            logHelper.logST.response = new JSONArray(response).toString();
            return this;
        }

        public LogBuilder setDestIp(String destIp){
            logHelper.logST.destip = destIp;
            return this;
        }

        public LogBuilder setDestPost(String destPost){
            logHelper.logST.destport = destPost;
            return this;
        }

        public LogBuilder setSource(Source source){
            logHelper.logST.source = source.value;
            return this;
        }

        public void uploadLog(){
            try {
                // 如果不是政务微信模式，则不上传日志
                if (!BuildConfig.IS_ZWWX){
                    return;
                }

                if (formatMap != null){
                    logHelper.logST.formatparam = new JSONArray().put(new JSONObject(formatMap)).toString();
                }
                if (responseMap != null){
                    logHelper.logST.response = new JSONArray().put(new JSONObject(responseMap)).toString();
                }
                logHelper.uploadLog();
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        private JSONObject getKeyValue(Object object){
            JSONObject jsonObject = new JSONObject();
            Class<?> clazz = object.getClass();
            Field[] fields = clazz.getDeclaredFields();//获得声明的成员变量
            for (Field field : fields) {
                //判断是否有注解
                try {
                    if (field.getAnnotations() != null) {
                        if (field.isAnnotationPresent(LogInjection.class)) {//如果属于这个注解
                            //为这个控件设置属性
                            field.setAccessible(true);//允许修改反射属性
                            LogInjection inject = field.getAnnotation(LogInjection.class);
                            String value = String.valueOf(field.get(object));
                            jsonObject.put(inject.value(), value);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return jsonObject;
        }
    }

    public class LogST {
        public String policeid = "";    // 警号
        public String sn = "";      // 序列号
        public String cardno = "";  // 身份证号
        public String requestid = "";   // 请求ID，随机数
        public String sessionid = "";   // 请求ID，随机数
        public String terminalip = "";  // 手机端IP
        public String source = "217";   // APP对应source
        public String logtype;      // 日志类型，增删改查
        public String module = "";  // 模块名称
        public String params = "";  // 请求参数
        public String formatparam = ""; // 格式化请求参数
        public String url = "";     // 请求的URL
        public String uri = "";     //
        public String content = ""; // 请求的api内容
        public String response = "";    // 响应参数
        public String responsetype = "1";   // 返回类型
        public String responsetime = "";    // 返回时间
        public String result = "成功";    // 返回结果
        public String errorcode = "";   // 如果请求失败，错误代码
        public String errorlog = "";    // 如果请求失败，错误日志
        public String sourceip = "";    //  请求的源ip，一般与终端ip一致，若经过多层转发，可能会是代理的ip
        public String sourceport = "";  // 请求源端口
        public String destip = "68.156.103.18"; // 服务的ip
        public String destport = "80";  // 服务的端口
        public String time = "";    // 时间戳，格式如1519978042000

        public String setSourceIp(String url){
            Pattern p= Pattern.compile("\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}");
            Matcher m=p.matcher(url);
            if(m.find()){
                sourceip = m.group();
                return sourceip;
            }
            return "";
        }

        public String setSourcePost(String url){
            Pattern p= Pattern.compile(":\\d{1,5}");
            Matcher m=p.matcher(url);
            if(m.find()){
                sourceport = m.group().replace(":","");
                return sourceport;
            }
            return "";
        }

        public String setContent(String url){
            Pattern p= Pattern.compile("http://\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}:\\d{1,5}");
            Matcher m=p.matcher(url);
            if(m.find()){
                content = url.replace(m.group(), "");
            }else {
                p = Pattern.compile("http://\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}");
                m = p.matcher(url);
                if (m.find()) {
                    content = url.replace(m.group(), "");
                }
            }
            return content;
        }


    }

    public enum LogType {

        Login("0"), Search("1"), New("2"), Update("3"), Delete("4"), Logout("5"), Else("6");

        private String value;

        LogType(String value){
            this.value = value;
        }

        public String getValue(){
            return value;
        }
    }

    public enum Source{

        YDOA("217"),// 移动OA
        DBGW("218"),// 待办公文
        DSJCX("219"),// 大数据查询
        WZXW("220"),// 网站新闻
        ZBLD("221"),// 领导值班
        DSJYJ("222"),// 大数据预警
        DXFS("223"),// 短信发送
        CLHC("224"),// 车辆核查
        HYFK("225"),// 惠眼封控
        HYAP("226"),// 会议安排
        JJZF("227"),// 交警执法
        RYHC("228"),// 人员核查
        SJBB("230"),// 数据报表
        SSP("231"),// 随手拍
        TYSC("232"),// 天云涉车
        WP("233"),// 网盘
        WZCX("234"),// 违章查询
        XFZF("235"),// 消防执法
        XFGL("236"),// 巡防管理
        XFRZ("237"),// 巡防日志
        YSCJ("238"),// 要素采集
        YQRD("239"),// 舆情热点
        ZXCX("240"),// 专项查询
        ZASP("241");// 治安视频

        private String value;

        Source(String value){
            this.value = value;
        }

        public String getValue(){
            return value;
        }
    }

    /**
     * 获取IP地址
     * <p>需添加权限 {@code <uses-permission android:name="android.permission.INTERNET"/>}</p>
     *
     * @param useIPv4 是否用IPv4
     * @return IP地址
     */
    public static String getIPAddress(boolean useIPv4) {
        try {
            for (Enumeration<NetworkInterface> nis = NetworkInterface.getNetworkInterfaces(); nis.hasMoreElements(); ) {
                NetworkInterface ni = nis.nextElement();
                // 防止小米手机返回10.0.2.15
                if (!ni.isUp()) continue;
                for (Enumeration<InetAddress> addresses = ni.getInetAddresses(); addresses.hasMoreElements(); ) {
                    InetAddress inetAddress = addresses.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        String hostAddress = inetAddress.getHostAddress();
                        boolean isIPv4 = hostAddress.indexOf(':') < 0;
                        if (useIPv4) {
                            if (isIPv4) return hostAddress;
                        } else {
                            if (!isIPv4) {
                                int index = hostAddress.indexOf('%');
                                return index < 0 ? hostAddress.toUpperCase() : hostAddress.substring(0, index).toUpperCase();
                            }
                        }
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return null;
    }
}
