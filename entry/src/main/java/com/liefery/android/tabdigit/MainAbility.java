package com.liefery.android.tabdigit;

import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;
import ohos.eventhandler.EventHandler;
import ohos.eventhandler.EventRunner;
import com.xenione.digit.TabDigit;


/**
 * MainAbility.
 */
public class MainAbility extends Ability implements Runnable {
    TabDigit tabDigit1;
    EventHandler eventHandler;

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_main);

        tabDigit1 = (TabDigit) findComponentById(ResourceTable.Id_charView1);
        assert tabDigit1 != null;
        eventHandler = new EventHandler(EventRunner.getMainEventRunner());
        eventHandler.postTask(this, 1000);

    }

    @Override
    public void run() {
        tabDigit1.start();
        eventHandler.postTask(this, 1000);
    }
}
