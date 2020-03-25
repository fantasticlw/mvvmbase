package cn.ruicz.basecore.manager;

import me.yokeyword.fragmentation.SwipeBackLayout;

public class SwipeBackManager {

    private static SwipeBackManager Instance = new SwipeBackManager();

    private int EdgeOrientation = SwipeBackLayout.EDGE_ALL;
    private float ParallaxOffset = 0.3f;
    private boolean EnableGesture = false;
    private float SwipeAlpha = 0.5f;
    private SwipeBackLayout.EdgeLevel edgeLevel = SwipeBackLayout.EdgeLevel.MAX;

    private SwipeBackManager(){}


    public static SwipeBackManager getInstance(){
        return Instance;
    }

    public int getEdgeOrientation() {
        return EdgeOrientation;
    }

    public SwipeBackManager setEdgeOrientation(int edgeOrientation) {
        EdgeOrientation = edgeOrientation;
        return this;
    }

    public float getParallaxOffset() {
        return ParallaxOffset;
    }

    public SwipeBackManager setParallaxOffset(float parallaxOffset) {
        ParallaxOffset = parallaxOffset;
        return this;
    }

    public boolean isEnableGesture() {
        return EnableGesture;
    }

    public SwipeBackManager setEnableGesture(boolean enableGesture) {
        EnableGesture = enableGesture;
        return this;
    }

    public float getSwipeAlpha() {
        return SwipeAlpha;
    }

    public SwipeBackManager setSwipeAlpha(float swipeAlpha) {
        SwipeAlpha = swipeAlpha;
        return this;
    }

    public SwipeBackLayout.EdgeLevel getEdgeLevel() {
        return edgeLevel;
    }

    public void setEdgeLevel(SwipeBackLayout.EdgeLevel edgeLevel) {
        this.edgeLevel = edgeLevel;
    }
}
