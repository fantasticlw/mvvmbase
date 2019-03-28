package cn.ruicz.demo.ui.tab_bar.fragment;

import android.view.View;

import cn.ruicz.demo.BR;
import cn.ruicz.demo.R;
import cn.ruicz.demo.databinding.FragmentTabBar1Binding;

import cn.ruicz.basecore.base.BaseChildFragment;
import cn.ruicz.basecore.base.BaseViewModel;

/**
 * Created by goldze on 2018/7/18.
 */

public class TabBar1Fragment extends BaseChildFragment<FragmentTabBar1Binding, BaseViewModel> {

    @Override
    public int getLayoutId() {
        return R.layout.fragment_tab_bar_1;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void init() {
        binding.textview1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start(new TabBar1Fragment());
            }
        });
    }
}
