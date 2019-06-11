package cn.ruicz.demo.ui.tab_bar.fragment;

import android.view.View;

import cn.ruicz.basecore.base.BaseFragment;
import cn.ruicz.basecore.base.BaseViewModel;
import cn.ruicz.demo.BR;
import cn.ruicz.demo.R;
import cn.ruicz.demo.databinding.FragmentTabBar5Binding;

/**
 * Created by goldze on 2018/7/18.
 */

public class TabBar5Fragment extends BaseFragment<FragmentTabBar5Binding, BaseViewModel> {

    @Override
    public int getLayoutId() {
        return R.layout.fragment_tab_bar_5;
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
//                start(new TabBar5Fragment());
            }
        });
    }
}
