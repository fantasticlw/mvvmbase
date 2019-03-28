package cn.ruicz.demo.ui.tab_bar.fragment;

import android.graphics.Color;
import android.support.v4.app.FragmentTransaction;

import cn.ruicz.demo.BR;
import cn.ruicz.demo.R;
import cn.ruicz.demo.databinding.ActivityTabBarBinding;

import cn.ruicz.basecore.base.BaseChildFragment;
import cn.ruicz.basecore.base.BaseFragment;
import cn.ruicz.basecore.base.BaseViewModel;
import me.majiajie.pagerbottomtabstrip.NavigationController;
import me.majiajie.pagerbottomtabstrip.item.BaseTabItem;
import me.majiajie.pagerbottomtabstrip.item.NormalItemView;
import me.majiajie.pagerbottomtabstrip.listener.OnTabItemSelectedListener;

/**
 * 底部tab按钮的例子
 * 所有例子仅做参考,理解如何使用才最重要。
 * Created by goldze on 2018/7/18.
 */

public class RootTabBarFragment extends BaseFragment<ActivityTabBarBinding, BaseViewModel> {
    private BaseChildFragment[] mFragments = new BaseChildFragment[4];

    @Override
    public int getLayoutId() {
        return R.layout.activity_tab_bar;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void init() {
        //初始化Fragment
        initFragment();
        //初始化底部Button
        initBottomTab();
    }

    private void initFragment() {
        mFragments[0] = new TabBar1Fragment();
        mFragments[1] = new TabBar2Fragment();
        mFragments[2] = new TabBar3Fragment();
        mFragments[3] = new TabBar4Fragment();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.frameLayout, mFragments[0]);
        transaction.commitAllowingStateLoss();
    }

    private void initBottomTab() {
        NavigationController navigationController = binding.pagerBottomTab.custom()
                .addItem(newItem(R.mipmap.yingyong, R.mipmap.yingyong,"应用"))
                .addItem(newItem(R.mipmap.huanzhe, R.mipmap.huanzhe,"工作"))
                .addItem(newItem(R.mipmap.xiaoxi_select, R.mipmap.xiaoxi_select, "消息"))
                .addItem(newItem(R.mipmap.wode_select, R.mipmap.wode_select, "我的"))
                .build();
        //底部按钮的点击事件监听
        navigationController.addTabItemSelectedListener(new OnTabItemSelectedListener() {
            @Override
            public void onSelected(int index, int old) {
                FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
                if (!mFragments[index].isAdded()){
                    transaction.add(R.id.frameLayout, mFragments[index]);
                }
                transaction.hide(mFragments[old]);
                transaction.show(mFragments[index]);
                transaction.commitAllowingStateLoss();
            }

            @Override
            public void onRepeat(int index) {
            }
        });
    }

    //创建一个Item
    private BaseTabItem newItem(int drawable, int checkedDrawable, String text){
        NormalItemView normalItemView = new NormalItemView(getActivity());
        normalItemView.initialize(drawable,checkedDrawable,text);
        normalItemView.setTextDefaultColor(Color.GRAY);
        normalItemView.setTextCheckedColor(0xFF009688);
        return normalItemView;
    }
}
