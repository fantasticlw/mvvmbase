package cn.ruicz.basecore.zwwx;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.Date;

import cn.ruicz.basecore.BuildConfig;
import cn.ruicz.basecore.R;
import cn.ruicz.basecore.base.AppManager;
import cn.ruicz.basecore.base.BaseActivity;
import cn.ruicz.basecore.base.BaseViewModel;
import cn.ruicz.basecore.databinding.CommonActBinding;
import cn.ruicz.basecore.http.BaseHttpManager;
import cn.ruicz.basecore.http.BaseHttpUtils;
import cn.ruicz.basecore.http.DialogObserver;
import cn.ruicz.basecore.http.SimpleObserver;
import cn.ruicz.basecore.utils.TimeUtils;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * cn.meeting.ruicz
 *
 * @author xyq
 * @time 2019-2-23 15:26
 * Remark ----------跳转页Act-----------------
 */
public class CommonActivity extends BaseActivity<CommonActBinding, BaseViewModel> {

    private String acode;

    private String params;

    public static String rzcToken;
    public static String token;


    @Override
    public int getLayoutId() {
        return R.layout.common_act;
    }

    @Override
    public int initVariableId() {
        return 0;
    }

    @Override
    public void init() {
        checkPermission();
//        checkAcode();
    }

    @SuppressLint("CheckResult")
    public void checkPermission(){
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions
                .request(Manifest.permission.CAMERA
                        , Manifest.permission.READ_EXTERNAL_STORAGE
                        , Manifest.permission.WRITE_EXTERNAL_STORAGE
                        , Manifest.permission.READ_PHONE_STATE)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(@NonNull Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            checkAcode();
                        } else {
                            showShortToast("请先授权，再使用");
                            finish();
                            if (!BuildConfig.DEBUG){
                                AppManager.getAppManager().AppExit();
                            }
                        }
                    }
                });
    }

    /**
     * 验证acode
     */
    private void checkAcode() {
        String launchParam = getIntent().getStringExtra("launchParam");
        if (launchParam != null) {
            if (launchParam.contains("=")) {
                acode = launchParam.substring(launchParam.indexOf("=") + 1);
                getAccessToken();
            } else if (launchParam.contains("|")) {
                acode = launchParam.substring(launchParam.indexOf("|") + 1);
                params = launchParam.substring(0, launchParam.indexOf("|"));
                getToken();
            }

        } else {
          //  showDialog("请从政务微信进入！");
            AlertDialog.Builder builder = new AlertDialog.Builder(this)
                    .setMessage("请从政务微信进入!").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                            AppManager.getAppManager().AppExit();
                        }
                    });
            builder.create().show();
        }
    }

    /**
     * 获取睿策者新闻平台Accesstoken
     */
    private void getAccessToken() {
        if (TextUtils.isEmpty(ZwwxInfoManager.getBaseUrl())){
            getNewUserInfo();
            return;
        }
        BaseHttpManager.newInstance().getRzcToken(getLifecycle(),acode, new SimpleObserver<>(this, new Consumer<String>() {
            @Override
            public void accept(String result) throws Exception {
                JSONObject jsonObject = JSONObject.parseObject(result);
                if (jsonObject.containsKey("access_token")) {
                    rzcToken = jsonObject.getString("access_token");
                    BaseHttpUtils.rczToken = rzcToken;
                } else if (jsonObject.containsKey("returnMsg")) {
                    showShortToast(jsonObject.getString("returnMsg"));
                }
                getNewUserInfo();
            }
        }));
    }


    /**
     * 获取睿策者旧版token
     */
    private void getToken() {
        if (TextUtils.isEmpty(ZwwxInfoManager.getBaseUrl())){
            getUserInfo();
            return;
        }
        BaseHttpManager.newInstance().getRzcToken(getLifecycle(),acode, new SimpleObserver<>(this, new Consumer<String>() {
            @Override
            public void accept(String result) throws Exception {
                JSONObject jsonObject = JSONObject.parseObject(result);
                if (jsonObject.containsKey("body")) {
                    rzcToken = jsonObject.getString("body");
                    BaseHttpUtils.rczToken = rzcToken;
                } else if (jsonObject.containsKey("returnMsg")) {
                    showShortToast(jsonObject.getString("returnMsg"));
                }
                getUserInfo();
            }
        }));
    }

    /**
     * 获取新版用户信息
     */
    public void getNewUserInfo() {
        BaseHttpManager.newInstance().token(getLifecycle(),ZwwxInfoManager.getAppid(), ZwwxInfoManager.getSecret(), new SimpleObserver<>(this, new Consumer<String>() {
            @Override
            public void accept(@NonNull String result) throws Exception {
                org.json.JSONObject jsonObject = new org.json.JSONObject(result);
                if ("0".equals(jsonObject.getString("code"))) {
                    token = jsonObject.getString("data");
                    BaseHttpUtils.wxToken = token;
                    BaseHttpManager.newInstance().getUser(getLifecycle(),acode,  new DialogObserver<>(CommonActivity.this, new Consumer<String>() {
                        @Override
                        public void accept(@NonNull String result) throws Exception {

                            ZwwxUserInfo zwwxUserInfoBean = new Gson().fromJson(result, ZwwxUserInfo.class);
                            ZwwxInfoManager.initZwwxUserInfo(zwwxUserInfoBean);
                            startActivity(ZwwxInfoManager.getLaunchActivity());
                            finish();
                        }
                    }));
                }
            }
        }));
    }


    /**
     * 获取旧版用户信息
     */
    public void getUserInfo() {
        BaseHttpManager.newInstance().getToken(getLifecycle(),new SimpleObserver<>(this, new Consumer<JsonObject>() {
            @Override
            public void accept(@NonNull JsonObject jsonObject) throws Exception {
                if (jsonObject.has("code") && TextUtils.equals(jsonObject.get("code").getAsString(), "0")) {
//                    final TokenBean tokenBean = new TokenBean();
//                    tokenBean.access_token = jsonObject.get("data").getAsJsonObject().get("token").getAsString();
//                    tokenBean.expires_in = jsonObject.get("data").getAsJsonObject().get("expiresIn").getAsInt();
//                    LocalDataManager.INSTANCE.saveTokenInfo(tokenBean);

                    BaseHttpManager.newInstance().getUser(getLifecycle(),jsonObject.get("data").getAsJsonObject().get("token").getAsString(), acode, "0000070", new SimpleObserver<>(CommonActivity.this, new Consumer<JsonObject>() {
                        @Override
                        public void accept(@NonNull JsonObject result) throws Exception {
                            if (result.has("code") && TextUtils.equals(result.get("code").getAsString(), "0")) {

                                if (!TextUtils.isEmpty(params)) {

                                    String mmid = params.substring(0, params.indexOf("_"));
                                    String seTime = params.substring(params.indexOf("_") + 1, params.length());

                                    Date sEnd = TimeUtils.string2Date(seTime);
                                    //Date sNow = TimeUtils.getNowTimeDate();
                                    Date sNow = TimeUtils.getNowDate();

                                    int outDate;
                                    if (sNow.before(sEnd)) {
                                        outDate = 1;
                                    } else {
                                        outDate = 0;
                                    }


//                                    startActivity(MapMainActivity.class);
                                    finish();
                                }

                            } else {
                                if (result.has("desc")) {
                                    showDialog(result.get("desc").getAsString());
                                }
                            }
                        }
                    }));
                } else {
                    if (jsonObject.has("desc")) {
                        showDialog(jsonObject.get("desc").getAsString());
                    }
                }
            }
        }));
    }


}
