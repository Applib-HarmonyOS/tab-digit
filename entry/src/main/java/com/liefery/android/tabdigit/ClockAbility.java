package com.liefery.android.tabdigit;

import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;
import com.liefery.android.tabdigit.slice.ClockAbilitySlice;

/**
 * ClockAbility.
 */
public class ClockAbility extends Ability {
    @Override
    protected void onStart(Intent intent) {
        super.onStart(intent);
        setMainRoute(ClockAbilitySlice.class.getName());
    }

}
