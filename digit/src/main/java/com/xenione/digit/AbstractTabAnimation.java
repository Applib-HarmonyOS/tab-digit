package com.xenione.digit;

/**
 * AbstractTabAnimation.
 */
public abstract class AbstractTabAnimation {
    protected static final int LOWER_POSITION = 0;
    protected static final int MIDDLE_POSITION = 1;
    protected static final int UPPER_POSITION = 2;

    public abstract void initState();

    public abstract void initMiddleTab();

    public abstract void run();

    protected abstract void makeSureCycleIsClosed();

    protected final TabDigit.Tab mTopTab;
    protected final TabDigit.Tab mBottomTab;
    protected final TabDigit.Tab mMiddleTab;

    protected int state;
    protected int mAlpha = 0;
    protected long mTime = -1;
    protected float mElapsedTime = 1000.0f;

    /**
     * AbstractTabAnimation.
     *
     * @param topTab    top tab
     * @param bottomTab bottom tab
     * @param middleTab middle tab
     */
    protected AbstractTabAnimation(TabDigit.Tab topTab, TabDigit.Tab bottomTab, TabDigit.Tab middleTab) {
        this.mTopTab = topTab;
        this.mBottomTab = bottomTab;
        this.mMiddleTab = middleTab;
        initState();
    }

    /**
     * start the animation.
     */
    public void start() {
        makeSureCycleIsClosed();
        mTime = System.currentTimeMillis();
    }

    public void sync() {
        makeSureCycleIsClosed();
    }


}
