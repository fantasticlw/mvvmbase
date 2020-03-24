package cn.ruicz.basecore.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class ViewPagerSwipeBack extends ViewPager {
    public ViewPagerSwipeBack(@NonNull Context context) {
        super(context);
    }

    public ViewPagerSwipeBack(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    float curX = 0;
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getActionMasked()){
            case MotionEvent.ACTION_DOWN:
                // 默认拦截父控件的touch事件
                requestDisallowInterceptTouchEvent(true);
                curX = ev.getRawX();
                return super.onTouchEvent(ev);
            case MotionEvent.ACTION_MOVE:
                if (getCurrentItem() == 0 && ev.getRawX() > curX){
                    // 如果是viewpager处在第一个item，并且从左向右滑动，将touch事件交给父控件处理
                    requestDisallowInterceptTouchEvent(false);
                    curX = ev.getRawX();
                    return false;
                }
        }
        return super.onTouchEvent(ev);
    }
}
