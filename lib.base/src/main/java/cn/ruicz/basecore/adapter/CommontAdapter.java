package cn.ruicz.basecore.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;

import cn.ruicz.basecore.utils.KLog;

/**
 * Created by MBENBEN on 2016/5/10.
 */
public abstract class CommontAdapter<T> extends RecyclerView.Adapter<ViewHolder> {

    public static final int FOOTER_STATE_LOADING = 100005;
    public static final int FOOTER_STATE_FAILED = 100006;
    public static final int FOOTER_STATE_END = 100007;
    public static final int FOOTER_STATE_EMPTY = 100008;
    protected static final int TYPE_COMMON_VIEW = 100001;
    protected static final int TYPE_FOOTER_VIEW = 100002;
    protected static final int TYPE_EMPTY_VIEW = 100003;
    protected static final int TYPE_HEADER_VIEW = 100004;
    private LayoutInflater inflater;
    private Context context;
    private List<T> mDatas;
    private OnItemClickListener<T> onItemClickListener;
    private OnItemLongClickListner<T> onItemLongClickListner;
    private RecyclerView.LayoutManager mLayoutManager;
    private int layoutId;
    public RelativeLayout mFooterLayout;
    private boolean mHasFooterMore = false;
    private OnLoadMoreListener mLoadMoreListener;
    private boolean mIsAutoLoadMore = true;
    private View mLoadingView;
    private View mLoadFailedView;
    private View mLoadEndView;
    private View mEmptyView;
    private View mHeanderView;
    @FooterState
    private int mFooterState = FOOTER_STATE_LOADING;

    public CommontAdapter(Context context) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.mDatas = new ArrayList<>();
    }

    public CommontAdapter(Context context, int layoutId) {
        this.context = context;
        this.layoutId = layoutId;
        this.inflater = LayoutInflater.from(context);
        this.mDatas = new ArrayList<>();
        this.mHasFooterMore = false;
    }

    public CommontAdapter(Context context, int layoutId, boolean hasFooterMore) {
        this.context = context;
        this.layoutId = layoutId;
        this.inflater = LayoutInflater.from(context);
        this.mDatas = new ArrayList<>();
        this.mHasFooterMore = hasFooterMore;
        if (mHasFooterMore) {
            mFooterLayout = new RelativeLayout(context);
        }
    }

    public Context getContext() {
        return context;
    }

    public void setLoadMoreListener(OnLoadMoreListener mLoadMoreListener) {
        this.mLoadMoreListener = mLoadMoreListener;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListner onItemLongClickListener) {
        this.onItemLongClickListner = onItemLongClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_FOOTER_VIEW:
                return ViewHolder.create(mFooterLayout);
            case TYPE_EMPTY_VIEW:
                return ViewHolder.create(mEmptyView);
            case TYPE_COMMON_VIEW:
                return ViewHolder.create(context, layoutId, parent);
            case TYPE_HEADER_VIEW:
                return ViewHolder.create(mHeanderView);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case TYPE_COMMON_VIEW:
                bindItemView(holder, position);
                convert(holder, getItemData(position), position);
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (isHeaderView(position)) {
            return TYPE_HEADER_VIEW;
        }
        if (mDatas.isEmpty() && mEmptyView != null) {
            return TYPE_EMPTY_VIEW;
        }
        if (isFooterView(position)) {
            return TYPE_FOOTER_VIEW;
        }
        return TYPE_COMMON_VIEW;
    }

    private void bindItemView(final ViewHolder holder, final int position) {
        holder.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(holder, position, getItemData(position));
                }
            }
        });
        holder.getConvertView().setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (onItemLongClickListner != null) {
                    return onItemLongClickListner.onItemLongClick(holder, position, getItemData(position));
                }
                return false;
            }
        });
    }

    public T getItemData(int position) {
        position -= getHeaderCount();
        return mDatas.get(position);
    }

    public List<T> getAll(){
        return mDatas;
    }

    protected abstract void convert(ViewHolder holder, T t, int position);

    @Override
    public int getItemCount() {
//        if (mDatas.isEmpty() && mEmptyView != null) {
//            return 1;
//        }
//        if (mDatas.isEmpty() && mHeanderView != null) {
//            return 1;
//        }
        return mDatas.size() + getFooterCount()+getHeaderCount();
    }

    private int getFooterCount() {
        return mHasFooterMore ? 1 : 0;
    }

    private int getHeaderCount() {
        return mHeanderView != null ? 1 : 0;
    }

    /**
     * 是否是FooterView
     *
     * @param position
     * @return
     */
    private boolean isFooterView(int position) {
        return mHasFooterMore && getItemCount() > 1 && position >= getItemCount() - 1;
    }

    private boolean isHeaderView(int position) {
        return mHeanderView != null && position == 0;
    }

    /**
     * StaggeredGridLayoutManager模式时，FooterView可占据一行
     *
     * @param holder
     */
    @Override
    public void onViewAttachedToWindow(ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        if (isHeaderView(holder.getLayoutPosition()) || isFooterView(holder.getLayoutPosition())) {
            ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
            if (lp != null && lp instanceof StaggeredGridLayoutManager.LayoutParams) {
                StaggeredGridLayoutManager.LayoutParams p = (StaggeredGridLayoutManager.LayoutParams) lp;
                p.setFullSpan(true);
            }
        }
    }

    /**
     * GridLayoutManager模式时， FooterView可占据一行，判断RecyclerView是否到达底部
     *
     * @param recyclerView
     */
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        mLayoutManager = recyclerView.getLayoutManager();
        if (mLayoutManager instanceof GridLayoutManager) {
            final GridLayoutManager gridManager = ((GridLayoutManager) mLayoutManager);
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    if (isHeaderView(position) || isFooterView(position)) {
                        return gridManager.getSpanCount();
                    }
                    return 1;
                }
            });
        }
        startLoadMore(recyclerView, mLayoutManager);
    }

    /**
     * 判断列表是否滑动到底部
     *
     * @param recyclerView
     * @param layoutManager
     */
    private void startLoadMore(RecyclerView recyclerView, final RecyclerView.LayoutManager layoutManager) {
        if (!mHasFooterMore || mLoadMoreListener == null) {
            return;
        }

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (!mIsAutoLoadMore && findLastVisibleItemPosition(mLayoutManager) + 1 == getItemCount()) {
                        scrollLoadMore();
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!isEmpty()) {
                    int lastPosition = findLastVisibleItemPosition(mLayoutManager);
                    int itemCount = getItemCount();
                    if (mFooterState == FOOTER_STATE_END) {
                        setFooter();
                    } else if (mIsAutoLoadMore && lastPosition + 2 >= itemCount) {
                        if (mFooterState == FOOTER_STATE_END) {
                            setFooter();
                        } else {
                            scrollLoadMore();
                        }
                    } else if (mIsAutoLoadMore) {
                        mIsAutoLoadMore = false;
                    }
                }
            }
        });
    }

    private int findLastVisibleItemPosition(RecyclerView.LayoutManager layoutManager) {
        try {
            if (layoutManager instanceof LinearLayoutManager) {
                int itemPosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
                return itemPosition;
            } else if (layoutManager instanceof StaggeredGridLayoutManager) {
                int[] lastVisibleItemPositions = ((StaggeredGridLayoutManager) layoutManager).findLastVisibleItemPositions(null);
                return findMax(lastVisibleItemPositions);
            }
        } catch (Exception e) {
            e.printStackTrace();
            KLog.e("TAG");
        }
        return -1;
    }

    private int findMax(int[] lastVisiblePositions) {
        int max = lastVisiblePositions[0];
        for (int value : lastVisiblePositions) {
            if (value > max) {
                max = value;
            }
        }
        return max;
    }

    private void scrollLoadMore() {
        if (mFooterLayout.getChildAt(0) == mLoadingView) {
            if (mLoadMoreListener != null) {
                mLoadMoreListener.onLoadMore();
            }
        }
    }

    public void reset() {
        clear();
        mIsAutoLoadMore = true;
        setFooterState(FOOTER_STATE_LOADING);
    }

    private void removeFooterView() {
        if (mFooterLayout != null) {
            mFooterLayout.removeAllViews();
        }
    }

    public void addHeaderView(@NonNull View heanderView) {
        if (heanderView == null) {
            return;
        }
        mHeanderView = heanderView;
    }

    public void setFooterState(@FooterState int state) {
        if (state != mFooterState) {
            mFooterState = state;
            setFooter();
        }
    }

    private void setFooter() {
        if (!mHasFooterMore && mFooterLayout == null) {
            return;
        }
        switch (mFooterState) {
            case FOOTER_STATE_LOADING:
                if (mLoadingView != null) {
                    removeFooterView();
                    mFooterLayout.addView(mLoadingView, mLoadingView.getLayoutParams());
                } else {
                    throw new NullPointerException("Loading View");
                }
                break;
            case FOOTER_STATE_END:
                if (mLoadEndView != null) {
                    removeFooterView();
                    mFooterLayout.addView(mLoadEndView);
                } else {
                    throw new NullPointerException("End View");
                }
                break;
            case FOOTER_STATE_EMPTY:
                if (mEmptyView != null) {
                    removeFooterView();
                    mFooterLayout.addView(mEmptyView);
                } else {
                    throw new NullPointerException("Empty View");
                }
                break;
            case FOOTER_STATE_FAILED:
                if (mLoadFailedView != null) {
                    removeFooterView();
                    mFooterLayout.addView(mLoadFailedView);
                } else {
                    throw new NullPointerException("Failed View");
                }
                break;
        }
    }

    public RelativeLayout getFooterLayout() {
        return mFooterLayout;
    }

    /**
     * 初始化加载中布局
     *
     * @param loadingView
     */
    public void setLoadingView(View loadingView) {
        if (loadingView != null) {
            mLoadingView = loadingView;
            setFooter();
        }
    }

    public void setLoadingView(@LayoutRes int loadingId) {
        setLoadingView(inflater.inflate(loadingId, mFooterLayout, false));
    }

    /**
     * 初始加载失败布局
     *
     * @param loadFailedView
     */
    public void setLoadFailedView(View loadFailedView) {
        if (loadFailedView == null) {
            return;
        }
        mLoadFailedView = loadFailedView;
        mLoadFailedView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFooterState(FOOTER_STATE_LOADING);
                mLoadMoreListener.onLoadMore();
            }
        });
    }

    public void setLoadFailedView(int loadFailedId) {
        setLoadFailedView(inflater.inflate(loadFailedId, mFooterLayout, false));
    }

    /**
     * 初始化全部加载完成布局
     *
     * @param loadEndView
     */
    public void setLoadEndView(View loadEndView) {
        if (loadEndView != null) {
            mLoadEndView = loadEndView;
        }
    }

    public void setLoadEndView(int loadEndId) {
        setLoadEndView(inflater.inflate(loadEndId, mFooterLayout, false));
    }

    public void setEmptyView(View emptyView) {
        if (emptyView != null) {
            mEmptyView = emptyView;
        }
    }

    public void setEmptyView(@LayoutRes int layoutId) {
        View view = inflater.inflate(layoutId, mFooterLayout, false);
        setEmptyView(view);
    }

    public List<T> add(T data) {
        if (data != null) {
            mDatas.add(data);
            notifyDataSetChanged();
        }
        return mDatas;
    }

    public void add(T data, int index) {
        if (data != null) {
            mDatas.add(index, data);
            notifyDataSetChanged();
        }
    }

    public void addAll(List<T> datas) {
        if (mDatas.isEmpty()) {
            mDatas.addAll(datas);
            notifyDataSetChanged();
        } else {
            int size = mDatas.size();
            mDatas.addAll(datas);
            notifyItemInserted(size);
        }
    }

    public void clear() {
        mDatas.clear();
        notifyDataSetChanged();
    }

    public void remove(T data) {
        if (data != null) {
            mDatas.remove(data);
        }
        notifyDataSetChanged();
    }

    public void remove(int index) {
        if (index < mDatas.size()) {
            mDatas.remove(index);
        }
        notifyDataSetChanged();
    }

    public boolean contains(T data) {
        return mDatas.contains(data);
    }

    public boolean isEmpty() {
        return mDatas.isEmpty();
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface FooterState {
    }
}
