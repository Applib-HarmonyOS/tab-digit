package com.liefery.android.tabdigit.slice;

import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import com.liefery.android.tabdigit.ClockView;
import com.liefery.android.tabdigit.ResourceTable;

/**
 * ClockAbilitySlice.
 */
public class ClockAbilitySlice extends AbilitySlice {
    ClockView clockView;

    @Override
    protected void onStart(Intent intent) {
        super.onStart(intent);
        setUIContent(ResourceTable.Layout_ability_clock);
        clockView = (ClockView) findComponentById(ResourceTable.Id_ClockView);
    }

    @Override
    protected void onActive() {
        super.onActive();
        clockView.resume();
    }

    @Override
    protected void onInactive() {
        super.onInactive();
        clockView.pause();
    }
}
