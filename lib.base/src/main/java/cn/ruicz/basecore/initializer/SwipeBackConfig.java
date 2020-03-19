package cn.ruicz.basecore.initializer;

import me.yokeyword.fragmentation.SwipeBackLayout;

public class SwipeBackConfig {

    private static SwipeBackConfig Instance = new SwipeBackConfig();

    private int EdgeOrientation = SwipeBackLayout.EDGE_ALL;
    private float ParallaxOffset = 0.3f;
    private boolean EnableGesture = true;
    private float SwipeAlpha = 0.5f;
    private SwipeBackLayout.EdgeLevel edgeLevel = SwipeBackLayout.EdgeLevel.MAX;

    private SwipeBackConfig(){}


    public static SwipeBackConfig getInstance(){
        return Instance;
    }

    public int getEdgeOrientation() {
        return EdgeOrientation;
    }

    public SwipeBackConfig setEdgeOrientation(int edgeOrientation) {
        EdgeOrientation = edgeOrientation;
        return this;
    }

    public float getParallaxOffset() {
        return ParallaxOffset;
    }

    public SwipeBackConfig setParallaxOffset(float parallaxOffset) {
        ParallaxOffset = parallaxOffset;
        return this;
    }

    public boolean isEnableGesture() {
        return EnableGesture;
    }

    public SwipeBackConfig setEnableGesture(boolean enableGesture) {
        EnableGesture = enableGesture;
        return this;
    }

    public float getSwipeAlpha() {
        return SwipeAlpha;
    }

    public SwipeBackConfig setSwipeAlpha(float swipeAlpha) {
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
