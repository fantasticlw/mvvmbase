package cn.ruicz.basecore.event;

/**
 * Created by ydjw on 2018/11/7.
 */

public class BottomBarEvent {
    private boolean isShow;

    public BottomBarEvent(boolean isShow){
        this.isShow = isShow;
    }

    public boolean isShow() {
        return isShow;
    }

    public void setShow(boolean show) {
        isShow = show;
    }
}
