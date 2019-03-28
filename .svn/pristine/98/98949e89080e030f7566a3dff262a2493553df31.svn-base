package com.goldze.mvvmhabit.ui.viewpager.vm;

import android.support.annotation.NonNull;

import cn.ruicz.basecore.base.BaseViewModel;
import cn.ruicz.basecore.base.ItemViewModel;
import cn.ruicz.basecore.binding.command.BindingAction;
import cn.ruicz.basecore.binding.command.BindingCommand;
import cn.ruicz.basecore.bus.event.SingleLiveEvent;

/**
 * Created by goldze on 2018/7/18.
 */

public class ViewPagerItemViewModel extends ItemViewModel {
    public String text;
    public SingleLiveEvent<String> clickEvent = new SingleLiveEvent();

    public ViewPagerItemViewModel(@NonNull BaseViewModel viewModel, String text) {
        super(viewModel);
        this.text = text;
    }

    public BindingCommand onItemClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            //点击之后将逻辑转到adapter中处理
            clickEvent.setValue(text);
        }
    });
}
