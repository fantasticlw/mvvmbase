package cn.ruicz.demo.ui.tab_bar.activity;

import cn.ruicz.demo.BR;
import cn.ruicz.demo.R;
import cn.ruicz.demo.ui.tab_bar.fragment.RootTabBarFragment;

import cn.ruicz.basecore.base.BaseActivity;

public class MainActivity extends BaseActivity {

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
        loadRootFragment(R.id.fl_container, new RootTabBarFragment());
    }
}