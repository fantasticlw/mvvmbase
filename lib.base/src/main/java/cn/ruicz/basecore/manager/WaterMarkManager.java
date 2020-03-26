package cn.ruicz.basecore.manager;

import android.content.Context;

import java.util.List;

import cn.ruicz.basecore.utils.WaterMarkBg;

public class WaterMarkManager {


    private WaterMarkBg waterMarkBg;
    private boolean isActivityBg = false;
    private boolean isFragmentBg = false;

    // 私有化构造函数
    private WaterMarkManager(){}

    private static class WaterMarkHolder{
        private static final WaterMarkManager INSTANCE = new WaterMarkManager();
    }

    public static WaterMarkManager getInstance(){
        return WaterMarkHolder.INSTANCE;
    }


    public WaterMarkManager setWaterMark(Context context, List<String> labels, int degress, int fontSize){
        this.waterMarkBg = new WaterMarkBg(context, labels, degress, fontSize);
        return this;
    }

    public WaterMarkManager setActivityBg(boolean isActivityBg){
        if (isActivityBg && waterMarkBg == null){
            throw new IllegalArgumentException("must first call setWaterMark()");
        }
        this.isActivityBg = isActivityBg;
        return this;
    }

    public WaterMarkManager setFragmentBg(boolean isFragmentBg){
        if (isFragmentBg && waterMarkBg == null){
            throw new IllegalArgumentException("must first call setWaterMark()");
        }
        this.isFragmentBg = isFragmentBg;
        return this;
    }

    public boolean isActivityBg() {
        return isActivityBg && waterMarkBg != null;
    }

    public boolean isFragmentBg() {
        return isFragmentBg && waterMarkBg != null;
    }

    public WaterMarkBg getWaterMarkBg() {
        return waterMarkBg;
    }
}
