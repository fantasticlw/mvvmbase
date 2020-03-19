package cn.ruicz.demo.ui.main;

import android.Manifest;
import android.annotation.SuppressLint;
import android.arch.lifecycle.Observer;
import android.databinding.Observable;
import android.support.annotation.Nullable;

import com.tbruyelle.rxpermissions2.RxPermissions;

import cn.ruicz.basecore.base.BaseFragment;
import cn.ruicz.basecore.utils.ToastUtils;
import cn.ruicz.demo.R;
import cn.ruicz.demo.databinding.ActivityDemoBinding;

public class DemoFragment extends BaseFragment<ActivityDemoBinding, DemoViewModel> {

    @Override
    public int getLayoutId() {
        return R.layout.activity_demo;
    }

    @Override
    public int initVariableId() {
        return cn.ruicz.demo.BR.viewModel;
    }

    @Override
    public void init() {
        setSwipeBackEnable(false);
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
    @SuppressLint("CheckResult")
    private void requestCameraPermissions() {
        //请求打开相机权限
        RxPermissions rxPermissions = new RxPermissions(getActivity());
        rxPermissions.request(Manifest.permission.CAMERA)
                .subscribe(aBoolean -> {
                    if (aBoolean) {
                        ToastUtils.showShort("相机权限已经打开，直接跳入相机");
                    } else {
                        ToastUtils.showShort("权限被拒绝");
                    }
                });
    }
}
