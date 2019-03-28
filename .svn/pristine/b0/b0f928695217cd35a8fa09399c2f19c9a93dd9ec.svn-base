package com.goldze.mvvmhabit.ui.login;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.databinding.ObservableBoolean;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;

import com.goldze.mvvmhabit.ui.main.DemoActivity;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import java.util.concurrent.TimeUnit;

import cn.ruicz.basecore.utils.ToastUtils;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import cn.ruicz.basecore.base.BaseViewModel;
import cn.ruicz.basecore.binding.command.BindingAction;
import cn.ruicz.basecore.binding.command.BindingCommand;
import cn.ruicz.basecore.binding.command.BindingConsumer;
import cn.ruicz.basecore.utils.RxUtils;

import static com.uber.autodispose.AutoDispose.autoDisposable;

/**
 * Created by goldze on 2017/7/17.
 */

public class LoginViewModel extends BaseViewModel {
    //用户名的绑定
    public MutableLiveData<String> userName = new MutableLiveData<>();
    //密码的绑定
    public MutableLiveData<String> password = new MutableLiveData<>();
    public MutableLiveData<NamePwd> namePwd = new MutableLiveData<>();
    //用户名清除按钮的显示隐藏绑定
    public MutableLiveData<Integer> clearBtnVisibility = new MutableLiveData<>();
    //封装一个界面发生改变的观察者
    public UIChangeObservable uc = new UIChangeObservable();

    public class UIChangeObservable {
        //密码开关观察者
        public ObservableBoolean pSwitchObservable = new ObservableBoolean(false);
    }

    public LoginViewModel(@NonNull Application application) {
        super(application);
    }

    //清除用户名的点击事件, 逻辑从View层转换到ViewModel层
    public BindingCommand clearUserNameOnClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            userName.setValue("");
        }
    });
    //密码显示开关  (你可以尝试着狂按这个按钮,会发现它有防多次点击的功能)
    public BindingCommand passwordShowSwitchOnClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            //让观察者的数据改变,逻辑从ViewModel层转到View层，在View层的监听则会被调用
            uc.pSwitchObservable.set(!uc.pSwitchObservable.get());
        }
    });
    //用户名输入框焦点改变的回调事件
    public BindingCommand<Boolean> onFocusChangeCommand = new BindingCommand<>(new BindingConsumer<Boolean>() {
        @Override
        public void call(Boolean hasFocus) {
            if (hasFocus) {
                clearBtnVisibility.setValue(View.VISIBLE);
            } else {
                clearBtnVisibility.setValue(View.INVISIBLE);
            }
        }
    });
    //登录按钮的点击事件
    public BindingCommand loginOnClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            login();
        }
    });

    /**
     * 网络模拟一个登陆操作
     **/
    private void login() {
        if (TextUtils.isEmpty(userName.getValue())) {
            ToastUtils.showShort("请输入账号！");
            return;
        }
        if (TextUtils.isEmpty(password.getValue())) {
            ToastUtils.showShort("请输入密码！");
            return;
        }
        //RaJava模拟一个延迟操作
        Observable.just("")
                .delay(3, TimeUnit.SECONDS) //延迟3秒
                .compose(RxUtils.schedulersTransformer()) //线程调度
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        LoginViewModel.this.showDialog();
                    }
                })
                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(getLifecycleOwner())))
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        LoginViewModel.this.dismissDialog();
                        //进入DemoActivity页面
                        LoginViewModel.this.startActivity(DemoActivity.class);
                        //关闭页面
                        LoginViewModel.this.finish();
                    }
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
