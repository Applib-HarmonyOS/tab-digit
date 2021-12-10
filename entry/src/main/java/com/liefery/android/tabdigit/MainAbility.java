package com.liefery.android.tabdigit;

import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;
import ohos.aafwk.content.Operation;
import ohos.agp.components.Slider;
import ohos.agp.utils.Color;
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
        tabDigit1 = (TabDigit) findComponentById(ResourceTable.Id_tabDigit1);
        assert tabDigit1 != null;
        eventHandler = new EventHandler(EventRunner.getMainEventRunner());
        eventHandler.postTask(this, 1000);

        Slider textSizeBar = (Slider) findComponentById(ResourceTable.Id_size_bar);
        assert textSizeBar != null;
        textSizeBar.setProgressValue(tabDigit1.getTextSize());
        textSizeBar.setValueChangedListener(textSizeValueChangeListener);

        Slider paddingSizeBar = (Slider) findComponentById(ResourceTable.Id_padding_size_bar);
        assert textSizeBar != null;
        paddingSizeBar.setProgressValue(tabDigit1.getTabDigitPadding());
        paddingSizeBar.setValueChangedListener(paddingValueChangeListener);

        ColorPicker textColor = (ColorPicker) findComponentById(ResourceTable.Id_textColor);
        assert textColor != null;
        textColor.setColorChangeListener(color -> tabDigit1.setTextColor(new Color(color)));

        ColorPicker backgroundColor = (ColorPicker) findComponentById(ResourceTable.Id_backgroundColor);
        assert backgroundColor != null;
        backgroundColor.setColorChangeListener(color -> tabDigit1.setBackgroundColor(new Color(color)));

        findComponentById(ResourceTable.Id_btnClock).setClickedListener(c -> {
            Intent navIntent = new Intent();
            Operation operation = new Intent.OperationBuilder().withDeviceId("")
                    .withBundleName(getBundleName())
                    .withAbilityName(ClockAbility.class.getName())
                    .build();
            navIntent.setOperation(operation);
            startAbilityForResult(navIntent, 1);
        });

        findComponentById(ResourceTable.Id_btnCountDown).setClickedListener(c -> {
            Intent navIntent = new Intent();
            Operation operation = new Intent.OperationBuilder().withDeviceId("")
                    .withBundleName(getBundleName())
                    .withAbilityName(CountdownAbility.class.getName())
                    .build();
            navIntent.setOperation(operation);
            startAbilityForResult(navIntent, 1);
        });


    }

    private final Slider.ValueChangedListener textSizeValueChangeListener = new Slider.ValueChangedListener() {
        @Override
        public void onProgressUpdated(Slider slider, int progress, boolean fromUser) {
            if (fromUser) {
                tabDigit1.setTextSize(progress);
            }
        }

        @Override
        public void onTouchStart(Slider slider) {
            // no need
        }

        @Override
        public void onTouchEnd(Slider slider) {
            // no need
        }
    };


    private final Slider.ValueChangedListener paddingValueChangeListener = new Slider.ValueChangedListener() {
        @Override
        public void onProgressUpdated(Slider slider, int progress, boolean fromUser) {
            if (fromUser) {
                tabDigit1.setPadding(progress);
            }
        }

        @Override
        public void onTouchStart(Slider slider) {
            // no need
        }

        @Override
        public void onTouchEnd(Slider slider) {
            // no need
        }
    };


    @Override
    public void run() {
        tabDigit1.start();
        eventHandler.postTask(this, 1000);
    }
}
