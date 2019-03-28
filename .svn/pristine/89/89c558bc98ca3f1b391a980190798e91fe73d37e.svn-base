package cn.ruicz.basecore.base;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import java.util.ArrayList;
import java.util.List;
import cn.ruicz.basecore.R;
import cn.ruicz.basecore.adapter.CommontAdapter;
import cn.ruicz.basecore.adapter.OnItemClickListener;
import cn.ruicz.basecore.adapter.OnItemLongClickListner;
import cn.ruicz.basecore.adapter.OnLoadMoreListener;
import cn.ruicz.basecore.adapter.RecyclerViewDecoration;
import cn.ruicz.basecore.databinding.FragmentRecyclerBinding;
import cn.ruicz.basecore.http.SimpleObserver;
import cn.ruicz.basecore.utils.KLog;
import cn.ruicz.utilcode.util.NetworkUtils;
import cn.ruicz.utilcode.util.ToastUtils;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

import static android.widget.LinearLayout.VERTICAL;
import static cn.ruicz.basecore.adapter.CommontAdapter.FOOTER_STATE_END;

/**
 * Author: MBENBEN
 * Time: 2016/10/17 on 09:18
 */

public abstract class BaseRecyclerActivity<T> extends BaseActivity<FragmentRecyclerBinding, BaseViewModel> implements SwipeRefreshLayout.OnRefreshListener, OnItemClickListener<T>, OnItemLongClickListner<T>, OnLoadMoreListener {

    public SwipeRefreshLayout mRefreshLayout;
    public RecyclerView mRecyclerView;
    public CommontAdapter<T> mAdapter;
    protected int INIT_INDEX = 1; // 初始化列表下标从0开始
    protected int PAGE_SIZE = 10;
    private int mIndex = INIT_INDEX;
    public int getIndex() {
        return mIndex;
    }

    public void setIndex(int index) {
        this.mIndex = index;
    }
    public void setTitle(String title){
        super.setTitle(title);
    }

    public int getInitIndex() {
        return INIT_INDEX;
    }


    // 设置下标从什么开始
    public void setInitIndex(int INIT_INDEX) {
        this.INIT_INDEX = INIT_INDEX;
        mIndex = INIT_INDEX;
    }

    public RecyclerView getRecyclerView() {
        return mRecyclerView;
    }

    public SwipeRefreshLayout getRefreshLayout(){
        return mRefreshLayout;
    }

    public CommontAdapter<T> getAdapter() {
        return mAdapter;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_recycler;
    }

    @Override
    public void init() {
        initView();
        initRecycler();
        initListener();
        requestData();
    }


    private void initView() {
        mRefreshLayout = binding.refreshLayout;
        mRecyclerView = binding.recyclerView;
        initLoadingLayout(mRecyclerView);
        loadingLayout.showContent();

        loadingLayout.setRetryListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onRefresh();
            }
        });
    }

    private void initListener() {
//        mRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.refresh_layout);
//        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRefreshLayout.setOnRefreshListener(this);
//        mRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.maincolor));
    }

    private void initRecycler() {
        mRecyclerView.setLayoutManager(createLayoutManager());
        mRecyclerView.addItemDecoration(createItemDecoration());
        mAdapter = createAdapter();
        mAdapter.setLoadingView(getLoadingView());
        mAdapter.setEmptyView(getEmptyView());
        mAdapter.setLoadEndView(getLoadEndView());
        mAdapter.setLoadFailedView(getLoadFailedView());
        setHeadView();
        mAdapter.setOnItemClickListener(this);
        mAdapter.setOnItemLongClickListener(this);
        mAdapter.setLoadMoreListener(this);
        mRecyclerView.setAdapter(mAdapter);
    }

    protected RecyclerView.ItemDecoration createItemDecoration() {

        return new RecyclerViewDecoration(this, VERTICAL, 1, R.color.conversation_gray, 0);
    }

    protected RecyclerView.LayoutManager createLayoutManager() {
        return new LinearLayoutManager(this);
    }

    public void setRefreshing(boolean refreshing) {
        mRefreshLayout.setRefreshing(refreshing);
    }

    @Override
    public void onRefresh() {
        if (NetworkUtils.isConnected()){
//            if (!mRefreshLayout.isRefreshing()) {
//                setRefreshing(true);
//            }
            mIndex = INIT_INDEX;
            requestData();
        }else{
            ToastUtils.showShort(R.string.network_break_off);
            setRefreshing(false);
            loadingLayout.showError();
        }
    }

    @Override
    public void onLoadMore() {
        if (NetworkUtils.isConnected()) {
            mIndex++;
            requestData();
        } else {
            getAdapter().setFooterState(CommontAdapter.FOOTER_STATE_FAILED);
        }
    }

    protected Action createCompleted() {
        return new Action() {
            @Override
            public void run() throws Exception {
                if (mRefreshLayout.isRefreshing()) {
                    setRefreshing(false);
                }
            }
        };
    }

    protected Consumer<Throwable> createError() {
        return new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                KLog.e(throwable.getMessage());
                getAdapter().setFooterState(CommontAdapter.FOOTER_STATE_FAILED);
                loadingLayout.showError();
            }
        };
    }

    protected Consumer<ArrayList<T>> createNext() {
        return new Consumer<ArrayList<T>>() {
            @Override
            public void accept(ArrayList<T> datas) {
                addAll(datas);
            }
        };
    }

    public SimpleObserver getSubscriber() {
        Consumer<ArrayList<T>> action1 = createNext();
        if (null == action1) {
            throw new NullPointerException("getNext Can't be null");
        }
        return new SimpleObserver<>(this, action1, createCompleted(), createError());
    }

    public void addAll(List<T> datas){
        addAll(datas, getAdapter().getAll().size());
    }

    public void addAll(List<T> datas, int position){
        if (datas.size() <= 0){
            loadingLayout.showEmpty();
            getAdapter().setFooterState(FOOTER_STATE_END);
        }else {
            //getAdapter().clear();

            loadingLayout.showContent();


            if (getIndex() == INIT_INDEX) {
                getAdapter().reset();
            }


//            getAdapter().getAll().addAll(position, datas);

            getAdapter().addAll(datas);


            // 加载数据如果小于 PAGE_SIZE 说明已全部加载完毕
            if (datas.size() < PAGE_SIZE){
                getAdapter().setFooterState(FOOTER_STATE_END);
            }

        }

//        if (datas == null || datas.isEmpty()){
//            if (getIndex() == INIT_INDEX){
//                loadingLayout.showEmpty();
//            }
//            getAdapter().setFooterState(FOOTER_STATE_END);
//        }else{
//            loadingLayout.showContent();
//
//            if (getIndex() == INIT_INDEX) {
//                getAdapter().reset();
//            }
//            getAdapter().getAll().addAll(position, datas);
//            // 加载数据如果小于 PAGE_SIZE 说明已全部加载完毕
//            if (datas.size() < PAGE_SIZE){
//                getAdapter().setFooterState(FOOTER_STATE_END);
//            }
//        }
    }

    public void add(T data){
        getAdapter().add(data);
        getAdapter().notifyDataSetChanged();
        if (getAdapter().getAll().size() == 1){
            loadingLayout.showContent();
        }
    }

    public void add(T data, int position){
        getAdapter().add(data, position);
        getAdapter().notifyDataSetChanged();
        if (getAdapter().getAll().size() == 1){
            loadingLayout.showContent();
        }
    }

    public void remove(int position){
        getAdapter().remove(position);
        getAdapter().notifyDataSetChanged();
        if (getAdapter().getItemCount() == 0){
            loadingLayout.showEmpty();
        }
    }

    public void remove(T data){
        getAdapter().remove(data);
        getAdapter().notifyDataSetChanged();
        if (getAdapter().getItemCount() == 0){
            loadingLayout.showEmpty();
        }
    }

    public int getLoadingView() {
        return R.layout.adapter_loading_layout;
    }

    public int getEmptyView() {
        return R.layout.adapter_empty_layout;
    }

    public int getLoadEndView() {
        return R.layout.adapter_load_end;
    }

    public void setHeadView(){

    }

    public int getLoadFailedView() {
        return R.layout.adapter_load_failed;
    }

    protected abstract CommontAdapter<T> createAdapter();

    /**
     * 请求网络数据
     */
    protected abstract void requestData();
}
