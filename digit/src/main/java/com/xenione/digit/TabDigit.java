package com.xenione.digit;

import ohos.agp.animation.Animator;
import ohos.agp.animation.AnimatorValue;
import ohos.agp.components.AttrSet;
import ohos.agp.components.Component;
import ohos.agp.render.Canvas;
import ohos.agp.render.Paint;
import ohos.agp.utils.Color;
import ohos.agp.utils.Matrix;
import ohos.agp.utils.Rect;
import ohos.app.Context;
import ohos.eventhandler.EventHandler;
import ohos.eventhandler.EventRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Eugeni on 16/10/2016.
 */
public class TabDigit extends Component implements Runnable, Component.DrawTask,
        Component.EstimateSizeListener, Component.LayoutRefreshedListener {

    /*
     * false: rotate upwards
     * true: rotate downwards
     */
    private boolean mReverseRotation = false;

    private Tab mMiddleTab;

    //#region internal variables
    private final List<Tab> tabs = new ArrayList<>(3);

    private AbstractTabAnimation tabAnimation;

    private final Matrix mProjectionMatrix = new Matrix();

    private int mCornerSize;

    private Paint mNumberPaint;

    private Paint mDividerPaint;

    private Paint mBackgroundPaint;

    private final Rect mTextMeasured = new Rect();

    private int mPadding = 0;

    private char[] mChars = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
    private EventHandler eventHandler;

    private static final class TabDigitAttrs {
        static final String TEXT_SIZE = "textSize";
        static final String PADDING = "padding";
        static final String CORNER_SIZE_Z = "cornerSizeZ";
        static final String TEXT_COLOR = "textColor";
        static final String BACKGROUND_COLOR = "backgroundColor";
        static final String REVERSE_ROTATION = "reverseRotation";
    }

    //#endregion internal variables


    //#region constructor

    public TabDigit(Context context) {
        super(context);
        init(null);
    }

    public TabDigit(Context context, AttrSet attrSet) {
        super(context, attrSet);
        init(attrSet);
    }

    public TabDigit(Context context, AttrSet attrSet, String styleName) {
        super(context, attrSet, styleName);
        init(attrSet);
    }

    public TabDigit(Context context, AttrSet attrSet, int resId) {
        super(context, attrSet, resId);
        init(attrSet);
    }

    //#endregion constructor

    //#region initialize

    /**
     * initialize the TabDigit.
     *
     * @param attrs attrs
     */
    private void init(AttrSet attrs) {
        addDrawTask(this);
        setEstimateSizeListener(this);
        setLayoutRefreshedListener(this);
        eventHandler = new EventHandler(EventRunner.getMainEventRunner());
        initPaints();

        AttrUtils attrUtils = new AttrUtils(attrs);

        final int textSize = attrUtils.getDimensionFromAttr(TabDigitAttrs.TEXT_SIZE, -1);
        final int padding = attrUtils.getDimensionFromAttr(TabDigitAttrs.PADDING, -1);
        final int cornerSize = attrUtils.getDimensionFromAttr(TabDigitAttrs.CORNER_SIZE_Z, -1);
        final Color textColor = attrUtils.getColorFromAttr(TabDigitAttrs.TEXT_COLOR, null);
        final Color backgroundColor = attrUtils.getColorFromAttr(TabDigitAttrs.BACKGROUND_COLOR, null);
        final boolean reverseRotation = attrUtils.getBooleanFromAttr(TabDigitAttrs.REVERSE_ROTATION, false);

        if (padding > 0) {
            mPadding = padding;
        }

        if (textSize > 0) {
            mNumberPaint.setTextSize(textSize);
        }

        if (cornerSize > 0) {
            mCornerSize = cornerSize;
        }

        if (textColor != null) {
            mNumberPaint.setColor(textColor);
        }
        if (backgroundColor != null) {
            mBackgroundPaint.setColor(backgroundColor);
        }

        mReverseRotation = reverseRotation;

        initTabs();

    }


    private void initPaints() {
        mNumberPaint = new Paint();
        mNumberPaint.setAntiAlias(true);
        mNumberPaint.setStyle(Paint.Style.FILLANDSTROKE_STYLE);
        mNumberPaint.setColor(Color.WHITE);

        mDividerPaint = new Paint();
        mDividerPaint.setAntiAlias(true);
        mDividerPaint.setStyle(Paint.Style.FILLANDSTROKE_STYLE);
        mDividerPaint.setColor(Color.WHITE);
        mDividerPaint.setStrokeWidth(1);

        mBackgroundPaint = new Paint();
        mBackgroundPaint.setAntiAlias(true);
        mBackgroundPaint.setColor(Color.BLACK);

    }

    private void initTabs() {
        animatorValue.setDelay(40);
        animatorValue.setDuration(1000);
        animatorValue.setLoopedCount(Animator.INFINITE);
        // top Tab
        Tab topTab = new Tab();
        topTab.rotate(180);
        tabs.add(topTab);

        // bottom Tab
        Tab bottomTab = new Tab();
        tabs.add(bottomTab);

        // middle Tab
        mMiddleTab = new Tab();
        tabs.add(mMiddleTab);

        tabAnimation = mReverseRotation
                ? new TabAnimationDown(topTab, bottomTab, mMiddleTab)
                : new TabAnimationUp(topTab, bottomTab, mMiddleTab);

        tabAnimation.initMiddleTab();

        setInternalChar(0);
    }
    //#endregion initialize

    //#region some calculation not important

    public void setChar(int index) {
        setInternalChar(index);
        invalidate();
    }

    private void setInternalChar(int index) {
        for (Tab tab : tabs) {
            tab.setChar(index);
        }
    }


    private void setupProjectionMatrix() {
        mProjectionMatrix.reset();
        int centerY = getHeight() / 2;
        int centerX = getWidth() / 2;
        MatrixHelper.translate(mProjectionMatrix, centerX, centerY, 0);
    }

    private void measureTabs(int width, int height) {
        for (Tab tab : tabs) {
            tab.measure(width, height);
        }
    }

    private void drawTabs(Canvas canvas) {
        for (Tab tab : tabs) {
            tab.draw(canvas);
        }
    }

    private void drawDivider(Canvas canvas) {
        canvas.save();
        canvas.concat(mProjectionMatrix);
        canvas.drawLine(
                (float) (-canvas.getLocalClipBounds().getWidth() / 2),
                0,
                (float) (canvas.getLocalClipBounds().getWidth() / 2),
                0,
                mDividerPaint
        );
        canvas.restore();
    }

    private void calculateTextSize(Rect rect) {
        Rect rect1 = mNumberPaint.getTextBounds("8");
        rect.modify(rect1);
    }
    //#endregion some calculation not important


    //#region getter & setter

    /**
     * set text size.
     *
     * @param size size of the text in pixel
     */
    public void setTextSize(int size) {
        mNumberPaint.setTextSize(size);
        invalidate();
        postLayout();
    }

    public int getTextSize() {
        return mNumberPaint.getTextSize();
    }

    /**
     * sets padding for the tab digit.
     *
     * @param padding padding in pixel
     */
    public void setPadding(int padding) {
        mPadding = padding;
        invalidate();
        postLayout();
    }

    /**
     * Sets chars that are going to be displayed.
     * Note: <b>That only one digit is allow per character.</b>
     *
     * @param chars chars
     */
    public void setChars(char[] chars) {
        mChars = chars;
    }

    public char[] getChars() {
        return mChars;
    }


    public void setDividerColor(Color color) {
        mDividerPaint.setColor(color);
    }

    public int getTabDigitPadding() {
        return mPadding;
    }

    public void setTextColor(Color color) {
        mNumberPaint.setColor(color);
    }

    public Color getTextColor() {
        return mNumberPaint.getColor();
    }

    public void setCornerSize(int cornerSize) {
        mCornerSize = cornerSize;
        invalidate();
    }

    public int getCornerSize() {
        return mCornerSize;
    }

    public void setBackgroundColor(Color color) {
        mBackgroundPaint.setColor(color);
    }

    public Color getBackgroundColor() {
        return mBackgroundPaint.getColor();
    }
    //#endregion getter & setter

    /**
     * start the animation.
     */
    public void start() {
        internalCounter = 0;
        tabAnimation.start();
        invalidate();
    }

    int internalCounter = 0;

    @Override
    public void run() {
        tabAnimation.run();
        invalidate();
    }

    public void sync() {
        tabAnimation.sync();
        invalidate();
    }


    /**
     * Measure dimension from estimate dimension.
     *
     * @param defaultSize Default width/height.
     * @param measureSpec estimate width/height.
     * @return Measured dimension.
     */
    private int measureDimension(int defaultSize, int measureSpec) {
        int result;
        int specMode = EstimateSpec.getMode(measureSpec);
        int specSize = EstimateSpec.getSize(measureSpec);
        if (specMode == EstimateSpec.PRECISE) {
            result = specSize;
        } else {
            result = defaultSize; // UNSPECIFIED
            if (specMode == EstimateSpec.NOT_EXCEED) {
                result = Math.min(result, specSize);
            }
        }
        return result;
    }

    //#region listeners

    int oldWidth = -1;
    int oldHeight = -1;

    @Override
    public void onRefreshed(Component component) {
        if (oldWidth != component.getWidth() || oldHeight != component.getHeight()) {
            setupProjectionMatrix();
        }
        oldWidth = component.getWidth();
        oldHeight = component.getHeight();
    }


    @Override
    public boolean onEstimateSize(int widthMeasureSpec, int heightMeasureSpec) {
        calculateTextSize(mTextMeasured);

        int childWidth = mTextMeasured.getWidth() + mPadding;
        int childHeight = mTextMeasured.getHeight() + mPadding;
        measureTabs(childWidth, childHeight);

        int maxChildWidth = mMiddleTab.maxWith();
        int maxChildHeight = 2 * mMiddleTab.maxHeight();


        int width = measureDimension(maxChildWidth, widthMeasureSpec);
        int height = measureDimension(maxChildHeight, heightMeasureSpec);


        //Do Size Estimation here and don't forgot to call setEstimatedSize(width, height)
        setEstimatedSize(EstimateSpec.getSizeWithMode(width, EstimateSpec.PRECISE),
                EstimateSpec.getSizeWithMode(height, EstimateSpec.PRECISE));

        return true;
    }


    AnimatorValue animatorValue = new AnimatorValue();

    @Override
    public void onDraw(Component component, Canvas canvas) {
        internalCounter++;
        drawTabs(canvas);
        drawDivider(canvas);
        eventHandler.postTask(this, 100);

    }
    //#endregion listeners

    /**
     * tab class.
     */
    public class Tab {

        //#region internal variable

        private final Matrix mModelViewMatrix = new Matrix();

        private final Matrix mModelViewProjectionMatrix = new Matrix();

        private final Matrix mRotationModelViewMatrix = new Matrix();

        private final RectF mStartBounds = new RectF();

        private final RectF mEndBounds = new RectF();

        private int mCurrIndex = 0;

        private int mAlpha;

        private final Matrix mMeasuredMatrixHeight = new Matrix();

        private final Matrix mMeasuredMatrixWidth = new Matrix();
        //#endregion internal variable

        //#region calculation

        /**
         * measure the height and width.
         *
         * @param width  width
         * @param height height
         */
        public void measure(int width, int height) {
            Rect area = new Rect(-width / 2, 0, width / 2, height / 2);
            mStartBounds.set(area);
            mEndBounds.set(area);
            mEndBounds.offset(0, -((float) height) / 2);
        }

        /**
         * get the max width of the tab.
         *
         * @return max width of the tab
         */
        public int maxWith() {
            final RectF rect = new RectF(mStartBounds);
            Matrix projectionMatrix = new Matrix();
            MatrixHelper.translate(projectionMatrix, mStartBounds.getLeft(), -mStartBounds.getTop(), 0);
            mMeasuredMatrixWidth.reset();
            mMeasuredMatrixWidth.setConcat(projectionMatrix, MatrixHelper.ROTATE_X_90);
            mMeasuredMatrixWidth.mapRect(rect.toRectFloat());
            return (int) rect.getWidth();
        }

        /**
         * Get the max height of the tab.
         *
         * @return max height of the tab
         */
        public int maxHeight() {
            RectF rect = new RectF(mStartBounds);
            Matrix projectionMatrix = new Matrix();
            mMeasuredMatrixHeight.reset();
            mMeasuredMatrixHeight.setConcat(projectionMatrix, MatrixHelper.ROTATE_X_0);
            mMeasuredMatrixHeight.mapRect(rect.toRectFloat());
            return (int) rect.getHeight();
        }
        //#endregion calculation

        public void setChar(int index) {
            mCurrIndex = index > mChars.length ? 0 : index;
        }

        /**
         * go to next digit.
         */
        public void next() {
            mCurrIndex++;
            if (mCurrIndex >= mChars.length) {
                mCurrIndex = 0;
            }
        }

        public void rotate(int alpha) {
            mAlpha = alpha;
            MatrixHelper.rotateX(mRotationModelViewMatrix, alpha);
        }

        public void draw(Canvas canvas) {
            drawBackground(canvas);
            drawText(canvas);
        }

        //#region draw

        private void drawBackground(Canvas canvas) {
            canvas.save();
            mModelViewMatrix.setMatrix(mRotationModelViewMatrix);
            applyTransformation(canvas, mModelViewMatrix);
            canvas.drawRoundRect(mStartBounds.toRectFloat(), mCornerSize, mCornerSize, mBackgroundPaint);
            canvas.restore();
        }

        private void drawText(Canvas canvas) {
            canvas.save();
            mModelViewMatrix.setMatrix(mRotationModelViewMatrix);
            RectF clip = mStartBounds;
            if (mAlpha > 90) {
                mModelViewMatrix.setConcat(mModelViewMatrix, MatrixHelper.MIRROR_X);
                clip = mEndBounds;
            }

            applyTransformation(canvas, mModelViewMatrix);
            canvas.clipRect(clip.toRectFloat());
            canvas.drawText(
                    mNumberPaint,
                    Character.toString(mChars[mCurrIndex]),
                    -mTextMeasured.getCenterX(),
                    -mTextMeasured.getCenterY()
            );
            canvas.restore();
        }
        //#endregion draw

        private void applyTransformation(Canvas canvas, Matrix matrix) {
            mModelViewProjectionMatrix.reset();
            mModelViewProjectionMatrix.setConcat(mProjectionMatrix, matrix);
            canvas.concat(mModelViewProjectionMatrix);
        }
    }

}
