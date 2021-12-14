package com.liefery.android.tabdigit;

import ohos.agp.components.AttrSet;
import ohos.agp.components.DirectionalLayout;
import ohos.agp.components.LayoutScatter;
import ohos.app.Context;
import ohos.eventhandler.EventHandler;
import ohos.eventhandler.EventRunner;
import com.xenione.digit.TabDigit;
import java.util.Calendar;

/**
 * Created by Eugeni on 04/12/2016.
 */
public class ClockView extends DirectionalLayout implements Runnable {

    private static final char[] HOURS = new char[]{'0', '1', '2'};

    private static final char[] SEXAGISIMAL = new char[]{'0', '1', '2', '3', '4', '5'};

    private TabDigit mCharHighSecond;
    private TabDigit mCharLowSecond;
    private TabDigit mCharHighMinute;
    private TabDigit mCharLowMinute;
    private TabDigit mCharHighHour;
    private TabDigit mCharLowHour;
    EventHandler eventHandler;
    private boolean mPause = true;

    private long elapsedTime = 0;

    //#region constructor

    public ClockView(Context context) {
        super(context);
        init();
    }

    public ClockView(Context context, AttrSet attrSet) {
        super(context, attrSet);
        init();
    }

    public ClockView(Context context, AttrSet attrSet, String styleName) {
        super(context, attrSet, styleName);
        init();
    }

    //#endregion constructor

    /**
     * initialize the ClockView.
     */
    public void init() {
        eventHandler = new EventHandler(EventRunner.getMainEventRunner());
        setOrientation(HORIZONTAL);

        LayoutScatter.getInstance(getContext()).parse(ResourceTable.Layout_clock, this, true);

        mCharHighSecond = (TabDigit) findComponentById(ResourceTable.Id_charHighSecond);
        mCharLowSecond = (TabDigit) findComponentById(ResourceTable.Id_charLowSecond);
        mCharHighMinute = (TabDigit) findComponentById(ResourceTable.Id_charHighMinute);
        mCharLowMinute = (TabDigit) findComponentById(ResourceTable.Id_charLowMinute);
        mCharHighHour = (TabDigit) findComponentById(ResourceTable.Id_charHighHour);
        mCharLowHour = (TabDigit) findComponentById(ResourceTable.Id_charLowHour);

        mCharHighSecond.setTextSize(100);
        mCharHighSecond.setChars(SEXAGISIMAL);
        mCharLowSecond.setTextSize(100);

        mCharHighMinute.setTextSize(100);
        mCharHighMinute.setChars(SEXAGISIMAL);
        mCharLowMinute.setTextSize(100);

        mCharHighHour.setTextSize(100);
        mCharHighHour.setChars(HOURS);
        mCharLowHour.setTextSize(100);

    }


    /**
     * pause all animation.
     */
    public void pause() {
        mPause = true;
        mCharHighSecond.sync();
        mCharLowSecond.sync();
        mCharHighMinute.sync();
        mCharLowMinute.sync();
        mCharHighHour.sync();
        mCharLowHour.sync();
    }

    /**
     * resume all animation.
     */
    public void resume() {
        mPause = false;
        Calendar time = Calendar.getInstance();
        /* hours*/
        int hour = time.get(Calendar.HOUR_OF_DAY);
        int highHour = hour / 10;
        mCharHighHour.setChar(highHour);

        int lowHour = (hour - highHour * 10);
        mCharLowHour.setChar(lowHour);

        /* minutes*/
        int minutes = time.get(Calendar.MINUTE);
        int highMinute = minutes / 10;
        mCharHighMinute.setChar(highMinute);

        int lowMinute = (minutes - highMinute * 10);
        mCharLowMinute.setChar(lowMinute);

        /* seconds*/
        int seconds = time.get(Calendar.SECOND);
        int highSecond = seconds / 10;
        mCharHighSecond.setChar(highSecond);

        int lowSecond = (seconds - highSecond * 10);
        mCharLowSecond.setChar(lowSecond);

        elapsedTime = lowSecond + (long) highSecond * 10
                + (long) lowMinute * 60 + (long) highMinute * 600
                + (long) lowHour * 3600 + highHour * 36000L;

        eventHandler.postTask(this, 1000);
    }

    @Override
    public void run() {
        if (mPause) {
            return;
        }
        elapsedTime += 1;
        mCharLowSecond.start();
        if (elapsedTime % 10 == 0) {
            mCharHighSecond.start();
        }
        if (elapsedTime % 60 == 0) {
            mCharLowMinute.start();
        }
        if (elapsedTime % 600 == 0) {
            mCharHighMinute.start();
        }
        if (elapsedTime % 3600 == 0) {
            mCharLowHour.start();
        }
        if (elapsedTime % 36000 == 0) {
            mCharHighHour.start();
        }
        eventHandler.postTask(this, 1000);
    }
}
