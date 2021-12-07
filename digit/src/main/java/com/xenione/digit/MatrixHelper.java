package com.xenione.digit;


import ohos.agp.render.Canvas;
import ohos.agp.render.ThreeDimView;
import ohos.agp.utils.Matrix;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;

/**
 * Created by Eugeni on 16/10/2016.
 */
public class MatrixHelper {
    private MatrixHelper() {
    }

    public static final Canvas canvas = new Canvas();
    public static final ThreeDimView threeDimView = new ThreeDimView();

    /**
     * Matrix with 180 degrees x rotation defined.
     */
    public static final Matrix MIRROR_X = new Matrix();

    static {
        MatrixHelper.rotateX(MIRROR_X, 180);
    }

    /**
     * Matrix with 0 degrees x rotation defined.
     */
    public static final Matrix ROTATE_X_0 = new Matrix();

    static {
        MatrixHelper.rotateX(ROTATE_X_0, 0);
    }

    /**
     * Matrix with 90 degrees x rotation defined.
     */
    public static final Matrix ROTATE_X_90 = new Matrix();

    static {
        MatrixHelper.rotateX(ROTATE_X_90, 90);
    }

    public static void mirrorX(Matrix matrix) {
        rotateX(matrix, 180);
    }

    private static final HiLogLabel LABEL_LOG = new HiLogLabel(HiLog.LOG_APP, 0x00201, "-MainAbility-");

    /**
     * rotate the matrix in the x axis.
     *
     * @param matrix matrix to rotate
     * @param alpha  alpha value
     */
    public static void rotateX(Matrix matrix, int alpha) {
        try {
            synchronized (canvas) {
                canvas.save();
                MatrixPrecomputed.getMatrix(matrix, alpha);
                canvas.restore();
            }
        } catch (Exception ex) {
            HiLog.warn(LABEL_LOG, "MainAbilitySlice: ex " + ex);
            for (StackTraceElement st : ex.getStackTrace()) {
                HiLog.warn(LABEL_LOG, "" + st);

            }
        }
    }

    /**
     * rotate the matrix in the z axis.
     *
     * @param matrix matrix to rotate
     * @param alpha  alpha value
     */
    public static void rotateZ(Matrix matrix, int alpha) {
        synchronized (canvas) {
            canvas.save();
            threeDimView.rotateZ(alpha);
            threeDimView.applyToCanvas(canvas);
            canvas.getMatrix(matrix);
            canvas.restore();
        }
    }

    /**
     * translate the matrix.
     *
     * @param matrix matrix
     * @param dx     dx
     * @param dy     dy
     * @param dz     dz
     */
    public static void translate(Matrix matrix, float dx, float dy, float dz) {
        synchronized (canvas) {
            canvas.save();
            canvas.translate(dx, dy);
            threeDimView.rotateZ(dz);

            Matrix matrix1 = new Matrix();
            threeDimView.getMatrix(matrix1);
            canvas.getMatrix(matrix);
            matrix1.postConcat(matrix);

            canvas.restore();
        }
    }

    /**
     * translate the matrix in Y axis.
     *
     * @param matrix matrix
     * @param dy     dy
     */
    public static void translateY(Matrix matrix, float dy) {
        synchronized (canvas) {
            canvas.save();
            canvas.translate(0, dy);
            canvas.getMatrix(matrix);
            canvas.restore();
        }
    }
}
