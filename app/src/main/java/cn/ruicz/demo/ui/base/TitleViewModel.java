package cn.ruicz.demo.ui.base;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.support.annotation.NonNull;
import android.view.View;

import cn.ruicz.basecore.base.AppManager;
import cn.ruicz.basecore.binding.command.BindingAction;
import cn.ruicz.basecore.binding.command.BindingCommand;

/**
 * 对应include标题的ViewModel
 * 所有例子仅做参考,业务多种多样,可能我这里写的例子和你的需求不同，理解如何使用才最重要。
 * Toolbar的封装方式有很多种，具体封装需根据项目实际业务和习惯来编写
 * Created by goldze on 2018/7/26.
 */

public class TitleViewModel extends AndroidViewModel {
    public ObservableField<String> titleText = new ObservableField<>("");
    public ObservableField<String> rightText = new ObservableField<>("");
    public ObservableInt rightTextVisibility = new ObservableInt(View.GONE);

    public TitleViewModel(@NonNull Application application) {
        super(application);
    }

    //点击返回
    public BindingCommand backOnClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            //统一处理返回事件
            AppManager.getAppManager().currentActivity().onBackPressed();
        }
    });
    //右边文字点击事件
    public BindingCommand rightTextOnClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            //统一处理右上角按钮事件。
        }
    });
}
