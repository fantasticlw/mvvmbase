package cn.ruicz.basecore.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewParent;

public class ViewPagerSwipeBack extends ViewPager {
    public ViewPagerSwipeBack(@NonNull Context context) {
        super(context);
    }

    public ViewPagerSwipeBack(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    float curX = 0;
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (ev.getActionMasked() == MotionEvent.ACTION_DOWN) {// 默认拦截父控件的touch事件
            curX = ev.getRawX();
            ViewParent parent = this.getParent();
            if (parent != null) {
                parent.requestDisallowInterceptTouchEvent(true);
            }
        }
        return super.onInterceptTouchEvent(ev);
    }


    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (ev.getActionMasked() == MotionEvent.ACTION_MOVE) {
            if (getCurrentItem() == 0 && ev.getRawX() > curX) {
                // 如果是viewpager处在第一个item，并且从左向右滑动，将touch事件交给父控件处理
                ViewParent parent = this.getParent();
                if (parent != null) {
                    parent.requestDisallowInterceptTouchEvent(false);
                }
                curX = ev.getRawX();
                return false;
            }
        }
        return super.onTouchEvent(ev);
    }
}
