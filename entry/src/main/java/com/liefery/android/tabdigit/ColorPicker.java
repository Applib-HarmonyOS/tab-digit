package com.liefery.android.tabdigit;

import ohos.agp.colors.RgbColor;
import ohos.agp.colors.RgbPalette;
import ohos.agp.components.AttrSet;
import ohos.agp.components.Component;
import ohos.agp.components.DirectionalLayout;
import ohos.agp.components.LayoutScatter;
import ohos.agp.components.Text;
import ohos.agp.components.TextField;
import ohos.agp.components.element.ShapeElement;
import ohos.app.Context;
import com.xenione.digit.AttrUtils;

/**
 * ColorPicker.
 */
public class ColorPicker extends DirectionalLayout {
    //#region constructor
    public ColorPicker(Context context) {
        super(context);
        init(context, null);
    }

    public ColorPicker(Context context, AttrSet attrSet) {
        super(context, attrSet);
        init(context, attrSet);
    }

    public ColorPicker(Context context, AttrSet attrSet, String styleName) {
        super(context, attrSet, styleName);
        init(context, attrSet);
    }

    //#endregion constructor
    //#region properties
    static class ColorPickerAttrs {
        private ColorPickerAttrs() {
        }

        static final String LABEL = "label";
        static final String VALUE = "value";
        static final String TEXT_SIZE = "text_size";
    }

    Component colorPreview;
    ColorChangeListener colorChangeListener;


    //#endregion properties
    private void init(Context context, AttrSet attrSet) {
        AttrUtils attrUtils = new AttrUtils(attrSet);
        final String label = attrUtils.getStringFromAttr(ColorPickerAttrs.LABEL, "");
        final String value = attrUtils.getStringFromAttr(ColorPickerAttrs.VALUE, "");
        final int textSize = attrUtils.getDimensionFromAttr(ColorPickerAttrs.TEXT_SIZE, 25);


        LayoutScatter.getInstance(context).parse(ResourceTable.Layout_color_picker, this, true);
        Text labelText = (Text) findComponentById(ResourceTable.Id_label_text);
        TextField valueTextField = (TextField) findComponentById(ResourceTable.Id_value);
        colorPreview = findComponentById(ResourceTable.Id_color_preview);
        labelText.setTextSize(textSize);
        valueTextField.setTextSize(textSize);


        labelText.setText(label);
        syncColor(value);

        valueTextField.setText(value);
        valueTextField.addTextObserver((text, start, before, count) -> syncColor(text));
    }


    public void setColorChangeListener(ColorChangeListener colorChangeListener) {
        this.colorChangeListener = colorChangeListener;
    }

    private void syncColor(String colorStr) {
        try {
            int color = RgbPalette.parse(colorStr);
            ShapeElement shapeElement = new ShapeElement();
            shapeElement.setRgbColor(RgbColor.fromArgbInt(color));
            colorPreview.setBackground(shapeElement);
            if (this.colorChangeListener != null) {
                this.colorChangeListener.onColorChange(color);
            }
        } catch (Exception ignore) {
            // ignore
        }
    }

    /**
     * ColorChangeListener.
     */
    public interface ColorChangeListener {
        /**
         * this method is called when the color value changes.
         *
         * @param color color
         */
        void onColorChange(int color);
    }
}
