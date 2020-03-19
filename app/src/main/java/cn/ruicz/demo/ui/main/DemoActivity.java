package cn.ruicz.demo.ui.main;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.arch.lifecycle.Observer;
import android.databinding.Observable;
import android.support.annotation.Nullable;

import cn.ruicz.basecore.base.BaseViewModel;
import cn.ruicz.demo.BR;
import cn.ruicz.demo.R;
import cn.ruicz.demo.databinding.ActivityDemoBinding;
import com.tbruyelle.rxpermissions2.RxPermissions;


import cn.ruicz.basecore.utils.ToastUtils;
import cn.ruicz.demo.ui.tab_bar.fragment.RootTabBarFragment;
import io.reactivex.functions.Consumer;
import cn.ruicz.basecore.base.BaseActivity;
import cn.ruicz.basecore.http.download.ProgressCallBack;
import okhttp3.ResponseBody;

/**
 * Created by goldze on 2017/7/17.
 */

public class DemoActivity extends BaseActivity<ActivityDemoBinding, BaseViewModel> {

    @Override
    public int getLayoutId() {
        return R.layout.fragment_container;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void init() {
        loadRootFragment(R.id.fl_container, new DemoFragment());

        setSwipeBackEnable(false);
    }

}
