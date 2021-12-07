package com.xenione.digit;


/**
 * rotates middle tab upwards.
 */
public final class TabAnimationUp extends AbstractTabAnimation {

    public TabAnimationUp(TabDigit.Tab topTab, TabDigit.Tab bottomTab, TabDigit.Tab middleTab) {
        super(topTab, bottomTab, middleTab);
    }

    @Override
    public void initState() {
        state = LOWER_POSITION;
    }

    @Override
    public void initMiddleTab() {
        /* nothing to do */
    }


    @Override
    public void run() {
        if (mTime == -1) {
            return;
        }

        switch (state) {
            case LOWER_POSITION: {
                mBottomTab.next();
                state = MIDDLE_POSITION;
                break;
            }
            case MIDDLE_POSITION: {
                if (mAlpha > 90) {
                    mMiddleTab.next();
                    state = UPPER_POSITION;
                }
                break;
            }
            case UPPER_POSITION: {
                if (mAlpha >= 180) {
                    mTopTab.next();
                    state = LOWER_POSITION;
                    mTime = -1; // animation finished
                }
                break;
            }
            default:
                break;
        }

        if (mTime != -1) {
            long delta = (System.currentTimeMillis() - mTime);
            mAlpha = (int) (180 * (1 - (1 * mElapsedTime - delta) / (1 * mElapsedTime)));
            mMiddleTab.rotate(mAlpha);
        }

    }

    @Override
    protected void makeSureCycleIsClosed() {
        if (mTime == -1) {
            return;
        }
        if (state == LOWER_POSITION) {
            mMiddleTab.next();
            state = UPPER_POSITION;
        } else if (state == UPPER_POSITION) {
            mTopTab.next();
            state = LOWER_POSITION;
            mTime = -1; // animation finished
        }
        mMiddleTab.rotate(180);
    }
}
