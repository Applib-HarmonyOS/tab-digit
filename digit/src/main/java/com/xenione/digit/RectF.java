package com.xenione.digit;

import ohos.agp.utils.Rect;
import ohos.agp.utils.RectFloat;

/**
 * RectF.
 */
public class RectF {
    private float left;
    private float top;
    private float right;
    private float bottom;


    public float getLeft() {
        return left;
    }

    public float getRight() {
        return right;
    }

    public float getTop() {
        return top;
    }

    public float getBottom() {
        return bottom;
    }

    //#region constructor
    public RectF() {

    }

    /**
     * Create a new rectangle with the specified coordinates. Note: no range
     * checking is performed, so the caller must ensure that left <= right and
     * top <= bottom.
     *
     * @param left   The X coordinate of the left side of the rectangle
     * @param top    The Y coordinate of the top of the rectangle
     * @param right  The X coordinate of the right side of the rectangle
     * @param bottom The Y coordinate of the bottom of the rectangle
     */
    public RectF(float left, float top, float right, float bottom) {
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
    }

    /**
     * Create a new rectangle, initialized with the values in the specified
     * rectangle (which is left unmodified).
     *
     * @param r The rectangle whose coordinates are copied into the new
     *          rectangle.
     */
    public RectF(RectF r) {
        if (r == null) {
            left = top = right = bottom = 0.0f;
        } else {
            left = r.left;
            top = r.top;
            right = r.right;
            bottom = r.bottom;
        }
    }

    /**
     * create a RectF with a Rect.
     *
     * @param r Rect
     */
    public RectF(Rect r) {
        if (r == null) {
            left = top = right = bottom = 0.0f;
        } else {
            left = r.left;
            top = r.top;
            right = r.right;
            bottom = r.bottom;
        }
    }
    //#endregion constructor

    /**
     * Set the rectangle's coordinates to the specified values. Note: no range
     * checking is performed, so it is up to the caller to ensure that
     * left <= right and top <= bottom.
     *
     * @param left   The X coordinate of the left side of the rectangle
     * @param top    The Y coordinate of the top of the rectangle
     * @param right  The X coordinate of the right side of the rectangle
     * @param bottom The Y coordinate of the bottom of the rectangle
     */
    public void set(float left, float top, float right, float bottom) {
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
    }


    /**
     * Copy the coordinates from src into this rectangle.
     *
     * @param src The rectangle whose coordinates are copied into this
     *            rectangle.
     */
    public void set(RectF src) {
        this.left = src.left;
        this.top = src.top;
        this.right = src.right;
        this.bottom = src.bottom;
    }

    /**
     * Copy the coordinates from src into this rectangle.
     *
     * @param src The rectangle whose coordinates are copied into this
     *            rectangle.
     */
    public void set(Rect src) {
        this.left = src.left;
        this.top = src.top;
        this.right = src.right;
        this.bottom = src.bottom;
    }

    /**
     * Offset the rectangle by adding dx to its left and right coordinates, and
     * adding dy to its top and bottom coordinates.
     *
     * @param dx The amount to add to the rectangle's left and right coordinates
     * @param dy The amount to add to the rectangle's top and bottom coordinates
     */
    public void offset(float dx, float dy) {
        left += dx;
        top += dy;
        right += dx;
        bottom += dy;
    }

    /**
     * Get rectangle's height. This does not check for a valid rectangle
     * (i.e. top <= bottom) so the result may be negative.
     *
     * @return the rectangle's height.
     */
    public final float getHeight() {
        return bottom - top;
    }

    public RectFloat toRectFloat() {
        int mul = 1;
        return new RectFloat(left * mul, top * mul, right * mul, bottom * mul);
    }


    @Override
    public String toString() {
        return String.format("RectF { top:%.2f, left:%.2f, right:%.2f,bottom: %.2f }", top, left, right, bottom);
    }

    /**
     * Get rectangle width. This does not check for a valid rectangle
     * (i.e. left <= right) so the result may be negative.
     *
     * @return the rectangle's width.
     */
    public final float getWidth() {
        return right - left;
    }

}
