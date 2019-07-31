package cn.ruicz.basecore.zwwx;

import android.Manifest;
import android.annotation.SuppressLint;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.tbruyelle.rxpermissions2.RxPermissions;

import cn.ruicz.basecore.R;
import cn.ruicz.basecore.base.AppManager;
import cn.ruicz.basecore.base.BaseActivity;
import cn.ruicz.basecore.base.BaseViewModel;
import cn.ruicz.basecore.databinding.CommonActBinding;
import cn.ruicz.basecore.http.BaseHttpManager;
import cn.ruicz.basecore.http.BaseHttpUtils;
import cn.ruicz.basecore.http.DialogObserver;
import cn.ruicz.basecore.http.SimpleObserver;
import cn.ruicz.basecore.utils.MaterialDialogUtils;
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
                            MaterialDialogUtils.showBasicDialogNoCancel(CommonActivity.this, null,"请先授权，再使用").dismissListener(dialog -> {
                                finish();
                                AppManager.getAppManager().AppExit();
                            }).show();
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
            if (launchParam.contains("=")) {// 从政务微信工作台界面进入
                acode = launchParam.substring(launchParam.indexOf("=") + 1);
                if (ZwwxInfoManager.isOldToken()){
                    getToken();
                }else {
                    getAccessToken();
                }
            } else if (launchParam.contains("|")) { // 从政务微信消息界面进入
                acode = launchParam.substring(launchParam.indexOf("|") + 1);
                params = launchParam.substring(0, launchParam.indexOf("|"));
                getToken();
            }

        } else {
          //  showDialog("请从政务微信进入！");
            MaterialDialogUtils.showBasicDialogNoCancel(this, null,"请从政务微信进入！").dismissListener(dialog -> {
                finish();
                AppManager.getAppManager().AppExit();
            }).show();
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
        BaseHttpManager.newInstance().token(getLifecycle(),ZwwxInfoManager.getAppid(), ZwwxInfoManager.getSecret(), new SimpleObserver<>(this, new Consumer<JSONObject>() {
            @Override
            public void accept(@NonNull JSONObject jsonObject) throws Exception {
                if (TextUtils.equals(jsonObject.getString("code"), "0")) {
                    token = jsonObject.getString("data");
                    BaseHttpUtils.wxToken = token;
                    BaseHttpManager.newInstance().getUser(getLifecycle(),acode,  new DialogObserver<>(CommonActivity.this, new Consumer<String>() {
                        @Override
                        public void accept(@NonNull String result) throws Exception {

                            ZwwxUserInfo zwwxUserInfoBean = new Gson().fromJson(result, ZwwxUserInfo.class);
                            ZwwxInfoManager.initZwwxUserInfo(zwwxUserInfoBean);
//                            startActivity(MapMainActivity.class);
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

                    BaseHttpManager.newInstance().getUser(getLifecycle(),jsonObject.get("data").getAsJsonObject().get("token").getAsString(), acode, ZwwxInfoManager.getAppid(), new SimpleObserver<>(CommonActivity.this, new Consumer<JSONObject>() {
                        @Override
                        public void accept(@NonNull JSONObject result) throws Exception {
                            if (TextUtils.equals(result.getString("code"), "0")) {

                                RczUserInfo userInfo = JSON.parseObject(result.getString("data"), RczUserInfo.class);
                                ZwwxInfoManager.initUserInfo(userInfo);

                                finish();
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
