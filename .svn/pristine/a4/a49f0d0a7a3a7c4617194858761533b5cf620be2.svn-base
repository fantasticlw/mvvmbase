package cn.ruicz.demo.ui.network.detail;

import android.os.Bundle;

import cn.ruicz.demo.BR;
import cn.ruicz.demo.R;
import cn.ruicz.demo.databinding.FragmentDetailBinding;
import cn.ruicz.demo.entity.DemoEntity;

import cn.ruicz.basecore.base.BaseFragment;

/**
 * Created by goldze on 2017/7/17.
 * 详情界面
 */

public class DetailFragment extends BaseFragment<FragmentDetailBinding, DetailViewModel> {

    private DemoEntity.ItemsEntity entity;


    @Override
    public int getLayoutId() {
        return R.layout.fragment_detail;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void init() {
        //获取列表传入的实体
        Bundle mBundle = getArguments();
        if (mBundle != null) {
            entity = mBundle.getParcelable("entity");
        }
        viewModel.setDemoEntity(entity);
    }
}
