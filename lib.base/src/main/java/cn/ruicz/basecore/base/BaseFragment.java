package cn.ruicz.basecore.base;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.afollestad.materialdialogs.MaterialDialog;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.Objects;

import cn.ruicz.basecore.R;
import cn.ruicz.basecore.base.BaseViewModel.ParameterField;
import cn.ruicz.basecore.bus.Messenger;
import cn.ruicz.basecore.manager.SwipeBackManager;
import cn.ruicz.basecore.manager.WaterMarkManager;
import cn.ruicz.basecore.utils.MaterialDialogUtils;
import ezy.ui.layout.LoadingLayout;
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;
import me.yokeyword.fragmentation_swipeback.SwipeBackFragment;

/**
 * Created by goldze on 2017/6/15.
 */
public abstract class BaseFragment<V extends ViewDataBinding, VM extends BaseViewModel> extends SwipeBackFragment implements IBaseActivity , Toolbar.OnMenuItemClickListener {
    protected V binding;
    protected VM viewModel;
    protected int viewModelId;
    private MaterialDialog dialog;
    public LoadingLayout loadingLayout;
    public Toolbar mToolbar;
    private boolean isInitFirst = true;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //解除Messenger注册
        Messenger.getDefault().unregister(viewModel);
        //解除ViewModel生命周期感应
        getLifecycle().removeObserver(viewModel);
        viewModel.removeRxBus();
        viewModel = null;
        if (loadingLayout != null) {
            loadingLayout.removeAllViews();
            loadingLayout = null;
        }
        binding.unbind();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false);
        viewModelId = initVariableId();
        viewModel = initViewModel();
        if (viewModel == null) {
            Class modelClass;
            Type type = getClass().getGenericSuperclass();
            if (type instanceof ParameterizedType) {
                modelClass = (Class) ((ParameterizedType) type).getActualTypeArguments()[1];
            } else {
                //如果没有指定泛型参数，则默认使用BaseViewModel
                modelClass = BaseViewModel.class;
            }
            viewModel = (VM) createViewModel(modelClass);
        }
        // 绑定Fragment生命周期
        binding.setLifecycleOwner(this);
        binding.setVariable(viewModelId, viewModel);
        //让ViewModel拥有View的生命周期感应
        getLifecycle().addObserver(viewModel);
        //注入RxLifecycle生命周期
        viewModel.injectLifecycleOwner(this);
        return attachToSwipeBack(binding.getRoot());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initSwipeBackConfig();

        // 如果开启滑动返回功能，初始化工作需要在 onEnterAnimationEnd 中完成，避免卡顿
        if (!isInitFirst || SwipeBackManager.getInstance().isEnable()) return;

        isInitFirst = false;

        //私有的ViewModel与View的契约事件回调逻辑
        registorUIChangeLiveDataCallBack();
        initToolbar();
        // 初始化背景水印
        initWaterMarkBg();
        //页面数据初始化方法
        init();
        //页面事件监听的方法，一般用于ViewModel层转到View层的事件注册
        initViewObservable();
        //注册RxBus
        viewModel.registerRxBus();
    }


    @Override
    public void onEnterAnimationEnd(Bundle savedInstanceState) {
        super.onEnterAnimationEnd(savedInstanceState);

        // 如果开启滑动返回功能，初始化工作需要在 onEnterAnimationEnd 中完成，避免卡顿
        if (!isInitFirst) return;

        isInitFirst = false;

        //私有的ViewModel与View的契约事件回调逻辑
        registorUIChangeLiveDataCallBack();
        initToolbar();
        // 初始化背景水印
        initWaterMarkBg();
        //页面数据初始化方法
        init();
        //页面事件监听的方法，一般用于ViewModel层转到View层的事件注册
        initViewObservable();
        //注册RxBus
        viewModel.registerRxBus();
    }

    public void initToolbar() {
        mToolbar = (Toolbar) binding.getRoot().findViewById(R.id.toolbar);
        setUpToolbar("", true);
    }

    public void setUpToolbar(String title, boolean hasBack) {
        if (null == mToolbar) {
            return;
        }
        mToolbar.setOnMenuItemClickListener(this);
        onCreateToolbarMenu(mToolbar, mToolbar.getMenu());
      /*  //setSupportActionBar(mToolbar);
        if (title != null) {
            mToolbar.setTitle("");
        }*/
        if (hasBack) {
            mToolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
            mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getActivity().onBackPressed();
                }
            });
        }
    }

    public void setTitle(String title){
        mToolbar.setTitle(title);
    }

    public void setTitle(@StringRes int resId){
        mToolbar.setTitle(resId);
    }


    public Toolbar getToolbar() {
        return mToolbar;
    }

    public void removeBackMenu(){
        mToolbar.setNavigationIcon(null);
    }


    public void onCreateToolbarMenu(Toolbar toolbar, Menu menu){}
    @Override
    public boolean onMenuItemClick(MenuItem item) {
        return false;
    }

    /**
     * 初始化滑动返回layout
     */
    public void initSwipeBackConfig(){
        setSwipeBackEnable(SwipeBackManager.getInstance().isEnable());// 是否允许滑动
        setEdgeLevel(SwipeBackManager.getInstance().getEdgeLevel());// 滑动范围
        setParallaxOffset(SwipeBackManager.getInstance().getParallaxOffset()); // （类iOS）滑动退出视觉差，默认0.3
        getSwipeBackLayout().setEdgeOrientation(SwipeBackManager.getInstance().getEdgeOrientation());    // EDGE_LEFT(默认),EDGE_ALL
        getSwipeBackLayout().setSwipeAlpha(SwipeBackManager.getInstance().getSwipeAlpha());    // 滑动中，设置上一个页面View的阴影透明程度度，默认0.5f
    }

    // 初始化loadinglayout
    protected LoadingLayout initLoadingLayout(View view){
        if (loadingLayout == null){
            loadingLayout = LoadingLayout.wrap(view);
            loadingLayout.setErrorImage(R.mipmap.error);
            loadingLayout.setEmptyImage(R.mipmap.empty);
            loadingLayout.setLoading(R.layout.shimmer_layout);
            ViewGroup.LayoutParams lp = loadingLayout.getLayoutParams();
            lp.height = ViewGroup.LayoutParams.MATCH_PARENT;
            lp.width = ViewGroup.LayoutParams.MATCH_PARENT;
            loadingLayout.setLayoutParams(lp);
        }
        return loadingLayout;
    }

    public LoadingLayout getLoadingLayout(){
        return loadingLayout;
    }

    // 初始化背景水印
    public void initWaterMarkBg() {
        if (WaterMarkManager.getInstance().isFragmentBg()) {
            binding.getRoot().setBackground(WaterMarkManager.getInstance().getWaterMarkBg());
        }
    }

    public void cleanWaterMarkBg(){
        binding.getRoot().setBackgroundColor(getResources().getColor(R.color.view_background));
    }

    /**
     * =====================================================================
     **/
    //注册ViewModel与View的契约UI回调事件
    private void registorUIChangeLiveDataCallBack() {
        //加载对话框显示
        viewModel.getUC().getShowLoadingEvent().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                loadingLayout.showLoading();
            }
        });
        //加载对话框消失
        viewModel.getUC().getDismissLoadingEvent().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                loadingLayout.showContent();
            }
        });

        //加载对话框显示
        viewModel.getUC().getShowDialogEvent().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String title) {
                showDialog(title);
            }
        });
        //加载对话框消失
        viewModel.getUC().getDismissDialogEvent().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                dismissDialog();
            }
        });
        //跳入新页面
        viewModel.getUC().getStartActivityEvent().observe(this, new Observer<Map<String, Object>>() {
            @Override
            public void onChanged(@Nullable Map<String, Object> params) {
                Class<?> clz = (Class<?>) params.get(ParameterField.CLASS);
                Bundle bundle = (Bundle) params.get(ParameterField.BUNDLE);
                startActivity(clz, bundle);
            }
        });
        //跳入新页面
        viewModel.getUC().getStartActivityForResultEvent().observe(this, new Observer<Map<String, Object>>() {
            @Override
            public void onChanged(@Nullable Map<String, Object> params) {
                Class<?> clz  = (Class<?>) params.get(ParameterField.CLASS);
                Bundle bundle = (Bundle) params.get(ParameterField.BUNDLE);
                int request = (int) params.get(ParameterField.RESULTCODE);
                startActivityForResult(clz, request, bundle);
            }
        });
        //跳入新页面
        viewModel.getUC().getStartFragmentEvent().observe(this, new Observer<Map<String, Object>>() {
            @Override
            public void onChanged(@Nullable Map<String, Object> params) {
                BaseFragment baseFragment  = (BaseFragment) params.get(ParameterField.FRAGMENT);
                int lcode = (int) params.get(ParameterField.LANUCHCODE);
                startFragment(baseFragment, lcode);
            }
        });
        //跳入新页面
        viewModel.getUC().getStartFragmentForResultEvent().observe(this, new Observer<Map<String, Object>>() {
            @Override
            public void onChanged(@Nullable Map<String, Object> params) {
                BaseFragment baseFragment  = (BaseFragment) params.get(ParameterField.FRAGMENT);
                int request = (int) params.get(ParameterField.RESULTCODE);
                startFragmentForResult(baseFragment, request);
            }
        });
        //关闭界面
        viewModel.getUC().getFinishEvent().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                Objects.requireNonNull(getActivity()).finish();
            }
        });
        //关闭上一层
        viewModel.getUC().getOnBackPressedEvent().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                Objects.requireNonNull(getActivity()).onBackPressed();
            }
        });
    }

    public void showDialog(String title) {
        if (dialog != null) {
            dialog.show();
        } else {
            MaterialDialog.Builder builder = MaterialDialogUtils.showIndeterminateProgressDialog(getActivity(), title, true);
            dialog = builder.show();
        }
    }

    public void dismissDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    /**
     * 跳转页面
     *
     * @param clz 所跳转的目的Activity类
     */
    public void startActivity(Class<?> clz) {
        startActivity(new Intent(getContext(), clz));
    }

    /**
     * 跳转页面
     *
     * @param clz    所跳转的目的Activity类
     * @param bundle 跳转所携带的信息
     */
    public void startActivity(Class<?> clz, Bundle bundle) {
        Intent intent = new Intent(getContext(), clz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    public void startActivityForResult(Class<?> clz, int requestCode, Bundle bundle) {
        Intent intent = new Intent(getContext(), clz);
        startActivityForResult(intent, requestCode, bundle);
    }

    public void startFragmentForResult(BaseFragment fragment, int requestCode){
        startForResult(fragment, requestCode);
    }

    public void startFragment(BaseFragment fragment, int launchMode){
        start(fragment, launchMode);
    }

    public void startFragment(BaseFragment fragment){
        start(fragment);
    }

    /**
     * =====================================================================
     **/

    //刷新布局
    public void refreshLayout() {
        if (viewModel != null) {
            binding.setVariable(viewModelId, viewModel);
        }
    }

    /**
     * 初始化根布局
     *
     * @return 布局layout的id
     */
    public abstract int getLayoutId();

    /**
     * 初始化ViewModel的id
     *
     * @return BR的id
     */
    public abstract int initVariableId();

    /**
     * 初始化ViewModel
     *
     * @return 继承BaseViewModel的ViewModel
     */
    public VM initViewModel() {
        return null;
    }

    @Override
    public void initViewObservable() {

    }

    public boolean isBackPressed() {
        return false;
    }

    /**
     * 创建ViewModel
     *
     * @param cls
     * @return
     */
    public <T extends ViewModel> T createViewModel(Class<T> cls) {
        if (getParentFragment() != null){
            return ViewModelProviders.of(getParentFragment()).get(cls);
        }else{
            return ViewModelProviders.of(getActivity()).get(cls);
        }
    }

    /**
     * 创建ViewModel
     *
     * @param cls
     * @return
     */
    public <T extends ViewModel> T createViewModel(Fragment fragment, Class<T> cls) {
        return ViewModelProviders.of(fragment).get(cls);
    }

    /**
     * 创建ViewModel
     *
     * @param cls
     * @return
     */
    public <T extends ViewModel> T createViewModel(FragmentActivity activity, Class<T> cls) {
        return ViewModelProviders.of(activity).get(cls);
    }

    @Override
    public FragmentAnimator onCreateFragmentAnimator() {
        return new DefaultHorizontalAnimator();
    }
}
