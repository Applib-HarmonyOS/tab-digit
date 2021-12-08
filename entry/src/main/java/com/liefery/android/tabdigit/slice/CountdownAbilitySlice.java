package com.liefery.android.tabdigit.slice;

import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import com.liefery.android.tabdigit.CountdownView;
import com.liefery.android.tabdigit.ResourceTable;

/**
 * CountdownAbilitySlice.
 */
public class CountdownAbilitySlice extends AbilitySlice {
    CountdownView countdownView;

    @Override
    protected void onStart(Intent intent) {
        super.onStart(intent);
        setUIContent(ResourceTable.Layout_ability_countdown);
        countdownView = (CountdownView) findComponentById(ResourceTable.Id_countDownView);
    }

    @Override
    protected void onInactive() {
        super.onInactive();
        countdownView.pause();
    }

    @Override
    protected void onActive() {
        super.onActive();
        countdownView.resume();
    }
}
