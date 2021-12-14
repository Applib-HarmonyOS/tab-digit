package com.liefery.android.tabdigit;

import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;
import com.liefery.android.tabdigit.slice.CountdownAbilitySlice;

/**
 * CountdownAbility.
 */
public class CountdownAbility extends Ability {
    @Override
    protected void onStart(Intent intent) {
        super.onStart(intent);
        setMainRoute(CountdownAbilitySlice.class.getName());
    }
}
