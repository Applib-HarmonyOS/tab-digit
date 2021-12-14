package com.liefery.android.tabdigit;


import ohos.agp.components.AttrSet;
import ohos.agp.components.DirectionalLayout;
import ohos.agp.components.LayoutScatter;
import ohos.app.Context;
import ohos.eventhandler.EventHandler;
import ohos.eventhandler.EventRunner;
import com.xenione.digit.TabDigit;

/**
 * Created by Eugeni on 04/12/2016.
 */
public class CountdownView extends DirectionalLayout implements Runnable {

    private static final char[] SEXAGISIMAL = new char[]{'5', '4', '3', '2', '1', '0'};

    private static final char[] DECIMAL = new char[]{'9', '8', '7', '6', '5', '4', '3', '2', '1', '0'};

    private TabDigit mCharHighSecond;
    private TabDigit mCharLowSecond;
    private TabDigit mCharHighMinute;
    private TabDigit mCharLowMinute;
    private TabDigit mCharHighHour;
    private TabDigit mCharLowHour;
    EventHandler eventHandler;

    private boolean mPause = true;

    private final long startedTime = System.currentTimeMillis();

    private long totalTime = 10L * 60L * 60L; // 10 hours count down

    private long elapsedTime = 0;

    //#region constructor

    public CountdownView(Context context) {
        super(context);
        init();
    }

    public CountdownView(Context context, AttrSet attrSet) {
        super(context, attrSet);
        init();
    }

    public CountdownView(Context context, AttrSet attrSet, String styleName) {
        super(context, attrSet, styleName);
        init();
    }
    //#endregion constructor

    /**
     * initialize the countdown view.
     */
    public void init() {
        eventHandler = new EventHandler(EventRunner.getMainEventRunner());
        setOrientation(HORIZONTAL);
        LayoutScatter.getInstance(getContext()).parse(ResourceTable.Layout_clock, this, true);
        mCharHighMinute = (TabDigit) findComponentById(ResourceTable.Id_charHighMinute);
        mCharLowMinute = (TabDigit) findComponentById(ResourceTable.Id_charLowMinute);
        mCharHighSecond = (TabDigit) findComponentById(ResourceTable.Id_charHighSecond);
        mCharLowSecond = (TabDigit) findComponentById(ResourceTable.Id_charLowSecond);
        mCharHighHour = (TabDigit) findComponentById(ResourceTable.Id_charHighHour);
        mCharLowHour = (TabDigit) findComponentById(ResourceTable.Id_charLowHour);


        mCharHighMinute.setTextSize(100);
        mCharHighMinute.setChars(SEXAGISIMAL);
        mCharLowMinute.setTextSize(100);
        mCharLowMinute.setChars(DECIMAL);

        mCharHighSecond.setTextSize(100);
        mCharHighSecond.setChars(SEXAGISIMAL);
        mCharLowSecond.setTextSize(100);
        mCharLowSecond.setChars(DECIMAL);

        mCharHighHour.setTextSize(100);
        mCharHighHour.setChars(DECIMAL);
        mCharLowHour.setTextSize(100);
        mCharLowHour.setChars(DECIMAL);

    }


    /**
     * pause all animation.
     */
    public void pause() {
        mPause = true;
        mCharHighHour.sync();
        mCharLowHour.sync();
        mCharHighMinute.sync();
        mCharLowMinute.sync();
        mCharHighSecond.sync();
        mCharLowSecond.sync();
    }

    /**
     * resume all animation.
     */
    public void resume() {
        mPause = false;

        long now = System.currentTimeMillis();
        elapsedTime = (now - startedTime) / 1000;
        totalTime -= elapsedTime;

        long time = totalTime;

        int hourHeight = (int) (time / 36000);
        mCharHighHour.setChar(9 - hourHeight);

        time -= hourHeight * 36000;

        int hourLow = (int) (time / 3600);
        mCharLowHour.setChar(9 - hourLow);

        time -= hourLow * 3600;

        int minuteHeight = (int) (time / 600);
        mCharHighMinute.setChar(5 - minuteHeight);

        time -= minuteHeight * 600;

        int minuteLow = (int) (time / 60);
        mCharLowMinute.setChar(9 - minuteLow);

        time -= minuteLow * 60;

        int secHeight = (int) (time / 10);
        mCharHighSecond.setChar(5 - secHeight);

        time -= secHeight * 10;

        int secLow = (int) time;
        mCharLowSecond.setChar(9 - secLow);

        elapsedTime = 0;
        eventHandler.postTask(this, 1000);
    }

    @Override
    public void run() {
        if (mPause) {
            return;
        }
        mCharLowSecond.start();
        if (elapsedTime % 36000 == 0) {
            mCharHighHour.start();
        }
        if (elapsedTime % 3600 == 0) {
            mCharLowHour.start();
        }
        if (elapsedTime % 600 == 0) {
            mCharHighMinute.start();
        }
        if (elapsedTime % 60 == 0) {
            mCharLowMinute.start();
        }
        if (elapsedTime % 10 == 0) {
            mCharHighSecond.start();
        }
        elapsedTime += 1;
        eventHandler.postTask(this, 1000);
    }
}