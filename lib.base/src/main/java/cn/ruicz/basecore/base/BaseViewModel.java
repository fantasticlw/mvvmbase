package cn.ruicz.basecore.base;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import cn.ruicz.basecore.bus.event.SingleLiveEvent;
import me.yokeyword.fragmentation.ISupportFragment;

/**
 * Created by goldze on 2017/6/15.
 */
public class BaseViewModel extends AndroidViewModel implements IBaseViewModel {
    private UIChangeLiveData uc;
    private LifecycleOwner lifecycle;

    public BaseViewModel(@NonNull Application application) {
        super(application);
    }

    /**
     * 注入RxLifecycle生命周期
     *
     * @param lifecycle
     */
    public void injectLifecycleOwner(LifecycleOwner lifecycle) {
        this.lifecycle = lifecycle;
    }

    public LifecycleOwner getLifecycleOwner() {
        return lifecycle;
    }

    public UIChangeLiveData getUC() {
        if (uc == null) {
            uc = new UIChangeLiveData();
        }
        return uc;
    }


    public void showToast(String str){
        Toast.makeText(getApplication(), str, Toast.LENGTH_SHORT).show();
    }

    public void showDialog() {
        showDialog("请稍后...");
    }

    public void showDialog(String title) {
        uc.getShowDialogEvent().postValue(title);
    }

    public void dismissDialog() {
        uc.getDismissDialogEvent().call();
    }

    /**
     * 跳转页面
     *
     * @param clz 所跳转的目的Activity类
     */
    public void startActivity(Class<?> clz) {
        startActivity(clz, null);
    }

    /**
     * 跳转页面
     *
     * @param clz    所跳转的目的Activity类
     * @param bundle 跳转所携带的信息
     */
    public void startActivity(Class<?> clz, Bundle bundle) {
        Map<String, Object> params = new HashMap<>();
        params.put(ParameterField.CLASS, clz);
        if (bundle != null) {
            params.put(ParameterField.BUNDLE, bundle);
        }
        uc.getStartActivityEvent().postValue(params);
    }

    /**
     * 跳转页面
     *
     * @param clz 所跳转的目的Activity类
     * @param requestCode 所跳转的目的Activity类
     */
    public void startActivityForResult(Class<?> clz, int requestCode) {
        startActivityForResult(clz, requestCode, null);
    }


    /**
     * 跳转页面
     * @param clz 所跳转的目的Activity类
     * @param bundle 带Bundle参数
     * @param requestCode 请求码
     */
    public void startActivityForResult(Class<?> clz, int requestCode, Bundle bundle) {
        Map<String, Object> params = new HashMap<>();
        params.put(ParameterField.CLASS, clz);
        params.put(ParameterField.RESULTCODE, requestCode);
        if (bundle != null) {
            params.put(ParameterField.BUNDLE, bundle);
        }

        uc.getStartActivityForResultEvent().postValue(params);
    }

    public void startFragmentForResult(BaseFragment fragment, int requestCode){
        Map<String, Object> params = new HashMap<>();
        params.put(ParameterField.FRAGMENT, fragment);
        params.put(ParameterField.RESULTCODE, requestCode);
        uc.getStartFragmentForResultEvent().postValue(params);
    }


    public void startFragment(BaseFragment fragment, int launchcode){
        Map<String, Object> params = new HashMap<>();
        params.put(ParameterField.FRAGMENT, fragment);
        params.put(ParameterField.LANUCHCODE, launchcode);
        uc.getStartFragmentEvent().postValue(params);
    }

    public void startFragment(BaseFragment fragment){
        startFragment(fragment, ISupportFragment.STANDARD);
    }

    /**
     * 关闭界面
     */
    public void finish() {
        uc.finishEvent.call();
    }

    /**
     * 返回上一层
     */
    public void onBackPressed() {
        uc.onBackPressedEvent.call();
    }

    @Override
    public void onAny(LifecycleOwner owner, Lifecycle.Event event) {
    }

    @Override
    public void onCreate() {
    }

    @Override
    public void onDestroy() {
    }

    @Override
    public void onStart() {
    }

    @Override
    public void onStop() {
    }

    @Override
    public void onResume() {
    }

    @Override
    public void onPause() {
    }

    @Override
    public void registerRxBus() {
    }

    @Override
    public void removeRxBus() {
    }

    public class UIChangeLiveData extends SingleLiveEvent {
        private SingleLiveEvent<String> showDialogEvent;
        private SingleLiveEvent<Boolean> dismissDialogEvent;
        private SingleLiveEvent<Boolean> showLoadingEvent;
        private SingleLiveEvent<Boolean> dismissLoadingEvent;
        private SingleLiveEvent<Map<String, Object>> startActivityEvent;
        private SingleLiveEvent<Map<String, Object>> startActivityForResultEvent;
        private SingleLiveEvent<Map<String, Object>> startFragmentEvent;
        private SingleLiveEvent<Map<String, Object>> startFragmentForResultEvent;
        private SingleLiveEvent<Boolean> finishEvent;
        private SingleLiveEvent<Boolean> onBackPressedEvent;

        public SingleLiveEvent<String> getShowDialogEvent() {
            return showDialogEvent = createLiveData(showDialogEvent);
        }

        public SingleLiveEvent<Boolean> getDismissDialogEvent() {
            return dismissDialogEvent = createLiveData(dismissDialogEvent);
        }

        public SingleLiveEvent<Boolean> getShowLoadingEvent() {
            return showLoadingEvent = createLiveData(showLoadingEvent);
        }

        public SingleLiveEvent<Boolean> getDismissLoadingEvent() {
            return dismissLoadingEvent = createLiveData(dismissLoadingEvent);
        }

        public SingleLiveEvent<Map<String, Object>> getStartActivityEvent() {
            return startActivityEvent = createLiveData(startActivityEvent);
        }

        public SingleLiveEvent<Map<String, Object>> getStartActivityForResultEvent() {
            return startActivityForResultEvent = createLiveData(startActivityForResultEvent);
        }

        public SingleLiveEvent<Map<String, Object>> getStartFragmentEvent() {
            return startFragmentEvent = createLiveData(startFragmentEvent);
        }

        public SingleLiveEvent<Map<String, Object>> getStartFragmentForResultEvent() {
            return startFragmentForResultEvent = createLiveData(startFragmentForResultEvent);
        }

        public SingleLiveEvent<Boolean> getFinishEvent() {
            return finishEvent = createLiveData(finishEvent);
        }

        public SingleLiveEvent<Boolean> getOnBackPressedEvent() {
            return onBackPressedEvent = createLiveData(onBackPressedEvent);
        }

        private <T> SingleLiveEvent<T> createLiveData(SingleLiveEvent<T> liveData) {
            if (liveData == null) {
                liveData = new SingleLiveEvent<>();
            }
            return liveData;
        }

        @Override
        public void observe(@NonNull LifecycleOwner owner, @NonNull Observer observer) {
            super.observe(owner, observer);
        }
    }

    public static class ParameterField {
        public static String CLASS = "CLASS";
        public static String BUNDLE = "BUNDLE";
        public static String RESULTCODE = "RESULTCODE";
        public static String FRAGMENT = "FRAGMENT";
        public static String LANUCHCODE = "LANUCHCODE";
    }
}
