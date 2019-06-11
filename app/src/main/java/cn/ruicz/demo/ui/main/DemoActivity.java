package cn.ruicz.demo.ui.main;

import android.Manifest;
import android.app.ProgressDialog;
import android.arch.lifecycle.Observer;
import android.databinding.Observable;
import android.support.annotation.Nullable;

import cn.ruicz.demo.BR;
import cn.ruicz.demo.R;
import cn.ruicz.demo.databinding.ActivityDemoBinding;
import com.tbruyelle.rxpermissions2.RxPermissions;


import cn.ruicz.basecore.utils.ToastUtils;
import io.reactivex.functions.Consumer;
import cn.ruicz.basecore.base.BaseActivity;
import cn.ruicz.basecore.http.download.ProgressCallBack;
import okhttp3.ResponseBody;

/**
 * Created by goldze on 2017/7/17.
 */

public class DemoActivity extends BaseActivity<ActivityDemoBinding, DemoViewModel> {

    @Override
    public int getLayoutId() {
        return R.layout.activity_demo;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void init() {

    }

    @Override
    public void initViewObservable() {
        //注册监听相机权限的请求
        viewModel.requestCameraPermissions.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable observable, int i) {
                requestCameraPermissions();
            }
        });
        //注册文件下载的监听
        viewModel.loadUrl.observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String url) {
            }
        });
    }

    /**
     * 请求相机权限
     */
    private void requestCameraPermissions() {
        //请求打开相机权限
        RxPermissions rxPermissions = new RxPermissions(DemoActivity.this);
        rxPermissions.request(Manifest.permission.CAMERA)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            ToastUtils.showShort("相机权限已经打开，直接跳入相机");
                        } else {
                            ToastUtils.showShort("权限被拒绝");
                        }
                    }
                });
    }

}
